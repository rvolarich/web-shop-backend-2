package com.volare_automation.springwebshop.service;

//import com.volare_automation.springwebshop.model.CartProductTest;
import com.lowagie.text.DocumentException;
import com.volare_automation.springwebshop.model.CartProduct;
import com.volare_automation.springwebshop.model.User;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface UserServiceInterface {

    List<User> getAllUsers();
    public User getUser(int id);
    public void saveUser(User user);
    public boolean updateUser(User user, Integer id);
    public boolean deleteUser(HttpServletRequest request);
    public String getSessionId(User user);
    public List<CartProduct> getList();
    void printUser(User u);
    boolean logoutUser(HttpServletRequest request);
    String testUserLogged(HttpServletRequest request);
    String getUserId(User user);
   // Integer parseUserId(HttpServletRequest request);
    String generateSessionId();
    boolean userExists(User user);
    String getUserName(HttpServletRequest request);
    Integer getUserIdFromCookie(HttpServletRequest request);
    String getSessionIdFromCookie(HttpServletRequest request);
    String authUser(User user);
    boolean registerUser(HttpServletRequest request, User user) throws IOException, MessagingException, DocumentException;
    String getName(HttpServletRequest request);
    Integer getExpValueFromCookie(HttpServletRequest request);
   // public void postCartProduct(CartProductTest c);
}
