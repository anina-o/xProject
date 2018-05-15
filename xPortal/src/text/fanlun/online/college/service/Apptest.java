package fanlun.online.college.service;

import fanlun.online.college.test.dao.TestDao;
import fanlun.online.college.web.SpringBeanFactory;
import junit.framework.TestCase;
import org.apache.log4j.Logger;

import java.util.Map;


public class Apptest extends TestCase {
    Logger logger = Logger.getLogger(Apptest.class);

    public void testApp(){
        TestDao testDao = (TestDao) SpringBeanFactory.getBean("testDao");
        Map<String, Object> map = testDao.testQuery();
        logger.info("### 当前时间 = " + map.get("curdate"));
    }

}
