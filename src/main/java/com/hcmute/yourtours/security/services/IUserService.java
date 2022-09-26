package com.hcmute.yourtours.security.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface IUserService {

    UserDetails loadUserByUserId(UUID userId);
}
