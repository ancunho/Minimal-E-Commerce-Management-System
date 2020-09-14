package com.ahnstudio.management.controller.backend;

import com.ahnstudio.management.common.Const;
import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.pojo.Product;
import com.ahnstudio.management.pojo.User;
import com.ahnstudio.management.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author : Cunho
 * @date : 2020/08/17
 */
@Slf4j
@RestController
@RequestMapping("/api/product/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "create", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ServerResponse create_product(HttpSession session, @RequestBody Product product) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        if (user == null) {
//            return ServerResponse.createByErrorMessage(Const.Message.NEED_LOGIN);
//        }
        log.info(product.toString());

        return productService.saveOrUpdateProduct(product);
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse list(HttpSession session, @RequestParam(value = "pageNumber", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        if (user == null) {
//            return ServerResponse.createByErrorMessage(Const.Message.NEED_LOGIN);
//        }
        return productService.getProductListForAdmin(pageNum, pageSize);
    }


}
