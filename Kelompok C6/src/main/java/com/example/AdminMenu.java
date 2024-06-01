    package com.example;

    import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

    public class AdminMenu {

        private Scanner scanner;

        public AdminMenu(Scanner scanner) {
            this.scanner = scanner;
        }

        private boolean isValidString(String input) {
            return input.chars().allMatch(Character::isLetter);
        }

        public void displayMenu() {
            while (true) {
                System.out.println("+----------------------+");
                System.out.println("|      Admin Menu      |");
                System.out.println("+----------------------+");
                System.out.println("| 1. Tambah Staff      |");
                System.out.println("| 2. Lihat Staff       |");
                System.out.println("| 3. Ubah Staff        |");
                System.out.println("| 4. Hapus Staff       |");
                System.out.println("| 5. Keluar Menu       |");
                System.out.println("+----------------------+");
                System.out.print("Masukkan Pilihan [1-5]: ");

                if (scanner.hasNextInt()) {
                    int pilihan = scanner.nextInt();
                    scanner.nextLine();

                    switch (pilihan) {
                        case 1:
                            TambahStaff();
                            break;
                        case 2:
                            HapusTerminal();
                            LiatAkunStaff();
                            EnterUntukLanjut(scanner);
                            break;
                        case 3:
                            UbahStaff();
                            break;
                        case 4:
                            HapusStaff();
                            break;
                        case 5:
                            return;
                        default:
                            
                            System.out.println("+-----------------------------------------------------+");
                            System.out.println("|Invalid pilihan. Mohon Masukkan Angka Dari 1 sampai 5|");                          
                            System.out.println("+-----------------------------------------------------+");
                            EnterUntukLanjut(scanner);
                    }
                } else {
                    System.out.println("Invalid input. Mohon Masukkan Angka yang sesuai.");
                    scanner.nextLine();
                }
            }
        }

        private void TambahStaff() {
            HapusTerminal();
            System.out.println("+-----------------------------------------------------+");
            System.out.println("|                     TAMBAH STAFF                       |");
            System.out.println("+-----------------------------------------------------+");
        
            System.out.print("Masukkan nama Staff: ");
            String name = scanner.nextLine();
            if (name.equals("-1") || !isValidString(name)) {
                System.out.println("Invalid inputan untuk nama staff. Mohon untuk masukkan yang bener.");
                return;
            }
        
            System.out.print("Masukkan username Staff: ");
            String username = scanner.nextLine();
            if (username.equals("-1")) {
                System.out.println("Invalid inputan untuk username staff. Mohon untuk masukkan yang bener.");
                return;
            }
        
            System.out.print("Masukkan password Staff: ");
            String password = scanner.nextLine();
            if (password.equals("-1")) {
                System.out.println("Invalid inputan untuk password staff. Mohon untuk masukkan yang bener.");
                return;
            }

            Admin staff = new Admin(1, name, username, password);
            try {
                staff.SimpanKeDBSTF();
                System.out.println("Sukses menambahkan Staff.");
                EnterUntukLanjut(scanner);
            } catch (InputMismatchException e) {
                System.out.println("Error: Tidak bisa menambahkan Staff. Mohon cek kembali inputan anda.");
            }
        }
        
        
        private void LiatAkunStaff() {
            String sql = "SELECT * FROM staff";
            try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                HapusTerminal();
                System.out.println("+-----------------------------------------------------+");
                System.out.println("|                     DAFTAR STAFF                    |");
                System.out.println("+-----------------------------------------------------+");
                while (rs.next()) {
                    int id = rs.getInt("staff_id");
                    String name = rs.getString("name");
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    System.out.println("ID: " + id + ", Name: " + name + ", Username: " + username + ", Password: "+ password);
                }
            } catch (SQLException e) {
                System.out.println("Error: Tidak bisa menampilkan Daftar Staff.");
                e.printStackTrace();
            }
        }
        

        private void UbahStaff() {
            HapusTerminal();
            LiatAkunStaff();
            System.out.println("+-----------------------------------------------------+");
            System.out.println("|                   Ubah Staff                        |");
            System.out.println("+-----------------------------------------------------+");
            try {
                System.out.print("Masukkan ID Staff yang ingin diubah: ");
                int id = scanner.nextInt();
                scanner.nextLine();
        
                if (id <= 0) {
                    System.out.println("Invalid inputan untuk ID Staff. Mohon masukkan angka positif.");
                    return;
                }
        
                if (!MencariStaff(id)) {
                    System.out.println("Error: Staff dengan ID " + id + " tidak ditemukan.");
                    return;
                }
        
                if (id <= 0) {
                    System.out.println("Invalid inputan untuk ID Staff. ID tidak bisa kurang dari 0 (nol).");
                    return;
                }
        
                System.out.print("Masukkan nama Staff baru: ");
                String name = scanner.nextLine();
                if (name.equals("-1") || !isValidString(name)) {
                    System.out.println("Invalid inputan untuk nama Staff. Mohon masukkan nama yang sesuai.");
                    return;
                }
        
                System.out.print("Masukkan nama Username baru: ");
                String username = scanner.nextLine();
                if (username.equals("-1")) {
                    System.out.println("Invalid inputan untuk username staf. Mohon masukkan username yang sesuai.");
                    return;
                }
        
                System.out.print("Masukkan Password baru: ");
                String password = scanner.nextLine();
                if (password.equals("-1")) {
                    System.out.println("Invalid inputan untuk Password Staff. Mohon masukkan password yang sesuai.");
                    return;
                }
        
                Admin staff = new Admin(id, name, username, password);
                staff.UbahDiDBSTF();
        
                System.out.println("ID Staff dengan " + id + " sukses diubah.");
                EnterUntukLanjut(scanner);
            } catch (InputMismatchException e) {
                System.out.println("Invalid inputan untuk ID Staff. Mohon masukkan angka yang sesuai.");
                scanner.nextLine(); 
            }
        }
        
        
        private void HapusStaff() {
            HapusTerminal();
            LiatAkunStaff();
            System.out.println("+-----------------------------------------------------+");
            System.out.println("|                   Hapus Staff                       |");
            System.out.println("+-----------------------------------------------------+");
            try {
                System.out.print("Masukkan ID Staff: ");
                int id = scanner.nextInt();
                scanner.nextLine();
        
                if (id <= 0) {
                    System.out.println("Invalid inputan untuk ID Staff. Mohon masukkan angka positif.");
                    return;
                }
        
                if (!MencariStaff(id)) {
                    System.out.println("Error: ID Staff dengan " + id + " tidak sesuai.");
                    return;
                }
        
                if (id <= 0) {
                    System.out.println("Invalid input untuk ID Staff. ID tidak bisa kurang dari 0 (nol).");
                    return;
                }
        
                Admin staff = new Admin(id, "", "", "");
                staff.HapusDariDBSTF();
        
                System.out.println("ID Staff dengan " + id + " sukses dihapus.");
                EnterUntukLanjut(scanner);
            } catch (InputMismatchException e) {
                System.out.println("Invalid inputan untuk ID Staff. Mohon masukkan angka yang sesuai.");
                scanner.nextLine(); 
            }
        }
        
        
        private boolean MencariStaff(int id) {
            String sql = "SELECT COUNT(*) AS count FROM staff WHERE staff_id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count > 0;
                }
            } catch (SQLException e) {
                System.out.println("ID Staff Tidak Ditemukan.");
                e.printStackTrace();
            }
            return false;
        }
        
        public static void HapusTerminal() {
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

        public static void EnterUntukLanjut(Scanner scanner) {
            System.out.println("\nTekan Enter untuk melanjutkan...");
            scanner.nextLine();
            HapusTerminal();
        }
    }
