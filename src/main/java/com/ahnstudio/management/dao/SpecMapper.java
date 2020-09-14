package com.ahnstudio.management.dao;

import com.ahnstudio.management.pojo.Spec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("specMapper")
public interface SpecMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Spec record);

    int insertAfterId(Spec record);

    int insertSelective(Spec record);

    Spec selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Spec record);

    int updateByPrimaryKey(Spec record);

    List<Spec> selectAllSpec();

    int deleteSpecByProductId(Integer productId);

    List<Spec> getSpecListByProductId(@Param("productId") Integer productId);
}