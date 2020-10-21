package com.ahnstudio.management.blog.dao;

import com.ahnstudio.management.blog.pojo.BlogContact;

public interface BlogContactMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BlogContact record);

    int insertSelective(BlogContact record);

    BlogContact selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BlogContact record);

    int updateByPrimaryKey(BlogContact record);
}