package yte.intern.spring.management.usecases.application.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserEvent implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private Event event;

    @CreatedDate
    @Column(name = "JOIN_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEvent)) return false;
        UserEvent that = (UserEvent) o;
        return Objects.equals(event, that.event) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, user);
    }
}
