package com.volare_automation.springwebshop.controller;
//import com.volare_automation.springwebshop.model.CartProductTest;
import com.lowagie.text.DocumentException;
import com.volare_automation.springwebshop.model.CartProduct;
import com.volare_automation.springwebshop.repository.ProductRepositoryInterface;
import com.volare_automation.springwebshop.service.ProductServiceInterface;
import com.volare_automation.springwebshop.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(methods = {RequestMethod.POST, RequestMethod.GET},
        allowCredentials = "true")
public class CartController {

    @Autowired
    ProductServiceInterface productServiceInterface;

    @Autowired
    ProductRepositoryInterface productRepositoryInterface;

    @Autowired
    UserServiceInterface userServiceInterface;


//    @RequestMapping(value = "/pcpr", method = RequestMethod.POST)
//    public void postCartProductReturn(@RequestBody CartProduct cp){
//        productRepositoryInterface.postCartProduct(cp);
//    }

    @RequestMapping(value = "/pcp", method = RequestMethod.POST)
    public void postCartProduct(HttpServletRequest request, @RequestBody CartProduct cp){

        Cookie[] cookies = request.getCookies();
        if(cookies == null) {
            System.out.println("no cookies");
        }
        productServiceInterface.postCartProduct(request, cp);

    }

    @RequestMapping(value = "/postcartall", method = RequestMethod.POST)
    public CartProduct postAllCartProducts(HttpServletRequest request,
                                           @RequestBody List<CartProduct> cp, CartProduct cartUpdate){

        String idString = userServiceInterface.getUserIdFromCookie(request).toString();
        productRepositoryInterface.postCartAll(cp, idString);
        String id = userServiceInterface.getUserIdFromCookie(request).toString();
        cartUpdate.setCartUpdated(true);
        cartUpdate.setTotalCartQty(productRepositoryInterface.getTableQty(id));
        return cartUpdate;
    }
    // returns only boolean and total cart quantity
//    @RequestMapping(value = "/postcartall", method = RequestMethod.POST)
//    public CartProduct postAllCartProducts(@RequestBody List<CartProduct> cp, CartProduct cartUpdate){
//        productRepositoryInterface.postCartAll(cp);
//        cartUpdate.setCartUpdated(true);
//        cartUpdate.setTotalCartQty(productRepositoryInterface.getTableQty());
//        return cartUpdate;
//    }

//    @RequestMapping(value = "/postcartall", method = RequestMethod.POST)
//    public void postAllCartProducts(@RequestBody List<CartProduct> cp){
//        productRepositoryInterface.postCartAll(cp);
//
//
//    }
    @RequestMapping(value = "/post/cart/local", method = RequestMethod.POST)
    public void postCartLocal(HttpServletRequest request, @RequestBody List<CartProduct> cpList){

        System.out.println("bio u post cart local");
        String id = userServiceInterface.getUserIdFromCookie(request).toString();
        productRepositoryInterface.postCartProductList(cpList, id);
}

    @RequestMapping(value = "/getid", method = RequestMethod.GET)
    public List<Integer> getProductId(HttpServletRequest request){

        String id = userServiceInterface.getUserIdFromCookie(request).toString();
        return productRepositoryInterface.getProductId(id);

    }

    @RequestMapping(value = "/getcart", method = RequestMethod.GET)
    public List<CartProduct> getCartProducts(HttpServletRequest request){

        String id = userServiceInterface.getUserIdFromCookie(request).toString();
        System.out.println("id in getcart");
        return productRepositoryInterface.getCartProducts(id);

    }

    @RequestMapping(value = "/getcartqty", method = RequestMethod.GET)
    public Integer getCartQty(HttpServletRequest request){

        String id = userServiceInterface.getUserIdFromCookie(request).toString();
        return productRepositoryInterface.getTableQty(id);

    }

    @RequestMapping(value = "/getcartitemqtys", method = RequestMethod.GET)
    public List<Integer> getCartItemQtys(HttpServletRequest request){

        String id = userServiceInterface.getUserIdFromCookie(request).toString();
        return productRepositoryInterface.getCartItemQty(id);

    }

    @RequestMapping(value = "/deletecart", method = RequestMethod.GET)
    public List<CartProduct> deleteCart(HttpServletRequest request){

        String id = userServiceInterface.getUserIdFromCookie(request).toString();
        return productRepositoryInterface.deleteCart(id);
    }

    @RequestMapping(value = "/deletecartbyid", method = RequestMethod.POST)
    public List<CartProduct> deleteCartById(HttpServletRequest request, @RequestBody CartProduct cp){

        String id = userServiceInterface.getUserIdFromCookie(request).toString();
        return productServiceInterface.deleteCartId(cp, id);
    }

    @RequestMapping(value = "/confirmorder", method = RequestMethod.POST)
    public void confirmOrder(HttpServletRequest request, @RequestBody List<CartProduct> cp) throws IOException, MessagingException, DocumentException {


        
        if(request.getCookies() != null) {

            String userAuthData = userServiceInterface.testUserLogged(request);
            if (userAuthData.equals("userAuthenticated") || userAuthData.equals("adminAuthenticated")) {
                String id = userServiceInterface.getUserIdFromCookie(request).toString();
                System.out.println("id confirm order: " + id);
                productServiceInterface.confirmCartSendMail(cp, id);
            }
            else{
                productServiceInterface.confirmCartSendMail(cp, "guest");
                System.out.println(("update cart return: " + productServiceInterface.confirmCartSendMail(cp, "guest")));
                System.out.println("bio u guest!");
            }
        }
//        else{
//            productServiceInterface.confirmCartSendMail(cp, "guest");
//            System.out.println("bio u guest coockies null!");
//        }
    }

}
