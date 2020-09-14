package com.ahnstudio.management.service.impl;

import com.ahnstudio.management.common.Const;
import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.dao.CategoryMapper;
import com.ahnstudio.management.pojo.Category;
import com.ahnstudio.management.service.CategoryService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse addCategory(String categoryName,Integer parentId) {
        // 1. parentid null 체크 && categoryName공백체크
        if (parentId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMessage(Const.Message.PARAMETER_ERROR);
        }

        // 1-1. 중복체크
        Integer exist = categoryMapper.checkRepeatCategoryName(categoryName);
        if (exist != null) {
            return ServerResponse.createByErrorMessage(Const.Message.DATA_EXIST);
        }

        // 2. Category오브젝트에 각 데이터값 담는다.
        Category category = new Category();
        category.setParentId(parentId);
        category.setName(categoryName);
        category.setStatus(Const.BooleanStatus.ACTIVE);
        category.setSortOrder(0);

        // 3. 저장한다.
        int insertCount = categoryMapper.insert(category);

        // 4. 리턴
        if (insertCount > 0) {
            return ServerResponse.createBySuccessMessage(Const.Message.SAVE_OK);
        }

        return ServerResponse.createByErrorMessage(Const.Message.SAVE_ERROR);

    }

    @Override
    public ServerResponse setCategoryName(Integer categoryId, String categoryNewName) {
        if (categoryId == null || StringUtils.isBlank(categoryNewName)) {
            return ServerResponse.createByErrorMessage(Const.Message.PARAMETER_ERROR);
        }

        // 1-1. 중복체크
        Integer exist = categoryMapper.checkRepeatCategoryName(categoryNewName);
        if (exist != null) {
            return ServerResponse.createByErrorMessage(Const.Message.DATA_EXIST);
        }

        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryNewName);

        int updateCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (updateCount > 0) {
            return ServerResponse.createBySuccessMessage(Const.Message.UPDATE_OK);
        }

        return ServerResponse.createByErrorMessage(Const.Message.UPDATE_ERROR);
    }

    @Override
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId) {
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if(CollectionUtils.isEmpty(categoryList)){
            log.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    /**
     * 递归查询本节点的id及孩子节点的id
     * @param categoryId
     * @return
     */
    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId){
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet,categoryId);


        List<Integer> categoryIdList = Lists.newArrayList();
        if(categoryId != null){
            for(Category categoryItem : categorySet){
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }


    //递归算法,算出子节点
    private Set<Category> findChildCategory(Set<Category> categorySet ,Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category != null){
            categorySet.add(category);
        }
        //查找子节点,递归算法一定要有一个退出的条件
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for(Category categoryItem : categoryList){
            findChildCategory(categorySet,categoryItem.getId());
        }
        return categorySet;
    }
}
