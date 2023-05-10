package com.telegame.code.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.telegame.code.models.Player;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    @Value("${tokenSecret}")
    String tokenSecret;
    @Value("${expTokenTime}")
    int expTokenTime;

    public String createUserToken(Player player) {
        Date expDate = new Date(System.currentTimeMillis() + expTokenTime);
        return JWT.create()
                .withSubject(player.getPlayerName())
                .withExpiresAt(expDate)
                .sign(Algorithm.HMAC512(tokenSecret.getBytes()));
    }

    public String getPlayerNameFromJWT(String token) {
        if (token.equals("null")) return null;
        try {
            return JWT.require(Algorithm.HMAC512(tokenSecret.getBytes())).build().verify(token).getSubject();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
