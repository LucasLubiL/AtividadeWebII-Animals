package com.animais.Animais.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.animais.Animais.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByEmail(String email);
}
