package com.ahnstudio.management.dao;

import com.ahnstudio.management.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("categoryMapper")
public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    Integer checkRepeatCategoryName(String categoryName);

    List<Category> selectCategoryChildrenByParentId(Integer parentId);
}