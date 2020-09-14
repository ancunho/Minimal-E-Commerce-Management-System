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
@RequestMapping("/manager/")
public class ManagePageController {

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
            return "redirect:/manager/login";
        }

        model.addAttribute("activeUri", "manager/home");
        return "backend/dashboard";
    }

    @RequestMapping(value = "login")
    public String login(HttpSession session) { return "backend/login"; }

    /************************************************************
     * 用户 - User
     ************************************************************/
    @RequestMapping(value = "customer/list")
    public String user(HttpSession session, Model model) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return "redirect:/manager/login";
        }

        model.addAttribute("activeUri", "manager/customer");
        return "backend/customer/list";
    }

    @RequestMapping(value = "customer/create")
    public String user_create(HttpSession session, Model model) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return "redirect:/manager/login";
        }
        model.addAttribute("activeUri", "manager/customer");
        return "backend/customer/create";
    }

    @RequestMapping(value = "customer/edit/{userId}", method = RequestMethod.GET)
    public String user_edit(HttpSession session, @PathVariable("userId") Integer userId, Model model, HttpServletRequest request) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return "redirect:/manager/login";
        }

        User user = userService.findUserById(userId);
        model.addAttribute("activeUri", "manager/customer");
        model.addAttribute("user", user);
        return "backend/customer/edit";
    }

    /************************************************************
     * 商品 - Product
     ************************************************************/
    @RequestMapping(value = "product/list")
    public String product(HttpSession session, Model model) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return "redirect:/manager/login";
        }
        model.addAttribute("activeUri", "manager/product");
        return "backend/product/list";
    }

    @RequestMapping(value = "product/create")
    public String product_create(HttpSession session, Model model) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return "redirect:/manager/login";
        }
        model.addAttribute("activeUri", "manager/product");
        return "backend/product/create";
    }

    @RequestMapping(value = "product/edit/{productId}", method = RequestMethod.GET)
    public String product_edit(HttpSession session, @PathVariable("productId") Integer productId, Model model, HttpServletRequest request) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return "redirect:/manager/login";
        }

        Product product = productService.getProductDetail(productId);
        List<Spec> specList = specService.getSpecListByProductId(productId);
        product.setSpecList(specList);
        model.addAttribute("activeUri", "manager/product");
        model.addAttribute("product", product);
        return "backend/product/edit";
    }

//    @RequestMapping(value = "product/view/{productId}", method = RequestMethod.GET)
//    public String product_view(HttpSession session, @PathVariable("productId") Integer productId, Model model, HttpServletRequest request) {
////        User user = (User) session.getAttribute(Const.CURRENT_USER);
////        if (user == null) {
////            return "redirect:/manager/login";
////        }
//
//        Product product = productService.getProductDetail(productId);
//        List<Spec> specList = specService.getSpecListByProductId(productId);
//        product.setSpecList(specList);
//        model.addAttribute("activeUri", "manager/product");
//        model.addAttribute("product", product);
//        return "backend/product/view";
//    }

    /************************************************************
     * 规格 - Spec
     ************************************************************/
    @RequestMapping(value = "spec/list")
    public String Spec_list(HttpSession session, Model model) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return "redirect:/manager/login";
        }
        model.addAttribute("activeUri", "manager/spec");
        return "spec/list";
    }

    @RequestMapping(value = "spec/create")
    public String Spec_create(HttpSession session, Model model) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return "redirect:/manager/login";
        }

        model.addAttribute("activeUri", "manager/spec");
        return "spec/create";
    }

    /************************************************************
     * 订单 - Order
     ************************************************************/
    @RequestMapping(value = "order/list")
    public String order(HttpSession session, Model model) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return "redirect:/manager/login";
        }

        model.addAttribute("activeUri", "manager/order");
        return "order/list";
    }

    /************************************************************
     * 用户 - User
     ************************************************************/
    @RequestMapping(value = "shipping/create")
    public String shipping_add(HttpSession session, Model model) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return "redirect:/manager/login";
        }

        model.addAttribute("activeUri", "manager/shipping");
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
