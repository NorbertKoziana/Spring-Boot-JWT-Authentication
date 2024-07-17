package com.norbertkoziana.JwtAuth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/private")
    public String messageSecure(){
        return "This message requires authentication!";
    }

    @GetMapping("/public")
    public String messageNotSecure(){
        return "This message does NOT require authentication!";
    }

    @GetMapping("/user/info")
    public String userInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "You are logged in as:\n"
                + "Email: " + authentication.getName() + "\n"
                + "Role: " + authentication.getAuthorities().toString()
        ;
    }

    @GetMapping("/admin")
    public String messageAdmin(){
        return "This message does require admin role!";
    }
}
