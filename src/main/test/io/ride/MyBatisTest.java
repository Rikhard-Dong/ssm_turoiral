package io.ride;

import io.ride.dao.InfoDao;
import io.ride.model.Info;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-28
 * Time: 上午9:52
 */
public class MyBatisTest {
    SqlSessionFactory factory;

    @Before
    public void before() {
        // 获取配置文件
        InputStream conf = MyBatisTest.class.getClassLoader()
                .getResourceAsStream("mybatis/mybatis-conf-test.xml");
        factory = new SqlSessionFactoryBuilder().build(conf);
    }

    @Test
    public void addTest() {
        SqlSession sqlSession = factory.openSession();
        InfoDao infoDao = sqlSession.getMapper(InfoDao.class);
        Info info = new Info("test", "test");
        int result = infoDao.add(info);
        System.out.println(result);
        System.out.println(info);
    }

    @Test
    public void test() {
        SqlSession sqlSession = factory.openSession();
        InfoDao infoDao = sqlSession.getMapper(InfoDao.class);
        System.out.println(infoDao.list());
    }
}
