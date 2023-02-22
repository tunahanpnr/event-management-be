package yte.intern.spring.management.usecases.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import yte.intern.spring.management.usecases.application.entity.ROLE;

@Getter
@RequiredArgsConstructor
public class LoginResDTO {
    private final String name;
    private final String surname;
    private final String username;
    private final String email;
    private final Long tc;
    private final String token;
    private final ROLE role;
}
