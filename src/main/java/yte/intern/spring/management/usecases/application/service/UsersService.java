package yte.intern.spring.management.usecases.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yte.intern.spring.management.usecases.application.dto.RegisterReqDTO;
import yte.intern.spring.management.usecases.application.dto.UsersDTO;
import yte.intern.spring.management.usecases.application.entity.Event;
import yte.intern.spring.management.usecases.application.entity.ROLE;
import yte.intern.spring.management.usecases.application.entity.Users;
import yte.intern.spring.management.usecases.application.mapper.UsersMapper;
import yte.intern.spring.management.usecases.application.repository.EventRepository;
import yte.intern.spring.management.usecases.application.repository.UsersRepository;
import yte.intern.spring.management.usecases.common.Enum.MessageType;
import yte.intern.spring.management.usecases.common.MessageResponse;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsersMapper usersMapper;


    public Users findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }


    public void updateUser(final Users user) {
        Users oldUser = (Users) usersRepository.findByUsername(user.getUsername());
        Users newUser = (Users) user;
        newUser.setId(oldUser.getId());
        usersRepository.save(newUser);
    }

    public void deleteUser(final String username) {
        usersRepository.deleteByUsername(username);
    }


    public ResponseEntity<?> addManager(RegisterReqDTO registerReqDTO) {

        if (usersRepository.existsByUsername(registerReqDTO.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(String.format("Username \"%s\" is already taken!", registerReqDTO.getUsername()), MessageType.ERROR));
        }

        if (usersRepository.existsByEmail(registerReqDTO.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(String.format("Email \"%s\" is already taken!", registerReqDTO.getEmail()), MessageType.ERROR));
        }

        registerReqDTO.setPassword(passwordEncoder.encode(registerReqDTO.getPassword()));
        Users users = usersMapper.mapToEntity(registerReqDTO);
        users.setRole(ROLE.MANAGER);
        usersRepository.save(users);

        return ResponseEntity.ok(new MessageResponse("Manager registered successfully!", MessageType.SUCCESS));
    }


    public List<Users> getManagers() {
        return usersRepository.getManagers();
    }
}
