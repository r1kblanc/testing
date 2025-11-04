// Import JavaFX and SQL libraries
package com.example.tictactoe;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Controller class for managing customers in the JavaFX application.
 * Handles adding and deleting customers from the MS Access database.
 */
public class CustomerController {

    // These fields link directly to the TextFields in the FXML file.
    @FXML private TextField txtName;
    @FXML private TextField txtAddress;
    @FXML private TextField txtPhone;
    @FXML private TextField txtEmail;
    @FXML private TextField tfDeleteCustNo;

    /**
     * Adds a new customer record into the Access database.
     * Triggered when the user clicks the "Add" button in the GUI.
     */
    @FXML
    private void addCustomer() {
        // Try-with-resources automatically closes the connection after use.
        try (Connection con = DBConnection.getConnection()) {

            // SQL command to insert a new customer into the Customer table.
            String sql = "INSERT INTO Customer (CustomerName, Address, Phone, Email) VALUES (?, ?, ?, ?)";

            // PreparedStatement helps prevent SQL injection and handles parameters safely.
            PreparedStatement ps = con.prepareStatement(sql);

            // Get text from input fields and set them into the SQL command.
            ps.setString(1, txtName.getText());    // Set CustomerName
            ps.setString(2, txtAddress.getText()); // Set Address
            ps.setString(3, txtPhone.getText());   // Set Phone
            ps.setString(4, txtEmail.getText());   // Set Email

            // Execute the SQL command (adds the customer).
            ps.executeUpdate();

            // Show success message and clear the input fields for next entry.
            showAlert(Alert.AlertType.INFORMATION, "Success", "Customer added successfully!");
            txtName.clear();
            txtAddress.clear();
            txtPhone.clear();
            txtEmail.clear();

        } catch (SQLException e) {
            // Handles database-related errors (like connection or SQL syntax issues).
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
            e.printStackTrace();

        }
    }

    /**
     * Deletes an existing customer from the Access database using their CustomerID.
     * Triggered when the user clicks the "Delete" button in the GUI.
     */
    @FXML
    private void deleteCustomer() {
        // Try-with-resources automatically closes the connection after the block ends.
        try (Connection con = DBConnection.getConnection()) {

            // SQL command to delete a customer with a specific CustomerID.
            String sql = "DELETE FROM Customer WHERE CustomerID = ?";

            // PreparedStatement for safe parameter handling.
            PreparedStatement ps = con.prepareStatement(sql);

            // Convert the text field input to an integer and set it as the CustomerID parameter.
            ps.setInt(1, Integer.parseInt(tfDeleteCustNo.getText()));

            // Execute the SQL DELETE command and get how many rows were affected.
            int rows = ps.executeUpdate();

            // If at least one row was deleted, show success; otherwise, show a warning.
            if (rows > 0)
                showAlert(Alert.AlertType.INFORMATION, "Deleted", "Customer deleted successfully!");
            else
                showAlert(Alert.AlertType.WARNING, "Not Found", "No customer found with that ID.");

            // Clear the delete text field after operation.
            tfDeleteCustNo.clear();

        } catch (SQLException e) {
            // Handles any SQL or connection errors.
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());

        } catch (NumberFormatException e) {
            // Handles the case where user enters a non-numeric value for the Customer ID.
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter a valid numeric Customer ID.");
        }
    }

    /**
     * Helper method to show popup alert messages to the user.
     * Can be used for success, warning, or error messages.
     *
     * @param type  The type of alert (INFORMATION, WARNING, ERROR)
     * @param title The title of the alert window
     * @param msg   The message content to display
     */
    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);  // Create a new alert of the given type
        alert.setTitle(title);          // Set the window title
        alert.setHeaderText(null);      // Remove default header text
        alert.setContentText(msg);      // Set the message text
        alert.showAndWait();            // Display the alert and wait for the user to close it
    }
}
