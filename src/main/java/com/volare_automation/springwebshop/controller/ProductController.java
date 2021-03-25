package com.volare_automation.springwebshop.controller;

import com.volare_automation.springwebshop.model.CartProduct;
import com.volare_automation.springwebshop.model.Products;
import com.volare_automation.springwebshop.repository.ProductRepositoryInterface;
import com.volare_automation.springwebshop.service.ProductServiceInterface;
import com.volare_automation.springwebshop.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(allowCredentials = "true")
public class ProductController {

    @Autowired
    private ProductServiceInterface productServiceInterface;

    @Autowired
    private UserServiceInterface userServiceInterface;

    private final ProductRepositoryInterface productRepositoryInterface;

    @Autowired
    public ProductController(ProductRepositoryInterface productRepositoryInterface){
        this.productRepositoryInterface = productRepositoryInterface;
    }

    @RequestMapping(value = "/photo", method = RequestMethod.GET)
    public String getPhoto() throws SQLException, IOException {
        String s = productServiceInterface.getImageService();
        return s;
    }

    @RequestMapping(value = "/postitem", method = RequestMethod.POST)
    public void storeItem() throws SQLException {
        productServiceInterface.storeImageService();
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadImage(@RequestParam(required = true, value = "file")MultipartFile file,
                                              @RequestParam(required = true, value = "jsondata")String jsondata)  {
        return null;
    }


    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<Products> getProducts(HttpServletRequest request, HttpServletResponse response) throws SQLException {
       List<Products> productList = new ArrayList<>();
        System.out.println("bio u products");
//        Cookie[] cookies = request.getCookies();
//        for (int i = 0; i < cookies.length; i++) {
//            String name = cookies[i].getName();
//            String value = cookies[i].getValue();
//            if(cookies[i].getName().equals("$1&sessid")){
//                System.out.println("sessionCookie= " + value);
//            }
//        }
        /*if(userServiceInterface.testUserLogged(request))*/ return productServiceInterface.getAllProducts();
        //else return productList;

    }

    @RequestMapping(value = "/products/update", method = RequestMethod.POST)
    public String updateProducts(@RequestBody CartProduct cp){

        if(productServiceInterface.updateProducts(cp)){
            return "Product successfully updated!";
        }
        return "Error updating product!";
    }

    @RequestMapping(value = "/products/insert", method = RequestMethod.POST)
    public String insertProducts(@RequestBody CartProduct cp){

        if(productServiceInterface.insertProduct(cp)){
            return "Product added successfully!";
        }


        return "Error adding product!";
    }

    @RequestMapping(value = "/products/del", method = RequestMethod.DELETE)
        public String deleteProduct(@RequestBody CartProduct cp){
        System.out.println("id: " + cp.getProductId());

        if(productRepositoryInterface.deleteProduct(cp)){
            return "Successfully deleted!";
        }
        return "Error deleting product!";
    }


}
