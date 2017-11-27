package io.ride.dao;

import io.ride.model.Info;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-26
 * Time: 下午6:32
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-dao.xml"})
public class InfoDaoTest {
    @Autowired
    private InfoDao infoDao;

    @Test
    public void test() {
        Info info = new Info();
        info.setUsername("ride");
        info.setContent("this is content!");
        info.setId(0);
        int result = infoDao.add(info);
        assertEquals(result, 1);
        System.out.println(infoDao.findById(info.getId()));
        result = infoDao.del(info.getId());
        assertEquals(result, 1);
        System.out.println(infoDao.findById(info.getId()));
    }

    @Test
    public void add() throws Exception {
/*        Info info = new Info();
        info.setUsername("ride");
        info.setContent("this is content!");
        info.setId(0);
        int result = infoDao.add(info);
        assertEquals(result, 1);*/
    }

    @Test
    public void addBatch() {
        /*for (int i = 0; i < 9; i++) {
            Info info = new Info("username" + i, "content is " + i);
            infoDao.add(info);
        }*/
    }

    @Test
    public void del() throws Exception {
        /*int result = infoDao.del(1);
        assertEquals(result, 1);
        */
    }

    @Test
    public void list() throws Exception {
        // System.out.println(infoDao.list());
    }

}