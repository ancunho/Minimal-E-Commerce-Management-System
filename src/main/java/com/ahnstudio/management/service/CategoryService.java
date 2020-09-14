package com.ahnstudio.management.service;

import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.pojo.Category;

import java.util.List;

public interface CategoryService {

    /**
     * 카테고리 추가
     * @param categoryName 카테고리명
     * @param parentId 부모ID
     * @return
     */
    ServerResponse addCategory(String categoryName, Integer parentId);

    /**
     * 카테고리 아이디로 카테고리명 바꾸기
     * @param categoryId
     * @param categoryNewName
     * @return
     */
    ServerResponse setCategoryName(Integer categoryId, String categoryNewName);

    /**
     * 자식카테고리 가져오기
     * @param categoryId
     * @return
     */
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    /**
     * 자식포함, 전체 카테고리 가져오기
     * @param categoryId
     * @return
     */
    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);



}
