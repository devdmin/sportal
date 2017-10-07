package com.devdmin.core.repository;

import com.devdmin.core.model.User;

import java.util.UUID;


public interface UserRepositoryCustom {
    User findByToken(UUID token);
}
