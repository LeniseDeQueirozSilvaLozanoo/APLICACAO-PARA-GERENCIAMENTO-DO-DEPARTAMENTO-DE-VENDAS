package com.gerenciadordepvendas;

import com.gerenciadordepvendas.db.DB;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("View.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello2!");
        stage.setScene(scene);
        stage.show();
        Connection con = DB.getConnection();
        DB.closeConection();
    }

    public static void main(String[] args) {
        launch();
    }
}