package Erofeev.MusicStoreCWsem4.controllers;

import Erofeev.MusicStoreCWsem4.errors.ErrorResponse;
import Erofeev.MusicStoreCWsem4.errors.NotUniqueEmailException;
import Erofeev.MusicStoreCWsem4.security.AuthenticationRequest;
import Erofeev.MusicStoreCWsem4.security.AuthenticationService;
import Erofeev.MusicStoreCWsem4.security.RegistrationRequest;
import Erofeev.MusicStoreCWsem4.security.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public TokenResponse register(@RequestBody RegistrationRequest registrationRequest) throws NotUniqueEmailException {
        String token = authenticationService.register(registrationRequest);
        return new TokenResponse(token);
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        String token = authenticationService.authenticate(authenticationRequest);
        return new TokenResponse(token);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> notUniqueMailException(NotUniqueEmailException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.SEE_OTHER);
    }
}
