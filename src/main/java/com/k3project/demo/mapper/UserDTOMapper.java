package com.k3project.demo.mapper;

import com.k3project.demo.dto.UserDTO;
import com.k3project.demo.entity.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class UserDTOMapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getAddress(),
                user.getPhoneNumber(),
                user.getEmail());
    }
}
