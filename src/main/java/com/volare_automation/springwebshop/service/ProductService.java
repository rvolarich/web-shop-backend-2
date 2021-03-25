package com.volare_automation.springwebshop.service;

import com.lowagie.text.DocumentException;
import com.volare_automation.springwebshop.model.CartProduct;
import com.volare_automation.springwebshop.model.Mail;
import com.volare_automation.springwebshop.model.Products;
import com.volare_automation.springwebshop.repository.ProductRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService implements ProductServiceInterface {

    @Autowired
    ProductRepositoryInterface productRepositoryInterface;

    @Autowired
    UserServiceInterface userServiceInterface;

    @Autowired
    EmailServiceInterface emailServiceInterface;


    @Override
    public String getImageService() throws IOException, SQLException {
        byte[] b = productRepositoryInterface.getImage();
        String base64Encoded = DatatypeConverter.printBase64Binary(b);
        return base64Encoded;
    }

    @Override
    public void storeImageService(){
        productRepositoryInterface.storeImage();
    }

    @Override
    public List<Products> getAllProducts() {
        return productRepositoryInterface.getAllProducts();
    }

    @Override
    public void postCartProduct(HttpServletRequest request, CartProduct cp) {

        System.out.println("ime produkta: " + cp.getProductName());
        cp.setProductQuantity(1);
        boolean b = true;
        String id = userServiceInterface.getUserIdFromCookie(request).toString();
        System.out.println("Idddddddd " + id);
        productRepositoryInterface.postCartProduct(cp, b, id);

    }

    @Override
    public List<Integer> getProductId() {
        return null;
    }

    @Override
    public List<CartProduct> deleteCartId(CartProduct cp, String idString) {
        Integer id = cp.getProductId();
        return productRepositoryInterface.deleteCartById(id, idString);
    }

    public double formatPrice(String price){
        double doublePrice = Double.parseDouble(price);
        double doublePriceRounded = Math.round(doublePrice * 100.0) /100.0;
        return doublePriceRounded;
    }

    @Override
    public boolean updateProducts(CartProduct cp) {

        double doublePriceRounded = formatPrice(cp.getProductPriceString());

        boolean sRet = productRepositoryInterface.updateProducts(doublePriceRounded,
                cp.getProductQuantity(), cp.getProductId());
        if(sRet){
            return true;
        }
        return false;
    }

    @Override
    public boolean insertProduct(CartProduct cp) {

        double doublePriceRounded = formatPrice(cp.getProductPriceString());
        cp.setProductPrice(doublePriceRounded);
        if(productRepositoryInterface.insertProduct(cp)){
            return true;
        }
        return false;
    }

    @Override
    public boolean confirmCartSendMail(List<CartProduct> cpList, String id) throws IOException, MessagingException, DocumentException {



        List<CartProduct> cartList = cpList;
 //       List<CartProduct> cartListRounded = new ArrayList<>();
        String nameName = cartList.get(cartList.size()-1).getNameName();
        String email = cartList.get(cartList.size()-1).getEmail();
        System.out.println("Email adresa: " + email);
        cartList.remove(cartList.size()-1);

        DecimalFormat twoDecimal = new DecimalFormat("#.00");

//        for(CartProduct c : cartList){
//            c.setProductPrice(Math.round(c.getProductPrice() * 100.0) / 100.0);
//            cartListRounded.add(c);
//        }
        double total = 0;

        for (int i = 0; i < cpList.size(); i++) {
            total += (cartList.get(i).getProductPrice()*cartList.get(i).getProductQuantity());
        }

        double totalRounded = Math.round(total * 100.0) / 100.0;

        double totalForPayment = totalRounded + 125;

        Map<String, Object> properties = new HashMap<>();
        properties.put("list", cartList);
        properties.put("sum", totalForPayment);
        properties.put("nameName", nameName);

        Mail mail = new Mail();
        mail.setFrom("noreply@gmail.com");
        mail.setTo(email);
        mail.setSubject("hi");
        mail.setHtmlTemplate(new Mail.HtmlTemplate("sample", properties));
        System.out.println("Email adresa nakon: " + mail.getTo());
        emailServiceInterface.sendMail(mail);

        productRepositoryInterface.confirmCartOrder(cartList, id);

        return false;
    }
}
