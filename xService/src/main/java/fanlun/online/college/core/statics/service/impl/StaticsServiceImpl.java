package fanlun.online.college.core.statics.service.impl;

import java.util.ArrayList;
import java.util.List;

import fanlun.online.college.core.statics.dao.CourseStudyStaticsDao;
import fanlun.online.college.core.statics.domain.CourseStudyStaticsDto;
import fanlun.online.college.core.statics.domain.StaticsVO;
import fanlun.online.college.core.statics.service.IStaticsService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 统计分析
 */
@Service
public class StaticsServiceImpl implements IStaticsService {

	@Autowired
	private CourseStudyStaticsDao entityDao;
	
	@Override
	public StaticsVO queryCourseStudyStatistics(CourseStudyStaticsDto queryEntity) {
		List<CourseStudyStaticsDto> list = entityDao.queryCourseStudyStatistics(queryEntity);
		
		StaticsVO returnVo = new StaticsVO();
		List<String> categories = new ArrayList<String>();
		List<Integer> data = new ArrayList<Integer>();
		
		if(CollectionUtils.isNotEmpty(list)){
			for(CourseStudyStaticsDto item : list){
				categories.add(item.getDateStr());
				data.add(item.getTotalCount());
			}
			returnVo.setCategories(categories);
			returnVo.setData(data);
		}
		
		return returnVo;
	}

}
