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



}
