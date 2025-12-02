package com.example.tictactoe;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SearchController {

    // ---- fx:id must match search-view.fxml ----
    @FXML private TextField tfSearchName;        // "By name:"
    @FXML private TextField tfSearchDurationId;  // "Stay duration by customer #"
    @FXML private TextField tfSearchCostId;      // "Total cost by customer #"

    @FXML private TableView<String> tblResult;   // table with one "Result" column
    @FXML private TableColumn<String, String> colResult;

    private final ObservableList<String> rows = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // One-column table that shows plain strings
        colResult.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        tblResult.setItems(rows);
        rows.clear();
    }

    // ----------------- SEARCH 1: by name (LIKE '%text%') -----------------
    @FXML
    private void searchByName() {
        String q = tfSearchName.getText().trim();
        rows.clear();

        if (q.isEmpty()) {
            rows.add("Enter a name to search.");
            return;
        }

        String sql = "SELECT CustomerID, CustomerName FROM Customer WHERE CustomerName LIKE ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + q + "%");
            try (ResultSet rs = ps.executeQuery()) {
                boolean any = false;
                while (rs.next()) {
                    any = true;
                    int id = rs.getInt("CustomerID");
                    String name = rs.getString("CustomerName");
                    rows.add("Found: #" + id + " – " + name);
                }
                if (!any) rows.add("No customers found matching: " + q);
            }
        } catch (Exception e) {
            rows.add("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ------------- SEARCH 2: total nights for a given customer ------------
    @FXML
    private void searchDuration() {
        rows.clear();
        String idText = tfSearchDurationId.getText().trim();
        if (idText.isEmpty()) {
            rows.add("Enter a customer #.");
            return;
        }

        int customerId;
        try { customerId = Integer.parseInt(idText); }
        catch (NumberFormatException nfe) { rows.add("Customer # must be a number."); return; }

        String sql = "SELECT CheckInDate, CheckOutDate FROM Reservation WHERE CustomerID = ?";
        long totalNights = 0;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Date in  = rs.getDate("CheckInDate");
                    Date out = rs.getDate("CheckOutDate");
                    if (in != null && out != null) {
                        LocalDate start = in.toLocalDate();
                        LocalDate end   = out.toLocalDate();
                        long nights = Math.max(0, ChronoUnit.DAYS.between(start, end));
                        totalNights += nights;
                    }
                }
            }
            rows.add("Stay duration for customer #" + customerId + ": " + totalNights + " nights");
        } catch (Exception e) {
            rows.add("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ------------- SEARCH 3: total cost for a given customer --------------
    @FXML
    private void searchTotalCost() {
        rows.clear();
        String idText = tfSearchCostId.getText().trim();
        if (idText.isEmpty()) {
            rows.add("Enter a customer #.");
            return;
        }

        int customerId;
        try { customerId = Integer.parseInt(idText); }
        catch (NumberFormatException nfe) { rows.add("Customer # must be a number."); return; }

        String sql = "SELECT TotalCost FROM Reservation WHERE CustomerID = ?";
        double total = 0.0;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    total += rs.getDouble("TotalCost");
                }
            }
            rows.add(String.format("Total cost for customer #%d: $%,.2f", customerId, total));
        } catch (Exception e) {
            rows.add("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
