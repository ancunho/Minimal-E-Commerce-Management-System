package com.ahnstudio.management.blog.dao;

import com.ahnstudio.management.blog.pojo.BlogPosts;
import com.ahnstudio.management.blog.pojo.BlogPostsWithBLOBs;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("blogPostsMapper")
public interface BlogPostsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BlogPostsWithBLOBs record);

    int insertSelective(BlogPostsWithBLOBs record);

    BlogPostsWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BlogPostsWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(BlogPostsWithBLOBs record);

    int updateByPrimaryKey(BlogPosts record);

    List<BlogPosts> selectAllPosts();
}