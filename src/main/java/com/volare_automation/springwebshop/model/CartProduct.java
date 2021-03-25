package com.volare_automation.springwebshop.model;

public class CartProduct {

    private int productId;
    private String productName;
    private String productDescription;
    private int productQuantity;
    private double productPrice;
    String productImage;
    boolean cartUpdated;
    private Integer totalCartQty;
    private Integer productStock;
    private String productPriceString;
    private String nameName;
    private String email;

    public CartProduct() {
    }

    public CartProduct(int productId, String productName, Integer productStock,
                       String productDescription, int productQuantity, double productPrice, String productImage) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.productStock = productStock;

    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public boolean isCartUpdated() {
        return cartUpdated;
    }

    public void setCartUpdated(boolean cartUpdated) {
        this.cartUpdated = cartUpdated;
    }

    public Integer getTotalCartQty() {
        return totalCartQty;
    }

    public void setTotalCartQty(Integer totalCartQty) {
        this.totalCartQty = totalCartQty;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }

    public String getProductPriceString() {
        return productPriceString;
    }

    public void setProductPriceString(String productPriceString) {
        this.productPriceString = productPriceString;
    }

    public String getNameName() {
        return nameName;
    }

    public void setNameName(String nameName) {
        this.nameName = nameName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
