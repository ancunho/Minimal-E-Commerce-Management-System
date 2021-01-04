package com.ahnstudio.management.service.impl;

import com.ahnstudio.management.common.Const;
import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.dao.ShippingMapper;
import com.ahnstudio.management.pojo.Shipping;
import com.ahnstudio.management.service.ShippingService;
import com.ahnstudio.management.vo.ShippingVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse add(Shipping shipping) {
        if (shipping.getUserId() == null) {
            return ServerResponse.createByErrorMessage(Const.Message.PARAMETER_ERROR);
        }

        int defaultCount = shippingMapper.selectDefaultCount(shipping.getUserId());
        if (defaultCount > 0) {
            shippingMapper.updateDefaultShippingByUserId(shipping);
        } else {
            shipping.setIsDefault("1");
        }

        int insertCount = shippingMapper.insert(shipping);
        if (insertCount > 0) {
            return ServerResponse.createBySuccessMessage(Const.Message.SAVE_OK);
        }
        return ServerResponse.createByErrorMessage(Const.Message.SAVE_ERROR);
    }

    @Override
    public ServerResponse selectListByUserId(Integer userId) {
        if (userId == null) {
            return ServerResponse.createByErrorMessage(Const.Message.PARAMETER_ERROR);
        }
        List<Shipping> shippingList = shippingMapper.selectListByUserId(userId);
        return ServerResponse.createBySuccess(shippingList);
    }

    @Override
    public ServerResponse selectDefaultShippingByUserId(Integer userId) {
        if (userId == null) {
            return ServerResponse.createByErrorMessage(Const.Message.PARAMETER_ERROR);
        }

        Shipping shipping = shippingMapper.selectDefaultShippingByUserId(userId);
        return ServerResponse.createBySuccess(shipping);
    }

    @Override
    public ServerResponse updateShipping(Shipping shipping) {
        if (StringUtils.isEmpty(String.valueOf(shipping.getId())) || StringUtils.isEmpty(String.valueOf(shipping.getUserId()))) {
            return ServerResponse.createByErrorMessage(Const.Message.PARAMETER_ERROR);
        }

        int defaultCount = shippingMapper.selectDefaultCount(shipping.getUserId());
        if (defaultCount > 0) {
            shippingMapper.updateDefaultShippingByUserId(shipping);
        } else {
            shipping.setIsDefault("1");
        }
        int updateCount = shippingMapper.updateShipping(shipping);
        if (updateCount > 0) {
            return ServerResponse.createBySuccessMessage(Const.Message.UPDATE_OK);
        }
        return ServerResponse.createByErrorMessage(Const.Message.UPDATE_ERROR);
    }

    @Override
    public ServerResponse deleteByShippingIdUserId(Integer userId, Integer shippingId) {
        int deleteCount = shippingMapper.deleteByShippingIdUserId(userId, shippingId);
        if (deleteCount > 0) {
            return ServerResponse.createBySuccessMessage(Const.Message.DELETE_OK);
        }
        return ServerResponse.createByErrorMessage(Const.Message.DELETE_ERROR);
    }

    @Override
    public ServerResponse selectShipByPk(Integer shippingId) {
        Shipping shipping = shippingMapper.selectByPrimaryKey(shippingId);
        return ServerResponse.createBySuccess(shipping);
    }
}
