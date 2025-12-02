package com.example.tictactoe;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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

    // If we ever implement a Back to Menu button:
    //     @FXML private AnchorPane contentRoot;

    // private void setContent(String fxml) throws Exception {
    //  // FXML files are in the same package as this class
    // Node view = FXMLLoader.load(getClass().getResource(fxml));
    // AnchorPane.setTopAnchor(view, 0.0);
    // AnchorPane.setRightAnchor(view, 0.0);
    // AnchorPane.setBottomAnchor(view, 0.0);
    // AnchorPane.setLeftAnchor(view, 0.0);
    // contentRoot.getChildren().setAll(view);
    // }

    // @FXML private void goBack() throws Exception { setContent("/tictactoe/main-view.fxml"); }
}
