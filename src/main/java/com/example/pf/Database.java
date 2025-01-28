package com.example.pf;

import java.sql.*;

public class Database {

    private static final String URL = "jdbc:sqlite:db1"; // Caminho do banco de dados

    private static boolean isTableCreated = false; // Controle para evitar recriação da tabela

    // Conectar ao banco de dados e criar a tabela se não existir
    public static Connection connect() {
        try {
            Connection connection = DriverManager.getConnection(URL);

            // Garantir que a tabela será criada apenas uma vez
            if (!isTableCreated) {
                createTable(connection);
                isTableCreated = true;
            }

            return connection;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar com o banco de dados: " + e.getMessage());
            return null;
        }
    }

    // Criar a tabela de usuários apenas se não existir
    private static void createTable(Connection connection) {
        String sqlCheck = "SELECT name FROM sqlite_master WHERE type='table' AND name='users'";
        String sqlCreate = "CREATE TABLE IF NOT EXISTS users (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " username TEXT NOT NULL, " +
                " email TEXT NOT NULL, " +
                " password TEXT NOT NULL);";

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sqlCheck);

            // Verificar se a tabela não existe antes de criá-la
            if (!rs.next()) {
                stmt.execute(sqlCreate);
                System.out.println("Tabela 'users' criada.");
            } else {
                System.out.println("Tabela 'users' já existente.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao criar/verificar tabela: " + e.getMessage());
        }
    }

    // Verificar se o usuário ou o e-mail já existe no banco de dados
    public static boolean userExists(String username, String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? OR email = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);

            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao verificar existência de usuário: " + e.getMessage());
        }
        return false;
    }

    // Inserir um novo usuário no banco de dados
    public static void insertUser(String username, String email, String password) {
        if (userExists(username, email)) {
            System.out.println("Erro: Usuário ou e-mail já cadastrado. Tente novamente com outro nome ou e-mail.");
            return;
        }

        String sql = "INSERT INTO users(username, email, password) VALUES(?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
            System.out.println("Usuário inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir usuário: " + e.getMessage());
        }
    }

    // Verificar se o usuário existe no banco de dados
    public static boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Erro ao validar usuário: " + e.getMessage());
        }
        return false;
    }

    // Listar todos os usuários no banco de dados
    public static void listUsers() {
        String sql = "SELECT * FROM users";

        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String email = rs.getString("email");
                System.out.println("ID: " + id + ", Username: " + username + ", Email: " + email);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }
    }
}
