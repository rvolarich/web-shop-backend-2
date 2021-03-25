package com.volare_automation.springwebshop.repository;

import com.volare_automation.springwebshop.model.CartProduct;
import com.volare_automation.springwebshop.model.Products;
import com.volare_automation.springwebshop.service.CartProductRowMapper;
import com.volare_automation.springwebshop.service.ProductsRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.*;
import java.sql.*;

@Repository
public class ProductRepository implements ProductRepositoryInterface {

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;


    private byte[] imgBytes;
    InputStream is;
    Connection conn;
    PreparedStatement preparedStatement;
    FileInputStream fileInputStream = null;


//    public Connection getConnection() throws SQLException {
//        conn = DriverManager
//                .getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
//        return conn;
//    }



    @PostConstruct
    private void postConstruct() throws IOException, SQLException {
        jdbcTemplate = new JdbcTemplate(dataSource);

        System.out.println("Iv been in post construct");
    }

//    public InputStream inputStream () throws SQLException {
//        conn = DriverManager
//                .getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
//        PreparedStatement ps = conn.prepareStatement("SELECT data FROM images WHERE name=?");
//        ps.setString(1, "fcu.jpg");
//        ResultSet rs = ps.executeQuery();
//        if (rs != null) {
//            while(rs.next()) {
//                imgBytes = rs.getBytes(1);
//                is = rs.getBinaryStream(2);
//            }
//            rs.close();
//        }
//        ps.close();
//        return is;
//    }

    @Override
    public List<Products> getAllProducts (){
        String sql = "SELECT * FROM products ORDER BY productId ASC";
        //jdbcTemplate.execute("SELECT * FROM products ORDER BY productId ASC;");
        List<Products> products = jdbcTemplate.query(
                sql,
                new ProductsRowMapper());
        return products;
    }

    @Override
    public void postCartProduct(CartProduct cp, boolean allowUpdate, String id) {
        String sql = String.format("INSERT INTO t_%s (productId, productName, productDescription, productQuantity," +
                " productPrice, productImage, productStock) " + "VALUES ( ?, ?, ?, ?, ?, ?, ?)", id);
        String query = String.format("SELECT productId FROM t_%s", id);
        String updateQty = String.format("UPDATE t_%s SET productquantity = ? WHERE productid = ?", id);
        String getSingleQty = String.format("SELECT productquantity FROM t_%s WHERE productid = ?", id);
        String getProductStock = "SELECT productquantity FROM products WHERE productid = ?";
        List<Integer> idList = jdbcTemplate.queryForList(query, Integer.class);
        for(int i = 0; i < idList.size(); i++){
            if(idList.get(i) == cp.getProductId()){
                Integer total = jdbcTemplate.queryForObject(getSingleQty,
                        new Object[]{cp.getProductId()}, Integer.class) + cp.getProductQuantity();
                jdbcTemplate.update(updateQty, total, cp.getProductId());
                allowUpdate = false;
            }
        }

        if(allowUpdate){
            Integer stock = jdbcTemplate.queryForObject(getProductStock,
                    new Object[]{cp.getProductId()}, Integer.class);
            int result = jdbcTemplate.update(sql, cp.getProductId(), cp.getProductName(), cp.getProductDescription(),
                    cp.getProductQuantity(), cp.getProductPrice(), cp.getProductImage(), stock);

            if (result > 0) {
                System.out.println("Insert successfully into Guest Cart.");
            }
            else if (result==0) {
                System.out.println("Unsuccessfull insert");
            }
        }

    }

    @Override
    public void postCartProductList(List<CartProduct> cpList, String idString) {
        boolean allowPushToSaveList = false;
        String queryForIdList = String.format("SELECT productId FROM t_%s", idString);
        String getSingleQty = String.format("SELECT productquantity FROM t_%s WHERE productid = ?", idString);
        String updateQty = String.format("UPDATE t_%s SET productquantity = ? WHERE productid = ?", idString);
        String insertProduct = String.format("INSERT INTO t_%s (productId, productName, productDescription, productQuantity," +
                " productPrice, productImage, productStock) " + "VALUES ( ?, ?, ?, ?, ?, ?, ?)", idString);
        String getProductStock = "SELECT productquantity FROM products WHERE productid = ?";
        List<Integer> idList = jdbcTemplate.queryForList(queryForIdList, Integer.class);
        List<CartProduct> listToSave = new ArrayList<CartProduct>();
        System.out.println("ID list size: " + idList.size());
        if(idList.size() == 0){
            postListToCart(cpList, idString);
        }else {
            for (int i = 0; i < cpList.size(); i++) {

                for (int k = 0; k < idList.size(); k++) {
                    if (cpList.get(i).getProductId() == idList.get(k)) {

                        allowPushToSaveList = false;
                        Integer total = jdbcTemplate.queryForObject(getSingleQty,
                                new Object[]{cpList.get(i).getProductId()}, Integer.class) + cpList.get(i).getProductQuantity();
                        jdbcTemplate.update(updateQty, total, cpList.get(i).getProductId());
                        break;

//
                    } else {
                        allowPushToSaveList = true;

                    }
                }
                if (allowPushToSaveList) {
                    listToSave.add(cpList.get(i));
                }
            }

            postListToCart(listToSave, idString);

        }
    }

