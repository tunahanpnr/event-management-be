package yte.intern.spring.management.usecases.application.mapper;

import org.mapstruct.Mapper;
import yte.intern.spring.management.usecases.application.dto.LoginReqDTO;
import yte.intern.spring.management.usecases.application.dto.RegisterReqDTO;
import yte.intern.spring.management.usecases.application.dto.UsersDTO;
import yte.intern.spring.management.usecases.application.entity.Users;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsersMapper {
    Users mapToEntity(UsersDTO usersDTO);

    Users mapToEntity(RegisterReqDTO registerDTO);

    Users mapToEntity(LoginReqDTO loginReqDTO);

    UsersDTO mapToDto(Users users);

    List<Users> mapToEntity(List<UsersDTO> usersDTOList);

    List<UsersDTO> mapToDto(List<Users> usersList);
}
