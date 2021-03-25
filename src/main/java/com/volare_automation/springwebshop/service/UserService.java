package com.volare_automation.springwebshop.service;

//import com.volare_automation.springwebshop.model.CartProductTest;
import com.lowagie.text.DocumentException;
import com.volare_automation.springwebshop.model.CartProduct;
import com.volare_automation.springwebshop.model.Mail;
import com.volare_automation.springwebshop.model.User;
import com.volare_automation.springwebshop.repository.ProductRepositoryInterface;
import com.volare_automation.springwebshop.repository.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Service
public class UserService implements UserServiceInterface{

    private String sessionId;
    private  Integer userId;


    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    UserRepositoryInterface userRepositoryInterface;

    @Autowired
    ProductRepositoryInterface productRepositoryInterface;

    @Autowired
    EmailServiceInterface emailServiceInterface;

    List<CartProduct> list = new ArrayList<>();

//    public UserService() {
//        System.out.println("Service layer created");
//        list.add(new CartProducts("čokolada", 2, 10 ,1));
//        list.add(new CartProducts("sladoled", 3, 15 ,2));
//        list.add(new CartProducts("masline", 5, 50 ,3));
//    }


    public List<CartProduct> getList() {

        return list;
    }

    @Override
    public void printUser(User u) {
        System.out.println("user = " + u.getUsername());
    }

    @Override
    public boolean logoutUser(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("UserId")) userId = Integer.parseInt(cookie.getValue());
        }
        int i = userRepositoryInterface.logoutUser(userId);
        if(i == 1) return false;
        else return true;
    }

    @Override
    public String testUserLogged(HttpServletRequest request) {
        String databaseSessionId = "";
        String isEnabled = "";
        String databaseRole = "";
//        Cookie[] cookies = request.getCookies();
//        for(Cookie cookie : cookies){
//            if(cookie.getName().equals("UserId")) userId = Integer.parseInt(cookie.getValue());
//            if(cookie.getName().equals("SessionId")) sessionId = cookie.getValue();
//        }
        for(Map.Entry m:userRepositoryInterface.testUserLogged(getUserIdFromCookie(request)).entrySet()){
            if(m.getKey().equals("sessionid")){
                databaseSessionId = (String) m.getValue();
            }
            else if(m.getKey().equals("enabled")){
                isEnabled = (String) m.getValue();
            }
            else {
                databaseRole = (String) m.getValue();
            }
        }

       if(getSessionIdFromCookie(request).equals(databaseSessionId) && isEnabled.equals("true") &&
        databaseRole.equals("ROLE_USER")){
           return "userAuthenticated";
       }
       else if(getSessionIdFromCookie(request).equals(databaseSessionId) && isEnabled.equals("true") &&
        databaseRole.equals("ROLE_ADMIN")){
            return "adminAuthenticated";
        }

        return "false";
    }

    @Override
    public String getUserId(User user) {
        return userRepositoryInterface.getUserId(user);
    }

