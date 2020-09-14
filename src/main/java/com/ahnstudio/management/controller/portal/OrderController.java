package com.ahnstudio.management.controller.portal;

import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.service.OrderService;
import com.ahnstudio.management.vo.CartVO;
import com.ahnstudio.management.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @RequestMapping(value = "create")
    public ServerResponse create_order_no(HttpSession session, @RequestBody CartVO cartVO) {
        // 파라미터로 넘긴 장바구니정보로 주문번호 생성한다
        return orderService.createOrder(cartVO);
    }



}
