package com.gerenciadordepvendas;

import com.gerenciadordepvendas.db.DB;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("View.fxml"));
        ScrollPane scrollPane = fxmlLoader.load();

        Scene scene = new Scene(scrollPane);

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        stage.setScene(scene);
        stage.setTitle("Hello!");
        stage.show();
        Connection con = DB.getConnection();
        DB.closeConection();
    }
    public static void main(String[] args) {
        launch();
    }
}