package fanlun.online.college.business.impl;

import fanlun.online.college.business.IPortalBusiness;
import fanlun.online.college.core.consts.CourseEnum;
import fanlun.online.college.core.consts.domain.ConstsClassify;
import fanlun.online.college.core.consts.service.IConstsClassifyService;
import fanlun.online.college.core.course.domain.Course;
import fanlun.online.college.core.course.domain.CourseQueryDto;
import fanlun.online.college.core.course.domain.CourseSection;
import fanlun.online.college.core.course.service.ICourseSectionService;
import fanlun.online.college.core.course.service.ICourseService;
import fanlun.online.college.vo.ConstsClassifyVO;
import fanlun.online.college.vo.CourseSectionVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * 首页业务层
 */
@Service
public class PortalBusinessImpl implements IPortalBusiness {

    @Autowired
    private IConstsClassifyService constsClassifyService;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private ICourseSectionService courseSectionService;

    /**
     * 获取所有，包括一级分类&二级分类
     */
    public List<ConstsClassifyVO> queryAllClassify() {
        List<ConstsClassifyVO> resultList = new ArrayList<ConstsClassifyVO>();
        for (ConstsClassifyVO vo : this.queryAllClassifyMap().values()) {
            resultList.add(vo);
        }
        return resultList;
    }

    /**
     * 获取所有分类
     */
    public Map<String, ConstsClassifyVO> queryAllClassifyMap() {
        Map<String, ConstsClassifyVO> resultMap = new LinkedHashMap<String, ConstsClassifyVO>();
        Iterator<ConstsClassify> it = constsClassifyService.queryAll().iterator();
        while (it.hasNext()) {
            ConstsClassify c = it.next();
            /**
             * 因为按parent_code排序，所以一级分类先全部加入map中，且一级分类的课程进行了VO包装带有子课程的list。
             */
            if ("0".equals(c.getParentCode())) {//一级分类
                ConstsClassifyVO vo = new ConstsClassifyVO();
                BeanUtils.copyProperties(c, vo);
                resultMap.put(vo.getCode(), vo);
            } else {//二级分类
                if (null != resultMap.get(c.getParentCode())) {
                    //map中取出父课程，取出list加入当前遍历到的课程
                    resultMap.get(c.getParentCode()).getSubClassifyList().add(c);
                }
            }
        }
        return resultMap;
    }


    /**
     * 获取课程章节
     */
    @Override
    public List<CourseSectionVO> queryCourseSection(Long courseId) {
        List<CourseSectionVO> resultList = new ArrayList<CourseSectionVO>();
        CourseSection queryEntity = new CourseSection();
        queryEntity.setCourseId(courseId);
        queryEntity.setOnsale(CourseEnum.ONSALE.value());//上架

        Map<Long,CourseSectionVO> tmpMap = new LinkedHashMap<Long,CourseSectionVO>();
        Iterator<CourseSection> it = courseSectionService.queryAll(queryEntity).iterator();
        while(it.hasNext()){
            CourseSection item = it.next();
            if(Long.valueOf(0).equals(item.getParentId())){//章
                CourseSectionVO vo = new CourseSectionVO();
                BeanUtils.copyProperties(item, vo);
                tmpMap.put(vo.getId(), vo);
            }else{
                tmpMap.get(item.getParentId()).getSections().add(item);//小节添加到大章中
            }
        }
        for(CourseSectionVO vo : tmpMap.values()){
            resultList.add(vo);
        }
        return resultList;
    }

    /**
     * 为分类设置课程推荐
     */
    public void prepareRecomdCourses(List<ConstsClassifyVO> classifyVoList) {
        if (CollectionUtils.isNotEmpty(classifyVoList)) {
            for (ConstsClassifyVO item : classifyVoList) {
                CourseQueryDto queryEntity = new CourseQueryDto();
                queryEntity.setCount(5);
                queryEntity.descSortField("weight");
                queryEntity.setClassify(item.getCode());//分类code

                List<Course> tmpList = this.courseService.queryList(queryEntity);
                if (CollectionUtils.isNotEmpty(tmpList)) {
                    item.setRecomdCourseList(tmpList);
                }
            }
        }
    }

}
