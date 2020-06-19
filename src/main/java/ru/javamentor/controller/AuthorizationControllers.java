package ru.javamentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.javamentor.config.Facebook;
import ru.javamentor.config.VKontakte;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class AuthorizationControllers {

    private final VKontakte vKontakte;
    private final Facebook facebook;

    @Autowired
    public AuthorizationControllers(VKontakte vKontakte, Facebook facebook) {
        this.vKontakte = vKontakte;
        this.facebook = facebook;
    }

    @GetMapping("/authorization/vkAuthorization")
    public ResponseEntity<Object> redirectToVK() throws URISyntaxException {
        URI vk = new URI(vKontakte.getAuthorizationUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(vk);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

    @GetMapping("/authorization/facebookAuthorization")
    public ResponseEntity<Object> redirectFacebook() throws URISyntaxException {
        URI vk = new URI(facebook.getAuthorizationUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(vk);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }
}