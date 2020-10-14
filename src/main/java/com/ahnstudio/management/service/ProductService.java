package com.ahnstudio.management.service;

import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.pojo.Product;
import com.ahnstudio.management.vo.ProudctDetailVO;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface ProductService {

    /**
     * 신규 제품 생성 혹은 업데이트
     * @param product
     * @return
     */
    ServerResponse saveOrUpdateProduct(Product product);

    /**
     * 제품 상태 바꾸기
     * @param productId
     * @param status
     * @return
     */
    ServerResponse<String> setSalesStatus(Integer productId, Integer status);

    /**
     * Prouduct Detail By productId
     * Front-End
     * @param productId
     * @return
     */
    Product getProductDetail(Integer productId);

    /**
     * Proudct Detail By productId
     * Back-End
     * @param productId
     * @return
     */
    ServerResponse<ProudctDetailVO> getProudctDetailInManage(Integer productId);

    /**
     * 전체 제품 리스트
     * @param pageNum
     * @param pageSize
     * @return
     */
    Map<String, Object> getProductList(int pageNum, int pageSize);

    ServerResponse getProductListForAdmin(int pageNum, int pageSize);

    /**
     * 검색해서
     * @param productName
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> searchProduct(String productName, int pageNum,int pageSize);



    /*********************************************************************
     ***************************** Portal API ****************************
     *********************************************************************/

    ServerResponse getProductListForWechat(int pageNum, int pageSize);

    ServerResponse getProductDetailForWechat(Integer productId);

}