//    @Override
//    public Integer parseUserId(HttpServletRequest request) {
//        Integer userId = 1;
//        StringBuilder sb = new StringBuilder();
//        Cookie[] cookies = request.getCookies();
//        for(Cookie cookie : cookies) {
//            if (cookie.getName().charAt(0) == '$') {
//                for (int i = 1; i < cookie.getName().toCharArray().length; i++) {
//                    if (cookie.getName().charAt(i) == '&') {
//                        break;
//                    } else {
//                        sb.append(cookie.getName().charAt(i));
//                    }
//
//                    System.out.println("sb je = " + sb.toString());
//                }
//            }
//        }
//        return userId;
//    }

    @Override
    public String generateSessionId() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 50;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    @Override
    public boolean userExists(User user) {

        List<String> usernames = userRepositoryInterface.listOfUsernames();
        for(int i = 0; i < usernames.size(); i++){
            if(usernames.get(i).equals(user.getUsername())){
                return false;
            }
        }
        return true;
    }

    @Override
    public String getUserName(HttpServletRequest request) {

        return userRepositoryInterface.getUserNameById(getUserIdFromCookie(request));
    }

    @Override
    public String getName(HttpServletRequest request) {

        return userRepositoryInterface.getNameById(getUserIdFromCookie(request));
    }

    @Override
    public Integer getExpValueFromCookie(HttpServletRequest request) {

        Integer expValue = 0;
        Cookie [] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("ExpValue")) {
                    expValue = Integer.parseInt(c.getValue());
                }
            }
        }
        return expValue;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepositoryInterface.getAllUsers();
    }

    @Override
    public User getUser(int id) {
        return null;
    }

    @Override
    public Integer getUserIdFromCookie(HttpServletRequest request){
        Integer id = 0;
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("UserId")) id = Integer.parseInt(cookie.getValue());
            }
        }
        return id;
    }

    @Override
    public String getSessionIdFromCookie(HttpServletRequest request) {
        String sessionId = "";
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("SessionId")) sessionId = cookie.getValue();
            }
        }
        return  sessionId;
    }

    @Override
    public String authUser(User user) {


        System.out.println("user password " + user.getPassword());
        String databasePassword = "";
        String databaseEnabled = "";
        String databaseRole = "";

        for(Map.Entry m:userRepositoryInterface.getPasswordEnabledRoleByUsername(user).entrySet()){
            if(m.getKey().equals("password")){
                databasePassword = (String) m.getValue();
            }
            else if(m.getKey().equals("enabled")){
                databaseEnabled = (String) m.getValue();
            }
            else if(m.getKey().equals("role")){
                databaseRole = (String) m.getValue();
            }
        }

        System.out.println("enabled" + databaseEnabled);
        System.out.println("role" + databaseRole);

        if(passwordEncoder.matches(user.getPassword(), databasePassword) && databaseEnabled.equals("true")
            && databaseRole.equals("ROLE_USER")){
            return "userAuthenticated";
        }

        else if(passwordEncoder.matches(user.getPassword(), databasePassword) && databaseEnabled.equals("true")
                && databaseRole.equals("ROLE_ADMIN")){
            return "adminAuthenticated";
        }

        else if(passwordEncoder.matches(user.getPassword(), databasePassword) && databaseEnabled.equals("false")) {
            return "disabled";
        }


        else return "wrongUsernameOrPassword";

    }

    @Override
    public boolean registerUser(HttpServletRequest request, User user) throws IOException, MessagingException, DocumentException {

        if(!userExists(user)){
            return false;
        }
        String link = generateSessionId();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(userRepositoryInterface.regUser(user, link)) {

            Map<String, Object> userMap = new HashMap<>();
            userMap.put("name", user.getNameName());
            userMap.put("link", link);

            Mail mail = new Mail();
            mail.setFrom("noreply@gmail.com");
            mail.setTo(user.getUsername());
            mail.setSubject("hi");
            mail.setHtmlTemplate(new Mail.HtmlTemplate("activationMail", userMap));

            emailServiceInterface.sendMail(mail);
            return true;
        }

        return false;
    }

    @Override
    public String getSessionId(User user){
        return userRepositoryInterface.queryForSessionId(user);
    }

//    @Override
//    public User getUser(int id) {
//        for(User u : list){
//            if(u.getId() == id){
//                return u;
//            }
//        }
//        return null;
//    }

//    @Override
//    public void saveUser(User u) {
//        this.list.add(u);
//    }

    @Override
    public void saveUser(User user) {

        userRepositoryInterface.saveUser(user);
    }

    @Override
    public boolean updateUser(User user, Integer id) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(userRepositoryInterface.updateUser(user, id)){
            return true;
        }
        return false;
    }

//    @Override
//    public void updateUser(User user) {
//        for(User u : list){
//            if(u.getId() == user.getId()){
//                if(user.getFirstname() != null) {
//                    u.setFirstname(user.getFirstname());
//                }
//                if(user.getSurname() != null) {
//                    u.setSurname(user.getSurname());
//                }
//
//            }
//        }
//    }

    @Override
    public boolean deleteUser(HttpServletRequest request) {

        Integer id = getUserIdFromCookie(request);
        if(userRepositoryInterface.deleteUserById(id)){
            return true;
        }
        return false;
    }

//    @Override
//    public void postCartProduct(CartProductTest c) {
//
//        userRepositoryInterface.postCartProd(c);
//    }

//    @Override
//    public void deleteUser(int id) {
//        list.remove(id);
//    }
}
