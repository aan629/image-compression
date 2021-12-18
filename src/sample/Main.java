package sample;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    public static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Image Compression (Huffman & Haarwavelet)");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        //Set icon
        primaryStage.getIcons().add(new Image(new File("src/logo.png").toURI().toString()));
        primaryStage.show();

        this.stage = primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
