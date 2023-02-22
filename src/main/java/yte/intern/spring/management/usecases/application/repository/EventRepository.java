package yte.intern.spring.management.usecases.application.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yte.intern.spring.management.usecases.application.entity.Event;
import yte.intern.spring.management.usecases.application.entity.UserEvent;
import yte.intern.spring.management.usecases.application.entity.Users;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    Event getEventByEventName(String eventName);

    @Query("select e from Event e where e.beginDate > :now")
    List<Event> getAvailableEvents(LocalDate now);

    @Query("select e from Event e where e.beginDate <= :now")
    List<Event> getPastEvents(LocalDate now);

    @Query("select ue from UserEvent ue where ue.event.eventName = :eventName and ue.user.role = 'USER'")
    List<UserEvent> getBarChart(String eventName);

    @Query("select user from Users user, UserEvent ue where ue.event.eventName = :eventName and ue.user.id = user.id and ue.user.role = 'USER'")
    List<Users> getParticipants(String eventName);

}
