package com.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            clearConsole();
            printMainMenu();

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        clearConsole();
                        LoginForm adminLoginForm = new LoginForm("Admin");
                        if (adminLoginForm.login(scanner)) {
                            clearConsole();
                            AdminMenu adminMenu = new AdminMenu(scanner);
                            adminMenu.displayMenu();
                        } else {
                            printLoginFailed("Admin");
                            waitForEnter(scanner);
                        }
                        break;
                    case 2:
                        clearConsole();
                        LoginForm staffLoginForm = new LoginForm("Staff");
                        if (staffLoginForm.login(scanner)) {
                            clearConsole();
                            StaffMenu staffMenu = new StaffMenu(scanner);
                            staffMenu.displayMenu();
                        } else {
                            printLoginFailed("Staff");
                            waitForEnter(scanner);
                        }
                        break;
                    case 3:
                        clearConsole();
                        System.out.println("+-----------------------------------+");
                        System.out.println("| Menutup program. Selamat tinggal! |");
                        System.out.println("+-----------------------------------+");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("+-----------------------------------------------------------+");
                        System.out.println("| Pilihan tidak valid. Harap masukkan angka antara 1 dan 3. |");
                        System.out.println("+-----------------------------------------------------------+");
                        waitForEnter(scanner);
                        break;
                }
            } else {
                System.out.println("+-----------------------------------------------------+");
                System.out.println("| Input tidak valid. Harap masukkan nomor yang valid. |");
                System.out.println("+-----------------------------------------------------+");
                scanner.nextLine();
                waitForEnter(scanner);
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("+--------------------------------------+");
        System.out.println("|      Selamat datang di Sistem        |");
        System.out.println("|      Manajemen Toko Sepatu           |");
        System.out.println("+--------------------------------------+");
        System.out.println("| 1. Login Admin                       |");
        System.out.println("| 2. Login Staff                       |");
        System.out.println("| 3. Keluar                            |");
        System.out.println("+--------------------------------------+");
        System.out.print("Masukkan pilihan Anda: ");
    }

    public static void waitForEnter(Scanner scanner) {
        System.out.println("Tekan Enter untuk melanjutkan...");
        scanner.nextLine();
    }

    private static void printLoginFailed(String userType) {
        String message = String.format("| Login %s gagal. Silakan coba lagi. |", userType);
        int length = message.length();
        String border = "+";
        for (int i = 0; i < length - 2; i++) {
            border += "-";
        }
        border += "+";
        System.out.println(border);
        System.out.println(message);
        System.out.println(border);
    }

    private static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception ex) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
}
