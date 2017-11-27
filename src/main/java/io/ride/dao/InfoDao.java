package io.ride.dao;

import io.ride.model.Info;
import org.apache.ibatis.annotations.Param;

import java.util.*;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-26
 * Time: 下午6:27
 */
public interface InfoDao {
    int add(Info info);

    int del(@Param("id") Integer id);

    List<Info> list();

    Info findById(@Param("id") Integer id);
}
