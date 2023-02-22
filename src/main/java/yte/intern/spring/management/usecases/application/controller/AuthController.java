package yte.intern.spring.management.usecases.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yte.intern.spring.management.usecases.application.dto.LoginReqDTO;
import yte.intern.spring.management.usecases.application.dto.LoginResDTO;
import yte.intern.spring.management.usecases.application.dto.RegisterReqDTO;
import yte.intern.spring.management.usecases.application.service.AuthService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    @PreAuthorize("permitAll()")
    public LoginResDTO login(@Valid @RequestBody final LoginReqDTO loginReqDTO) {
        return authService.login(loginReqDTO);
    }

    @PostMapping("/signup")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> addUser(@Valid @RequestBody final RegisterReqDTO registerReqDTO) {
        return authService.addUser(registerReqDTO);
    }
}
