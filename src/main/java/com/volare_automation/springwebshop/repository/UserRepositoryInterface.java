package com.volare_automation.springwebshop.repository;

//import com.volare_automation.springwebshop.model.CartProductTest;
import com.volare_automation.springwebshop.model.User;

import java.util.List;
import java.util.Map;

public interface UserRepositoryInterface {

    public List<User> getAllUsers();
    public void saveUser(User user);
    public User getUserById(int id);
    public boolean updateUser(User user, Integer id);
    public boolean deleteUserById(int id);
    //public boolean authUser(User user);
    public boolean regUser(User user, String activationToken);
    public void userLogin(User user);
    public String queryForSessionId(User user);
    public void saveSessionId(User user, String sessionId);
    public String getUserId(User user);
    String getUserNameById(Integer id);
    Map<String, Object> getPasswordEnabledRoleByUsername(User user);
    int logoutUser(Integer userId);
    Map<String, Object> testUserLogged(Integer userId);
    List<String> listOfUsernames();
    String userEnabled(Integer id);
    void saveCartproductToSession(String sessionId);
    String getNameById(Integer id);
    boolean activateUser(String token);



    //public void postCartProd(CartProductTest c);

}
