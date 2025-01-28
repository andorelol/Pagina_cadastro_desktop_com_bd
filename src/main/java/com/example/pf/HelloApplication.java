package com.example.pf;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Corrigido: Mover o código de FXMLLoader para dentro do start()
        FXMLLoader loader = new FXMLLoader(new File("src/main/resources/view.fxml").toURI().toURL());

        // Carregar a cena e definir o palco
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Sistema de Cadastro");
        stage.show();
    }

    // Função chamada pelo JavaScript para registrar usuário
    public void registerUser(String username, String email, String password) {
        DatabaseHelper.registerUser(username, email, password);
    }

    // Função chamada pelo JavaScript para verificar login
    public void checkLogin(String username, String password) {
        boolean loginSuccess = DatabaseHelper.checkUserLogin(username, password);
        if (loginSuccess) {
            System.out.println("Login realizado com sucesso!");
        } else {
            System.out.println("Credenciais inválidas.");
        }
    }
}
