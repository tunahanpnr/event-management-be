package yte.intern.spring.management.usecases.application.mapper;

import org.mapstruct.Mapper;
import yte.intern.spring.management.usecases.application.dto.EventDTO;
import yte.intern.spring.management.usecases.application.entity.Event;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event mapToEntity(EventDTO eventDTO);

    EventDTO mapToDto(Event event);

    List<Event> mapToEntity(List<EventDTO> eventDTOList);

    List<EventDTO> mapToDto(List<Event> eventList);
}
