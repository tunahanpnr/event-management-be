package yte.intern.spring.management.usecases.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.intern.spring.management.usecases.application.entity.UserEvent;
import yte.intern.spring.management.usecases.application.entity.UserEventID;

public interface UserEventRepository extends JpaRepository<UserEvent, UserEventID> {
}
