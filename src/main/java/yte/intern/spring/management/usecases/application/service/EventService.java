package yte.intern.spring.management.usecases.application.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yte.intern.spring.management.usecases.application.dto.AnswerDTO;
import yte.intern.spring.management.usecases.application.dto.ChartDTO;
import yte.intern.spring.management.usecases.application.dto.EventDTO;
import yte.intern.spring.management.usecases.application.entity.*;
import yte.intern.spring.management.usecases.application.repository.QuestionRepository;
import yte.intern.spring.management.usecases.application.repository.EventRepository;
import yte.intern.spring.management.usecases.application.repository.UserEventRepository;
import yte.intern.spring.management.usecases.application.repository.UsersRepository;
import yte.intern.spring.management.usecases.common.Enum.MessageType;
import yte.intern.spring.management.usecases.common.MessageResponse;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final UsersRepository usersRepository;
    private final UserEventRepository userEventRepository;
    private final QuestionRepository questionRepository;

    public MessageResponse addEvent(Event event, String username) {
        if (checkEventExist(event.getEventName())) {
            return new MessageResponse(String.format("Event %s is already exist!", event.getEventName()), MessageType.ERROR);
        }
        if (event.getBeginDate().isAfter(event.getEndDate())) {
            return new MessageResponse("Begin date must be before from end date!", MessageType.ERROR);
        }
        if (event.getBeginDate().isBefore(LocalDate.now()))
            return new MessageResponse("Begin date can't be in the past!", MessageType.ERROR);

        Users manager = usersRepository.findByUsername(username);

        event.setManager(manager);
        eventRepository.save(event);

        UserEvent userEvent = new UserEvent();
        userEvent.setUser(manager);
        userEvent.setEvent(event);

        userEventRepository.save(userEvent);


        return new MessageResponse(String.format("Event %s is added successfully.", event.getEventName()), MessageType.SUCCESS);
    }

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }


    public List<Event> getMyEvents(String username) {
        Users users = usersRepository.findByUsername(username);
        return usersRepository.getMyEvents(users.getId());
    }

    public List<Event> getAvailableEvents(LocalDate now) {
        return eventRepository.getAvailableEvents(now);
    }

    public List<Event> getPastEvents(LocalDate now) {
        return eventRepository.getPastEvents(now);
    }

    public MessageResponse updateMyEvent(String eventName, EventDTO eventDTO) {
        if (checkEventExist(eventDTO.getEventName())) {
            return new MessageResponse(String.format("Event %s is already exist.", eventDTO.getEventName()), MessageType.ERROR);
        }

        Event eventFromDB = eventRepository.getEventByEventName(eventName);

        MessageResponse messageResponse = checkEvent(eventFromDB);
        if (messageResponse != null)
            return messageResponse;

        updateEventFields(eventDTO, eventFromDB);
        eventRepository.save(eventFromDB);

        return new MessageResponse(String.format("Event %s is updated successfully", eventDTO.getEventName()), MessageType.SUCCESS);
    }


    private void updateEventFields(EventDTO eventDTO, Event eventFromDB) {
        eventFromDB.setEventName(eventDTO.getEventName());
        eventFromDB.setBeginDate(eventDTO.getBeginDate());
        eventFromDB.setEndDate(eventDTO.getEndDate());
        eventFromDB.setCapacity(eventDTO.getCapacity());
        eventFromDB.setInfo(eventDTO.getInfo());
    }

    public MessageResponse deleteMyEvent(String username, String eventName) {
        Event eventFromDB = eventRepository.getEventByEventName(eventName);

        MessageResponse messageResponse = checkEvent(eventFromDB);

        if (messageResponse != null)
            return messageResponse;

        eventFromDB.setManager(null);
        eventRepository.save(eventFromDB);

        eventRepository.delete(eventFromDB);

        return new MessageResponse(String.format("Event \"%s\" is deleted successfully", eventName), MessageType.SUCCESS);
    }

    public MessageResponse joinEvent(List<AnswerDTO> answers, String username, String eventName) {
        Event event = eventRepository.getEventByEventName(eventName);

        MessageResponse messageResponse = checkEvent(event);

        if (messageResponse != null)
            return messageResponse;

        if (event.getCapacity() <= event.getReserved()) {
            return new MessageResponse(String.format("Event \"%s\" is full!", eventName), MessageType.ERROR);
        }

        Users user = usersRepository.findByUsername(username);
        List<Event> events = usersRepository.getMyEvents(user.getId());

        if (events.contains(event)) {
            return new MessageResponse(String.format("You have already joined this event!"), MessageType.ERROR);
        }

        event.setReserved(event.getReserved() + 1);
        UserEvent userEvent = new UserEvent();
        userEvent.setUser(user);
        userEvent.setEvent(event);
        userEventRepository.save(userEvent);
        eventRepository.save(event);

        if (answers != null) {
            for (AnswerDTO answerDTO : answers) {
                Answer answer = new Answer(answerDTO.getAnswer(), usersRepository.findByUsername(answerDTO.getUsername()));
                Question question = questionRepository.getOne(answerDTO.getQuestionId());
                question.getAnswers().add(answer);
                questionRepository.save(question);
            }
        }

        return new MessageResponse(String.format("Joined to event \"%s\" successfully", eventName), MessageType.SUCCESS);
    }

    public List<Users> getParticipants(String eventName) {
        return eventRepository.getParticipants(eventName);
    }

    public List<ChartDTO> getBarChart(String eventName) {
        List<UserEvent> userEvents = eventRepository.getBarChart(eventName);
        List<ChartDTO> chartDTOList = new ArrayList<>();
        HashMap<LocalDate, Long> hashMap = new HashMap<>();

        for (UserEvent userEvent : userEvents) {
            LocalDate date = userEvent.getJoinDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (hashMap.containsKey(date)) {
                hashMap.put(date, hashMap.get(date) + 1);
            } else {
                hashMap.put(date, 1L);
            }
        }

        for (LocalDate localDate : hashMap.keySet()) {
            ChartDTO chartDTO = new ChartDTO(localDate, hashMap.get(localDate));
            chartDTOList.add(chartDTO);
        }

        return chartDTOList;
    }

    private boolean checkEventExist(String eventName) {
        Event event = eventRepository.getEventByEventName(eventName);
        return !(event == null);
    }

    private MessageResponse checkEvent(Event event) {
        if (event == null)
            return new MessageResponse(String.format("Event \"%s\" could not found. Check your event name.", event.getEventName()), MessageType.ERROR);

        if (event.getBeginDate().isBefore(LocalDate.now())) {
            return new MessageResponse(String.format("Event \"%s\" is already begin.", event.getEventName()), MessageType.ERROR);
        }


        return null;
    }
}