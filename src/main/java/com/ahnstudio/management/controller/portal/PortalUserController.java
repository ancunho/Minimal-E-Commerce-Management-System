package com.ahnstudio.management.controller.portal;

import com.ahnstudio.management.common.Const;
import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.pojo.User;
import com.ahnstudio.management.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author : Cunho
 * @date : 2020/09/21
 */
@Slf4j
@RestController
@RequestMapping("/api/miniapp/user/")
public class PortalUserController {

    @Autowired
    private UserService userService;

    @RequestMapping("login")
    @ResponseBody
    public ServerResponse login(HttpSession session, String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return ServerResponse.createByErrorMessage(Const.Message.PARAMETER_ERROR);
        }

        User currentUser = userService.loginCustomer(username, password);

        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户名或者密码错误");
        }

        session.setAttribute(Const.CURRENT_USER, currentUser);

        return ServerResponse.createBySuccess("登录成功",currentUser);
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ServerResponse register(@RequestBody User user) {
        return userService.addUser(user);
    }



}
