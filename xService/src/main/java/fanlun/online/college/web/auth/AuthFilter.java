package fanlun.online.college.web.auth;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fanlun.online.college.util.JsonUtil;
import fanlun.online.college.web.HttpHelper;
import fanlun.online.college.web.JsonView;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;


/**
 * shiro 对用户是否登录的 filter
 * shiro提供的FormAuthenticationFilter认证过滤器，继承了AuthenticatingFilter ，若已登录则isAccessAllowed直接通过，否则在 onAccessDenied中判断是否是登录请求，若是请求登录页面，直接通过，若是post提交登录信息则会进行登录操作。否则直接跳转到登录页面。登录是由shiro的securityManager完成的，securityManager从Realm获取用户的真实身份，从FormAuthenticationFilter的createToken获取用户提交的token，credentialsMatcher完成是否匹配成功操作。
 */
public class AuthFilter extends FormAuthenticationFilter {
    private static final Integer SHIRO_TIME_OUT = 1001;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return super.isAccessAllowed(request, response, mappedValue);
    }

    //认证方法
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpservletrequest = (HttpServletRequest) request;
        // 获取请求路径
        String login = httpservletrequest.getServletPath();
        // 判断请求路径是否为登录页 如果为登录页放行
        if (login.equals("/index.html")) {
            return true;
        }
        // 获取当前登录用户
        Subject subject = getSubject(request, response);
        // 判断是否认证，即是否已登陆
        if (subject.isAuthenticated()) {
            return true;
        }

        // 判断是否为ajax请求
        if (HttpHelper.isAjaxRequest(httpservletrequest)) {
            JsonView jv = new JsonView();
            jv.setMessage("SHIRO登录超时");
            jv.setErrcode(SHIRO_TIME_OUT);
            HttpServletResponse _response = (HttpServletResponse) response;
            PrintWriter pw = _response.getWriter();
            _response.setContentType("application/json");
            pw.write(JsonUtil.toJson(jv));
            pw.flush();
            pw.close();
        } else {
            saveRequestAndRedirectToLogin(request, response);
        }

        // 如果没有授权则跳转到登录页面
        return false;
    }

}
