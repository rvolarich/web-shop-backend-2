package com.volare_automation.springwebshop.controller;

import com.lowagie.text.DocumentException;
import com.volare_automation.springwebshop.JavaMailConfig;
import com.volare_automation.springwebshop.model.CartProduct;
import com.volare_automation.springwebshop.model.Mail;
import com.volare_automation.springwebshop.model.Products;
import com.volare_automation.springwebshop.model.User;
import com.volare_automation.springwebshop.repository.ProductRepositoryInterface;
import com.volare_automation.springwebshop.repository.UserRepositoryInterface;
import com.volare_automation.springwebshop.service.EmailServiceInterface;
import com.volare_automation.springwebshop.service.UserRepoInterface;
import com.volare_automation.springwebshop.service.UserService;
import com.volare_automation.springwebshop.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(allowCredentials = "true",
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})

public class UserController {

    @Autowired
    private UserServiceInterface userServiceInterface;

    @Autowired
    private UserRepositoryInterface userRepositoryInterface;

    @Autowired
    private EmailServiceInterface emailServiceInterface;

    @Autowired
    private ProductRepositoryInterface productRepositoryInterface;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsers(){
        return userServiceInterface.getAllUsers();
    }

    @RequestMapping(value = "/get/user", method = RequestMethod.GET)
    public User getUserById(HttpServletRequest request){
        return userRepositoryInterface.getUserById(userServiceInterface.getUserIdFromCookie(request));
    }

    @RequestMapping(value = "users/save", method = RequestMethod.POST)
    public void saveUser( @RequestBody User user){


        userServiceInterface.saveUser(user);
    }

    @RequestMapping(value = "users/update", method = RequestMethod.PUT)
    public String updateUser(HttpServletRequest request, HttpServletResponse response, @RequestBody User u){

        Integer id = userServiceInterface.getUserIdFromCookie(request);
        if(userServiceInterface.updateUser(u, id)){
            return "Successfully updated!";
        }
        return "Error updating user!";
    }

//    @RequestMapping(value = "/user/del", method = RequestMethod.GET)
//    public boolean deleteUser(HttpServletRequest request){
//
//
//        if(userServiceInterface.deleteUser(request)){
//            return true;
//        }
//        return false;
//    }

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public List <CartProduct> deleteUser(){
        return userServiceInterface.getList();
    }

    @RequestMapping(value = "/req", method = RequestMethod.POST)
    public String reqParam(@RequestBody List<CartProduct> cpList){
        System.out.println("cartProduct: " + cpList.get(1).getProductName());
        System.out.println("email: " + cpList.get(cpList.size()-1).getEmail());
        System.out.println("name: " + cpList.get(cpList.size()-1).getNameName());
        return "hi";
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)

    //@GetMapping("/send")
    public String sendEmail(@RequestBody List<CartProduct> cpList)
            throws MessagingException, IOException, DocumentException {

        List<CartProduct> cartList = cpList;
        String nameName = cartList.get(cartList.size()-1).getNameName();
        String email = cartList.get(cartList.size()-1).getEmail();



        double total = 0.00;
        //List<Products> list = productRepositoryInterface.getAllProducts();

        for (int i = 0; i < cpList.size(); i++) {
            total += (cartList.get(i).getProductPrice()*cartList.get(i).getProductQuantity());
        }

        double totalForPayment = total + 125;
//        System.out.println("total " + total);
//        list.add("Mila");
//        list.add("Andi");
//        list.add("mama");
//        list.add("tata");
//
//        model.addAttribute("listUsers", list);
    //System.out.println("usernames " + user.getUsername());
        Map<String, Object> properties = new HashMap<>();
        properties.put("list", cartList);
        properties.put("sum", totalForPayment);
        properties.put("nameName", nameName);
//        properties.put("location", "Sri Lanka");
//        properties.put("sign", "Java Developer");
//
        Mail mail = new Mail();
        mail.setFrom("noreply@gmail.com");
        mail.setTo(email);
        mail.setSubject("hi");
        mail.setHtmlTemplate(new Mail.HtmlTemplate("sample", properties));

        emailServiceInterface.sendMail(mail);
        return "sample";
    }
}
