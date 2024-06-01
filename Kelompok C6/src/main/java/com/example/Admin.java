package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {
    private int id;
    private String name;
    private String username;
    private String password;

    public Admin(int id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public void SimpanKeDBSTF() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO staff (name, username, password) VALUES (?, ?, ?)")) {

            pstmt.setString(1, name);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UbahDiDBSTF() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("UPDATE staff SET name = ?, username = ?, password = ? WHERE staff_id = ?")) {

            pstmt.setString(1, name);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void HapusDariDBSTF() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM staff WHERE staff_id = ?")) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Admin getByUsername(String username) {
        Admin staff = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM staff WHERE username = ?")) {

            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("staff_id");
                    String name = rs.getString("name");
                    String password = rs.getString("password");
                    staff = new Admin(id, name, username, password);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staff;
    }

    public void setId(int int1) {
        throw new UnsupportedOperationException("Unimplemented method 'setId'");
    }
}
