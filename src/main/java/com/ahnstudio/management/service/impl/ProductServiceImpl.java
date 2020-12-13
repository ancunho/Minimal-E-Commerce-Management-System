package com.ahnstudio.management.service.impl;

import com.ahnstudio.management.common.Const;
import com.ahnstudio.management.common.ResponseCode;
import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.dao.CategoryMapper;
import com.ahnstudio.management.dao.ProductMapper;
import com.ahnstudio.management.dao.SpecMapper;
import com.ahnstudio.management.pojo.Product;
import com.ahnstudio.management.pojo.Spec;
import com.ahnstudio.management.pojo.User;
import com.ahnstudio.management.service.ProductService;
import com.ahnstudio.management.vo.ProudctDetailVO;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private SpecMapper specMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse saveOrUpdateProduct(Product product) {
        if (product != null) {
            if(product.getId() != null){
                int rowCount = productMapper.updateByPrimaryKey(product);
                if(rowCount > 0){
                    if ((product.getSpec() != null) && (JSONArray.parseArray(product.getSpec(), Spec.class).size() > 0)) {
                        // 1. 먼저 이 상품의 모든 스펙을 삭제한다.
                        int deleteCount = specMapper.deleteSpecByProductId(product.getId());
                        // 2. 다음 넣는다.
                        List<Spec> specList = JSONArray.parseArray(product.getSpec(), Spec.class);
                        for (Spec specItem : specList) {
                            specItem.setProductId(product.getId());
                            specItem.setProductName(product.getName());
                            specMapper.insert(specItem);
                        }
                    }
                    return ServerResponse.createBySuccess(Const.Message.UPDATE_OK);
                }
                return ServerResponse.createByErrorMessage(Const.Message.UPDATE_ERROR);
            }else{
                // 新增加
                int insertCount = productMapper.insertAfterId(product);

                if(insertCount > 0){
                    if (JSONArray.parseArray(product.getSpec(), Spec.class).size() > 0) {
                        List<Spec> specList = JSONArray.parseArray(product.getSpec(), Spec.class);
                        for (Spec specItem : specList) {
                            specItem.setProductId(product.getId());
                            specItem.setProductName(product.getName());
                            specMapper.insert(specItem);
                        }
                    }
                    return ServerResponse.createBySuccess(Const.Message.SAVE_OK);
                }
                return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), "规格保存失败");
            }
        }
        return ServerResponse.createByErrorMessage(Const.Message.PARAMETER_ERROR);
    }

    @Override
    public ServerResponse<String> setSalesStatus(Integer productId, Integer status) {
        return null;
    }

    @Override
    public Product getProductDetail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        return product;
    }

    @Override
    public ServerResponse<ProudctDetailVO> getProudctDetailInManage(Integer productId) {
        return null;
    }

    @Override
    public Map<String, Object> getProductList(int pageNum, int pageSize) {
        List<Product> productList = productMapper.selectAllProduct();
        Map<String, Object> result = new HashMap<>();
        result.put("total", productList.size());
        result.put("rows", productList);
        result.put("code", 0);
        return result;
    }

    @Override
    public ServerResponse getProductListForAdmin(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Product> productList = productMapper.selectAllProduct();
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse<PageInfo> searchProduct(String productName, int pageNum, int pageSize) {
        return null;
    }


    /*********************************************************************
     ***************************** Portal API ****************************
     *********************************************************************/

    @Override
    public ServerResponse getProductListForWechat(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Product> productList = productMapper.selectAllProductForWechat();
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse getProductDetailForWechat(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage(Const.Message.SELECT_ERROR);
        }

        return ServerResponse.createBySuccess(product);
    }

}
