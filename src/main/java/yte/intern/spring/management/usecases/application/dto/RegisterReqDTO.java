package yte.intern.spring.management.usecases.application.dto;

import lombok.Getter;
import lombok.Setter;
import yte.intern.spring.management.usecases.application.entity.ROLE;

@Getter
@Setter
public class RegisterReqDTO extends LoginReqDTO {
    private String name;

    private String surname;

    private String username;

    private String email;

    private Long tc;

    private String password;

}
