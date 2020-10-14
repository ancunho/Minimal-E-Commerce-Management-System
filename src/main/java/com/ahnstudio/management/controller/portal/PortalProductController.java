package com.ahnstudio.management.controller.portal;

import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.pojo.Product;
import com.ahnstudio.management.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author : Cunho
 * @date : 2020/09/3
 */
@Slf4j
@RestController
@RequestMapping("/api/miniapp/product/")
public class PortalProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse list(@RequestParam(value = "page", defaultValue = "1") int pageNum, @RequestParam(value = "limit", defaultValue = "10") int pageSize) {
        return productService.getProductListForWechat(pageNum, pageSize);
    }

    @RequestMapping(value = "detail", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse detail(Integer productId) {
        return productService.getProductDetailForWechat(productId);
    }
}
