package com.volare_automation.springwebshop.service;

import java.sql.ResultSet;
import java.sql.SQLException;



import com.volare_automation.springwebshop.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    private int userid;
    private String username;
    private String password;
    private String isEnabled;
    private String nameName;
    private String surname;
    private String email;
    private String address;
    private String zip;
    private String city;
    private String country;
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        User user = new User();
       // user.setUserid(rs.getInt("userid"));
        user.setUsername(rs.getString("username"));
        //user.setPassword(rs.getString("password"));
        user.setNameName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setAddress(rs.getString("address"));
        user.setCity(rs.getString("city"));
        user.setZip(rs.getString("zip"));
        user.setCountry(rs.getString("country"));
        //user.setEnabled(rs.getBoolean("enabled"));
        //System.out.println("been in RowMapper");
        return user;

    }
}