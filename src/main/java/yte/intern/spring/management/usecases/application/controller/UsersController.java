package yte.intern.spring.management.usecases.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import yte.intern.spring.management.usecases.application.dto.EventDTO;
import yte.intern.spring.management.usecases.application.dto.RegisterReqDTO;
import yte.intern.spring.management.usecases.application.dto.UsersDTO;
import yte.intern.spring.management.usecases.application.entity.Event;
import yte.intern.spring.management.usecases.application.entity.Users;
import yte.intern.spring.management.usecases.application.mapper.EventMapper;
import yte.intern.spring.management.usecases.application.mapper.UsersMapper;
import yte.intern.spring.management.usecases.application.service.UsersService;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UsersMapper usersMapper;
    private final UsersService usersService;

    @PostMapping("/addmanager")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addManager(@Valid @RequestBody final RegisterReqDTO registerReqDTO) {
        return usersService.addManager(registerReqDTO);
    }

    @GetMapping("/managers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UsersDTO> getManagers() {
        return usersMapper.mapToDto(usersService.getManagers());
    }

}
