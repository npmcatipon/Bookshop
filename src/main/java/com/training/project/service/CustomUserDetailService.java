package com.training.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.training.project.entity.User;
import com.training.project.repository.RoleRepository;
import com.training.project.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public CustomUserDetailService(UserRepository userRepository,
        RoleRepository roleRepository) {
            this.roleRepository = roleRepository;
            this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<String> roles = roleRepository.findRolesByUserId(user.getId());

        List<SimpleGrantedAuthority> authorities = roles.stream()
                                                    .map(role -> new SimpleGrantedAuthority(role))
                                                    .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            true,
            true,
            true,
            true, 
            authorities
        );
    }

}