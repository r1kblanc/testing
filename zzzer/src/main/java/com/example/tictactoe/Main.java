package com.example.tictactoe;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        showStart();

        stage.setTitle("Data Communications & Network Project");
        stage.show();
    }

    public void showStart() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tictactoe/login-view.fxml"));
            Parent root = loader.load();

           // SelectionScreenController controller = loader.getController();
            //controller.setMainApplication(this);

            stage.setTitle("Data Communications & Network Project");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
