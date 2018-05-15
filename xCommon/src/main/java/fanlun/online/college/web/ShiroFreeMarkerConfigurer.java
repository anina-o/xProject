package fanlun.online.college.web;


import fanlun.online.college.web.freemarker.ShiroTags;
import freemarker.template.TemplateException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

/**
 * Shiro FreeMarker 整合
 */
public class ShiroFreeMarkerConfigurer extends FreeMarkerConfigurer{
    @Override
    public void afterPropertiesSet() throws IOException, TemplateException {
        super.afterPropertiesSet();
        this.getConfiguration().setSharedVariable("shiro", new ShiroTags());//shiro标签
    }
}
