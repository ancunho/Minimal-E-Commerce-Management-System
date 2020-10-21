package com.ahnstudio.management.controller.portal;

import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.wxpay.MyConfig;
import com.ahnstudio.management.wxpay.WXPay;
import com.ahnstudio.management.wxpay.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/wx/")
public class WeixinController {

    @RequestMapping(value = "pay")
    public ServerResponse doUnifiedOrder(String openId ) {
        MyConfig config = new MyConfig();
        WXPay wxpay= null;
        try {
            wxpay = new WXPay(config);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map resultMap = new HashMap<>();

        //生成的随机字符串
        String nonce_str = WXPayUtil.generateNonceStr();
        //获取客户端的ip地址
        //获取本机的ip地址
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }



        String spbill_create_ip = addr.getHostAddress();
        //支付金额，需要转成字符串类型，否则后面的签名会失败
        int  total_fee=1;
        //商品描述
        String body = "咖啡支付";
        //商户订单号
        String out_trade_no= WXPayUtil.generateNonceStr();
        //统一下单接口参数
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("appid", config.getAppID());
        data.put("mch_id", config.getMchID());
        data.put("nonce_str", nonce_str);
        data.put("body", body);
        data.put("out_trade_no",out_trade_no);
        data.put("total_fee", String.valueOf(total_fee));
        data.put("spbill_create_ip", spbill_create_ip);
        data.put("notify_url", "https://localhost/"); //TODO
        data.put("trade_type","JSAPI"); //TODO
        data.put("openid", openId); //TODO

        String sign = null;
        try {
            sign = WXPayUtil.generateSignature(data, config.getKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        data.put("sign", sign);
        log.info(data.toString());
        try {
            Map<String, String> rMap = wxpay.unifiedOrder(data);
            System.out.println("统一下单接口返回: " + rMap);
            String return_code = (String) rMap.get("return_code");
            String result_code = (String) rMap.get("result_code");
            String nonceStr = WXPayUtil.generateNonceStr();
            resultMap.put("nonceStr", nonceStr);
            Long timeStamp = System.currentTimeMillis() / 1000;
            if ("SUCCESS".equals(return_code) && return_code.equals(result_code)) {
                String prepayid = rMap.get("prepay_id");
                resultMap.put("package", "prepay_id="+prepayid);
                resultMap.put("signType", "MD5");
                //这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
                resultMap.put("timeStamp", timeStamp + "");
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                resultMap.put("appId",config.getAppID());
                sign = WXPayUtil.generateSignature(resultMap, config.getKey());
                resultMap.put("paySign", sign);
                System.out.println("生成的签名paySign : "+ sign);

                return ServerResponse.createBySuccess(resultMap);
            }else{
                return  ServerResponse.createBySuccess("???", resultMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  ServerResponse.createByErrorMessage("ERROR ERROR ERROR ERROR");
        }

    }

}
