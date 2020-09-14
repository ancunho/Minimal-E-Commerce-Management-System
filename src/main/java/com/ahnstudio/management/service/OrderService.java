package com.ahnstudio.management.service;

import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.vo.CartVO;

public interface OrderService {

    ServerResponse createOrder(CartVO cartVO);

}
