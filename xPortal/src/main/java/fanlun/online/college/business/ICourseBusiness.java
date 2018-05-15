package fanlun.online.college.business;

import fanlun.online.college.vo.CourseSectionVO;

import java.util.List;


public interface ICourseBusiness {

	/**
	 * 获取课程章节
	 */
	List<CourseSectionVO> queryCourseSection(Long courseId);
	
}
