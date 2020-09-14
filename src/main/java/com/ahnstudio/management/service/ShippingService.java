package com.ahnstudio.management.service;

import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.pojo.Shipping;

import java.util.List;

public interface ShippingService {

    ServerResponse add(Shipping shipping);

    ServerResponse selectListByUserId(Integer userId);

    ServerResponse updateShipping(Shipping shipping);

    ServerResponse deleteByShippingIdUserId(Integer userId, Integer shippingId);

}
