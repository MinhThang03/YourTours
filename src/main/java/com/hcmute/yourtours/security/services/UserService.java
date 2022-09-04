package com.hcmute.yourtours.security.services;

import com.hcmute.yourtours.commands.UserCommand;
import com.hcmute.yourtours.libs.exceptions.ErrorCode;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.repositories.UserRepository;
import com.hcmute.yourtours.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements UserDetailsService, IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCommand userCommand = userRepository.findByUsername(username).orElse(null);
        if (userCommand == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(userCommand);
    }

    @Override
    public UserDetails loadUserByUserId(UUID userId) {
        UserCommand userCommand = userRepository.findByUserId(userId).orElse(null);
        if (userCommand == null) {
            throw new RestException(ErrorCode.UNAUTHORIZED);
        }
        return new CustomUserDetails(userCommand);
    }
}
