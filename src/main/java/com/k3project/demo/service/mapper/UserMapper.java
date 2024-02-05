package com.k3project.demo.service.mapper;

import com.k3project.demo.entity.User;
import com.k3project.demo.service.dto.UserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")

public interface UserMapper extends EntityMapper<UserDTO, User> {

}
