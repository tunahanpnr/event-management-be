package yte.intern.spring.management.usecases.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import yte.intern.spring.management.usecases.application.dto.AnswerDTO;
import yte.intern.spring.management.usecases.application.dto.ChartDTO;
import yte.intern.spring.management.usecases.application.dto.EventDTO;
import yte.intern.spring.management.usecases.application.dto.UsersDTO;
import yte.intern.spring.management.usecases.application.entity.Answer;
import yte.intern.spring.management.usecases.application.entity.Event;
import yte.intern.spring.management.usecases.application.entity.Users;
import yte.intern.spring.management.usecases.application.mapper.EventMapper;
import yte.intern.spring.management.usecases.application.mapper.UsersMapper;
import yte.intern.spring.management.usecases.application.service.EmailService;
import yte.intern.spring.management.usecases.application.service.EventService;
import yte.intern.spring.management.usecases.common.MessageResponse;
import yte.intern.spring.management.usecases.common.qrCodeGenerator.QrCodeGenerator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final EmailService emailService;
    private final EventMapper eventMapper;
    private final UsersMapper usersMapper;

    @PostMapping("/{username}/add-event")
    @PreAuthorize("hasAuthority('MANAGER')")
    public MessageResponse addEvent(@RequestBody EventDTO eventDTO, @PathVariable String username) {
        Event event = eventMapper.mapToEntity(eventDTO);
        return eventService.addEvent(event, username);
    }

    @GetMapping("/events")
    @PreAuthorize("isAuthenticated()")
    public List<EventDTO> getEvents() {
        List<Event> events = eventService.getEvents();

        return eventMapper.mapToDto(events);
    }

    @GetMapping("/available-events")
    @PreAuthorize("isAuthenticated()")
    public List<EventDTO> getAvailableEvents() {
        List<Event> events = eventService.getAvailableEvents(LocalDate.now());

        return eventMapper.mapToDto(events);
    }

    @GetMapping("/past-events")
    @PreAuthorize("isAuthenticated()")
    public List<EventDTO> getPastEvents() {
        List<Event> events = eventService.getPastEvents(LocalDate.now());

        return eventMapper.mapToDto(events);
    }

    @PutMapping("/{eventName}/update")
    @PreAuthorize("hasAuthority('MANAGER')")
    public MessageResponse updateEvent(@RequestBody EventDTO eventDTO, @PathVariable String eventName) {
        return eventService.updateMyEvent(eventName, eventDTO);
    }

    @DeleteMapping("/{username}/{eventName}/delete")
    @PreAuthorize("hasAuthority('MANAGER')")
    public MessageResponse deleteEvent(@PathVariable String eventName, @PathVariable String username) {
        return eventService.deleteMyEvent(username, eventName);
    }

    @GetMapping("/{username}/my-events")
    @PreAuthorize("isAuthenticated()")
    public List<EventDTO> getMyEvents(@PathVariable String username) {
        List<Event> events = eventService.getMyEvents(username);
        return eventMapper.mapToDto(events);
    }

    @PostMapping("/{username}/{eventName}/join")
    @PreAuthorize("hasAuthority('USER')")
    public MessageResponse joinEvent(@RequestBody List<AnswerDTO> answers, @PathVariable String username, @PathVariable String eventName) {
        return eventService.joinEvent(answers,username, eventName);
    }

    @GetMapping("/{eventName}/getParticipants")
    @PreAuthorize("hasAuthority('MANAGER')")
    public List<UsersDTO> getParticipants(@PathVariable String eventName){
        List<Users> users = eventService.getParticipants(eventName);
        return usersMapper.mapToDto(users);
    }

    @GetMapping("/{eventName}/barChart")
    @PreAuthorize("hasAuthority('MANAGER')")
    public List<ChartDTO> getBarChart(@PathVariable String eventName){
        return eventService.getBarChart(eventName);
    }



    @PostMapping("/sendEmail/{to}")
    @GetMapping(value = "/sendEmail", produces = MediaType.IMAGE_PNG_VALUE)
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> sendQrcodeEmail(@RequestBody String barcode, @PathVariable String to) throws Exception {

        BufferedImage bufferedImage =QrCodeGenerator.generateQRCodeImage(barcode);
        String image = encodeToString(bufferedImage,"png");

        emailService.sendMessageWithAttachment(to,image);

        return ResponseEntity.ok(image);
    }


    public static String encodeToString(BufferedImage bufferedImage, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(bufferedImage, type, bos);
            byte[] imageBytes = bos.toByteArray();

            Base64.Encoder encoder = Base64.getEncoder();
            imageString = encoder.encodeToString(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }
}
