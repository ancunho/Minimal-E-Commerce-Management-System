package com.ahnstudio.management.controller.portal;

import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.util.WechatUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Cunho
 * @date : 2020/09/22
 */
@Slf4j
@RestController
@RequestMapping("/api/miniapp/")
public class StrongHoldWechatController {

    @RequestMapping(value = "getOpenId")
    public ServerResponse getOpenIdOrSessionKeyByCODE(@RequestParam(value = "code", required = false) String code) {
        JSONObject SessionKeyOpenId = WechatUtil.getSessionKeyOrOpenId(code);

        // 3.接收微信接口服务 获取返回的参数
        String openid = SessionKeyOpenId.getString("openid");
        String sessionKey = SessionKeyOpenId.getString("session_key");

        log.info("openid:" + openid);
        log.info("sessionKey:" + sessionKey);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("openId", openid);
        result.put("sessionKey", sessionKey);
        return ServerResponse.createBySuccess(result);
    }



    @RequestMapping(value = "login")
    public ServerResponse create_order_no(@RequestParam(value = "code", required = false) String code,
                                          @RequestParam(value = "rawData", required = false) String rawData,
                                          @RequestParam(value = "signature", required = false) String signature,
                                          @RequestParam(value = "encrypteData", required = false) String encrypteData,
                                          @RequestParam(value = "iv", required = false) String iv) {
        log.info(code);
        log.info(rawData);
        log.info(signature);
        log.info(encrypteData);
        log.info(iv);
        // 用户非敏感信息：rawData
        // 签名：signature
        JSONObject rawDataJson = JSON.parseObject(rawData);
        // 1.接收小程序发送的code
        // 2.开发者服务器 登录凭证校验接口 appi + appsecret + code
        JSONObject SessionKeyOpenId = WechatUtil.getSessionKeyOrOpenId(code);
        // 3.接收微信接口服务 获取返回的参数
        String openid = SessionKeyOpenId.getString("openid");
        String sessionKey = SessionKeyOpenId.getString("session_key");

        log.info("openid:" + openid);
        log.info("sessionKey:" + sessionKey);
        // 4.校验签名 小程序发送的签名signature与服务器端生成的签名signature2 = sha1(rawData + sessionKey)
        String signature2 = DigestUtils.sha1Hex(rawData + sessionKey);
        if (!signature.equals(signature2)) {
            return ServerResponse.createByErrorCodeMessage(500, "签名校验失败");
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("openId", openid);
        result.put("sessionKey", sessionKey);
        return ServerResponse.createBySuccess(result);
    }

}
