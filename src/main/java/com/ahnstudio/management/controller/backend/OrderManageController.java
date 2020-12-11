package com.ahnstudio.management.controller.backend;


import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.pojo.Order;
import com.ahnstudio.management.service.OrderService;
import com.ahnstudio.management.util.Box;
import com.ahnstudio.management.util.HttpUtility;
import com.ahnstudio.management.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Cunho
 * @date : 2020/09/20
 */
@Slf4j
@RestController
@RequestMapping("/api/order/")
public class OrderManageController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "list")
    public ServerResponse order_list(HttpServletRequest request
                                    , @RequestParam(value = "pageNum",defaultValue = "1") int pageNum
                                    , @RequestParam(value = "pageSize",defaultValue = "10") int pageSize
                                     ) {
        Box box = HttpUtility.getBox(request);
        Map<String, Object> mapParams = new HashMap<>();
        box.copyToEntityMap(mapParams);
        return orderService.selectOrderByPaging(pageNum, pageSize, mapParams);
    }

    @RequestMapping(value = "detail/{orderId}")
    public ServerResponse getOrderDetail(HttpSession session, @PathVariable(value = "orderId", required = false) String orderId) {
        return orderService.getOrderDetail(orderId);
    }

    @RequestMapping(value = "detailVO")
    public OrderVO getOrderVO(HttpSession session, String orderNo) {
        return orderService.getOrderVO(orderNo);
    }

    @RequestMapping(value = "update_deliveryno")
    public ServerResponse order_delivery_success(HttpSession session, String orderNo, String deliveryNo) {
        return orderService.orderDeliverySuccess(orderNo, deliveryNo);
    }



}
