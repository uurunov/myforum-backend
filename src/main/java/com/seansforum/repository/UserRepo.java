package com.seansforum.repository;

import com.seansforum.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long>{
	public Optional<User> findByLogin(String login);
}
