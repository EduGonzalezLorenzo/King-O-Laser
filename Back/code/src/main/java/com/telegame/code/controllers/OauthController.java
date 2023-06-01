package com.telegame.code.controllers;

import com.telegame.code.DTO.Message;
import com.telegame.code.services.GoogleLoginService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Map;

@CrossOrigin()
@Controller
public class OauthController {
    @Autowired
    GoogleLoginService googleLoginService;

    @GetMapping("/logingoogle")
    public ResponseEntity<Message> loginGoogle() throws MalformedURLException, URISyntaxException {
        String url = googleLoginService.getRedirect();
      try{
          return new ResponseEntity<>(Message.builder()
                  .message(url)
                  .build(), HttpStatus.OK);
      }catch(Exception e){
          return new ResponseEntity<>(Message.builder().message("Google Login Error").build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/login/callback")
    public String callBack(String code, Model model, HttpSession session) throws IOException, URISyntaxException {
        String token = googleLoginService.getAccessToken(code);
        Map<String, String> userInfo = googleLoginService.getUserInfo(token);

        session.setAttribute("userInfo", userInfo);
        return "redirect:http://localhost:3000/profile/"+userInfo.get("email");
    }


    @GetMapping("/private")
    public String logged(Model model, HttpSession session) {
        Object userInfoTry = session.getAttribute("userInfo");
        if (userInfoTry == null) throw new RuntimeException("Not logged");
        Map<String,String> userInfo = (Map<String, String>) userInfoTry;

        model.addAttribute("userInfo", userInfo);
        return "oauth";
    }
}
