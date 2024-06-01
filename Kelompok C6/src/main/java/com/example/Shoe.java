package com.example;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Shoe {
    private int id;
    private String name;
    private int size;
    private double price;
    private int stok;
    private String jenis;
    private Date tanggal_masuk;
    public String deskripsi;
    public abstract void printDescription();

    public Shoe(int id, String name, int size, double price, int stok, String jenis, Date tanggal_masuk, String deskripsi) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.price = price;
        this.stok = stok;
        this.jenis = jenis;
        this.tanggal_masuk = tanggal_masuk;
        this.deskripsi = deskripsi;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public Date getTanggal_masuk() {
        return tanggal_masuk;
    }

    public void setTanggal_masuk(Date tanggal_masuk) {
        this.tanggal_masuk = tanggal_masuk;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getDescription() {
        return "ID: " + id + ", Name: " + name + ", Size: " + size + ", Price: " + price + 
               ", Stok: " + stok + ", Jenis: " + jenis + ", Tanggal Masuk: " + tanggal_masuk;
    }

    public void SimpanKeDB() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO shoes (name, size, price, stok, jenis, tanggal_masuk, deskripsi) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            pstmt.setString(1, name);
            pstmt.setInt(2, size);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, stok);
            pstmt.setString(5, jenis);
            pstmt.setDate(6, tanggal_masuk);
            pstmt.setString(7, deskripsi);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UbahDiDB() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("UPDATE shoes SET name = ?, size = ?, price = ?, stok = ?, jenis = ?, tanggal_masuk = ? , deskripsi = ? WHERE shoe_id = ?")) {

            pstmt.setString(1, name);
            pstmt.setInt(2, size);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, stok);
            pstmt.setString(5, jenis);
            pstmt.setDate(6, tanggal_masuk);
            pstmt.setInt(7, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void HapusDariDB() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM shoes WHERE shoe_id = ?")) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Shoe createShoeFromResultSet(ResultSet rs) throws SQLException {
        String jenis = rs.getString("jenis");
        int id = rs.getInt("shoe_id");
        String name = rs.getString("name");
        int size = rs.getInt("size");
        double price = rs.getDouble("price");
        int stok = rs.getInt("stok");
        Date tanggal_masuk = rs.getDate("tanggal_masuk");
        String deskripsi = rs.getString("deskripsi");
    
        switch (jenis.toLowerCase()) {
            case "sport":
                String sportType = deskripsi;
                return new SportShoe(id, name, size, price, stok, jenis, tanggal_masuk, sportType);
            case "sneaker":
                String material = deskripsi;
                return new Sneaker(id, name, size, price, stok, jenis, tanggal_masuk, material);
            case "formal":
                String style = deskripsi;
                return new FormalShoe(id, name, size, price, stok, jenis, tanggal_masuk, style);
            default:
                throw new SQLException("Unknown shoe type: " + jenis);
        }
    }
    

    public static Shoe getById(int id) {
        Shoe shoe = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM shoes WHERE shoe_id = ?")) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    shoe = createShoeFromResultSet(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shoe;
    }
}
