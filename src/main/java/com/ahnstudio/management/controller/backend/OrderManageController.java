package com.ahnstudio.management.controller.backend;


import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.service.OrderService;
import com.ahnstudio.management.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

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
    public ServerResponse order_list(HttpSession session, @RequestParam(value = "page", defaultValue = "1") int pageNum, @RequestParam(value = "limit", defaultValue = "10") int pageSize) {
        return orderService.getAllOrderList(pageNum, pageSize);
    }

    @RequestMapping(value = "detail/{orderId}")
    public ServerResponse getOrderDetail(HttpSession session, @PathVariable(value = "orderId", required = false) String orderId) {
        return orderService.getOrderDetail(orderId);
    }

    @RequestMapping(value = "detailVO")
    public OrderVO getOrderVO(HttpSession session, String orderNo) {
        return orderService.getOrderVO(orderNo);
    }


}
