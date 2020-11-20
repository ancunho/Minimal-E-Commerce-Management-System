package com.ahnstudio.management.dao;

import com.ahnstudio.management.pojo.Order;
import com.ahnstudio.management.vo.OrderListVO;
import com.ahnstudio.management.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("orderMapper")
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<OrderListVO> getAllOrderList();

    Order selectByOrderId(String orderId);

    int orderPaySuccess(String orderNo);

    List<String> getOrderListByStatus10(Integer userId);

    List<String> getOrderListInStatus20And40(Integer userId);

    List<String> getOrderListByStatus0(Integer userId);

    List<String> getOrderAllList(Integer userId);

    int updateDeliveryNo(String orderNo, String deliveryNo);

}