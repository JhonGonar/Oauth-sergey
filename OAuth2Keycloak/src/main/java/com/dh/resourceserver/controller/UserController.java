package com.dh.resourceserver.controller;

import com.dh.resourceserver.model.UserRest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/status/check")
    public String status(){
        return "Working";
    }

    @PreAuthorize("hasRole('developer') or #id == #jwt.subject")
    //@Secured("ROLE_developer") //Here goes Authorities, so we need to prefix ROLE/SCOPE
    @DeleteMapping(path = "/{id}")
    public String deleteUser(@PathVariable("id") String id, @AuthenticationPrincipal Jwt jwt){
        return "Deleted user with id " + id + "and JWT subject " + jwt.getSubject();
    }

    //PostAuth has access to teh returned element
    @PostAuthorize("returnObject.userId() == #jwt.subject")
    @GetMapping("/{id}")
    public UserRest getUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt){
        return new UserRest("Freddy", "Gomez", id);
    }

}
