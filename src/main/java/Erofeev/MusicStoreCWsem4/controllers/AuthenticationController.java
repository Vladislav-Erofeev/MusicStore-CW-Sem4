package Erofeev.MusicStoreCWsem4.controllers;

import Erofeev.MusicStoreCWsem4.errors.ErrorResponse;
import Erofeev.MusicStoreCWsem4.errors.NotUniqueEmailException;
import Erofeev.MusicStoreCWsem4.security.AuthenticationRequest;
import Erofeev.MusicStoreCWsem4.security.AuthenticationService;
import Erofeev.MusicStoreCWsem4.security.RegistrationRequest;
import Erofeev.MusicStoreCWsem4.security.TokenResponse;
import Erofeev.MusicStoreCWsem4.utils.ImageNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final ImageNameService nameService;
    private final String UPLOAD_DIRECTORY = "C:/musicstore/images";

    @PostMapping("/register")
    public TokenResponse register(@RequestPart("request") RegistrationRequest request,
                                  @RequestPart(value = "file", required = false) MultipartFile file) throws NotUniqueEmailException, IOException {
        String url = null;
        if (file != null) {
            String imageName = nameService.generate(file.getContentType());
            Path filenameAndPath = Paths.get(UPLOAD_DIRECTORY + "/profile", imageName);
            Files.write(filenameAndPath, file.getBytes());
            url = "/profile/" + imageName;
        }
        String token = authenticationService.register(request, url);
        return new TokenResponse(token);
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        String token = authenticationService.authenticate(authenticationRequest);
        return new TokenResponse(token);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> notUniqueMailException(NotUniqueEmailException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.SEE_OTHER);
    }
}
