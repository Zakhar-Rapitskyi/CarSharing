package com.rapitskyi.carsharing.service;


import com.rapitskyi.carsharing.entity.User;
import com.rapitskyi.carsharing.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository repository;
    private PasswordEncoder passwordEncoder;
    public User findUserByEmail(String email){
        Optional<User> optionalUser = repository.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("User with this email is not found");
        }
        return optionalUser.get();
    }
    boolean existsUserByEmail(String email){return repository.existsByEmail(email);}
    boolean existsByPhone(String phone){return repository.existsByPhone(phone);}
    public User findUserById(Long id){
        Optional<User> optionalUser = repository.findById(id);
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("User with this id is not found");
        }
        return optionalUser.get();
    }
    public void save(User user) {
        repository.save(user);
    }


}
