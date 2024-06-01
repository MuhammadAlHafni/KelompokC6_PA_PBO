package com.example;

import java.util.Scanner;

public interface Verifikasi {
    boolean login(Scanner scanner);
    boolean validateLogin(String username, String password);
}
