package com.ahnstudio.management.controller.backend;

import com.ahnstudio.management.common.Const;
import com.ahnstudio.management.pojo.Product;
import com.ahnstudio.management.pojo.Spec;
import com.ahnstudio.management.pojo.User;
import com.ahnstudio.management.service.ProductService;
import com.ahnstudio.management.service.SpecService;
import com.ahnstudio.management.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/page/")
public class PageController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SpecService specService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "home")
    public String index(HttpSession session, Model model) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return "redirect:/page/login";
        }

        model.addAttribute("activeUri", "page/home");
        return "index";
    }

    @RequestMapping(value = "login")
    public String login(HttpSession session) { return "login"; }

    /************************************************************
     * 用户 - User
     ************************************************************/
    @RequestMapping(value = "user/list")
    public String user(HttpSession session, Model model) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return "redirect:/page/login";
        }

        model.addAttribute("activeUri", "page/user");
        return "user/list";
    }

    @RequestMapping(value = "user/create")
    public String user_create(HttpSession session, Model model) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return "redirect:/page/login";
        }
        model.addAttribute("activeUri", "page/user");
        return "user/create";
    }

    @RequestMapping(value = "user/edit/{userId}", method = RequestMethod.GET)
    public String user_edit(HttpSession session, @PathVariable("userId") Integer userId, Model model, HttpServletRequest request) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return "redirect:/page/login";
        }

        User user = userService.findUserById(userId);
        model.addAttribute("activeUri", "page/user");
        model.addAttribute("user", user);
        return "user/edit";
    }

    /************************************************************
     * 商品 - Product
     ************************************************************/
    @RequestMapping(value = "product/list")
    public String product(HttpSession session, Model model) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return "redirect:/page/login";
        }
        model.addAttribute("activeUri", "page/product");
        return "product/list";
    }

    @RequestMapping(value = "product/create")
    public String product_create(HttpSession session, Model model) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return "redirect:/page/login";
        }
        model.addAttribute("activeUri", "page/product");
        return "product/create";
    }

    @RequestMapping(value = "product/edit/{productId}", method = RequestMethod.GET)
    public String product_edit(HttpSession session, @PathVariable("productId") Integer productId, Model model, HttpServletRequest request) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return "redirect:/page/login";
        }

        Product product = productService.getProductDetail(productId);
        List<Spec> specList = specService.getSpecListByProductId(productId);
        product.setSpecList(specList);
        model.addAttribute("activeUri", "page/product");
        model.addAttribute("product", product);
        return "product/edit";
    }

    /************************************************************
     * 规格 - Spec
     ************************************************************/
    @RequestMapping(value = "spec/list")
    public String Spec_list(HttpSession session, Model model) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return "redirect:/page/login";
        }
        model.addAttribute("activeUri", "page/spec");
        return "spec/list";
    }

    @RequestMapping(value = "spec/create")
    public String Spec_create(HttpSession session, Model model) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return "redirect:/page/login";
        }

        model.addAttribute("activeUri", "page/spec");
        return "spec/create";
    }

    /************************************************************
     * 订单 - Order
     ************************************************************/
    @RequestMapping(value = "order/list")
    public String order(HttpSession session, Model model) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return "redirect:/page/login";
        }

        model.addAttribute("activeUri", "page/order");
        return "order/list";
    }

    /************************************************************
     * 用户 - User
     ************************************************************/
    @RequestMapping(value = "shipping/create")
    public String shipping_add(HttpSession session, Model model) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return "redirect:/page/login";
        }

        model.addAttribute("activeUri", "page/shipping");
        return "shipping/create";
    }




    /************************************************************
     ************************************************************
     * Portal
     ************************************************************
     ************************************************************/
    @RequestMapping(value = "portal")
    public String portal(HttpSession session, Model model) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return "redirect:/page/login";
        }

        model.addAttribute("activeUri", "page/shipping");
        return "shipping/create";
    }




















}
