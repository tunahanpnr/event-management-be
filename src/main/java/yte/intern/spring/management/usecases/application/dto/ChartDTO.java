package yte.intern.spring.management.usecases.application.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChartDTO {
    private LocalDate date;
    private Long count;
}
