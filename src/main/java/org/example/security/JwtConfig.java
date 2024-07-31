package org.example.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config/jwt.properties")
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secret;  // 비밀키
    @Value("${jwt.expiration}")
    private long expiration;    // 토큰 만료 시간
    @Value("${jwt.header}")
    private String header;  // 헤더 이름
    @Value("${jwt.prefix}")
    private String prefix;  // 접두사

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}