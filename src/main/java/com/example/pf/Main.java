package com.example.pf;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import java.sql.Connection;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Criação do WebView
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Carregar o HTML
        webEngine.load(getClass().getResource("/web/index.html").toExternalForm());

        // Criar o objeto JavaBridge que será acessado pelo JavaScript
        JavaBridge javaBridge = new JavaBridge();

        // Registrar o objeto Java no WebEngine
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("javaBridge", javaBridge);  // Registra o objeto javaBridge

                // Caminho do CSS e JS que devem ser injetados
                String css = getClass().getResource("/web/css/style.css").toExternalForm();
                String js = getClass().getResource("/web/js/script.js").toExternalForm();

                // Injeta o link do CSS no HTML carregado
                webEngine.executeScript("document.head.innerHTML += '<link rel=\"stylesheet\" type=\"text/css\" href=\"" + css + "\">';");

                // Injeta o link do JS no HTML carregado
                webEngine.executeScript("var script = document.createElement('script'); script.src = '" + js + "'; document.head.appendChild(script);");
            }
        });

        // Configuração da cena
        Scene scene = new Scene(webView, 800, 600);
        primaryStage.setTitle("Aplicação Web com JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Conectar ao banco de dados
        Connection connection = Database.connect();

        if (connection != null) {
            // Inserir um novo usuário
            Database.insertUser("johndoe", "johndoe@example.com", "password123");

            // Listar todos os usuários
            Database.listUsers();
        }

        // Iniciar a aplicação JavaFX
        launch(args);
    }
}
