package com.gerenciadordepvendas;

import com.gerenciadordepvendas.db.DB;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class Application extends javafx.application.Application {

    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("View.fxml"));
            ScrollPane scrollPane = fxmlLoader.load();
            scene = new Scene(scrollPane);
            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);
            stage.setScene(scene);
            stage.setTitle("Gerenciador de Department de Vendas");
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Scene getScene(){
        return scene;
    }
    public static void main(String[] args) {
        launch();
    }
}