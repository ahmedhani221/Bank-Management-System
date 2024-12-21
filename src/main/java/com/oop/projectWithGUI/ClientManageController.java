package com.oop.projectWithGUI;

import Classes.*;
import java.time.*;
import java.util.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import static com.oop.projectWithGUI.LoginController.currentClient;

public class ClientManageController extends SwitchScenes{
    @FXML
    private Label lblGreeting;

    @FXML
    private Label lbFirstName;

    @FXML
    private Label lbLastName;

    @FXML
    private Label lbUserName;

    @FXML
    private Label lbPhone;

    @FXML
    private Label lbSavingsNum;

    @FXML
    private Label lbCurrentNum;

    @FXML
    private TextField txEditFirstName;

    @FXML
    private TextField txEditLastName;

    @FXML
    private Label lbEditUserName;

    @FXML
    private TextField txEditPhone;

    @FXML
    private Label lbEditSavingsNum;

    @FXML
    private Label lbEditCurrentNum;

    public void initialize() {
        lblGreeting.setText(String.format("Hi, %s", currentClient.getFirstName()));

        setClientDetails();
        setEditClientDetails();
    }

    private void setClientDetails() {
        lbFirstName.setText(String.valueOf(currentClient.getFirstName()));
        lbLastName.setText(String.valueOf(currentClient.getLastName()));
        lbUserName.setText(String.valueOf(currentClient.getUsername()));
        lbPhone.setText(String.valueOf(currentClient.getPhoneNum()));
        if (currentClient.savingsAcc != null) {
            lbSavingsNum.setText(String.valueOf(currentClient.savingsAcc.getAccountNumber()));
        } else {
            lbSavingsNum.setText("Not Found");
        }

        if (currentClient.currentAcc != null) {
            lbCurrentNum.setText(String.valueOf(currentClient.currentAcc.getAccountNumber()));
        } else {
            lbCurrentNum.setText("Not Found");
        }
    }

    private void setEditClientDetails() {
        txEditFirstName.setText(String.valueOf(currentClient.getFirstName()));
        txEditLastName.setText(String.valueOf(currentClient.getLastName()));
        lbEditUserName.setText(String.valueOf(currentClient.getUsername()));
        txEditPhone.setText(String.valueOf(currentClient.getPhoneNum()));

        if (currentClient.savingsAcc != null) {
            lbEditSavingsNum.setText(String.valueOf(currentClient.savingsAcc.getAccountNumber()));
        } else {
            lbEditSavingsNum.setText("Not Found");
        }

        if (currentClient.currentAcc != null) {
            lbEditCurrentNum.setText(String.valueOf(currentClient.currentAcc.getAccountNumber()));
        } else {
            lbEditCurrentNum.setText("Not Found");
        }
    }

    public void updateClientDetails() {
        if (txEditFirstName.getText().isEmpty() || txEditLastName.getText().isEmpty() || txEditPhone.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "You Must Fill All Text Fields!", "Empty Fields");
            return;
        }

        String newFirstName = txEditFirstName.getText();
        String newLastName = txEditLastName.getText();
        String newPhone = txEditPhone.getText();

        currentClient.setFirstName(newFirstName);
        currentClient.setLastName(newLastName);
        currentClient.setPhoneNum(newPhone);

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to edit your info?",
                ButtonType.YES, ButtonType.NO);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setTitle("Confirmation");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            showAlert(Alert.AlertType.INFORMATION, "You Edit Info Successfully :)", "Edit Info");
        }

        initialize();
    }

    private void showAlert(Alert.AlertType alertType, String message, String title) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
