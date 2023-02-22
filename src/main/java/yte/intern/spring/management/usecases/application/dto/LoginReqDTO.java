package yte.intern.spring.management.usecases.application.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginReqDTO {

    private String username;

    private String password;
}
