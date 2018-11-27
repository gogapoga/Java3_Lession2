package ru.gb;

import java.sql.*;
import java.util.ArrayList;

public class SQLService {
    private static Connection connection;
    private static Statement stmt;

    public static  void connect() throws  SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:mainDB.db");
            stmt = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void createTab() {
        String SQL = "CREATE TABLE IF NOT EXISTS PRODUCTS " +
                "(ID INTEGER NOT NULL, " +
                " PRODID STRING NOT NULL, " +
                " TITLE STRING PRIMARY KEY NOT NULL, " +
                " COST INTEGER NOT NULL)";
        try{
            stmt.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void clearTab() {
        String SQL = "DELETE FROM PRODUCTS";
        try{
            stmt.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addProduct(int ID, String prodID, String title, int cost) {
        String SQL = "INSERT INTO PRODUCTS (ID , PRODID , TITLE , COST) VALUES (? , ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setInt(1, ID);
            ps.setString(2, prodID);
            ps.setString(3, title);
            ps.setInt(4, cost);
            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addProduct2(int ID, String prodID, String title, int cost) {
        String SQL = String.format("INSERT INTO PRODUCTS (ID , PRODID , TITLE , COST) VALUES ('%s' , '%s', '%s', '%s')", ID, prodID, title, cost);
        try{
            stmt.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Product getProduct(String title) {
        String sql = String.format("SELECT * FROM PRODUCTS WHERE TITLE= '%s'", title);
        try{
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) return new Product(rs.getString(3), rs.getInt(4));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<Product> getProductsbyCost(int MinCost, int MaxCost) {
        String sql = String.format("SELECT * FROM PRODUCTS WHERE COST >= '%s' AND COST <= '%s'", MinCost, MaxCost);
        ArrayList<Product> Res = new ArrayList<Product>();
        try{
           ResultSet rs = stmt.executeQuery(sql);
           while(rs.next()) {
                  Res.add(new Product(rs.getString(3), rs.getInt(4)));
           }
           return Res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void changeProduct(Product product) {
        String SQL = String.format("UPDATE PRODUCTS SET COST = '%s' WHERE TITLE = '%s'", product.getCost(),product.getTitle());
        try{
            stmt.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

