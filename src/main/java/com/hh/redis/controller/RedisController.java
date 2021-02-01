package com.hh.redis.controller;

import com.alibaba.fastjson.JSON;
import com.hh.redis.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

@Controller
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("save/value")
    @ResponseBody
    public String saveUserData() {
        try {
            User user = new User("ZHANGSAN","123456","976869901@qq.com","china");
            stringRedisTemplate.opsForValue().set("userValue", JSON.toJSONString(user));
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "ok";
    }

    @RequestMapping("save/list")
    @ResponseBody
    public String saveUserListData() {
        try {
            for (int i = 0; i < 10; i++) {
                User user = new User("ZHANGSAN" + i,"123456","976869901@qq.com","china");
                stringRedisTemplate.opsForList().leftPush("userList",JSON.toJSONString(user));
            }
            List<String> userList = stringRedisTemplate.opsForList().range("userList", 0, 5);
            userList.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "ok";
    }

    @RequestMapping("save/set")
    @ResponseBody
    public String saveUserSetData() {
        try {
            for (int i = 0; i < 10; i++) {
                User user = new User("ZHANGSAN" + i,"123456","976869901@qq.com","china");
                stringRedisTemplate.opsForSet().add("userSet",JSON.toJSONString(user));
            }
            List<String> userList = stringRedisTemplate.opsForSet().pop("userSet",1);
            userList.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "ok";
    }

    @RequestMapping("save/zset")
    @ResponseBody
    public String saveUserZSetData() {
        try {
            for (int i = 0; i < 10; i++) {
                User user = new User("ZHANGSAN" + i,"123456","976869901@qq.com","china");
                stringRedisTemplate.opsForZSet().add("userZSet", JSON.toJSONString(user), 1.0);
            }
            Set<String> userList = stringRedisTemplate.opsForZSet().range("userZSet",0,4);
            userList.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "ok";
    }

    @RequestMapping("save/hash")
    @ResponseBody
    public String saveUserHashData() {
        try {
            User user = new User("ZHANGSAN","123456","976869901@qq.com","china");
            stringRedisTemplate.opsForHash().put("hash","user",JSON.toJSONString(user));
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "ok";
    }

    private static String channel1 = "channel:1";
    private static String channel2 = "channel:2";

    @RequestMapping(value = "publish1",method = RequestMethod.POST)
    @ResponseBody
    public String publishMessage1(@RequestBody String message) {
        try {
            stringRedisTemplate.convertAndSend(channel1,message);
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "ok";
    }

    @RequestMapping(value = "publish2",method = RequestMethod.POST)
    @ResponseBody
    public String publishMessage2(@RequestBody String message) {
        try {
            stringRedisTemplate.convertAndSend(channel2,message);
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "ok";
    }

}
