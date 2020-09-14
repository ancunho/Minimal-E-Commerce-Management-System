package com.ahnstudio.management.controller.backend;

import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author : Cunho
 * @date : 2020/08/04
 */
@Slf4j
@RestController
@RequestMapping("/api/category/")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Category Create New
     * @param session
     * @param categoryName
     * @param parentId
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse add_category(HttpSession session, String categoryName, @RequestParam(value = "parentId",defaultValue = "0")  Integer parentId) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        if (user == null) {
//            return ServerResponse.createByErrorMessage(Const.Message.NEED_LOGIN);
//        }

        return categoryService.addCategory(categoryName, parentId);
    }

    @RequestMapping(value = "update_name", method = RequestMethod.POST)
    public ServerResponse set_category_name(HttpSession session, Integer categoryId, String categoryNewName) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        if (user == null) {
//            return ServerResponse.createByErrorMessage(Const.Message.NEED_LOGIN);
//        }

        return categoryService.setCategoryName(categoryId, categoryNewName);
    }

    @RequestMapping(value = "get_child_category", method = RequestMethod.POST)
    public ServerResponse getChildrenParallelCategory(HttpSession session,@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId){
//        User user = (User)session.getAttribute(Const.CURRENT_USER);
//        if(user == null){
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), Const.Message.NEED_LOGIN);
//        }

        return categoryService.getChildrenParallelCategory(categoryId);
    }

    @RequestMapping("get_deep_category")
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId){
//        User user = (User)session.getAttribute(Const.CURRENT_USER);
//        if(user == null){
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), Const.Message.NEED_LOGIN);
//        }
        // 查询当前节点的id和递归子节点的id
        // 0->10000->100000
        return categoryService.selectCategoryAndChildrenById(categoryId);
    }


}
