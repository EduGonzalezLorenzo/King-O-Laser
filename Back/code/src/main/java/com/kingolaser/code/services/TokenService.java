package com.kingolaser.code.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kingolaser.code.models.Player;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {
    @Value("${tokenSecret}")
    String tokenSecret;
    @Value("${expTokenTime}")
    int expTokenTime;

    public String createUserToken(Player player) {
        Date expDate = new Date(System.currentTimeMillis() + expTokenTime);
        return JWT.create()
                .withSubject(player.getName())
                .withExpiresAt(expDate)
                .sign(Algorithm.HMAC512(tokenSecret.getBytes()));
    }

    public Map<String, Object> getUserFromToken(String token) {
//        if (token.equals("null")) return null;
//        UserBuilder userBuilder = new UserBuilder();
//        Map<String, Claim> tokenInfo = JWT.require(Algorithm.HMAC512(tokenSecret.getBytes())).build().verify(token).getClaims();
//        long iat = JWT.require(Algorithm.HMAC512(tokenSecret.getBytes())).build().verify(token).getExpiresAt().getTime();
//        return userBuilder.fromToken(tokenInfo, iat);
        return new HashMap<>();
    }
}