    public void postListToCart(List<CartProduct> cpList, String idString){

        String getProductStock = "SELECT productquantity FROM products WHERE productid = ?";
        String sql = String.format("INSERT INTO t_%s (productId, productName, productDescription, productQuantity," +
                " productPrice, productImage, productStock) " + "VALUES ( ?, ?, ?, ?, ?, ?, ?)", idString);


        for(int i = 0; i < cpList.size(); i++){
            Integer stock = jdbcTemplate.queryForObject(getProductStock, new Object[]{cpList.get(i).getProductId()},
                    Integer.class);
            //while(stock == null){};
            jdbcTemplate.update(sql, cpList.get(i).getProductId(), cpList.get(i).getProductName(),
                    cpList.get(i).getProductDescription(), cpList.get(i).getProductQuantity(), cpList.get(i).getProductPrice(),
                    cpList.get(i).getProductImage(), stock);
        }
    }

    @Override
    public List<Integer> getProductId(String id) {

        String query = String.format("SELECT productId FROM t_%s", id);
        List<Integer> ids = jdbcTemplate.queryForList(query,Integer.class);

        return ids;
    }

    @Override
    public List<CartProduct> getCartProducts(String id) {
        String sql = String.format("SELECT * FROM t_%s", id);

        List<CartProduct> cartProducts = jdbcTemplate.query(
                sql,
                new CartProductRowMapper());
        return cartProducts;
    }

    public Integer getTableQty(String id) {
        Integer totalQuantity = 0;
        List<Integer> totalQty = new ArrayList<Integer>();
        String queryForQty = String.format("SELECT productquantity FROM t_%s", id);


        totalQty = jdbcTemplate.queryForList(queryForQty, Integer.class);

        for(int i = 0; i < totalQty.size(); i++){
            totalQuantity += totalQty.get(i);
        }



        return totalQuantity;
    }

    @Override
    public List<Integer> getCartItemQty(String id) {
        List<Integer> qtyCartItemList = new ArrayList<Integer>();
        String query = String.format("SELECT productquantity FROM t_%s", id);
        qtyCartItemList = jdbcTemplate.queryForList(query, Integer.class);
        return qtyCartItemList;
    }

    @Override
    public List<CartProduct> deleteCart(String id) {
        String sql = String.format("DELETE FROM t_%s", id);
        String query = String.format("SELECT * FROM t_%s", id);

        int delInt = jdbcTemplate.update(sql);
        List<CartProduct> cp = jdbcTemplate.queryForList(query, CartProduct.class);
        return cp;
    }

    @Override
    public List<CartProduct> deleteCartById(Integer id, String idString) {
        String sql = String.format("DELETE FROM t_%s WHERE productid = ?", idString);
        String query = String.format("SELECT * FROM t_%s", idString);

        Object [] cartItem = new Object[]{id};
        System.out.println("ID: " + id);
        int i = jdbcTemplate.update(sql, cartItem);
        if(i == 0){
            System.out.println("unsuccesfull delete");
        }else{
            System.out.println("succesfull delete");
        }
        List<CartProduct> cartProducts = jdbcTemplate.query(
                query,
                new CartProductRowMapper());
        return cartProducts;
    }

    @Override
    public CartProduct postCartAll(List<CartProduct> cpl, String id) {

        String query = String.format("insert into t_%s (productId, productName, productDescription, " +
                "productQuantity, productPrice, productImage, productStock) values (?,?,?,?,?,?,?)", id);
        String getProductStock = "SELECT productquantity FROM products WHERE productid = ?";
        String sql = String.format("DELETE FROM t_%s", id);
        jdbcTemplate.execute(sql);
        List<Object[]> inputList = new ArrayList<Object[]>();
        for(CartProduct emp:cpl){
            if(emp.getProductQuantity() > 0) {
                Integer stock = jdbcTemplate.queryForObject(getProductStock,
                        new Object[]{emp.getProductId()}, Integer.class);
                Object[] tmp = {emp.getProductId(), emp.getProductName(), emp.getProductDescription(),
                        emp.getProductQuantity(), emp.getProductPrice(), emp.getProductImage(), stock};
                inputList.add(tmp);
            }
        }
        CartProduct cartProduct = new CartProduct();
        int [] iList = jdbcTemplate.batchUpdate(query, inputList);
        for(int i = 0; i < iList.length; i++){
            if(iList[i] != 1){
                //cartProduct.setCartUpdated(false);
            }else {
//                cartProduct.setCartUpdated(true);
//                cartProduct.setTotalCartQty(getTableQty());
            }
        }

        return cartProduct;
    }

