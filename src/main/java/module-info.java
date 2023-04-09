module com.example.gerenciadordepvendas {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires mysql.connector.j;

    opens com.gerenciadordepvendas.model.entities;
    opens com.gerenciadordepvendas to javafx.fxml;
    exports com.gerenciadordepvendas;
}