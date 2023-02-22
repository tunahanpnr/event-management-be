package yte.intern.spring.management.usecases.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import yte.intern.spring.management.usecases.application.entity.Question;
import yte.intern.spring.management.usecases.application.entity.Users;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
public class EventDTO {
    private String eventName;

    private LocalDate beginDate;

    private LocalDate endDate;

    private int capacity;

    private int reserved;

    private String info;

    private Double lat;

    private Double lng;

    private List<Question> questions;

}
