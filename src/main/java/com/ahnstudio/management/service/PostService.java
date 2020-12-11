package com.ahnstudio.management.service;

import com.ahnstudio.management.pojo.BlogPostsWithBLOBs;
import com.ahnstudio.management.common.ServerResponse;

public interface PostService {

    ServerResponse createNewPost(BlogPostsWithBLOBs blogPost);

    ServerResponse selectAllPosts(int pageNum, int pageSize);

    ServerResponse selectPostsById(Integer postId);

    ServerResponse updatePost(BlogPostsWithBLOBs blogPost);

    ServerResponse deletePost(BlogPostsWithBLOBs blogPost);

}
