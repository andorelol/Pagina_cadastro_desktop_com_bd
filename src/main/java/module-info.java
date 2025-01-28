module com.example.pf {
    requires jbcrypt; // Adiciona a dependência do módulo jbcrypt



    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires jdk.jsobject;

    opens com.example.pf to javafx.fxml;
    exports com.example.pf;
}