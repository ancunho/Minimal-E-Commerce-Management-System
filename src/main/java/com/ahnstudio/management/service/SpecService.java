package com.ahnstudio.management.service;

import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.pojo.Spec;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface SpecService {

    ServerResponse saveOrUpdateSpec(Spec spec);

    ServerResponse saveSpec(Spec spec);

    ServerResponse updateSpec(Spec spec);

    ServerResponse unActiveSpec(Spec spec);

    Map<String, Object> getSpecList(int pageNum, int pageSize);

    List<Spec> getSpecListByProductId(Integer productId);

}
