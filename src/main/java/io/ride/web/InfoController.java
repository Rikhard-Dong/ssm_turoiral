package io.ride.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.ride.dto.Result;
import io.ride.model.Info;
import io.ride.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-26
 * Time: 下午11:06
 */
@Controller
@RequestMapping("/info")
@ResponseBody
public class InfoController {

    @Autowired
    private InfoService infoService;

    @RequestMapping("/list")
    public Result list(@RequestParam("page") Integer page) {
        PageHelper.startPage(page, 10);
        List<Info> infos = infoService.list();
        PageInfo<Info> pageInfo = new PageInfo<Info>(infos);
        return new Result(true, "成功!").addData("pageInfo", pageInfo);
    }

    @RequestMapping("/add")
    public Result add(@RequestParam("username") String username,
                      @RequestParam("content") String content) {
        Boolean flag = infoService.add(username, content);
        if (flag) {
            return new Result(true, "添加成功!");
        } else {
            return new Result(false, "添加失败!");
        }
    }

    @RequestMapping("/del")
    public Result del(@RequestParam("id") Integer id) {
        boolean flag = infoService.del(id);
        if (flag) {
            return new Result(true, "删除成功!");
        } else {
            return new Result(false, "删除失败!");
        }
    }
}
