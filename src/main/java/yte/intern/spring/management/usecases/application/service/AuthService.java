package yte.intern.spring.management.usecases.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yte.intern.spring.management.usecases.application.dto.LoginReqDTO;
import yte.intern.spring.management.usecases.application.dto.LoginResDTO;
import yte.intern.spring.management.usecases.application.dto.RegisterReqDTO;
import yte.intern.spring.management.usecases.application.entity.ROLE;
import yte.intern.spring.management.usecases.application.entity.Users;
import yte.intern.spring.management.usecases.application.mapper.UsersMapper;
import yte.intern.spring.management.usecases.application.repository.UsersRepository;
import yte.intern.spring.management.usecases.application.security.util.JwtUtil;
import yte.intern.spring.management.usecases.common.Enum.MessageType;
import yte.intern.spring.management.usecases.common.MessageResponse;


@Service
@RequiredArgsConstructor
public class AuthService {

    @Value(value = "${security.jwt.secret-key}")
    private String secretKey;

    private final UsersMapper usersMapper;
    private final UsersRepository usersRepository;
    private final DaoAuthenticationProvider authenticationProvider;
    private final PasswordEncoder passwordEncoder;

    public LoginResDTO login(final LoginReqDTO loginReqDTO) {

        Authentication usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginReqDTO.getUsername(), loginReqDTO.getPassword());
        try {
            Authentication user = authenticationProvider.authenticate(usernamePasswordAuthenticationToken);
            String token = JwtUtil.generateToken(user, secretKey, 15);
            Users userFromDB = usersRepository.findByUsername(loginReqDTO.getUsername());
            return new LoginResDTO(userFromDB.getName(), userFromDB.getSurname(),
                    userFromDB.getUsername(), userFromDB.getEmail(), userFromDB.getTc(), token, userFromDB.getRole());
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResponseEntity<?> addUser(RegisterReqDTO registerReqDTO) {
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
        users.setRole(ROLE.USER);
        usersRepository.save(users);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!", MessageType.SUCCESS));
    }
}
