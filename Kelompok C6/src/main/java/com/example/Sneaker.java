package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Sneaker extends Shoe {
    @SuppressWarnings("unused")
    private String material;

    public Sneaker(int id, String name, int size, double price, int stok, String jenis, Date tanggal_masuk,
            String deskripsi) {
        super(id, name, size, price, stok, jenis, new java.sql.Date(tanggal_masuk.getTime()), deskripsi);
        this.material = deskripsi;
    }

    public String getMaterial() {
        return deskripsi;
    }

    public void setMaterial(String material) {
        this.material = deskripsi;
    }

    @Override
    public void SimpanKeDB() {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO shoes (name, size, price, stok, jenis, tanggal_masuk, deskripsi) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            pstmt.setString(1, getName());
            pstmt.setInt(2, getSize());
            pstmt.setDouble(3, getPrice());
            pstmt.setInt(4, getStok());
            pstmt.setString(5, getJenis());
            pstmt.setDate(6, getTanggal_masuk());
            pstmt.setString(7, getDeskripsi());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void UbahDiDB() {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(
                        "UPDATE shoes SET name = ?, size = ?, price = ?, stok = ?, jenis = ?, tanggal_masuk = ?, deskripsi = ? WHERE shoe_id = ?")) {

            pstmt.setString(1, getName());
            pstmt.setInt(2, getSize());
            pstmt.setDouble(3, getPrice());
            pstmt.setInt(4, getStok());
            pstmt.setString(5, getJenis());
            pstmt.setDate(6, getTanggal_masuk());
            pstmt.setString(7, getDeskripsi());
            pstmt.setInt(8, getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void HapusDariDB() {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement("DELETE FROM shoes WHERE shoe_id = ?")) {

            pstmt.setInt(1, getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printDescription() {
        System.out.println("Ini adalah Sepatu Sneakers.");
    }
}
