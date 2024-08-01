package org.example.redis.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.example.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final String keyPrefix = "jwt:";

    public void setData(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // Redis에 JWT 토큰 저장
    public void saveTokenToRedis(String uuid, String token, long expiration, TimeUnit timeUnit) {

        // System.out.println("uuid : " + uuid);
        // System.out.println("token : " + token);
        // System.out.println("expiration : " + expiration);
        // System.out.println("timeUnit : " + timeUnit);

        // Redis에 저장시 사용할 key 지정
        String key = keyPrefix + uuid;

        // JWT 토큰 저장
        redisTemplate.opsForValue().set(
                key       // Redis 저장 Key 설정
                , token     // JWT 토큰
        );

        // 토큰 만료시간 설정
        redisTemplate.expire(
                key           // Redis 저장 Key 설정
                , expiration    // 토큰 만료 시간
                , timeUnit      // 시간 단위 설정( 초 )
        );
    }

    // Redis에서 JWT 토큰 삭제
    public void deleteJwtToken(String uuid) {

        // Redis에 저장시 사용할 key 지정
        String key = keyPrefix + uuid;

        redisTemplate.delete(key);
    }

    // Redis에서 JWT 토큰값 가져오기
    public String getJwtToken(String uuid) {

        System.out.println("getJwtToken().uuid : " + uuid);

        // Redis에 저장시 사용할 key 지정
        String key = keyPrefix + uuid;

        return (String) redisTemplate.opsForValue().get(key);
    }
}