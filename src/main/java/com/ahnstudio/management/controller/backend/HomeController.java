package com.ahnstudio.management.controller.backend;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.List;

public class HomeController {


    public static void main(String[] args) throws Exception {
        System.out.println(">>>>>>>>>");
        String jsonString = "[{\"weight\":\"1\",\"deliveryType\":\"快递\",\"price\":\"100\",\"vipPrice\":\"100\",\"stock\":\"100\",\"status\":\"\"}]";
        System.out.println(jsonString);

        List<String> arrayString = JSON.parseArray(jsonString,String.class);

        System.out.println(arrayString.size());
        System.out.println(JSON.toJSON(arrayString.get(0)));

        JSONArray jsonArray = new JSONArray(Integer.parseInt(jsonString));
        System.out.println(jsonArray);






    }



}
