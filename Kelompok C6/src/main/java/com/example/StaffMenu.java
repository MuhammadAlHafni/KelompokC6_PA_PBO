package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StaffMenu {

    private Scanner scanner;

    public StaffMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void displayMenu() {
        while (true) {
            clearConsole();
            System.out.println("+----------------------+");
            System.out.println("|      Menu Staff      |");
            System.out.println("+----------------------+");
            System.out.println("| 1. Tampilkan Sepatu  |");
            System.out.println("| 2. Tambah Sepatu     |");
            System.out.println("| 3. Ubah Sepatu       |");
            System.out.println("| 4. Hapus Sepatu      |");
            System.out.println("| 5. Keluar Menu       |");
            System.out.println("+----------------------+");
            System.out.print("Masukkan Pilihan [1-5]: ");
    
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
    
                switch (choice) {
                    case 1:
                        viewShoes();
                        waitForEnter(scanner);
                        break;
                    case 2:
                        addShoe();
                        break;
                    case 3:
                        updateShoe();
                        break;
                    case 4:
                        deleteShoe();
                        break;
                    case 5:
                        clearConsole();
                        System.out.println("+-----------------------------------+");
                        System.out.println("| Program Selesai. Goodbye!         |");
                        System.out.println("+-----------------------------------+");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("+-----------------------------------------------------------+");
                        System.out.println("| Invalid pilihan. Mohon Masukkan Angka Dari 1 sampai 5     |");
                        System.out.println("+-----------------------------------------------------------+");
                        waitForEnter(scanner);
                        break;
                }
            } else {
                System.out.println("+-----------------------------------------------------+");
                System.out.println("| Invalid input. Mohon Masukkan Angka yang sesuai.    |");
                System.out.println("+-----------------------------------------------------+");
                scanner.nextLine();
                waitForEnter(scanner);
            }
        }
    }
    

    private void viewShoes() {
        System.out.println("+----------------------+");
        System.out.println("|   Tampilkan Sepatu   |");
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM shoes");
             ResultSet rs = pstmt.executeQuery()) {

            boolean shoesFound = false;
            while (rs.next()) {
                shoesFound = true;
                Shoe shoe = Shoe.createShoeFromResultSet(rs);
                System.out.println("+----------------------+");
                System.out.println("-ID: " + shoe.getId());
                System.out.println("-Nama: " + shoe.getName());
                System.out.println("-Ukuran: " + shoe.getSize());
                System.out.println("-Harga: " + shoe.getPrice());
                System.out.println("-Stok: " + shoe.getStok());
                System.out.println("-Tipe: " + shoe.getJenis());
                System.out.println("-Tanggal Masuk: " + shoe.getTanggal_masuk());
                System.out.println("-Deskripsi: " + shoe.getDeskripsi());
                System.out.println("+----------------------+");
            }

            if (!shoesFound) {
                System.out.println("Sepatu Tidak Ditemukan.");
            }

        } catch (SQLException e) {
            System.out.println("Error: Tidak bisa menampilkan Daftar Sepatu.");
            e.printStackTrace();
        }
    }
    

    private void addShoe() {
        clearConsole();
        System.out.println("+-------------------------+");
        System.out.println("|       Tambah Sepatu     |");
        System.out.println("+-------------------------+");
        System.out.println("| Pilih Tipe Sepatu:      |");
        System.out.println("| 1. Sepatu Sport         |");
        System.out.println("| 2. Sneaker              |");
        System.out.println("| 3. Sepatu Formal        |");
        System.out.println("+-------------------------+");
        System.out.print("Masukkan Pilihan [1-3]: ");
    
        int shoeTypeChoice = 0;
        try {
            shoeTypeChoice = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("+-------------------------------------------------------+");
            System.out.println("| Input Salah. Kembali Ke Main Menu.                    |");
            System.out.println("+-------------------------------------------------------+");
            scanner.nextLine();
            return;
        }
    
        if (shoeTypeChoice < 1 || shoeTypeChoice > 3) {
            System.out.println("+-----------------------------------------------------------+");
            System.out.println("| Pilihan Salah. Masukkan angka 1 sampai 3.                 |");
            System.out.println("+-----------------------------------------------------------+");
            waitForEnter(scanner);
            return;
        }
    
        clearConsole();
        System.out.print("\nMasukkan Nama Sepatu: ");
        String name = "";
        boolean isValidName = false;
    
        while (!isValidName) {
            name = scanner.nextLine().trim(); 
    
            if (name.matches("[a-zA-Z ]+")) {
                isValidName = true;
            } else {
                clearConsole();
                System.out.println("+------------------------------------------------------------------+");
                System.out.println("| Inputan Salah. Sepatu harus menggandung huruf dan spasi.         |");
                System.out.println("+------------------------------------------------------------------+");
                waitForEnter(scanner);
                clearConsole();
                System.out.print("Masukkan Nama Sepatu: ");
            }
        }
    
        int size = 0;
        double price = 0;
        int stock = 0;
        java.sql.Date entryDate = null;
        String description = "";
    
        boolean isValidInput = false;
        while (!isValidInput) {
            try {
                System.out.print("Masukkan Ukuran Sepatu: ");
                size = scanner.nextInt();
                if (size <= 0) {
                    throw new Exception("Ukuran harus angka positif.");
                }
                scanner.nextLine();
    
                System.out.print("Masukkan Harga Sepatu: ");
                price = scanner.nextDouble();
                if (price <= 0) {
                    throw new Exception("Harga harus angka positif.");
                }
                scanner.nextLine();
    
                System.out.print("Masukann Stok Sepatu: ");
                stock = scanner.nextInt();
                if (stock < 0) {
                    throw new Exception("Stok harus angka positif.");
                }
                scanner.nextLine();
    
                System.out.print("Masukkan Tanggal Masuk Sepatu (yyyy-mm-dd): ");
                String entryDateString = scanner.nextLine();
                entryDate = java.sql.Date.valueOf(entryDateString);
    
                System.out.print("Masukkan Deskripsi Sepatu: ");
                description = scanner.nextLine();
    
                isValidInput = true;
            } catch (Exception e) {
                System.out.println("+-------------------------------------------------------------------------+");
                System.out.println("  | Inputan Salah: " + e.getMessage());
                System.out.println("| Tolong Masukkan Ukuran, Harga, Stok, Tanggal, dan Deskripsi yang benar. |");
                System.out.println("+-------------------------------------------------------------------------+");
                scanner.nextLine();
                waitForEnter(scanner);
            }
        }
    
        Shoe newShoe = null;
        String type = "";
    
        switch (shoeTypeChoice) {
            case 1:
                type = "Sport";
                newShoe = new SportShoe(0, name, size, price, stock, type, entryDate, description);
                break;
            case 2:
                type = "Sneaker";
                newShoe = new Sneaker(0, name, size, price, stock, type, entryDate, description);
                break;
            case 3:
                type = "Formal";
                newShoe = new FormalShoe(0, name, size, price, stock, type, entryDate, description);
                break;
            default:
                System.out.println("+-----------------------------------------------------+");
                System.out.println("| Pilihan Sepatu Salah. Kembali ke menu.   |");
                System.out.println("+-----------------------------------------------------+");
                return;
        }
    
        if (newShoe != null) {
            newShoe.SimpanKeDB();
            System.out.println("Sepatu berhasil ditambahkan.");
        }
    }
    

    private void updateShoe() {
        clearConsole();
        viewShoes();
        System.out.println("+-----------------------------------------------------+");
        System.out.println("|                   Ubah Sepatu                       |");
        System.out.println("+-----------------------------------------------------+");
        System.out.print("Masukkan ID Sepatu untuk diubah: ");
    
        int id = 0;
        try {
            id = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("+-----------------------------------------------------+");
            System.out.println("| Inputan Salah. Kembali ke menu.                     |");
            System.out.println("+-----------------------------------------------------+");
            scanner.nextLine();
            return;
        }
    
        Shoe shoeToUpdate = Shoe.getById(id);
    
        if (shoeToUpdate != null) {
            System.out.print("Masukkan Nama Sepatu Baru: ");
            String name = "";
            boolean isValidName = false;
    
            while (!isValidName) {
                name = scanner.nextLine().trim();
    
                if (name.matches("[a-zA-Z ]+")) {
                    isValidName = true;
                } else {
                    System.out.println("+----------------------------------------------------------------+");
                    System.out.println("| Inputan Salah. Sepatu harus menggandung huruf dan spasi.       |");
                    System.out.println("+----------------------------------------------------------------+");
                    System.out.print("Masukkan Nama Sepatu Baru: ");
                }
            }
    
            int size = 0;
            double price = 0;
            int stock = 0;
            java.sql.Date entryDate = null;
            String description = "";
    
            boolean isValidInput = false;
            while (!isValidInput) {
                try {
                    System.out.print("Masukkan Ukuran Sepatu Baru: ");
                    size = scanner.nextInt();
                    if (size <= 0) {
                        throw new Exception("Ukuran harus angka positif.");
                    }
                    scanner.nextLine(); 
    
                    System.out.print("Masukkan Harga Sepatu Baru: ");
                    price = scanner.nextDouble();
                    if (price <= 0) {
                        throw new Exception("Harga harus angka positif.");
                    }
                    scanner.nextLine();
    
                    System.out.print("Masukkan Stok Sepatu Baru: ");
                    stock = scanner.nextInt();
                    if (stock < 0) {
                        throw new Exception("Stok harus angka positif.");
                    }
                    scanner.nextLine();
    
                    System.out.print("Masukkan Tanggal Masuk Sepatu Baru (yyyy-mm-dd): ");
                    String entryDateString = scanner.nextLine();
                    entryDate = java.sql.Date.valueOf(entryDateString);
    
                    System.out.print("Masukkan Deskripsi Sepatu Baru: ");
                    description = scanner.nextLine();
    
                    isValidInput = true;
                } catch (Exception e) {
                    System.out.println("+------------------------------------------------------------------------+");
                    System.out.println("  | Inputan Salah: " + e.getMessage());
                    System.out.println("| Tolong Masukkan Ukuran, Harga, Stok, Tanggal, dan Deskripsi yang benar.|");
                    System.out.println("+------------------------------------------------------------------------+");
                    scanner.nextLine();
                    waitForEnter(scanner);
                }
            }
    
            shoeToUpdate.setName(name);
            shoeToUpdate.setSize(size);
            shoeToUpdate.setPrice(price);
            shoeToUpdate.setStok(stock);
            shoeToUpdate.setTanggal_masuk(entryDate);
            shoeToUpdate.setDeskripsi(description);
            shoeToUpdate.UbahDiDB();
    
            System.out.println("+-----------------------------------------------------+");
            System.out.println("| Sepatu dengan ID " + id + " Berhasil diubah.       |");
            System.out.println("+-----------------------------------------------------+");
        } else {
            System.out.println("+-----------------------------------------------------+");
            System.out.println("| Sepatu dengan ID " + id + " Tidak ditemukan.                  |");
            System.out.println("+-----------------------------------------------------+");
        }
    }
    

    private void deleteShoe() {
        clearConsole();
        viewShoes();
        System.out.println("+-------------------------+");
        System.out.println("|       Hapus Sepatu      |");
        System.out.println("+-------------------------+");
        System.out.print("Masukkan ID Sepatu untuk dihapus: ");
    
        int id = 0;
        try {
            id = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("+-----------------------------------------------------+");
            System.out.println("| Inputan Salah. Kembali ke menu.                     |");
            System.out.println("+-----------------------------------------------------+");
            scanner.nextLine();
            return;
        }
    
        Shoe shoeToDelete = Shoe.getById(id);
    
        if (shoeToDelete != null) {
            shoeToDelete.HapusDariDB();
    
            System.out.println("+-----------------------------------------------------+");
            System.out.println("| Sepatu dengan ID " + id + " Berhasil dihapus.       |");
            System.out.println("+-----------------------------------------------------+");
        } else {
            System.out.println("+-----------------------------------------------------+");
            System.out.println("| Sepatu dengan ID " + id + " Tidak ditemukan.                  |");
            System.out.println("+-----------------------------------------------------+");
        }
    }
    

    public static void clearConsole() {
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

    public void waitForEnter(Scanner scanner) {
        System.out.println("Tekan Enter untuk melanjutkan...");
        scanner.nextLine();
    }
}
