package com.ahnstudio.management.service;

import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.pojo.Order;
import com.ahnstudio.management.vo.CartVO;
import com.ahnstudio.management.vo.OrderVO;

import java.util.Map;

public interface OrderService {


    /************************************************************
     * Portal
     ************************************************************/
    ServerResponse createOrder(CartVO cartVO);



    /************************************************************
     * Backend
     ************************************************************/
    ServerResponse getAllOrderList(Integer pageNum, Integer pageSize);
    ServerResponse getAllOrderListByCondition(Integer pageNum, Integer pageSize, String status, String startTime, String endTime);
    ServerResponse getOrderDetail(String orderId);
    OrderVO getOrderVO(String orderNo);
    ServerResponse orderPaySuccess(String orderNo);
    ServerResponse getOrderListByStatus10(int pageNum, int pageSize, Integer userId);
    ServerResponse getOrderListByStatus(Integer userId, Integer status, int pageNum, int pageSize);
    ServerResponse orderDeliverySuccess(String orderNo, String deliveryNo);
    ServerResponse selectOrderByPaging(int pageNum, int pageSize, Map<String, Object> mapParams);



}
