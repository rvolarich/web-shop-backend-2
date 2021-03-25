//package com.volare_automation.springwebshop.auth;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ApplicationUserService implements UserDetailsService {
//
//
//    private final ApplicationUserDao applicationUserDao;
//
//
//    public ApplicationUserService(ApplicationUserDao applicationUserDao) {
//        this.applicationUserDao = applicationUserDao;
//    }
//
//
////    public ApplicationUserService(ApplicationUserDao applicationUserDAO) {
////        this.applicationUserDao = applicationUserDAO;
////    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return applicationUserDao.selectApplicationUserByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException
//                        (String.format("Username %s not found", username)));
//    }
//
//
//}
