package yte.intern.spring.management.usecases.application.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEventID implements Serializable {


    private Long user_id;

    //@Temporal(TemporalType.TIMESTAMP)
    private Long event_id;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEventID)) return false;
        UserEventID that = (UserEventID) o;
        return Objects.equals(event_id, that.event_id) &&
                Objects.equals(user_id, that.user_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event_id, user_id);
    }
}