    @Override
    public boolean confirmCartOrder(List<CartProduct> cp, String id) {

        Integer confirmUpdate = 0;
        List<Integer> updateList = new ArrayList();
        //try {
            boolean allowUpdate = true;

            String getProductStock = "SELECT productquantity FROM products WHERE productid = ?";
            String sql = "UPDATE products SET productquantity = ? WHERE productid = ?";
            for (int i = 0; i < cp.size(); i++) {
                //Object [] tmp = {c.getProductId(), c.getProductQuantity()};
                Integer stock = jdbcTemplate.queryForObject(getProductStock,
                        new Object[]{cp.get(i).getProductId()}, Integer.class);
                if (stock - cp.get(i).getProductQuantity() < 0) {
                    allowUpdate = false;
                }
 //               System.out.println("result: " + (stock - c.getProductQuantity()));
                if (allowUpdate) {
                    if (!id.equals("guest")) {
                        String query = String.format("DELETE FROM t_%s", id);
                        jdbcTemplate.execute(query);
                        System.out.println("bio u delete user caert");
                    }
                    int prodQty = stock - cp.get(i).getProductQuantity();
                    confirmUpdate = jdbcTemplate.update(sql, prodQty, cp.get(i).getProductId());
                    allowUpdate = true;
                    updateList.add(confirmUpdate);
                    System.out.println("update list: " + updateList);
                }

            }
            System.out.println("fgdd list: " + updateList);

            return true;
//        }catch (Exception e){
//
//        }
    }

    @Override
    public void createTable(String id) {
        String createTable = String.format("CREATE TABLE IF NOT EXISTS t_%s %s", id,
               "(productid integer," +
                       "    productname character varying(100), " +
                       "    productdescription character varying(511), " +
                       "    productquantity integer," +
                       "    productprice numeric(16,2)," +
                       "    productimage character varying," +
                       "    productstock integer);");

        jdbcTemplate.execute(createTable);
    }

    @Override
    public boolean updateProducts(double price, Integer quantity, Integer id) {

        String sql = "UPDATE products SET productprice = ?, productquantity = ? WHERE productid = ?";
        int i = jdbcTemplate.update(sql, price, quantity, id);
        if(i == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean insertProduct(CartProduct cp) {

        String sql = "INSERT INTO products (productname, productdescription," +
                "productquantity, productprice, productimage) VALUES (?,?,?,?,?)";

        int i = jdbcTemplate.update(sql, cp.getProductName(), cp.getProductDescription(), cp.getProductQuantity(),
                cp.getProductPrice(), cp.getProductImage());
        if(i == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteProduct(CartProduct cp) {

        String sql = "DELETE FROM products WHERE productid=?";

        int i = jdbcTemplate.update(sql, cp.getProductId());
        if(i == 1){
            return true;
        }
        return false;
    }


    @Override
    public byte[] getImage() throws SQLException, IOException {




        String sql = "SELECT * FROM images";
        conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        //FileOutputStream fileOutputStream = new FileOutputStream("G:/fcu.jpg");
        if (rs != null) {
            int index = 0;
            while(rs.next()) {
                imgBytes = rs.getBytes(6);
               // fileOutputStream.write(imgBytes);
            }
            //fileOutputStream.close();
            rs.close();
        }
        ps.close();
//        while(rs.next()){
//            Blob imageBlob = rs.getBlob(2);
//            FileOutputStream fileOutputStream = new FileOutputStream("G:/");
//            fileOutputStream.write(imageBlob.getBytes(1, (int) imageBlob.length()));
//
//        }
        return imgBytes;
    }

    @Override
    public void storeImage(){
//        String sql = "INSERT INTO num(number) VALUES  (?)";
//        conn = DriverManager
//                .getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
//        preparedStatement = conn.prepareStatement(sql);
//        preparedStatement.setInt(1,5);
//        preparedStatement.executeUpdate();
        String sql = "INSERT INTO products (product_name, product_description, " +
                "product_quantity, product_price, product_image) VALUES  (?,?,?,?,?)";
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
             PreparedStatement pst = con.prepareStatement(sql)) {

            File img = new File("C:/borg_ship.jpg");

            try (FileInputStream fin = new FileInputStream(img)) {

                pst.setBinaryStream(5, fin, (int) img.length());
                pst.setString(1, "Ultimate Vessel Borg ");
                pst.setString(2, "The Conqueror Of The Universe");
                pst.setInt(3, 5);
                pst.setDouble(4, 249.98);
                pst.executeUpdate();
            } catch (IOException ex) {
                Logger.getLogger(ProductRepository.class.getName()).log(
                        Level.SEVERE, ex.getMessage(), ex);
            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(ProductRepository.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }


}

//    public void storeImage() {
//        String sql = "INSERT INTO num(number) VALUES  (?)";
//
//        jdbcTemplate.update(connection -> {
//
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ps.setInt(1, 1);
//
////                File file = new File("F:/fcu.jpg");
////
////                try {
////                    fileInputStream = new FileInputStream(file);
////                } catch (FileNotFoundException e) {
////                    e.printStackTrace();
////                }
////                ps.setString(2, file.getName());
////                ps.setBinaryStream(3, fileInputStream);
//            return ps;


 //       });
  //  }
