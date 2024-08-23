package com.anoopsen.SpringSecurityCookieAuthenticationDemo1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anoopsen.SpringSecurityCookieAuthenticationDemo1.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	public Optional<User> findByUsername(String username);
}
