package yte.intern.spring.management.usecases.application.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import yte.intern.spring.management.usecases.application.dto.LoginReqDTO;
import yte.intern.spring.management.usecases.application.dto.RegisterReqDTO;
import yte.intern.spring.management.usecases.application.dto.UsersDTO;
import yte.intern.spring.management.usecases.application.entity.Users;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-22T22:47:31+0300",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.17 (Amazon.com Inc.)"
)
@Component
public class UsersMapperImpl implements UsersMapper {

    @Override
    public Users mapToEntity(UsersDTO usersDTO) {
        if ( usersDTO == null ) {
            return null;
        }

        Users users = new Users();

        users.setName( usersDTO.getName() );
        users.setSurname( usersDTO.getSurname() );
        users.setEmail( usersDTO.getEmail() );
        users.setUsername( usersDTO.getUsername() );
        users.setTc( usersDTO.getTc() );

        return users;
    }

    @Override
    public Users mapToEntity(RegisterReqDTO registerDTO) {
        if ( registerDTO == null ) {
            return null;
        }

        Users users = new Users();

        users.setName( registerDTO.getName() );
        users.setSurname( registerDTO.getSurname() );
        users.setEmail( registerDTO.getEmail() );
        users.setUsername( registerDTO.getUsername() );
        users.setTc( registerDTO.getTc() );
        users.setPassword( registerDTO.getPassword() );

        return users;
    }

    @Override
    public Users mapToEntity(LoginReqDTO loginReqDTO) {
        if ( loginReqDTO == null ) {
            return null;
        }

        Users users = new Users();

        users.setUsername( loginReqDTO.getUsername() );
        users.setPassword( loginReqDTO.getPassword() );

        return users;
    }

    @Override
    public UsersDTO mapToDto(Users users) {
        if ( users == null ) {
            return null;
        }

        UsersDTO usersDTO = new UsersDTO();

        usersDTO.setName( users.getName() );
        usersDTO.setSurname( users.getSurname() );
        usersDTO.setEmail( users.getEmail() );
        usersDTO.setUsername( users.getUsername() );
        usersDTO.setTc( users.getTc() );

        return usersDTO;
    }

    @Override
    public List<Users> mapToEntity(List<UsersDTO> usersDTOList) {
        if ( usersDTOList == null ) {
            return null;
        }

        List<Users> list = new ArrayList<Users>( usersDTOList.size() );
        for ( UsersDTO usersDTO : usersDTOList ) {
            list.add( mapToEntity( usersDTO ) );
        }

        return list;
    }

    @Override
    public List<UsersDTO> mapToDto(List<Users> usersList) {
        if ( usersList == null ) {
            return null;
        }

        List<UsersDTO> list = new ArrayList<UsersDTO>( usersList.size() );
        for ( Users users : usersList ) {
            list.add( mapToDto( users ) );
        }

        return list;
    }
}
