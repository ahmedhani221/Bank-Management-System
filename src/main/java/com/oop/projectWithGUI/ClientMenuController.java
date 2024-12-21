package com.oop.projectWithGUI;

import static com.oop.projectWithGUI.LoginController.currentClient;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ClientMenuController extends SwitchScenes{
    @FXML
    private Label lblGreeting;

    @FXML
    private Label labelOverdraftLimit;

    @FXML
    private Label labelNumberOfTransactions;

    @FXML
    private Label labelAccountInterestRate;

    @FXML
    private Label labelMaxTransactions;

    @FXML
    private Label labelWithdrawLimit;

    @FXML
    private Label labelAccountInterestType;

    @FXML
    private Label lbAccountNumber;

    @FXML
    private Label lbClientID;

    @FXML
    private Label lbAccountType;

    @FXML
    private Label lbAccountStatus;

    @FXML
    private Label lbAccountBalance;

    @FXML
    private Label lbNumberOfTransactions;

    @FXML
    private Label lbMaxTransactions;

    @FXML
    private Label lbAccountInterestType;

    @FXML
    private Label lbAccountInterestRate;

    @FXML
    private Label lbWithdrawLimit;

    @FXML
    private Label lbOverdraftLimit;

    @FXML
    private RadioButton rdSavingAcc;

    @FXML
    private RadioButton rdCurrentAcc;

    @FXML
    public void initialize() {
        lblGreeting.setText(String.format("Hi, %s", currentClient.getFirstName()));
        updateScene();
    }

    private void updateScene() {
        // Clear all labels and hide additional details
        clearLabels();
        hideAllDetails();
    }

    private void clearLabels() {
        lbAccountNumber.setText("");
        lbClientID.setText("");
        lbAccountType.setText("");
        lbAccountStatus.setText("");
        lbAccountBalance.setText("");
    }

    private void hideAllDetails() {
        setVisibility(false, labelOverdraftLimit, labelWithdrawLimit, labelNumberOfTransactions,
                labelMaxTransactions, labelAccountInterestRate, labelAccountInterestType,
                lbNumberOfTransactions, lbMaxTransactions, lbAccountInterestRate, lbAccountInterestType,
                lbWithdrawLimit, lbOverdraftLimit);
    }

    private void setVisibility(boolean visible, Label... labels) {
        for (Label label : labels) {
            label.setVisible(visible);
        }
    }

    public void setDetails() {
        if (rdSavingAcc.isSelected()) {
            setSavingsAccountDetails();
        } else if (rdCurrentAcc.isSelected()) {
            setCurrentAccountDetails();
        }
    }

    private void setSavingsAccountDetails() {
        if (currentClient.savingsAcc != null) {
            updateScene();
            showSavingsAccountDetails();
            populateSavingsAccountDetails();
        } else {
            showAlert(Alert.AlertType.ERROR, "You Don't have a Savings Account", "Account Option Error");
            rdSavingAcc.setSelected(false);
        }
    }

    private void setCurrentAccountDetails() {
        if (currentClient.currentAcc != null) {
            updateScene();
            showCurrentAccountDetails();
            populateCurrentAccountDetails();
        } else {
            showAlert(Alert.AlertType.ERROR, "You Don't have a Current Account", "Account Option Error");
            rdCurrentAcc.setSelected(false);
        }
    }

    private void showSavingsAccountDetails() {
        setVisibility(true, labelAccountInterestRate, labelAccountInterestType, labelMaxTransactions,
                labelNumberOfTransactions, lbAccountInterestRate, lbAccountInterestType,
                lbMaxTransactions, lbNumberOfTransactions);

        labelAccountInterestRate.setLayoutY(340);
        labelAccountInterestType.setLayoutY(380);
        labelMaxTransactions.setLayoutY(420);
        labelNumberOfTransactions.setLayoutY(460);

        lbAccountInterestRate.setLayoutY(340);
        lbAccountInterestType.setLayoutY(380);
        lbMaxTransactions.setLayoutY(420);
        lbNumberOfTransactions.setLayoutY(460);
    }

    private void populateSavingsAccountDetails() {
        lbAccountNumber.setText(String.valueOf(currentClient.savingsAcc.getAccountNumber()));
        lbClientID.setText(String.valueOf(currentClient.savingsAcc.getClientID()));
        lbAccountType.setText(currentClient.savingsAcc.getAccountType());
        lbAccountStatus.setText(currentClient.savingsAcc.getAccountStatus());
        lbAccountBalance.setText(String.valueOf(currentClient.savingsAcc.getBalance()));
        lbAccountInterestRate.setText(String.valueOf(currentClient.savingsAcc.getAccountInterestRate()));
        lbAccountInterestType.setText(currentClient.savingsAcc.getAccountInterestType());
        lbMaxTransactions.setText(String.valueOf(currentClient.savingsAcc.getMaxTransactions()));
        lbNumberOfTransactions.setText(String.valueOf(currentClient.savingsAcc.getNumberOfTransactions()));
    }

    private void showCurrentAccountDetails() {
        setVisibility(true, labelOverdraftLimit, labelWithdrawLimit, lbOverdraftLimit, lbWithdrawLimit);
    }

    private void populateCurrentAccountDetails() {
        lbAccountNumber.setText(String.valueOf(currentClient.currentAcc.getAccountNumber()));
        lbClientID.setText(String.valueOf(currentClient.currentAcc.getClientID()));
        lbAccountType.setText(currentClient.currentAcc.getAccountType());
        lbAccountStatus.setText(currentClient.currentAcc.getAccountStatus());
        lbAccountBalance.setText(String.valueOf(currentClient.currentAcc.getBalance()));
        lbOverdraftLimit.setText(String.valueOf(currentClient.currentAcc.getOverdraftLimit()));
        lbWithdrawLimit.setText(String.valueOf(currentClient.currentAcc.getWithdrawalLimit()));
    }

    private void showAlert(Alert.AlertType alertType, String message, String title) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.showAndWait();
        updateScene();
    }
}
