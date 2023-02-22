package yte.intern.spring.management.usecases.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import yte.intern.spring.management.usecases.application.entity.Event;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class UsersDTO {
    private String name;

    private String surname;

    private String email;

    private String username;

    private Long tc;

}
