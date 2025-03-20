package com.lihui.picturebackend.controller;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.lihui.picturebackend.utils.CheckCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;
@Slf4j
@RestController
@RequestMapping("/mail")
public class SendMailController {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private static final Cache<String, String> LOCAL_CACHE = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    /**
     * 发送验证码
     */
    @GetMapping("/getCode")
    public String mail(@RequestParam("targetEmail") String targetEmail) {
        String cleanEmail = targetEmail.trim();

        if (!isValidEmail(cleanEmail)) {
            return "邮箱格式错误";
        }

        String hashKey = DigestUtils.md5DigestAsHex(cleanEmail.getBytes());
        String cacheKey = "picture:sendMailCode:" + hashKey;

        // 防止频繁请求
        if (LOCAL_CACHE.getIfPresent(cacheKey) != null) {
            return "请勿重复发送验证码";
        }

        // 查询 Redis 是否已有验证码
        if (redisTemplate.opsForValue().get(cacheKey) != null) {
            LOCAL_CACHE.put(cacheKey, "1");
            return "请勿重复发送验证码";
        }

        // 生成 6 位验证码
        int authNum = new Random().nextInt(899999) + 100000;
        String authCode = String.valueOf(authNum);

        // 发送邮件
        CheckCodeUtils.getEmailCode(cleanEmail, "你的验证码为: " + authCode + " (五分钟内有效)");

        // 存入 Redis (5 分钟)
        redisTemplate.opsForValue().set(cacheKey, authCode, 5, TimeUnit.MINUTES);

        // 存入本地缓存 (1 分钟防刷)
        LOCAL_CACHE.put(cacheKey, "1");

        return "发送成功";
    }

    /**
     * 校验邮箱格式
     */
    private static boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
    }
}
