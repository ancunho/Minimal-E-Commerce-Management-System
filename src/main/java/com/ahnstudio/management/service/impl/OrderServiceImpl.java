package com.ahnstudio.management.service.impl;

import com.ahnstudio.management.common.Const;
import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.dao.*;
import com.ahnstudio.management.pojo.*;
import com.ahnstudio.management.service.OrderService;
import com.ahnstudio.management.util.BigDecimalUtil;
import com.ahnstudio.management.util.DateUtil;
import com.ahnstudio.management.util.PropertiesUtil;
import com.ahnstudio.management.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private SpecMapper specMapper;

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
        Order order = this.assembleOrder(cartVO.getUserId(), cartVO.getShippingId(), totalPrice, cartVO.getComment());
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
        orderVO.setComment(order.getParam1());
        orderVO.setParam1(order.getParam1());
        orderVO.setParam2(order.getParam2());
        orderVO.setParam3(order.getParam3());
        orderVO.setParam4(order.getParam4());
        orderVO.setParam5(order.getParam5());

        orderVO.setShippingId(order.getShippingId());
        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        if(shipping != null){
            orderVO.setReceiverName(shipping.getReceiverName());
            orderVO.setShippingVo(assembleShippingVo(shipping));
        }

        orderVO.setPaymentTime(DateUtil.dateToStr(order.getPaymentTime()));
        orderVO.setSendTime(DateUtil.dateToStr(order.getSendTime()));
        orderVO.setEndTime(DateUtil.dateToStr(order.getEndTime()));
        orderVO.setCreateTime(order.getCreatetime());
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
    private Order assembleOrder(Integer userId, Integer shippingId, BigDecimal totalPrice, String comment) {
        Order order = new Order();
        String orderNo = this.generateOrderNo();
        order.setOrderId(orderNo);
        order.setStatus(Const.OrderStatusEnum.NO_PAY.getCode());
        order.setPostage(0);
        order.setPaymentType(Const.PaymentTypeEnum.ONLINE_PAY.getCode());
        order.setPayment(totalPrice);
        order.setParam1(comment);

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
    private static final AtomicInteger SEQ = new AtomicInteger(1000);
    private static final DateTimeFormatter DF_FMT_PREFIX = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
    private static ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");

    public static String generateOrderNo() {
        LocalDateTime dataTime = LocalDateTime.now(ZONE_ID);
        if(SEQ.intValue() > 9990){
            SEQ.getAndSet(1000);
        }
        return  dataTime.format(DF_FMT_PREFIX) + SEQ.getAndIncrement();
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
            BigDecimal totalPrice = BigDecimalUtil.mul(cartProductItem.getProductPrice().doubleValue(),cartProductItem.getQuantity());
            orderItem.setTotalPrice(totalPrice);
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
        orderItemVo.setSpecId(orderItem.getSpecId());
        orderItemVo.setSpec(specMapper.selectByPrimaryKey(orderItem.getSpecId()));
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setQuantity(orderItem.getQty());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());

        orderItemVo.setCreateTime(DateUtil.dateToStr(orderItem.getCreatetime()));
        return orderItemVo;
    }



    /************************************************************
     * Backend
     ************************************************************/

    @Override
    public ServerResponse getAllOrderList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<OrderListVO> orderList = orderMapper.getAllOrderList();
        PageInfo pageResult = new PageInfo(orderList);
        pageResult.setList(orderList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse getOrderDetail(String orderId) {
        if (orderId == null) {
            return ServerResponse.createByErrorMessage(Const.Message.PARAMETER_ERROR);
        }
        Order order = orderMapper.selectByOrderId(orderId);
        if (order != null) {
            List<OrderItem> orderItemList = orderItemMapper.getByOrderId(orderId);
            OrderVO orderVO = assembleOrderVo(order, orderItemList);
            return ServerResponse.createBySuccess(orderVO);
        }
        return ServerResponse.createByErrorMessage("订单不存在");
    }

    @Override
    public OrderVO getOrderVO(String orderNo) {
        if (orderNo == null) {
            return null;
        }
        Order order = orderMapper.selectByOrderId(orderNo);
        if (order != null) {
            List<OrderItem> orderItemList = orderItemMapper.getByOrderId(orderNo);
            User customer = userMapper.selectByPrimaryKey(order.getUserId());
            Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
            OrderVO orderVO = assembleOrderVo(order, orderItemList);
            orderVO.setStatusDesc(this.getOrderStatusDesc(order.getStatus()));
            orderVO.setCustomer(customer);
            orderVO.setShipping(shipping);
            return orderVO;
        }
        return null;
    }

    @Override
    public ServerResponse orderPaySuccess(String orderNo) {
        int updateCount = orderMapper.orderPaySuccess(orderNo);
        return ServerResponse.createBySuccessMessage("支付成功， 后台成功");
    }

    @Override
    public ServerResponse getOrderListByStatus10(int pageNum, int pageSize, Integer userId) {
        PageHelper.startPage(pageNum,pageSize);
        List orderNoList = orderMapper.getOrderListByStatus10(userId);

        if (orderNoList.size() > 0) {

            List<OrderVO> orderVOList = new ArrayList<>();
            for (int i = 0; i < orderNoList.size(); i++) {
                OrderVO orderVO = new OrderVO();
                orderVO = this.getOrderVO(orderNoList.get(i).toString());
                orderVOList.add(orderVO);
            }
            PageInfo pageResult = new PageInfo(orderVOList);
            pageResult.setList(orderVOList);
            return ServerResponse.createBySuccess(pageResult);
        }
        return null;
    }

    @Override
    public ServerResponse getOrderListByStatus(Integer userId, Integer status, int pageNum, int pageSize) {
        if (userId == null || status == null) {
            return ServerResponse.createByErrorMessage(Const.Message.PARAMETER_ERROR);
        }

        List orderNoList = new ArrayList();
        switch (status) {
            case 10 :
                orderNoList = orderMapper.getOrderListByStatus10(userId);
                break;
            case 40 :
                orderNoList = orderMapper.getOrderListInStatus20And40(userId);
                break;
            case 0 :
                orderNoList = orderMapper.getOrderListByStatus0(userId);
                break;
            default:
                orderNoList = orderMapper.getOrderAllList(userId);
                break;
        }

        if (orderNoList.size() > 0) {
            List<OrderVO> orderVOList = new ArrayList<>();
            for (int i = 0; i < orderNoList.size(); i++) {
                OrderVO orderVO = new OrderVO();
                orderVO = this.getOrderVO(orderNoList.get(i).toString());
                orderVOList.add(orderVO);
            }
            PageInfo pageResult = new PageInfo(orderVOList);
            pageResult.setList(orderVOList);
            return ServerResponse.createBySuccess(pageResult);
        }
        return ServerResponse.createByErrorMessage("无数据");


    }

    @Override
    public ServerResponse orderDeliverySuccess(String orderNo, String deliveryno) {
        Order order = orderMapper.selectByOrderId(orderNo);
        if (order == null) {
            return ServerResponse.createByErrorMessage("订单不存在， 请联系IT管理员，查看数据");
        }

//        if (order.getParam2() != null) {
//            return ServerResponse.createByErrorCodeMessage(97, "已经有快递单号");
//        }

        order.setParam2(deliveryno);

        int updateCount = orderMapper.updateDeliveryNo(orderNo, deliveryno);
        if (updateCount > 0) {
            return ServerResponse.createBySuccess();
        }

        return ServerResponse.createByErrorMessage(Const.Message.UPDATE_ERROR);
    }

    private String getOrderStatusDesc(Integer status) {
        if (status == 0) {
            return "已取消";
        } else if (status == 10) {
            return "未支付";
        } else if (status == 20) {
            return "已付款";
        } else if (status == 40) {
            return "已发货";
        } else if (status == 50) {
            return "订单完成";
        } else if (status == 60) {
            return "订单关闭";
        } else {
            return null;
        }
    }


}
