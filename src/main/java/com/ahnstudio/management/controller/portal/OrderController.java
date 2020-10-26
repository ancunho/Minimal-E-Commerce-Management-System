package com.ahnstudio.management.controller.portal;

import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.service.OrderService;
import com.ahnstudio.management.vo.CartVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author : Cunho
 * @date : 2020/09/11
 */
@Slf4j
@RestController
@RequestMapping("/api/miniapp/order/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ServerResponse create_order_no(HttpSession session, @RequestBody CartVO cartVO) {
        // 파라미터로 넘긴 장바구니정보로 주문번호 생성한다
        return orderService.createOrder(cartVO);
    }

    public ServerResponse order_list(HttpSession session, @RequestParam(value = "pageNumber", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return orderService.getAllOrderList(pageNum, pageSize);
    }

    @RequestMapping(value = "list_status_10", method = RequestMethod.POST)
    public ServerResponse order_list_status_10(HttpSession session, @RequestParam(value = "pageNumber", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, Integer userId) {
        return orderService.getOrderListByStatus10(pageNum, pageSize, userId);
    }

    @RequestMapping(value = "pay_success",method = RequestMethod.POST)
    public ServerResponse order_pay_success(HttpSession session, String orderNo,@RequestParam(value = "pageNumber", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return orderService.orderPaySuccess(orderNo);
    }

    @RequestMapping(value = "by_status",method = RequestMethod.POST)
    public ServerResponse order_list_by_status(HttpSession session, Integer userId, Integer status, @RequestParam(value = "pageNumber", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return orderService.getOrderListByStatus(userId, status, pageNum, pageSize);
    }


}
