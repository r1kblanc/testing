package com.example.tictactoe;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class MainController {

    @FXML private AnchorPane contentRoot;

    private void setContent(String fxml) throws Exception {
        // FXML files are in the same package as this class
        Node view = FXMLLoader.load(getClass().getResource(fxml));
        AnchorPane.setTopAnchor(view, 0.0);
        AnchorPane.setRightAnchor(view, 0.0);
        AnchorPane.setBottomAnchor(view, 0.0);
        AnchorPane.setLeftAnchor(view, 0.0);
        contentRoot.getChildren().setAll(view);
    }



    // Customer
    @FXML private void showCustomerViewAdd() throws Exception { setContent("/tictactoe/customer-view.fxml"); }
    @FXML private void showCustomerViewDelete() throws Exception { setContent("/tictactoe/customer-view.fxml"); }

    // Reservation
    @FXML private void showReservationAdd() throws Exception { setContent("/tictactoe/reservation-view.fxml"); }
    @FXML private void showReservationDelete() throws Exception { setContent("/tictactoe/reservation-view.fxml"); }

    // Search
    @FXML private void showSearchByName() throws Exception { setContent("/tictactoe/search-view.fxml"); }
    @FXML private void showSearchDuration() throws Exception { setContent("/tictactoe/search-view.fxml"); }
    @FXML private void showSearchTotalCost() throws Exception { setContent("/tictactoe/search-view.fxml"); }

    // Exit
    @FXML private void exitApp() { System.exit(0); }
}
