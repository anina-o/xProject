package fanlun.online.college.core.statics.dao;

import fanlun.online.college.core.statics.domain.CourseStudyStaticsDto;

import java.util.List;


public interface CourseStudyStaticsDao {
	
	/**
	*统计课程学习情况
	**/
	public List<CourseStudyStaticsDto> queryCourseStudyStatistics(CourseStudyStaticsDto queryEntity);
	
}

