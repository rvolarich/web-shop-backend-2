package com.volare_automation.springwebshop.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Products {

    private int productId;
    private String productName;
    private String productDescription;
    private int productQuantity;
    private double productPrice;
    private String productImage;

    public Products() {
    }

    public Products(int product_id, String product_name,
                    String product_description, int product_quantity, double product_price, String product_image) {
        this.productId = product_id;
        this.productName = product_name;
        this.productDescription = product_description;
        this.productQuantity = product_quantity;
        this.productPrice = product_price;
        this.productImage = product_image;
        System.out.println("bio u konstruktoru");
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int product_id) {
        this.productId = product_id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String product_name) {
        this.productName = product_name;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String product_description) {
        this.productDescription = product_description;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int product_quantity) {
        this.productQuantity = product_quantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double product_price) {
        this.productPrice = product_price;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String product_image) {
        this.productImage = product_image;
    }
}
