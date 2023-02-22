package yte.intern.spring.management.usecases.application.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import yte.intern.spring.management.usecases.application.dto.EventDTO;
import yte.intern.spring.management.usecases.application.entity.Event;
import yte.intern.spring.management.usecases.application.entity.Question;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-22T22:47:31+0300",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.17 (Amazon.com Inc.)"
)
@Component
public class EventMapperImpl implements EventMapper {

    @Override
    public Event mapToEntity(EventDTO eventDTO) {
        if ( eventDTO == null ) {
            return null;
        }

        Event event = new Event();

        event.setEventName( eventDTO.getEventName() );
        event.setBeginDate( eventDTO.getBeginDate() );
        event.setEndDate( eventDTO.getEndDate() );
        event.setCapacity( eventDTO.getCapacity() );
        event.setReserved( eventDTO.getReserved() );
        event.setInfo( eventDTO.getInfo() );
        List<Question> list = eventDTO.getQuestions();
        if ( list != null ) {
            event.setQuestions( new ArrayList<Question>( list ) );
        }
        event.setLat( eventDTO.getLat() );
        event.setLng( eventDTO.getLng() );

        return event;
    }

    @Override
    public EventDTO mapToDto(Event event) {
        if ( event == null ) {
            return null;
        }

        EventDTO eventDTO = new EventDTO();

        eventDTO.setEventName( event.getEventName() );
        eventDTO.setBeginDate( event.getBeginDate() );
        eventDTO.setEndDate( event.getEndDate() );
        eventDTO.setCapacity( event.getCapacity() );
        eventDTO.setReserved( event.getReserved() );
        eventDTO.setInfo( event.getInfo() );
        eventDTO.setLat( event.getLat() );
        eventDTO.setLng( event.getLng() );
        List<Question> list = event.getQuestions();
        if ( list != null ) {
            eventDTO.setQuestions( new ArrayList<Question>( list ) );
        }

        return eventDTO;
    }

    @Override
    public List<Event> mapToEntity(List<EventDTO> eventDTOList) {
        if ( eventDTOList == null ) {
            return null;
        }

        List<Event> list = new ArrayList<Event>( eventDTOList.size() );
        for ( EventDTO eventDTO : eventDTOList ) {
            list.add( mapToEntity( eventDTO ) );
        }

        return list;
    }

    @Override
    public List<EventDTO> mapToDto(List<Event> eventList) {
        if ( eventList == null ) {
            return null;
        }

        List<EventDTO> list = new ArrayList<EventDTO>( eventList.size() );
        for ( Event event : eventList ) {
            list.add( mapToDto( event ) );
        }

        return list;
    }
}
