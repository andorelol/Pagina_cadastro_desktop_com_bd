package com.example.pf;

import java.sql.*;

public class DatabaseHelper {

    // Método para estabelecer a conexão com o banco de dados SQLite
    public static Connection connect() throws SQLException {
        // Caminho para o arquivo de banco de dados SQLite
        String url = "jdbc:sqlite:usuarios.db"; // Certifique-se de que o arquivo usuarios.db está no diretório correto

        // Estabelece a conexão com o banco de dados
        return DriverManager.getConnection(url);
    }

    // Método para verificar o login do usuário
    public static boolean checkUserLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            // Se encontrar um resultado, o login é bem-sucedido
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para registrar um novo usuário
    public static boolean registerUser(String username, String email, String password) {
        String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);

            int rowsAffected = pstmt.executeUpdate();

            // Se inseriu uma linha, significa que o usuário foi registrado
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
