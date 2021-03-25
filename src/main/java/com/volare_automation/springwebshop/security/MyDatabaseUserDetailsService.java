//package com.volare_automation.springwebshop.security;
//
//import com.volare_automation.springwebshop.repository.UserRepositoryInterface;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//
//public class MyDatabaseUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    UserRepositoryInterface userRepositoryInterface;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        try{
//
//
//        }catch (UsernameNotFoundException e){
//
//        }
//
//        // (1)
//        // 1. Load the user from the users table by username. If not found, throw UsernameNotFoundException.
//        // 2. Convert/wrap the user to a UserDetails object and return it.
//        return null;
//    }
//}
