package yte.intern.spring.management.usecases.application.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import yte.intern.spring.management.usecases.common.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "EVENT_SEQ")
public class Event extends BaseEntity {
    //fields

    @Column(name = "EVENT_NAME")
    @NonNull
    private String eventName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "BEGIN_DATE")
    @NonNull
    private LocalDate beginDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "END_DATE")
    @NonNull
    private LocalDate endDate;

    @Column(name = "CAPACITY")
    @NonNull
    private int capacity;

    @Column(name = "RESERVED")
    private int reserved;

    @Column(name = "INFO")
    private String info;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MANAGER_ID", referencedColumnName = "ID")
    @JsonBackReference
    private Users manager;

    @OneToMany(mappedBy = "event", orphanRemoval = true)
    private List<UserEvent> participants = new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "EVENT_ID")
    private List<Question> questions = new LinkedList<>();

    @Column(name = "LAT")
    private Double lat;

    @Column(name = "LNG")
    private Double lng;


}
