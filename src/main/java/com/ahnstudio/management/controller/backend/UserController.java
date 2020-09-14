package com.ahnstudio.management.controller.backend;

import com.ahnstudio.management.common.Const;
import com.ahnstudio.management.common.ResponseCode;
import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.pojo.User;
import com.ahnstudio.management.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author : Cunho
 * @date : 2020/8/31
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 로그인
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ServerResponse login(HttpSession session, String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return ServerResponse.createByErrorMessage(Const.Message.PARAMETER_ERROR);
        }

        User currentUser = userService.login(username, password);

        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户名或者密码错误");
        }

        session.setAttribute(Const.CURRENT_USER, currentUser);

        return ServerResponse.createBySuccess("登录成功");
    }

    /**
     * 로그아웃
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ServerResponse logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccessMessage("已成功退出");
    }

    /**
     * 개인정보 조회
     * @param userId
     * @return
     */
//    @RequestMapping(value = "/infoByToken", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ServerResponse info(HttpSession session, @RequestHeader("Access-Token") String token) {
//        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
//        if (currentUser == null){
//            return ServerResponse.createByErrorMessage(Const.Message.NEED_LOGIN);
//        }
//        return userService.info(token);
//    }

    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ServerResponse infoByUserId(HttpSession session, @RequestHeader("userId") Integer userId) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorMessage(Const.Message.NEED_LOGIN);
        }
        return userService.infoByUserId(userId);
    }

    /**
     * 개인정보 수정
     * @param session
     * @param user
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ServerResponse edit(HttpSession session, @RequestBody User user) {
//        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        // 2. 정보 업데이트
        ServerResponse<User> response = userService.updateInformation(user);
        return response;
    }

    /**
     * 找回密码 3 - 1
     * username을 통해 물음 가져오기
     * @param username
     * @return
     */
    @RequestMapping(value = "/forget_get_question", method = RequestMethod.POST)
    public ServerResponse<String> forgetGetQuestion(String username) {
        return userService.selectQuestion(username);
    }

//    /**
//     * 找回密码 3 - 2
//     * 답안 체크 및 token반환
//     * @param username
//     * @param question
//     * @param answer
//     * @return
//     */
//    @RequestMapping(value = "/forget_check_answer", method = RequestMethod.POST)
//    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
//        return userService.checkAnswer(username,question,answer);
//    }

//    /**
//     * 找回密码 3 - 3
//     * 새 비밀번호 저장
//     * @param username
//     * @param passwordNew
//     * @param forgetToken
//     * @return
//     */
//    @RequestMapping(value = "/forget_reset_password", method = RequestMethod.POST)
//    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
//        return userService.forgetResetPassword(username, passwordNew, forgetToken);
//    }

//    /**
//     * 비밀번호 수정
//     * @param session
//     * @param passwordOld
//     * @param passwordNew
//     * @return
//     */
//    @RequestMapping(value = "/update/password", method = RequestMethod.POST)
//    public ServerResponse<String> reset_password(HttpSession session, String passwordOld, String passwordNew) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登陆");
//        }
//
//        return userService.resetPassword(passwordOld, passwordNew, user);
//    }

    /**
     * 회원가입
     * @param user
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ServerResponse create(@RequestBody User user) {
        return userService.addUser(user);
    }

    /**
     * username 중복체크
     * 회원가입할때 혹은 관리자가 username수정할때 사용
     * @param username
     * @return
     */
    @RequestMapping(value = "/check_username", method = RequestMethod.POST)
    public ServerResponse check_username(String username) {
        return userService.checkValid(username, Const.USERNAME);
    }

    /**
     * Email 중복체크
     * 회원가입할때 혹은 관리자가 email 수정할때 사용
     * @param email
     * @return
     */
    @RequestMapping(value = "/check_email", method = RequestMethod.POST)
    public ServerResponse check_email(String email) {
        return userService.checkValid(email, Const.EMAIL);
    }


    /*********************************************************************************************************
     * 												관리자 전용
     *********************************************************************************************************/

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ServerResponse delete_user(HttpSession session, @RequestBody User user) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登陆");
        }

        return userService.delete_user(user);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse list(HttpSession session, @RequestParam(value = "page", defaultValue = "1") int pageNum, @RequestParam(value = "limit", defaultValue = "10") int pageSize) {
        return userService.getUserListForAdmin(pageNum, pageSize);
    }


}
