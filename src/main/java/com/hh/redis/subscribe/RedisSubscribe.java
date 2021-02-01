package com.hh.redis.subscribe;

import org.springframework.stereotype.Component;

@Component
public class RedisSubscribe {

    public void receiverMessage(String message) {
        System.out.println(message+"---");
    }

}
