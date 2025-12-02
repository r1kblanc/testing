package com.example.tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML private TextField txtUser;
    @FXML private PasswordField txtPass;

    @FXML
    private void handleLogin(ActionEvent e) throws Exception {
        String user = txtUser.getText();
        String pass = txtPass.getText();

        // Hardcoded credentials for now
        if (user.equals("admin") && pass.equals("1234")) {
            Stage stage = (Stage) ((javafx.scene.Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/tictactoe/main-view.fxml")));
            stage.setTitle("Hotel Management");
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid ID or Password. Try again.");
            alert.showAndWait();
        }
    }

    
}
