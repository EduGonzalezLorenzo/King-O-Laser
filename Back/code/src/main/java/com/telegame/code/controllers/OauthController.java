package com.telegame.code.controllers;

import com.telegame.code.services.GoogleLoginService;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin()
@RestController
@AllArgsConstructor
public class OauthController {

    private final GoogleLoginService googleLoginService;

    @GetMapping("/logingoogle")
    public String loginGoogle() throws Exception {
        return googleLoginService.getRedirect();
    }

    @GetMapping("/login/callback")
    public String callback(String code, Model m) throws Exception {
        String token = googleLoginService.getAccessToken(code);
        Map<String,String> userDetails = googleLoginService.getUserDetails(token);
        m.addAttribute("userdetails",userDetails);
        return "oauth";
    }
}
