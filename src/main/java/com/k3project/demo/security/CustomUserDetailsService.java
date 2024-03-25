package com.k3project.demo.security;

import com.k3project.demo.entity.Role;
import com.k3project.demo.entity.UserEntity;
import com.k3project.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String firstName) throws UsernameNotFoundException {
        logger.info("Loading user by username: {}", firstName);
        try {
            UserEntity user = userRepository.findByFirstName(firstName)
                    .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + firstName));
            logger.info("User '{}' loaded successfully", firstName);
            return new User(user.getFirstName(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
        } catch (UsernameNotFoundException e) {
            logger.error("Username not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while loading user by username '{}': {}", firstName, e.getMessage(), e);
            throw new UsernameNotFoundException("Error occurred while loading user by username: " + firstName, e);
        }finally {
            logger.info("Method loadUserByUsername end");
        }
    }

    private Collection<GrantedAuthority>mapRolesToAuthorities(List<Role>roles){
        return roles.stream().map(role->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
