package com.volare_automation.springwebshop.authentication;

import com.lowagie.text.DocumentException;
import com.volare_automation.springwebshop.model.User;
import com.volare_automation.springwebshop.model.UserAuthDataModel;
import com.volare_automation.springwebshop.repository.ProductRepositoryInterface;
import com.volare_automation.springwebshop.repository.UserRepositoryInterface;
import com.volare_automation.springwebshop.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(allowCredentials = "true",
                methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
                        RequestMethod.OPTIONS, RequestMethod.DELETE})

public class UserAuth {


    UserServiceInterface userServiceInterface;

    @Autowired
    private ProductRepositoryInterface productRepositoryInterface;

    @Autowired
    private UserRepositoryInterface userRepositoryInterface;

    @Autowired
    public UserAuth(UserServiceInterface userServiceInterface){
        this.userServiceInterface = userServiceInterface;
    }

    // This following commented method causes blank home page when JAR is produced

//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public void getSession(HttpServletRequest request, HttpServletResponse response){
//        HttpSession session = request.getSession();
//
//
//    }

    @RequestMapping(value = "/logged_in", method = RequestMethod.GET)
    public UserAuthDataModel loggedIn(HttpServletRequest request,
                                      HttpServletResponse response, @RequestParam Boolean sessionExpired) {




        UserAuthDataModel userAuthDataModel = new UserAuthDataModel();

        userAuthDataModel.setLogged(false);


        Cookie [] cookies = request.getCookies();

        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("UserId")) {
                    userAuthDataModel.setSessionExpired(true);
                    for (Cookie c : cookies) {
                        if (c.getName().equals("SessionId")) {
                            userAuthDataModel.setSessionExpired(false);
                        }
                    }

                } else {
                    userAuthDataModel.setSessionExpired(false);
                }
            }
        }

        if(userAuthDataModel.isSessionExpired()){
            response.addHeader("Set-Cookie", "SessionId=0; HttpOnly; SameSite=Lax; Path=/; Max-Age=0;");
            response.addHeader("Set-Cookie", "UserId=0; HttpOnly; SameSite=Lax; Path=/; Max-Age=0;");
            response.addHeader("Set-Cookie", "ExpValue=0; HttpOnly; SameSite=Lax; Path=/; Max-Age=0;");
        }

        String userAuthData = userServiceInterface.testUserLogged(request);

        if(userAuthData.equals("userAuthenticated") || userAuthData.equals("adminAuthenticated")){

            if(userAuthData.equals("adminAuthenticated")){
                userAuthDataModel.setAdminLogged(true);
            }
            else{
                userAuthDataModel.setAdminLogged(false);
            }
            userAuthDataModel.setNameName(userServiceInterface.getName(request));
            userAuthDataModel.setLogged(true);
            int maxAge = userServiceInterface.getExpValueFromCookie(request);
            System.out.println("staylogged u logged_in: ");
            System.out.println("userLogged u logged_in: ");
            String sessionId = userServiceInterface.getSessionIdFromCookie(request);

                response.addHeader("Set-Cookie",
                        String.format("%s=%s; %s; %s; %s; %s=%s",
                                "SessionId", sessionId,
                                "HttpOnly;", "SameSite=Lax", "Path=/", "Max-Age", maxAge));

        }
        return  userAuthDataModel;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserAuthDataModel userLogin(HttpServletRequest request, HttpServletResponse response,
                                       @RequestBody User user) throws IOException {

        UserAuthDataModel userAuthDataModel = new UserAuthDataModel();
        userAuthDataModel.setUsername(user.getUsername());
        userAuthDataModel.setLogged(false);
        String userAuthData = userServiceInterface.authUser(user);
        System.out.println("bio u login");

        if(userAuthData.equals("userAuthenticated") || userAuthData.equals("adminAuthenticated")){


            String sessionId = userServiceInterface.generateSessionId();
            userRepositoryInterface.saveSessionId(user, sessionId);

            int maxAge;
            if(user.isStayLogged()){
               maxAge = 1800;
               response.addHeader("Set-Cookie",
                       String.format("ExpValue=%s; HttpOnly; SameSite=Lax; Path=/;", maxAge));
            }
            else{
                maxAge = 300;
                response.addHeader("Set-Cookie",
                        String.format("ExpValue=%s; HttpOnly; SameSite=Lax; Path=/;", maxAge));
            }

            response.addHeader("Set-Cookie",
                    String.format("%s=%s; %s; %s; %s; %s=%s",
                            "SessionId", sessionId,
                            "HttpOnly;", "SameSite=Lax", "Path=/", "Max-Age", maxAge));
            response.addHeader("Set-Cookie",
                    String.format("%s=%s; %s; %s; %s", "UserId", userRepositoryInterface.getUserId(user),
                            "HttpOnly;", "SameSite=Lax", "Path=/"));

            userAuthDataModel.setLogged(true);
            if(userAuthData.equals("adminAuthenticated")){
                userAuthDataModel.setAdminLogged(true);
            }
            else{
                userAuthDataModel.setAdminLogged(false);
            }
            userAuthDataModel.setLoginStatus("");

            productRepositoryInterface.createTable(userRepositoryInterface.getUserId(user));

        }

        else if(userAuthData.equals("disabled")){
            userAuthDataModel.setLoginStatus("The user account is not activated!");
        }

        else if (userAuthData.equals("wrongUsernameOrPassword")){
            userAuthDataModel.setLoginStatus("The username or password is incorrect!");
        }


        return userAuthDataModel;

    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public boolean logoutUser(HttpServletRequest request, HttpServletResponse response){

        response.addHeader("Set-Cookie", "SessionId=0; HttpOnly; SameSite=Lax; Path=/; Max-Age=0;");
        response.addHeader("Set-Cookie", "UserId=0; HttpOnly; SameSite=Lax; Path=/; Max-Age=0;");
        response.addHeader("Set-Cookie", "ExpValue=0; HttpOnly; SameSite=Lax; Path=/; Max-Age=0;");
        userServiceInterface.logoutUser(request);
            return false;
    }


    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public boolean register(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) throws IOException, MessagingException, DocumentException {



        return userServiceInterface.registerUser(request, user);
    }

    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public boolean activateAccount(HttpServletResponse response, @RequestParam String token){

        System.out.println("token " + token);
        response.addHeader("Set-Cookie",
                String.format("%s=%s; %s; %s; %s;",
                        "SessionId", "welcome",
                        "HttpOnly;", "SameSite=Lax", "Path=/"));
        userRepositoryInterface.activateUser(token);
        return true;
    }

    @RequestMapping(value = "/user/del", method = RequestMethod.GET)
    public boolean deleteUser(HttpServletRequest request, HttpServletResponse response){

        response.addHeader("Set-Cookie", "SessionId=0; HttpOnly; SameSite=Lax; Path=/; Max-Age=0;");
        response.addHeader("Set-Cookie", "UserId=0; HttpOnly; SameSite=Lax; Path=/; Max-Age=0;");
        response.addHeader("Set-Cookie", "ExpValue=0; HttpOnly; SameSite=Lax; Path=/; Max-Age=0;");


        if(userServiceInterface.deleteUser(request)){
            return true;
        }
        return false;
    }

    }


