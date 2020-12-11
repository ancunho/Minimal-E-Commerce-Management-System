package com.ahnstudio.management.dao;

import com.ahnstudio.management.pojo.BlogContact;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component("blogContactMapper")
public interface BlogContactMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BlogContact record);

    int insertSelective(BlogContact record);

    BlogContact selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BlogContact record);

    int updateByPrimaryKey(BlogContact record);
}