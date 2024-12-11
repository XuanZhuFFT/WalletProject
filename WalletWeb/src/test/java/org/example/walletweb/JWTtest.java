package org.example.walletweb;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTtest {

    @Test
    public void testGen() {

        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("id",1);
        claims.put("name","张三");

        //生成JWT令牌
        String token = JWT.create()
                .withClaim("user",claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*24))//添加有效时间
                .sign(Algorithm.HMAC256("2499307660"));

        System.out.println(token);
    }

    @Test
    public void testDecode() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                "eyJ1c2VyIjp7Im5hbWUiOiLlvKDkuIkiLCJpZCI6MX0sImV4cCI6MTczMTgyODY2MH0." +
                "B7tAUrnYV5c-F7CrpEOu8nj_0aNsSUkBwJpClDDakyA";

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("2499307660")).build();

        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.get("user"));
    }
}
