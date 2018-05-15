package fanlun.online.college.controller;

import java.util.Date;
import java.util.List;

import fanlun.online.college.core.consts.CourseEnum;
import fanlun.online.college.core.user.domain.UserCollections;
import fanlun.online.college.core.user.service.IUserCollectionsService;
import fanlun.online.college.web.JsonView;
import fanlun.online.college.web.SessionContext;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 用户收藏
 */
@Controller
@RequestMapping("/collections")
public class CollectionsController {

    @Autowired
    private IUserCollectionsService userCollectionsService;

    @RequestMapping(value = "/doCollection")
    @ResponseBody
    public String doCollection(Long courseId) {
        //获取当前用户
        Long curUserId = SessionContext.getUserId();
        UserCollections userCollections = new UserCollections();

        userCollections.setUserId(curUserId);
        userCollections.setClassify(CourseEnum.COLLECTION_CLASSIFY_COURSE.value());//课程收藏分类
        userCollections.setObjectId(courseId);
        List<UserCollections> list = userCollectionsService.queryAll(userCollections);

        /**
         *  传入的userId和 courseId 构成唯一的课程收藏，即只可能查询到一条，
         *  如果查到的list为空， 则是未收藏，所以收藏操作 在‘已经填好的userID和objectId’基础下需要设置当前时间，
         *      返回的是 errcode：1；的json 供前端判断  说明收藏操作成功
         *  如果查到的list 不为空， 说明是已收藏的，需要取消收藏操作：将取得的第一条数据（理论上讲应该有且仅有一条）
         *  删除记录，对应返回errcode：0， 说明 取消收藏。
         */
        if (CollectionUtils.isNotEmpty(list)) {
            userCollectionsService.delete(list.get(0));
            return new JsonView(0).toString();
        } else {
            userCollections.setCreateTime(new Date());
            userCollectionsService.createSelectivity(userCollections);
            return new JsonView(1).toString();//已经收藏
        }
    }

    /**
     * 是否已经收藏
     *
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/isCollection")
    @ResponseBody
    public String isCollection(Long courseId) {
        //获取当前用户
        Long curUserId = SessionContext.getUserId();
        UserCollections userCollections = new UserCollections();

        userCollections.setUserId(curUserId);
        userCollections.setClassify(CourseEnum.COLLECTION_CLASSIFY_COURSE.value());//课程收藏
        userCollections.setObjectId(courseId);
        List<UserCollections> list = userCollectionsService.queryAll(userCollections);

        if (CollectionUtils.isNotEmpty(list)) {//已经收藏
            return new JsonView(1).toString();
        } else {
            return new JsonView(0).toString();
        }
    }

}

