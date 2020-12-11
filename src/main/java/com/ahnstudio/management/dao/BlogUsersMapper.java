package com.ahnstudio.management.dao;

import com.ahnstudio.management.pojo.BlogUsers;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component("blogUsersMapper")
public interface BlogUsersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BlogUsers record);

    int insertSelective(BlogUsers record);

    BlogUsers selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BlogUsers record);

    int updateByPrimaryKey(BlogUsers record);
}