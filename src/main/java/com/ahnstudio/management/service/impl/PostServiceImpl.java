package com.ahnstudio.management.service.impl;

import com.ahnstudio.management.dao.BlogPostsMapper;
import com.ahnstudio.management.pojo.BlogPosts;
import com.ahnstudio.management.pojo.BlogPostsWithBLOBs;
import com.ahnstudio.management.service.PostService;
import com.ahnstudio.management.common.Const;
import com.ahnstudio.management.common.ServerResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private BlogPostsMapper blogPostsMapper;

    @Override
    public ServerResponse createNewPost(BlogPostsWithBLOBs blogPost) {
        if (blogPost == null) {
            return ServerResponse.createByErrorMessage(Const.Message.PARAMETER_ERROR);
        }

        int insertCount = blogPostsMapper.insert(blogPost);
        if (insertCount > 0) {
            return ServerResponse.createBySuccessMessage(Const.Message.SAVE_OK);
        }

        return ServerResponse.createByErrorMessage(Const.Message.SAVE_ERROR);
    }

    @Override
    public ServerResponse selectPostsById(Integer postId) {
        if (postId == null) {
            ServerResponse.createByErrorMessage(Const.Message.PARAMETER_ERROR);
        }

        BlogPostsWithBLOBs post = blogPostsMapper.selectByPrimaryKey(Long.valueOf(postId));

        if (post == null) {
            return ServerResponse.createByErrorMessage(Const.Message.SELECT_ERROR);
        }

        return ServerResponse.createByErrorMessage(Const.Message.SELECT_OK);
    }

    @Override
    public ServerResponse selectAllPosts(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BlogPosts> blogPostsList = blogPostsMapper.selectAllPosts();
        PageInfo pageResult = new PageInfo(blogPostsList);
        pageResult.setList(blogPostsList);

        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse updatePost(BlogPostsWithBLOBs blogPost) {
        if (blogPost == null) {
            return ServerResponse.createByErrorMessage(Const.Message.PARAMETER_ERROR);
        }

        int updateCount = blogPostsMapper.updateByPrimaryKey(blogPost);
        if (updateCount > 0) {
            return ServerResponse.createBySuccessMessage(Const.Message.UPDATE_OK);
        }

        return ServerResponse.createByErrorMessage(Const.Message.UPDATE_ERROR);
    }

    @Override
    public ServerResponse deletePost(BlogPostsWithBLOBs blogPost) {
        return null;
    }



}
