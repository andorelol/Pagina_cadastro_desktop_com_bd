package com.example.pf;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class JavaBridge {

    // Função para salvar os dados do usuário no banco de dados
    public void saveData(String username, String email, String password) {
        // Verificar se o usuário ou e-mail já estão cadastrados
        if (Database.userExists(username, email)) {
            // Exibir alerta de erro
            showErrorAlert("Erro", "Usuário ou e-mail já cadastrado", "Tente novamente com outro nome ou e-mail.");
            return; // Impede o salvamento se o usuário ou e-mail já existirem
        }

        // Salva os dados no banco de dados SQLite
        Database.insertUser(username, email, password);

        // Exibir alerta de sucesso
        showSuccessAlert("Usuário cadastrado com sucesso!");
    }

    // Função para exibir alertas de sucesso no JavaFX
    public void showSuccessAlert(String message) {
        Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    // Método para exibir alertas personalizados de erro
    private static void showErrorAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    // Função de login com verificação no banco de dados
    public void login(String username, String password) {
        // Verifica as credenciais no banco de dados SQLite
        boolean isValid = Database.validateUser(username, password);

        if (isValid) {
            showSuccessAlert("Login bem-sucedido!");
        } else {
            showErrorAlert("Credenciais inválidas", "Tente novamente.", "O nome de usuário ou senha estão incorretos.");
        }
    }
}
