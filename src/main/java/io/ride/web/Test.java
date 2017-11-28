package io.ride.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-28
 * Time: 下午7:22
 */
@Controller
public class Test {

    @RequestMapping("/test")
    @ResponseBody
    public Object test() {
        Map<String, Object> map = new HashMap<String,  Object>();
        map.put("username", "test");
        map.put("address", "ningbo");
        return map;
    }
}
