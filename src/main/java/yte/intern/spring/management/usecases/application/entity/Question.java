package yte.intern.spring.management.usecases.application.entity;

import lombok.*;
import yte.intern.spring.management.usecases.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question extends BaseEntity {

    @Column(name = "QUESTION")
    @NonNull
    private String question;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "QUESTION_ID")
    private List<Answer> answers = new LinkedList<>();
}
