package com.volare_automation.springwebshop.service;

import com.volare_automation.springwebshop.model.CartProduct;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartProductRowMapper implements RowMapper<CartProduct> {
    @Override
    public CartProduct mapRow(ResultSet rs, int i) throws SQLException {
        CartProduct cp = new CartProduct();
        cp.setProductId(rs.getInt("productId"));
        cp.setProductName(rs.getString("productName"));
        cp.setProductDescription(rs.getString("productDescription"));
        cp.setProductQuantity(rs.getInt("productQuantity"));
        cp.setProductPrice(rs.getDouble("productPrice"));
        cp.setProductImage(rs.getString("productImage"));
        cp.setProductStock(rs.getInt("productStock"));
        return cp;
    }
}
