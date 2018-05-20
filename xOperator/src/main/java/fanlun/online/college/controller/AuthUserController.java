package fanlun.online.college.controller;

import fanlun.online.college.core.auth.domain.AuthUser;
import fanlun.online.college.core.auth.service.IAuthUserService;
import fanlun.online.college.page.TailPage;
import fanlun.online.college.web.JsonView;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/user")
public class AuthUserController {

    @Autowired
    private IAuthUserService entityService;

    @RequestMapping(value = "/getById")
    @ResponseBody
    public String getById(Long id) {
        AuthUser user = entityService.getById(id);
        return JsonView.render(user);
    }

    /**
     * 分页
     */
    @RequestMapping(value = "/userPageList")
    public ModelAndView queryPage(AuthUser queryEntity, TailPage<AuthUser> page) {
        ModelAndView mv = new ModelAndView("cms/user/userPageList");
        mv.addObject("curNav", "user");

        if (StringUtils.isNotEmpty(queryEntity.getUsername())) {
            queryEntity.setUsername(queryEntity.getUsername().trim());
        } else {
            queryEntity.setUsername(null);
        }

        if (Integer.valueOf(-1).equals(queryEntity.getStatus())) {
            queryEntity.setStatus(null);
        }

        page = entityService.queryPage(queryEntity, page);
        mv.addObject("page", page);
        mv.addObject("queryEntity", queryEntity);

        return mv;
    }

    @RequestMapping(value = "/doMerge")
    @ResponseBody
    public String doMerge(AuthUser entity) {
        entity.setUsername(null);//防止更新登录名
        entity.setRealname(null);//防止更新姓名，而只通过id更新
        entityService.updateSelectivity(entity);
        return new JsonView(0).toString();
    }

}

