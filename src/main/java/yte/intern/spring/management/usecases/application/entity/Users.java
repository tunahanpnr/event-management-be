package yte.intern.spring.management.usecases.application.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import yte.intern.spring.management.usecases.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "idgen", sequenceName = "MANAGER_SEQ")
public class Users extends BaseEntity {
    //fields
    @Column(name = "NAME")
    @NonNull
    private String name;

    @Column(name = "SURNAME")
    @NonNull
    private String surname;

    @Column(name = "EMAIL", unique = true)
    @NonNull
    private String email;

    @Column(name = "USERNAME", unique = true)
    @NonNull
    private String username;

    @Column(name = "TC", unique = true)
    @NonNull
    private Long tc;

    @Column(name = "PASSWORD")
    @NonNull
    private String password;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private ROLE role;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<UserEvent> events = new LinkedList<>();

}
