package com.telegame.code.controllers;

import com.telegame.code.DTO.Message;
import com.telegame.code.exceptions.player.LoginException;
import com.telegame.code.exceptions.player.PlayerNameException;
import com.telegame.code.services.GoogleLoginService;
import com.telegame.code.services.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

@CrossOrigin()
@Controller
@AllArgsConstructor
public class OauthController {
    GoogleLoginService googleLoginService;
    TokenService tokenService;

    @GetMapping("/logingoogle")
    public ResponseEntity<Message> loginGoogle() throws MalformedURLException, URISyntaxException {
        try {
            return new ResponseEntity<>(Message.builder()
                    .message(googleLoginService.getRedirect())
                    .build(), HttpStatus.OK);
        } catch (LoginException e) {
            return new ResponseEntity<>(Message.builder().message("Google Login Error").build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/login/callback")
    public String callBack(String code, Model model) throws IOException, URISyntaxException, NoSuchAlgorithmException {
        try {
            return "redirect:http://localhost:3000/profile/successGoogleLogin?jwt=" + googleLoginService.getUserInfo(code);
        } catch (PlayerNameException e) {
            return "Google Login Error";
        }
    }
}
