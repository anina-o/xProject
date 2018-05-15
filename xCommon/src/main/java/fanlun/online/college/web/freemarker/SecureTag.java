package fanlun.online.college.web.freemarker;

import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.io.IOException;
import java.util.Map;

public abstract class SecureTag implements TemplateDirectiveModel {
    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        verifyParameters(map);
        render(environment, map, templateDirectiveBody);
    }

    public abstract void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException;

    private void verifyParameters(Map map) throws TemplateModelException {
    }

    protected String getParam(Map params, String name) {
        Object value = params.get(name);

        if (value instanceof SimpleScalar) {
            return ((SimpleScalar) value).getAsString();
        }
        return null;
    }

    protected Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    protected void renderBody(Environment env, TemplateDirectiveBody body) throws IOException, TemplateException {
        if (body != null) {
            body.render(env.getOut());
        }
    }
}
