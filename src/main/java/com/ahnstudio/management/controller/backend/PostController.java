package com.ahnstudio.management.controller.backend;

import com.ahnstudio.management.pojo.BlogPostsWithBLOBs;
import com.ahnstudio.management.service.PostService;
import com.ahnstudio.management.common.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author : Cunho
 * @date : 2020/10/15
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/blog/post/")
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping("create")
    @ResponseBody
    public ServerResponse create_new_post(HttpSession session, @RequestBody BlogPostsWithBLOBs blogPosts) {
        return postService.createNewPost(blogPosts);
    }

    @RequestMapping("list")
    @ResponseBody
    public ServerResponse post_list(HttpSession session, @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        return postService.selectAllPosts(pageNum, pageSize);
    }

    @RequestMapping("detail")
    @ResponseBody
    public ServerResponse post_detail(HttpSession session, Integer postId) {
        return postService.selectPostsById(postId);
    }

    @RequestMapping("update")
    @ResponseBody
    public ServerResponse post_update(HttpSession session, @RequestBody BlogPostsWithBLOBs blogPost) {
        return postService.updatePost(blogPost);
    }

}
