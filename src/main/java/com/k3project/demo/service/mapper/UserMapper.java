package com.k3project.demo.service.mapper;

import com.k3project.demo.entity.User;
import com.k3project.demo.service.dto.UserDTO;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")

public interface UserMapper extends EntityMapper<UserDTO, User> {

}
