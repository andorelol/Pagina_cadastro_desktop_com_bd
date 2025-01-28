package com.example.pf;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class UserApp extends Application {

    // Lista para armazenar os usuários em memória
    private List<User> userList = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Campos de entrada para cadastro e login
        TextField usernameField = new TextField();
        usernameField.setPromptText("Nome de usuário");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Senha");

        Button registerButton = new Button("Cadastrar Usuário");
        Button loginButton = new Button("Login");

        Label messageLabel = new Label();

        // Evento para cadastrar usuário
        registerButton.setOnAction(event -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                if (registerUser(username, email, password)) {
                    messageLabel.setText("Usuário cadastrado com sucesso!");
                } else {
                    messageLabel.setText("Usuário já existe!");
                }
            } else {
                messageLabel.setText("Por favor, preencha todos os campos.");
            }
        });

        // Evento para realizar login
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (authenticateUser(username, password)) {
                messageLabel.setText("Login realizado com sucesso!");
            } else {
                messageLabel.setText("Credenciais inválidas.");
            }
        });

        VBox root = new VBox(10, usernameField, emailField, passwordField, registerButton, loginButton, messageLabel);
        root.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cadastro e Login de Usuários");
        primaryStage.show();
    }

    // Método para cadastrar usuário
    private boolean registerUser(String username, String email, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return false; // Usuário já existe
            }
        }
        userList.add(new User(username, email, password));
        return true;
    }

    // Método para autenticar usuário
    private boolean authenticateUser(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Classe simples para representar um usuário
    static class User {
        private String username;
        private String email;
        private String password;

        public User(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}
