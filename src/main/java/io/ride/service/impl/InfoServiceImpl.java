package io.ride.service.impl;

import io.ride.dao.InfoDao;
import io.ride.model.Info;
import io.ride.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-27
 * Time: 下午12:07
 */
@Service("infoService")
public class InfoServiceImpl implements InfoService {

    @Autowired
    private InfoDao infoDao;

    public boolean add(String username, String content) {
        Info info = new Info(username, content);
        return infoDao.add(info) == 1;
    }

    public List<Info> list() {

        return infoDao.list();
    }

    public boolean del(Integer id) {

        return infoDao.del(id) == 1;
    }
}
