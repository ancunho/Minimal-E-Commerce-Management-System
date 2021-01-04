package com.ahnstudio.management.controller.portal;

import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.pojo.Shipping;
import com.ahnstudio.management.service.ShippingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerRequestExtensionsKt;

import javax.servlet.http.HttpSession;

/**
 * @author : Cunho
 * @date : 2020/09/01
 */
@Slf4j
@RestController
@RequestMapping("/api/miniapp/shipping/")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse add(HttpSession session, @RequestBody Shipping shipping){

        return shippingService.add(shipping);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse shipping_list(HttpSession session, Integer userId){
        return shippingService.selectListByUserId(userId);
    }

    @RequestMapping(value = "default", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse default_shipping(HttpSession session, Integer userId) {
        return shippingService.selectDefaultShippingByUserId(userId);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse shipping_update(HttpSession session, @RequestBody Shipping shipping) {
        return shippingService.updateShipping(shipping);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse shipping_delete(HttpSession session, Integer userId, Integer id) {
        return shippingService.deleteByShippingIdUserId(userId, id);
    }

    @RequestMapping(value = "detailById", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse selectShipByPk(HttpSession session, Integer shippingId) {
        return shippingService.selectShipByPk(shippingId);
    }

}
