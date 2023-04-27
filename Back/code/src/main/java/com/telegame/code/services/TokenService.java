package com.telegame.code.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.telegame.code.DTO.PlayerDTO;
import com.telegame.code.builder.PlayerBuilder;
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
                .withClaim("gameMatches", player.getMatches())
                .sign(Algorithm.HMAC512(tokenSecret.getBytes()));
    }

    public PlayerDTO getUserFromToken(String token) {
        if (token.equals("null")) return null;

        String tokenInfo = JWT.require(Algorithm.HMAC512(tokenSecret.getBytes())).build().verify(token).getSubject();
        long iat = JWT.require(Algorithm.HMAC512(tokenSecret.getBytes())).build().verify(token).getExpiresAt().getTime();

        return PlayerBuilder.fromJWT(tokenInfo, iat);
    }
}
