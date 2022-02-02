package com.seansforum.security;

import com.seansforum.entity.User;
import com.seansforum.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class MyUserDetailsService implements UserDetailsService {
	@Autowired private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> userRes = userRepo.findByLogin(login);
        if(userRes.isEmpty())
            throw new UsernameNotFoundException("Could not findUser with login = " + login);
        User user = userRes.get();
        if (user.getUser_access() == 1) {
        	return new org.springframework.security.core.userdetails.User(
            		user.getLogin(),
                    user.getPswd(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        } else {
        	throw new UsernameNotFoundException("User with login = " + login + " is BLOCKED");
        }
    }
}
