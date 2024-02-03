package com.softedge.solution.common;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

/**
 * Config JWT.
 * Only one property 'shuaicj.security.jwt.secret' is mandatory.
 *
 * @author shuaicj 2017/10/18
 */
@Getter
@ToString
public class JwtAuthenticationConfig {

    @Value("${shuaicj.security.jwt.url:/login}")
    private String url;

    @Value("${shuaicj.security.jwt.header:Authorization}")
    private String header;

    @Value("${shuaicj.security.jwt.prefix:Bearer}")
    private String prefix;

    @Value("${shuaicj.security.jwt.expiration:#{1440 * 60 * 60 * 60 * 1000}}")
    private int expiration; // default 24 hrs

    @Value("${shuaicj.security.jwt.secret}")
    private String secret;
}
