package com.ahnstudio.management.controller.backend;

import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.pojo.Spec;
import com.ahnstudio.management.service.SpecService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author : Cunho
 * @date : 2020/08/19
 */
@Slf4j
@RestController
@RequestMapping("/api/spec/")
public class SpecController {

    @Autowired
    private SpecService specService;

    @RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse save_spec(HttpSession session, Spec spec) {
        return specService.saveOrUpdateSpec(spec);
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> spec_list(HttpSession session, @RequestParam(value = "pageNumber", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return specService.getSpecList(pageNum, pageSize);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ServerResponse delete_spec(HttpSession session, @RequestBody Spec spec) {
        return specService.unActiveSpec(spec);
    }

    @RequestMapping(value = "getSpecListByProductId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ServerResponse getSpecListByProductId(HttpSession session, @RequestParam(value = "productId") Integer productId) {
        log.info(">>>>>" + productId);
        List<Spec> specList = specService.getSpecListByProductId(productId);
        return ServerResponse.createBySuccess(specList);
    }


}
