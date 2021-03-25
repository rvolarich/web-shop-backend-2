package com.volare_automation.springwebshop.repository;

import com.volare_automation.springwebshop.model.CartProduct;
import com.volare_automation.springwebshop.model.Products;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ProductRepositoryInterface {

    public byte [] getImage() throws SQLException, IOException;
    public void storeImage();
    public List<Products> getAllProducts();
    public void postCartProduct(CartProduct cp, boolean allowUpdate, String username);
    void postCartProductList(List<CartProduct> cpList, String idString);
    public List<Integer> getProductId(String id);
    public List<CartProduct> getCartProducts(String id);
    public Integer getTableQty(String id);
    public List<Integer> getCartItemQty(String id);
    public List<CartProduct> deleteCart(String id);
    public List<CartProduct> deleteCartById(Integer id, String idString);
    public CartProduct postCartAll(List<CartProduct> cpl, String id);
    public boolean confirmCartOrder(List<CartProduct> cp, String id);
    void createTable(String username);
    boolean updateProducts(double price, Integer quantity, Integer id);
    boolean insertProduct(CartProduct cp);
    boolean deleteProduct(CartProduct cp);

}
