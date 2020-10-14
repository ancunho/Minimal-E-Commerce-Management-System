package com.ahnstudio.management.dao;

import com.ahnstudio.management.pojo.Shipping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("shippingMapper")
public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    List<Shipping> selectListByUserId(@Param("userId") Integer userId);

    Shipping selectDefaultShippingByUserId(@Param("userId") Integer userId);

    int selectDefaultCount(@Param("userId") Integer userId);

    int updateDefaultShippingByUserId(Shipping shipping);

    int updateShipping(Shipping shipping);

    int deleteByShippingIdUserId(@Param("userId") Integer userId, @Param("shippingId") Integer shippingId);
}