package com.biorbac.repository;

import com.biorbac.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserNameOrEmail(String userName,String email);
    Boolean existsByUserName(String userName);
    Boolean existsByEmail(String email);}
