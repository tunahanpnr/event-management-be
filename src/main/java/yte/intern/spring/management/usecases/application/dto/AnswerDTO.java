package yte.intern.spring.management.usecases.application.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {
    private Long questionId;
    private String username;
    private String answer;
}
