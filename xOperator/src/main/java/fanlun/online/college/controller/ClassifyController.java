package fanlun.online.college.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fanlun.online.college.business.IPortalBusiness;
import fanlun.online.college.core.consts.domain.ConstsClassify;
import fanlun.online.college.core.consts.service.IConstsClassifyService;
import fanlun.online.college.page.TailPage;
import fanlun.online.college.vo.ConstsClassifyVO;
import fanlun.online.college.web.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 * 课程分类管理
 */
@Controller
@RequestMapping("/classify")
public class ClassifyController {

    @Autowired
    private IConstsClassifyService entityService;

    @Autowired
    private IPortalBusiness portalBusiness;


    @RequestMapping(value = "/getById")
    @ResponseBody
    public String getById(Long id) {
        return JsonView.render(entityService.getById(id));
    }

    @RequestMapping(value = "/index")
    public ModelAndView classifyIndex(ConstsClassify queryEntity, TailPage<ConstsClassify> page) {
        ModelAndView mv = new ModelAndView("cms/classify/classifyIndex");
        mv.addObject("curNav", "classify");
        Map<String, ConstsClassifyVO> classifyMap = portalBusiness.queryAllClassifyMap();

        //所有一级分类
        List<ConstsClassifyVO> classifysList = new ArrayList<ConstsClassifyVO>();
        for (ConstsClassifyVO vo : classifyMap.values()) {
            classifysList.add(vo);
        }
        mv.addObject("classifys", classifysList);

        List<ConstsClassify> subClassifys = new ArrayList<ConstsClassify>();
        for (ConstsClassifyVO vo : classifyMap.values()) {
            subClassifys.addAll(vo.getSubClassifyList());
        }
        mv.addObject("subClassifys", subClassifys);//所有二级分类

        return mv;
    }

    @RequestMapping(value = "/doMerge")
    @ResponseBody
    public String doMerge(ConstsClassify entity) {
        if (entity.getId() == null) {
            // **** 添加 *****
            ConstsClassify tmpEntity = entityService.getByCode(entity.getCode());
            if (tmpEntity != null) {
                return JsonView.render(1, "此编码已存在");
            }
            entityService.createSelectivity(entity);
        } else {
            // **** 修改 ****
            entityService.updateSelectivity(entity);
        }
        return new JsonView().toString();
    }

    @RequestMapping(value = "/deleteLogic")
    @ResponseBody
    public String deleteLogic(ConstsClassify entity) {
        entityService.deleteLogic(entity);
        return new JsonView().toString();
    }


}

