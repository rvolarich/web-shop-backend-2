package com.volare_automation.springwebshop.service;

import com.lowagie.text.DocumentException;
import com.volare_automation.springwebshop.model.CartProduct;
import com.volare_automation.springwebshop.model.Products;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ProductServiceInterface {

    public String getImageService() throws IOException, SQLException;
    public void storeImageService();
    public List<Products> getAllProducts();
    public void postCartProduct(HttpServletRequest request, CartProduct cp);
    public List<Integer> getProductId();
    public List<CartProduct> deleteCartId(CartProduct cp, String idString);
    boolean updateProducts(CartProduct cp);
    boolean insertProduct(CartProduct cp);
    boolean confirmCartSendMail(List<CartProduct> cpList, String id) throws IOException, MessagingException, DocumentException;
}
