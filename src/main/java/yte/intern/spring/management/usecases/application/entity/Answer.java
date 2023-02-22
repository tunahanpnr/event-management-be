package yte.intern.spring.management.usecases.application.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import yte.intern.spring.management.usecases.common.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Answer extends BaseEntity {
    @Column(name = "ANSWER")
    @NonNull
    private String answer;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @JsonBackReference
    private Users user;
}
