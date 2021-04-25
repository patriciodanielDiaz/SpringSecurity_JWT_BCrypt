package com.demoSecurity.demo.service;

import com.demoSecurity.demo.model.User;
import com.demoSecurity.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository=userRepository;
        this.bCryptPasswordEncoder= bCryptPasswordEncoder;
    }

    public ResponseEntity<List<User>> findAll(){
        List<User> list=  userRepository.findAll();
        return ResponseEntity.ok(list);
    }
    public ResponseEntity<User> addUser(User user){
        User newUser = userRepository.save(user);
        String pass = bCryptPasswordEncoder.encode(newUser.getPassword());
        newUser.setPassword(pass);
        return ResponseEntity.ok(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User u = userRepository.findByUsername(s);

        return new org.springframework.security.core.userdetails.User(u.getUsername(),u.getPassword(),u.getIsActive(), true,true,true, buildGrante(u.getRol()));
    }
    public List<GrantedAuthority> buildGrante(Byte rol){

        String[] r={"USER","ADMIN"};
        List<GrantedAuthority> auths = new ArrayList<>();

        for (int i=0;i<rol;i++){
            auths.add(new SimpleGrantedAuthority(r[i]));
        }
        return auths;
    }
}
