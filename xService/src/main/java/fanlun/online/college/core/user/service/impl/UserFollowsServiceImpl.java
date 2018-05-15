package fanlun.online.college.core.user.service.impl;

import java.util.List;

import fanlun.online.college.core.user.dao.UserFollowsDao;
import fanlun.online.college.core.user.domain.UserFollowStudyRecord;
import fanlun.online.college.core.user.domain.UserFollows;
import fanlun.online.college.core.user.service.IUserFollowsService;
import fanlun.online.college.page.TailPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserFollowsServiceImpl implements IUserFollowsService {

    @Autowired
    private UserFollowsDao entityDao;

    public UserFollows getById(Long id) {
        return entityDao.getById(id);
    }

    public List<UserFollows> queryAll(UserFollows queryEntity) {
        return entityDao.queryAll(queryEntity);
    }

    public TailPage<UserFollows> queryPage(UserFollows queryEntity, TailPage<UserFollows> page) {
        Integer itemsTotalCount = entityDao.getTotalItemsCount(queryEntity);
        List<UserFollows> items = entityDao.queryPage(queryEntity, page);
        page.setItemsTotalCount(itemsTotalCount);
        page.setItems(items);
        return page;
    }

    public TailPage<UserFollowStudyRecord> queryUserFollowStudyRecordPage(UserFollowStudyRecord queryEntity, TailPage<UserFollowStudyRecord> page) {
        Integer itemsTotalCount = entityDao.getFollowStudyRecordCount(queryEntity);
        List<UserFollowStudyRecord> items = entityDao.queryFollowStudyRecord(queryEntity, page);
        page.setItemsTotalCount(itemsTotalCount);
        page.setItems(items);
        return page;
    }

    public void createSelectivity(UserFollows entity) {
        entityDao.createSelectivity(entity);
    }

    public void update(UserFollows entity) {
        entityDao.update(entity);
    }

    public void updateSelectivity(UserFollows entity) {
        entityDao.updateSelectivity(entity);
    }

    public void delete(UserFollows entity) {
        entityDao.delete(entity);
    }

    public void deleteLogic(UserFollows entity) {
        entityDao.deleteLogic(entity);
    }


}

