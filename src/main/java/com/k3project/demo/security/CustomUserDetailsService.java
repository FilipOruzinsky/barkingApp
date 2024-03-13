package com.k3project.demo.security;

import com.k3project.demo.entity.Role;
import com.k3project.demo.entity.UserEntity;
import com.k3project.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String firstName) throws UsernameNotFoundException {
        System.out.println("tusom++++++++++++");
        System.out.println(firstName + " " +  "firstName");
        Optional<UserEntity> userEntity = userRepository.findByFirstName(firstName);
        System.out.println(userEntity.isEmpty());

        System.out.println("tak co teda ");

        System.out.println("tusom++++++++++++");
        UserEntity user = userRepository.findByFirstName(firstName).orElseThrow(()->new UsernameNotFoundException("Username not fond"));
        return new User(user.getFirstName(),user.getPassword(),mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<GrantedAuthority>mapRolesToAuthorities(List<Role>roles){
        return roles.stream().map(role->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
