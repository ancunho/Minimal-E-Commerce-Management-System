package com.ahnstudio.management.controller.portal;

import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.dao.ProductMapper;
import com.ahnstudio.management.pojo.Product;
import com.ahnstudio.management.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/portal")
public class PortalPageController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "")
    public String index() {
        return "portal/index";
    }

    @RequestMapping(value = "/product/list")
    public String product_list(HttpSession session, Model model) {
        return "portal/product-list";
    }

    @RequestMapping(value = "/product/{productId}")
    public String product_detail(HttpSession session, @PathVariable("productId") Integer productId, Model model) {
        return "portal/product-detail";
    }

    @RequestMapping(value = "/cart/{userId}")
    public String cart(HttpSession session, @PathVariable(value = "userId", required = false) Integer userId, Model model) {
        return "portal/cart";
    }

    @RequestMapping(value = "/pay/{orderNo}")
    public String pay(HttpSession session, @PathVariable("orderNo") Integer orderNo, Model model) {
        return "portal/pay";
    }

}
