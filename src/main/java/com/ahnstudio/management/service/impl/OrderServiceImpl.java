package com.ahnstudio.management.service.impl;

import com.ahnstudio.management.common.Const;
import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.dao.OrderItemMapper;
import com.ahnstudio.management.dao.OrderMapper;
import com.ahnstudio.management.dao.ProductMapper;
import com.ahnstudio.management.dao.ShippingMapper;
import com.ahnstudio.management.pojo.*;
import com.ahnstudio.management.service.OrderService;
import com.ahnstudio.management.util.BigDecimalUtil;
import com.ahnstudio.management.util.DateUtil;
import com.ahnstudio.management.util.PropertiesUtil;
import com.ahnstudio.management.vo.*;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse createOrder(CartVO cartVO) {
        log.info("1::" + cartVO.toString());
        // 1. 주문 아이템 생성
        List<OrderItem> orderItemList = this.getOrderItemByCartParameter(cartVO);
        for (OrderItem item : orderItemList) {
            log.info("2:::" + item.getProductId() + "--" + item.getSpecId());
        }
        // 2. 주문 총가격 계산
        BigDecimal totalPrice = this.getOrderTotalPrice(orderItemList);
        log.info("3:::" + totalPrice);
        // 3. 주문번호 생성
        Order order = this.assembleOrder(cartVO.getUserId(), cartVO.getShippingId(), totalPrice);
        if(order == null){
            return ServerResponse.createByErrorMessage("生成订单错误");
        }
        if(CollectionUtils.isEmpty(orderItemList)){
            return ServerResponse.createByErrorMessage("购物车为空");
        }
        for(OrderItem orderItem : orderItemList){
            orderItem.setOrderId(order.getOrderId());
        }

        // 4. 주문아이템 데이타 저장
        orderItemMapper.batchInsert(orderItemList);

        // 5. 재고 감소
        this.reduceProductStock(orderItemList);

        // 6. 주문VO 반환

        OrderVO orderVo = assembleOrderVo(order,orderItemList);

        return ServerResponse.createBySuccess(orderVo);
    }

    private OrderVO assembleOrderVo(Order order, List<OrderItem> orderItemList) {
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderNo(order.getOrderId());
        orderVO.setPayment(order.getPayment());
        orderVO.setPaymentType(order.getPaymentType());
        orderVO.setPostage(order.getPostage());
        orderVO.setStatus(order.getStatus());
        orderVO.setStatusDesc(Const.OrderStatusEnum.codeOf(order.getStatus()).getValue());

        orderVO.setShippingId(order.getShippingId());
        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        if(shipping != null){
            orderVO.setReceiverName(shipping.getReceiverName());
            orderVO.setShippingVo(assembleShippingVo(shipping));
        }

        orderVO.setPaymentTime(DateUtil.dateToStr(order.getPaymentTime()));
        orderVO.setSendTime(DateUtil.dateToStr(order.getSendTime()));
        orderVO.setEndTime(DateUtil.dateToStr(order.getEndTime()));
        orderVO.setCreateTime(DateUtil.dateToStr(order.getCreatetime()));
        orderVO.setCloseTime(DateUtil.dateToStr(order.getCloseTime()));

        List<OrderItemVO> orderItemVoList = Lists.newArrayList();

        for(OrderItem orderItem : orderItemList){
            OrderItemVO orderItemVo = assembleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        orderVO.setOrderItemVoList(orderItemVoList);
        return orderVO;
    }

    private void reduceProductStock(List<OrderItem> orderItemList){
        for(OrderItem orderItem : orderItemList){
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            product.setStock(product.getStock()-orderItem.getQty());
            productMapper.updateByPrimaryKeySelective(product);
        }
    }

    /**
     * Order Object Create
     * @param userId
     * @param shippingId
     * @param totalPrice
     * @return
     */
    private Order assembleOrder(Integer userId, Integer shippingId, BigDecimal totalPrice) {
        Order order = new Order();
        long orderNo = this.generateOrderNo();
        order.setOrderId(orderNo);
        order.setStatus(Const.OrderStatusEnum.NO_PAY.getCode());
        order.setPostage(0);
        order.setPaymentType(Const.PaymentTypeEnum.ONLINE_PAY.getCode());
        order.setPayment(totalPrice);

        order.setUserId(userId);
        order.setShippingId(shippingId);
        //发货时间等等
        //付款时间等等
        int rowCount = orderMapper.insert(order);
        if(rowCount > 0){
            return order;
        }
        return null;
    }

    /**
     * 주문번호생성
     * @return
     */
    private long generateOrderNo(){
        long currentTime =System.currentTimeMillis();
        return currentTime+new Random().nextInt(100);
    }

    /**
     * 총가격 도출
     * @param orderItemList
     * @return
     */
    private BigDecimal getOrderTotalPrice(List<OrderItem> orderItemList){
        BigDecimal payment = new BigDecimal("0");
        for(OrderItem orderItem : orderItemList){
            payment = BigDecimalUtil.add(payment.doubleValue(),orderItem.getTotalPrice().doubleValue());
        }
        return payment;
    }

    /**
     * 앞단에서 넘어온 장바구니데이타로 오더아이템을 만든다.
     * @param cartVO
     * @return
     */
    private List<OrderItem> getOrderItemByCartParameter(CartVO cartVO) {
        List<OrderItem> orderItemList = new ArrayList<>();
        if (CollectionUtils.isEmpty(cartVO.getCartProductVoList())) {
            return null;
        }

        List<CartProductVO> cartProductVOList = cartVO.getCartProductVoList();

        for (CartProductVO cartProductItem : cartProductVOList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setUserId(cartVO.getUserId());
            orderItem.setProductId(cartProductItem.getProductId());
            orderItem.setSpecId(cartProductItem.getSpecId());
            orderItem.setProductName(cartProductItem.getProductName());
            orderItem.setProductImage(cartProductItem.getProductMainImage());
            orderItem.setCurrentUnitPrice(cartProductItem.getProductPrice());
            orderItem.setQty(cartProductItem.getQuantity());
            orderItem.setTotalPrice(cartProductItem.getProductTotalPrice());
            log.info(orderItem.toString());
            orderItemList.add(orderItem);
        }

        return orderItemList;
    }

    private ShippingVO assembleShippingVo(Shipping shipping){
        ShippingVO shippingVo = new ShippingVO();
        shippingVo.setReceiverName(shipping.getReceiverName());
        shippingVo.setReceiverAddress(shipping.getReceiverAddress());
        shippingVo.setReceiverProvince(shipping.getReceiverProvince());
        shippingVo.setReceiverCity(shipping.getReceiverCity());
        shippingVo.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVo.setReceiverMobile(shipping.getReceiverMobile());
        shippingVo.setReceiverZip(shipping.getReceiverZip());
        shippingVo.setReceiverPhone(shippingVo.getReceiverPhone());
        return shippingVo;
    }

    private OrderItemVO assembleOrderItemVo(OrderItem orderItem){
        OrderItemVO orderItemVo = new OrderItemVO();
        orderItemVo.setOrderNo(orderItem.getOrderId());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setQuantity(orderItem.getQty());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());

        orderItemVo.setCreateTime(DateUtil.dateToStr(orderItem.getCreatetime()));
        return orderItemVo;
    }



}
