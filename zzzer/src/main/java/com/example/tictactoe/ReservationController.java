package com.example.tictactoe;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ReservationController {

    // --- fx:id must match reservation-view.fxml ---
    @FXML private TextField tfRCustNo;        // "Customer #"
    @FXML private DatePicker dpCheckIn;       // Check-in date
    @FXML private DatePicker dpCheckOut;      // Check-out date
    @FXML private ChoiceBox<String> cbRoomType; // Room Type (ChoiceBox in your FXML)

    // Delete section
    @FXML private TextField tfResId;          // "Reservation #"

    // Populate room types when the view loads
    @FXML
    private void initialize() {
        cbRoomType.getItems().setAll("Standard", "Deluxe", "Suite");
        if (!cbRoomType.getItems().isEmpty()) cbRoomType.setValue(cbRoomType.getItems().get(0));
    }

    @FXML
    private void addReservation() {
        try {
            int customerId = Integer.parseInt(tfRCustNo.getText().trim());
            LocalDate checkIn  = dpCheckIn.getValue();
            LocalDate checkOut = dpCheckOut.getValue();
            String roomType    = cbRoomType.getValue();

            if (checkIn == null || checkOut == null || roomType == null) {
                System.out.println("⚠️ Fill all fields (dates + room type).");
                return;
            }
            if (!checkOut.isAfter(checkIn)) {
                System.out.println("⚠️ Check-out must be after check-in.");
                return;
            }

            try (Connection conn = DatabaseUtil.getConnection()) {
                if (conn == null) {
                    System.out.println("❌ Database connection failed!");
                    return;
                }

                String sql = "INSERT INTO Reservation " +
                        "(CustomerID, CheckInDate, CheckOutDate, TotalCost) " +
                        "VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, customerId);
                    stmt.setDate(2, Date.valueOf(checkIn));
                    stmt.setDate(3, Date.valueOf(checkOut));

                    long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
                    double rate = "Deluxe".equalsIgnoreCase(roomType) ? 150.0 :
                            "Suite".equalsIgnoreCase(roomType)   ? 220.0 : 100.0;
                    double total = rate * nights;
                    stmt.setDouble(4, total);

                    int rows = stmt.executeUpdate();
                    System.out.println("✅ Inserted rows: " + rows);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (NumberFormatException ex) {
            System.out.println("⚠️ Invalid Customer #.");
        }
    }

    @FXML
    private void deleteReservation() {
        String idText = tfResId == null ? "" : tfResId.getText().trim();
        if (idText.isEmpty()) {
            System.out.println("⚠️ Enter a Reservation # to delete.");
            return;
        }
        int id = Integer.parseInt(idText);

        try (Connection conn = DatabaseUtil.getConnection()) {
            if (conn == null) {
                System.out.println("❌ Database connection failed!");
                return;
            }
            String sql = "DELETE FROM Reservation WHERE ReservationID = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                int rows = ps.executeUpdate();
                System.out.println("🗑️ Deleted rows: " + rows);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
