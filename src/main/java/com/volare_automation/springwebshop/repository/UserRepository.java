package com.volare_automation.springwebshop.repository;

//import com.volare_automation.springwebshop.model.CartProductTest;
import com.volare_automation.springwebshop.model.User;
//import com.volare_automation.springwebshop.service.UserRowMapper;
import com.volare_automation.springwebshop.service.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository implements UserRepositoryInterface{

    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<User> getAllUsers() {
       String sql = "SELECT * FROM users";

          List<User> users = new ArrayList<>();//
         jdbcTemplate.query(
               sql,
               new UserRowMapper());

        return users;
    }

    @Override
    public void saveUser(User u){
        String sql = "INSERT INTO customers (firstname, surname) VALUES ( ?, ?)";
        int result = jdbcTemplate.update(sql, u.getUsername(), u.getPassword());

        if (result > 0) {
            System.out.println("Insert successfully.");
        }
        else if (result==0) {
            System.out.println("Unsuccessfull insert");
        }
    }

    @Override
    public User getUserById(int id) {

        String query = "SELECT username, name, surname, address, city, zip, country FROM users WHERE userid=?";
        User userData = jdbcTemplate.queryForObject(query, new Object[]{id}, new UserRowMapper());
        return userData;
    }

    @Override
    public String queryForSessionId(User user){
        String query = "SELECT sessionid FROM users WHERE username=?";
        String id = jdbcTemplate.queryForObject(query, new Object[]{user.getUsername()},
                String.class);
        return  id;
    }

//    @Override
//    public void userLogin(User user){
//        String sql = "UPDATE users SET sessionid = ? WHERE username = ?";
//        String query = "SELECT sessionid FROM users WHERE username=?";
//        String queryId = "SELECT userid FROM users WHERE username=?";
//
//        String id = jdbcTemplate.queryForObject(query, new Object[]{"mila"}, String.class);
//        Integer userid = jdbcTemplate.queryForObject(queryId, new Object[]{"mila"}, Integer.class);
//
//        jdbcTemplate.update(sql, sessionId, "mila");
//    }

//    @Override
//    public User getUserById(int id) {
//
//        String sql = "SELECT * FROM customers WHERE id = ?";
//
//        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserRowMapper());
//
//    }

    @Override
    public boolean updateUser(User user, Integer id) {

        String sql = "UPDATE users SET name = ?, surname = ?, email = ?, password = ?," +
                "address = ?, zip = ?, city = ?, country = ?  WHERE userid = ?";

         int i = jdbcTemplate.update(sql, user.getNameName(), user.getSurname(), user.getEmail(),
                    user.getPassword(), user.getAddress(), user.getZip(), user.getCity(),
                    user.getCountry(), id);
         if(i == 1){
             return true;
         }

         return false;
            //System.out.println("user updated");
        }





    @Override
    public boolean deleteUserById(int id) {
        String sql = "DELETE FROM users WHERE userid = ?";
        String deleteTable = String.format("DROP TABLE IF EXISTS t_%s", String.valueOf(id));
        Object [] userObject = new Object[]{id};
        System.out.println("user id" + id);
        jdbcTemplate.execute(deleteTable);
        int del = jdbcTemplate.update(sql, userObject);
        if(del > 0){
            return true;
        }
        System.out.println("user deleted");
        return false;
    }

    @Override
    public boolean regUser(User user, String activationToken) {

        String role;
        if(user.isAdminAuth()){
            role = "ROLE_ADMIN";
        }else{
            role = "ROLE_USER";
        }

        String sql = "INSERT INTO users (username, password, role, enabled, name, surname, activationtoken) " +
                "VALUES (?,?,?,?,?,?,?)";
        int i = jdbcTemplate.update(sql, user.getUsername(), user.getPassword(),
                role, "false", user.getNameName(), user.getSurname(), activationToken);
        if(i == 1){
            System.out.println("User registered");
            return true;
        }
        return  false;
    }

    @Override
    public void userLogin(User user) {

    }




 //   @Override
//    public boolean authUser(User user) {
//
//
//            String sql = "SELECT * FROM users";
//
//        List<User> userList = jdbcTemplate.query(
//                sql,
//                (rs, rowNum) ->
//                        new User(
//
//
//                                rs.getString("username"),
//                                rs.getString("password"),
//                                rs.getString("enabled")
//
//                        )
//        );
//        for(User u : userList){
//            if(u.getUsername().equals(user.getUsername()) &&
//                    u.getPassword().equals(user.getPassword()) && u.getIsEnabled().equals("true")) {
//                return true;
//            }
//        }
//        return false;
//    }

    @Override
    public void saveSessionId(User user, String sessionId){
        String sql = "UPDATE users SET sessionid = ? WHERE username = ?";
       // String createTable = String.format("%s %s %s %s", "CREATE", "TABLE", user.getUsername(),
         //       "(id int, name varchar)");

        jdbcTemplate.update(sql, sessionId, user.getUsername());
        //jdbcTemplate.execute(createTable);
    }

    @Override
    public String getUserId(User user) {
        String queryId = "SELECT userid FROM users WHERE username=?";
        Integer userid = jdbcTemplate.queryForObject(queryId, new Object[]{user.getUsername()}, Integer.class);
        return Integer.toString(userid);
    }

    @Override
    public String getUserNameById(Integer id) {
        String username = "";
        String queryId = "SELECT username FROM users WHERE userid=?";
        if(id != 0) {
            username = jdbcTemplate.queryForObject(queryId, new Object[]{id}, String.class);

        }
        return username;
    }

    @Override
    public String getNameById(Integer id) {
        String name = "";
        String queryId = "SELECT name FROM users WHERE userid=?";
        if(id != 0) {
            name = jdbcTemplate.queryForObject(queryId, new Object[]{id}, String.class);
        }
        System.out.println("name from data: " + name);
        return name;
    }

    @Override
    public boolean activateUser(String token) {

        String sql = "UPDATE users SET enabled=? WHERE activationtoken=?";
        int i = jdbcTemplate.update(sql, "true", token);

        if(i > 0){
            return true;
        }
        return false;
    }

//    @Override
//    public Map<String, Object> getPasswordAndEnabledByUsername(User user) {
//
//        boolean allowMap = false;
//        Map<String, Object> passwordEnabledMap = new HashMap<>();
//        String sql = "SELECT password, enabled FROM users WHERE username=?";
//        String query = "SELECT username FROM users";
//
//        List<String> usernameList = jdbcTemplate.queryForList(query, String.class);
//        for(int i = 0; i < usernameList.size(); i++){
//            if(usernameList.get(i).equals(user.getUsername())){
//                allowMap = true;
//            }
//        }
//        if(allowMap) {
//            passwordEnabledMap = jdbcTemplate.queryForMap
//                    (sql, new Object[]{user.getUsername()});
//        }
//        else{
//            passwordEnabledMap.put("password","");
//            passwordEnabledMap.put("enabled","");
//        }
//
//        return passwordEnabledMap;
//    }

    @Override
    public Map<String, Object> getPasswordEnabledRoleByUsername(User user) {

        boolean allowMap = false;
        Map<String, Object> passwordEnabledMap = new HashMap<>();
        String sql = "SELECT password, enabled, role FROM users WHERE username=?";
        String query = "SELECT username FROM users";

        List<String> usernameList = jdbcTemplate.queryForList(query, String.class);
        for(int i = 0; i < usernameList.size(); i++){
            if(usernameList.get(i).equals(user.getUsername())){
                allowMap = true;
            }
        }
        if(allowMap) {
            passwordEnabledMap = jdbcTemplate.queryForMap
                    (sql, new Object[]{user.getUsername()});
        }
        else{
            passwordEnabledMap.put("password","");
            passwordEnabledMap.put("enabled","");
            passwordEnabledMap.put("role","");
        }
//        for(Map.Entry m:passwordEnabledMap.entrySet()){
//            System.out.println(m.getKey()+" "+m.getValue());
//        }
        return passwordEnabledMap;
    }

    @Override
    public int logoutUser(Integer userId){
        String sql = "UPDATE users SET sessionid = NULL WHERE userid = ?";
        int i = jdbcTemplate.update(sql, userId);
       return i;
    }

    @Override
    public Map<String, Object> testUserLogged(Integer userId) {
        boolean allowTest = false;
        String sql = "SELECT role, enabled, sessionid FROM users WHERE userid=?";
        Map<String, Object> result = new HashMap<>();
        for(int i = 0; i < listOfUserIds().size(); i++){
            if(listOfUserIds().get(i) == userId){
                allowTest = true;
            }
        }
        if(allowTest) {
            if (userId != 0) {
                result = jdbcTemplate
                        .queryForMap(sql, new Object[]{userId});
//        for(Map.Entry m:result.entrySet()){
//            System.out.println(m.getKey()+" "+m.getValue());
//        }
            }
        }
        return result;

    }

    public List<Integer> listOfUserIds(){

        String sql = "SELECT userid FROM users";
        List<Integer> userIdList = jdbcTemplate.queryForList(sql, Integer.class);
        return userIdList;
    }

    @Override
    public List<String> listOfUsernames() {

        String sql = "SELECT username FROM users";
        List<String> usernameList = jdbcTemplate.queryForList(sql, String.class);
        return usernameList;
    }

    @Override
    public String userEnabled(Integer id) {

        String sql = "SELECT enabled FROM users WHERE userid=?";
        String isEnabled = jdbcTemplate.queryForObject(sql, new Object[]{id}, String.class);
        System.out.println("Is enabled iz funkcije = " + id  + isEnabled);
        return isEnabled;
    }

    @Override
    public void saveCartproductToSession(String sessionId) {
        String sql = "INSERT INTO spring_session_attributes (sessionid, products) VALUES (?,?)";
        //String sql = "UPDATE spring_session_attributes SET products = ? WHERE attribute_name= ?";
        int i = jdbcTemplate.update(sql, sessionId, "hi");

    }


}
