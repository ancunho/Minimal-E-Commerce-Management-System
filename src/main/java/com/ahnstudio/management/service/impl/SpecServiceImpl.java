package com.ahnstudio.management.service.impl;

import com.ahnstudio.management.common.Const;
import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.dao.SpecMapper;
import com.ahnstudio.management.pojo.Spec;
import com.ahnstudio.management.service.SpecService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SpecServiceImpl implements SpecService {

    @Autowired
    private SpecMapper specMapper;

    @Override
    public ServerResponse saveOrUpdateSpec(Spec spec) {
        if (spec.getWeight() == null || spec.getDeliveryType() == null || spec.getPrice() == null) {
            return ServerResponse.createByErrorMessage(Const.Message.PARAMETER_ERROR);
        }

        if (spec.getId() == null) {
            spec.setStatus(1);
            int insertCount = specMapper.insert(spec);
            if (insertCount == 0) {
                return ServerResponse.createByErrorMessage(Const.Message.SAVE_ERROR);
            }
            return ServerResponse.createBySuccessMessage(Const.Message.SAVE_OK);
        } else {
            int updateCount = specMapper.updateByPrimaryKeySelective(spec);
            if (updateCount == 0) {
                return ServerResponse.createByErrorMessage(Const.Message.UPDATE_ERROR);
            }
            return ServerResponse.createBySuccessMessage(Const.Message.UPDATE_OK);
        }
    }

    /**
     * 규격 저장.
     * @param spec
     * @return
     */
    @Override
    public ServerResponse saveSpec(Spec spec) {
        if (spec.getWeight() == null || spec.getDeliveryType() == null || spec.getPrice() == null) {
            return ServerResponse.createByErrorMessage(Const.Message.PARAMETER_ERROR);
        }

        spec.setStatus(1);

        int insertCount = specMapper.insert(spec);
        if (insertCount == 0) {
            return ServerResponse.createByErrorMessage(Const.Message.SAVE_ERROR);
        }
        return ServerResponse.createBySuccessMessage(Const.Message.SAVE_OK);
    }

    @Override
    public ServerResponse updateSpec(Spec spec) {
        if (spec.getWeight() == null || spec.getDeliveryType() == null || spec.getPrice() == null) {
            return ServerResponse.createByErrorMessage(Const.Message.PARAMETER_ERROR);
        }

        int updateCount = specMapper.updateByPrimaryKeySelective(spec);
        if (updateCount == 0) {
            return ServerResponse.createByErrorMessage(Const.Message.UPDATE_ERROR);
        }
        return ServerResponse.createBySuccessMessage(Const.Message.UPDATE_OK);
    }

    @Override
    public ServerResponse unActiveSpec(Spec spec) {
        if (spec.getId() == null) {
            return ServerResponse.createByErrorMessage(Const.Message.UPDATE_ERROR);
        }

        spec.setStatus(0);
        int updateCount = specMapper.updateByPrimaryKeySelective(spec);
        if (updateCount == 0) {
            return ServerResponse.createByErrorMessage(Const.Message.UPDATE_ERROR);
        }
        return ServerResponse.createBySuccessMessage(Const.Message.UPDATE_OK);
    }

    @Override
    public Map<String, Object> getSpecList(int pageNum, int pageSize) {
//        PageHelper.startPage(pageNum, pageSize);
//        List<Spec> specList = specMapper.selectAllSpec();
//        PageInfo pageInfo = new PageInfo(specList);
        List<Spec> specList = specMapper.selectAllSpec();


        Map<String, Object> result = new HashMap<>();
        result.put("total", specList.size());
        result.put("rows", specList);

        return result;
    }

    @Override
    public List<Spec> getSpecListByProductId(Integer productId) {
        return specMapper.getSpecListByProductId(productId);
    }
}
