package yte.intern.spring.management.usecases.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import yte.intern.spring.management.usecases.application.entity.Event;
import yte.intern.spring.management.usecases.application.entity.Users;

import java.time.LocalDate;
import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {
    void deleteByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Users findByUsername(String username);

    @Query("select e from Event e, UserEvent ue where ue.event.id = e.id and ue.user.id = :id")
    List<Event> getMyEvents(Long id);

    @Query("select manager from Users manager where manager.role = 'MANAGER'")
    List<Users> getManagers();
}
