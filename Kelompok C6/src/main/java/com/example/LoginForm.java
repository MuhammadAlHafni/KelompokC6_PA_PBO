package com.example;

import java.util.Scanner;

public final class LoginForm implements Verifikasi {
    private String role;

    public LoginForm(String role) {
        this.role = role;
    }

    @Override
    public boolean login(Scanner scanner) {
        System.out.println("+-----------------------------------------------------+");
        System.out.println("|                     "+ role +" Login                     |");
        System.out.println("+-----------------------------------------------------+");
        System.out.print("Masukkan Username: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan Password: ");
        String password = scanner.nextLine();

        boolean isValid = validateLogin(username, password);
        if (isValid) {
            System.out.println(role + " Login Berhasil!");
            waitForEnter(scanner);
        } else {
            System.out.println("Username atau Password salah.");
        }
        return isValid;
    }

    @Override
    public boolean validateLogin(String username, String password) {
        if (role.equals("Admin")) {
            return username.equals("admin") && password.equals("root");
        } else if (role.equals("Staff")) {
            Admin staff = Admin.getByUsername(username);
            return staff != null && staff.getPassword().equals(password);
        }
        return false;
    }

    public static void waitForEnter(Scanner scanner) {
        System.out.println("Tekan Enter untuk melanjutkan...");
        scanner.nextLine();
    }
}
