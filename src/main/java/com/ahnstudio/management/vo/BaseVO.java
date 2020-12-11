package com.ahnstudio.management.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class BaseVO implements Serializable {

    private String startDate;
    private String endDate;

    private long rn = 0;
    private long pageNo = 0;
    private long startRowPostion = 0;
    private long pageRowCount = 0;
    private long allCount = 0;

    private String errCd = "";
    private String errMsg = "";
    private String sessionId = "";
    private String ipAddr = "";
    private String userAgent = "";
    private String referer = "";


}
