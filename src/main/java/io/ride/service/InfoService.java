package io.ride.service;

import io.ride.model.Info;

import java.util.*;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-27
 * Time: 下午12:07
 */
public interface InfoService {
    boolean add(String username, String content);

    List<Info> list();

    boolean del(Integer id);
}
