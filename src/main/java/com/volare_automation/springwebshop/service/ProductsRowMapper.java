package com.volare_automation.springwebshop.service;
import com.volare_automation.springwebshop.model.Products;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductsRowMapper implements RowMapper<Products> {
    @Override
    public Products mapRow(ResultSet rs, int i) throws SQLException {
        Products products = new Products();
        products.setProductId(rs.getInt("productId"));
        products.setProductName(rs.getString("productName"));
        products.setProductDescription(rs.getString("productDescription"));
        products.setProductQuantity(rs.getInt("productQuantity"));
        products.setProductPrice(rs.getDouble("productPrice"));
        products.setProductImage(rs.getString("productImage"));
        return products;
    }
}
