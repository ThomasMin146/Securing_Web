package com.thomas.Login;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class PrivateController {

    WebSecurityConfig webSecurityConfig;

    public PrivateController(WebSecurityConfig webSecurityConfig){
        this.webSecurityConfig = webSecurityConfig;
    }

    @GetMapping("/private")
    public String privatePage(Authentication authentication){
        return "Welcome to the Private Room: " + getName(authentication);
    }

    @PostMapping("/private")
    public String postText(@RequestBody String text){
        return text;
    }

    private static String getName(Authentication authentication){
        return Optional.of(authentication.getPrincipal())
                .filter(OidcUser.class::isInstance)
                .map(OidcUser.class::cast)
                .map(OidcUser::getEmail)
                .orElseGet(authentication::getName);
    }


}
