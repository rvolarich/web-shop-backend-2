//package com.volare_automation.springwebshop.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//@Configuration
//@EnableWebSecurity // (1)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter { // (1)
//
//    private  final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public WebSecurityConfig(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }
//
////    @Override
////    @Bean
////    public UserDetailsService userDetailsService() {
////        UserDetails milaUser = (UserDetails) User.builder()
////                .username("mila")
////                .password(passwordEncoder.encode("4567"))
////                .roles("STUDENT")
////                .build();
////        return new InMemoryUserDetailsManager(milaUser
////        ); // (1)
////    }
//
//
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//
//        UserDetails mailaUser = User.builder()
//                .username("mila")
//                .password(passwordEncoder.encode("4567"))
//                .roles(ApplicationUserRole.STUDENT.name())
//                .build();
//
//        UserDetails andiUser = User.builder()
//                .username("andi")
//                .password(passwordEncoder.encode("1234"))
//                .roles(ApplicationUserRole.ADMIN.name())
//                .build();
//
//        UserDetails tomUser = User.builder()
//                .username("tom")
//                .password(passwordEncoder.encode("12345"))
//                .roles(ApplicationUserRole.ADMINTRAINEE.name())
//                .build();
//
//        return new InMemoryUserDetailsManager(mailaUser,
//                andiUser,
//                tomUser);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {  // (2)
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
//                .antMatchers("/products").hasRole(ApplicationUserRole.ADMIN.name()) // (3)
//                .anyRequest().authenticated() // (4)
//                .and()
//                .formLogin();
////                    .loginPage("/login"); // (7)
////                .and()
////                .logout()
////                    .logoutUrl("/logout")
////                    .clearAuthentication(true)
////                    .invalidateHttpSession(true)
////                    .deleteCookies("JSESSIONID")
////                    .logoutSuccessUrl("/login");
//
//    }
//}