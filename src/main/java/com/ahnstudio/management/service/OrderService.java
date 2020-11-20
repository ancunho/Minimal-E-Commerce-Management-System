package com.ahnstudio.management.service;

import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.vo.CartVO;
import com.ahnstudio.management.vo.OrderVO;

public interface OrderService {


    /************************************************************
     * Portal
     ************************************************************/
    ServerResponse createOrder(CartVO cartVO);

    /************************************************************
     * Backend
     ************************************************************/

    /**
     * 获取全部订单
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse getAllOrderList(Integer pageNum, Integer pageSize);

    ServerResponse getOrderDetail(String orderId);

    OrderVO getOrderVO(String orderNo);

    ServerResponse orderPaySuccess(String orderNo);

    ServerResponse getOrderListByStatus10(int pageNum, int pageSize, Integer userId);

    ServerResponse getOrderListByStatus(Integer userId, Integer status, int pageNum, int pageSize);

    ServerResponse orderDeliverySuccess(String orderNo, String deliveryNo);

}
