package com.oop.projectWithGUI;

import Classes.*;

import static Classes.Transaction.TransactionHistory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static com.oop.projectWithGUI.CSVController.*;
import static com.oop.projectWithGUI.LoginController.currentClient;

public class TransactionsController extends SwitchScenes {

    @FXML
    private Button btnTransaction;

    @FXML
    private Button btnCalc;

    @FXML
    private Button btnReset;

    @FXML
    private ComboBox<String> cbConvertFrom;

    @FXML
    private ComboBox<String> cbConvertTo;

    @FXML
    private Label lblAccOption;

    @FXML
    private Label lblTransactionBalance;

    @FXML
    private Label lblGreeting;

    @FXML
    private Label lblTransferBalance;

    @FXML
    private Label lblConvertFrom;

    @FXML
    private Label lblConvertTo;

    @FXML
    private Label lblResult;

    @FXML
    private RadioButton rbDeposit;

    @FXML
    private RadioButton rbWithdraw;

    @FXML
    private RadioButton rbCurrentAcc;

    @FXML
    private RadioButton rbSavingAcc;

    @FXML
    private RadioButton rbTransferCurrentAcc;

    @FXML
    private RadioButton rbTransferSavingsAcc;

    @FXML
    private Spinner<Double> spinnerTransaction;

    @FXML
    private Spinner<Double> spinnerTransfer;

    @FXML
    private Spinner<Double> spinnerExchangeAmount;

    @FXML
    private Label titleTransaction;

    @FXML
    private Button transaction;

    @FXML
    private TableView<Transaction> tvTransactionHistory;

    @FXML
    private TableColumn<Transaction, Integer> colAccountFrom;

    @FXML
    private TableColumn<Transaction, Integer> colAccountTo;

    @FXML
    private TableColumn<Transaction, Double> colAmount;

    @FXML
    private TableColumn<Transaction, LocalDateTime> colDate;

    @FXML
    private TableColumn<Transaction, Integer> colTransactionID;

    @FXML
    private TableColumn<Transaction, String> colType;

    @FXML
    private TextField tfAccountTo;

    @FXML
    private Label lblAccToBalance;

    private Currency convertFrom = null;
    private Currency convertTo = null;

    @FXML
    public void initialize() {
        // Set up the Spinner with a range of values
        SpinnerValueFactory<Double> valueFactoryTransfer = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 999999999);
        SpinnerValueFactory<Double> valueFactoryTransactions = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 999999999);
        SpinnerValueFactory<Double> valueFactoryExchange = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 999999999);
        spinnerTransfer.setValueFactory(valueFactoryTransfer);
        spinnerTransaction.setValueFactory(valueFactoryTransactions);
        spinnerExchangeAmount.setValueFactory(valueFactoryExchange);

        lblGreeting.setText(String.format("Hi, %s", currentClient.getFirstName()));

        updateScene();
        populateTableView();
        populateComboBoxes();
    }

    public void TransactionOption(){
        if(rbDeposit.isSelected()){
            lblAccOption.setText("Deposit For");
            titleTransaction.setText("Deposit");
            btnTransaction.setText("Deposit");
        }
        else{
            lblAccOption.setText("Withdraw For");
            titleTransaction.setText("Withdraw");
            btnTransaction.setText("Withdraw");
        }
    }

    public void AccountOption(){
        Alert alert;
        if(rbSavingAcc.isSelected()){
            if(currentClient.savingsAcc != null){
                lblTransactionBalance.setText(String.format("%.2f $", currentClient.savingsAcc.getBalance()));
            }
            else{
                showAlert(Alert.AlertType.ERROR, "You don't have savings account", "Error");
                lblTransactionBalance.setText("");
                rbSavingAcc.setSelected(false);
            }
        }
        else if (rbCurrentAcc.isSelected()){
            if(currentClient.currentAcc != null){
                lblTransactionBalance.setText(String.format("%.2f $", currentClient.currentAcc.getBalance()));
            }
            else{
                showAlert(Alert.AlertType.ERROR, "You don't have current account", "Error");
                lblTransactionBalance.setText("");
                rbCurrentAcc.setSelected(false);
            }
        }
        else if (rbTransferSavingsAcc.isSelected()){
            if(currentClient.savingsAcc != null){
                lblTransferBalance.setText(String.format("%.2f $", currentClient.savingsAcc.getBalance()));
            }
            else{
                showAlert(Alert.AlertType.ERROR, "You don't have savings account", "Error");
                lblTransferBalance.setText("");
                rbTransferSavingsAcc.setSelected(false);
            }
        }
        else if (rbTransferCurrentAcc.isSelected()) {
            if(currentClient.currentAcc != null){
                lblTransferBalance.setText(String.format("%.2f $", currentClient.currentAcc.getBalance()));
            }
            else{
                showAlert(Alert.AlertType.ERROR, "You don't have current account", "Error");
                lblTransferBalance.setText("");
                rbTransferCurrentAcc.setSelected(false);
            }
        }
    }

    private void updateScene(){
        rbDeposit.setSelected(true);
        resetScene();
        populateTableView();
    }

    private void resetScene(){
        spinnerTransfer.getValueFactory().setValue(0.0);
        spinnerTransaction.getValueFactory().setValue(0.0);
        spinnerExchangeAmount.getValueFactory().setValue(0.0);

        lblTransactionBalance.setText("");
        lblTransferBalance.setText("");
        lblAccToBalance.setText("");

        lblConvertFrom.setOpacity(0);
        lblConvertFrom.setText("");
        lblConvertTo.setOpacity(0);
        lblConvertTo.setText("");
        lblResult.setOpacity(0);
        lblResult.setText("");

        cbConvertFrom.setValue("");
        convertFrom = null;
        cbConvertTo.setValue("");
        convertTo = null;

        tfAccountTo.setText("");

        btnReset.setOpacity(0.0);
        btnReset.setDisable(true);
    }

    public void populateTableView(){
        ArrayList<Transaction> transactions = Transaction.findTransaction(TransactionHistory, currentClient.getID());

        // Convert ArrayList to ObservableList
        ObservableList<Transaction> data = FXCollections.observableArrayList(transactions);

        // Link TableColumns to model properties
        colAccountFrom.setCellValueFactory(new PropertyValueFactory<>("AccountFrom"));
        colAccountTo.setCellValueFactory(new PropertyValueFactory<>("AccountTo"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        colTransactionID.setCellValueFactory(new PropertyValueFactory<>("TransactionID"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colType.setCellValueFactory(new PropertyValueFactory<>("TransactionType"));

        // Populate TableView
        tvTransactionHistory.setItems(data);
    }

    public void populateComboBoxes(){
        ObservableList<String> countriesName = FXCollections.observableArrayList();

        for (Currency c : currencies){
            countriesName.add(c.getCountryName());
        }

        // Set up cbConvertFrom
        cbConvertFrom.getItems().clear();
        cbConvertFrom.setItems(countriesName);
        // Set up cbConvertTo
        cbConvertTo.getItems().clear();
        cbConvertTo.setItems(countriesName);
    }

    public void transactionBtnClicked() {
        if (spinnerTransaction.getValue() < 10){
            showAlert(Alert.AlertType.ERROR, "Can't Make a transaction with Amount less than 10", "Error");
            return;
        }

        // Confirmation Alert of the Transaction
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to make this transaction?",
                ButtonType.YES, ButtonType.NO);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setTitle("Confirmation");

        // Show confirmation dialog and process response
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            boolean isCompleted = processTransaction();

            // Notify the user about the transaction status
            if (isCompleted) {
                showAlert(Alert.AlertType.INFORMATION, "Transaction Completed :)", "Success");
                updateScene();
            } else {
                showAlert(Alert.AlertType.ERROR, "Transaction Failed!", "Error");
            }
        }
    }

    // Processes the transaction based on the selected operation.
    private boolean processTransaction() {
        double amount = spinnerTransaction.getValue();

        if (rbDeposit.isSelected()) {
            if (rbCurrentAcc.isSelected())
                return currentClient.currentAcc.Deposit(amount);
            else
                return currentClient.savingsAcc.Deposit(amount);
        } else if (rbWithdraw.isSelected()) {
            if (rbCurrentAcc.isSelected())
                return currentClient.currentAcc.Withdraw(amount);
            else
                return currentClient.savingsAcc.Withdraw(amount);
        }
        return false;
    }

    private void showAlert(Alert.AlertType alertType, String message, String title) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.showAndWait();
    }

    public void transferBtnClicked() {
        double transferAmount = spinnerTransfer.getValue();

        // Validate the transfer amount
        if (transferAmount < 10) {
            showAlert(Alert.AlertType.ERROR, "Can't make a transfer with an amount less than 10", "Error");
            return;
        }

        // Determine the target account type
        String targetAccountNumber = tfAccountTo.getText();
        Account targetAccount = null;

        if (targetAccountNumber.contains("C")) {
            targetAccount = CurrentAccount.findAccount(currentAccounts, targetAccountNumber);
        } else if (targetAccountNumber.contains("S")) {
            targetAccount = SavingsAccount.findAccount(savingsAccounts, targetAccountNumber);
        } else {
            showAlert(Alert.AlertType.ERROR, "Invalid account number. Must contain 'C' or 'S'.", "Error");
            return;
        }

        // Validate the target account existence
        if (targetAccount == null) {
            showAlert(Alert.AlertType.ERROR, String.format("Can't find client with account number [%s]", targetAccountNumber), "Error");
            return;
        }
        else{
            lblAccToBalance.setText(targetAccount.getBalance() + " $");
        }
        // Show confirmation alert
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to make this transfer?",
                ButtonType.YES, ButtonType.NO);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setTitle("Confirmation");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // Process the transfer if confirmed
        if (result.isPresent() && result.get() == ButtonType.YES) {
            boolean isCompleted = processTransfer(targetAccount, transferAmount);

            // Notify the user about the transaction status
            if (isCompleted) {
                showAlert(Alert.AlertType.INFORMATION, "Transfer Completed :)", "Success");
                updateScene();
            } else {
                showAlert(Alert.AlertType.ERROR, "Transfer Failed!", "Error");
            }
        }
    }

    private boolean processTransfer(Account targetAccount, double amount) {
        if (rbTransferCurrentAcc.isSelected()) {
            return currentClient.currentAcc.Transfer(targetAccount, amount, currentClient.getID());
        } else if (rbTransferSavingsAcc.isSelected()) {
            return currentClient.savingsAcc.Transfer(targetAccount, amount, currentClient.getID());
        }
        return false;
    }

    public void cbConvertFromItemChanged() {
        convertFrom = Currency.Find(currencies, cbConvertFrom.getValue());

        if (convertFrom == null) {
            lblConvertFrom.setOpacity(0);
            lblConvertFrom.setText("");
            return;
        }

        lblConvertFrom.setText(String.format("""
        Country: %s
        Currency Code: %s
        Currency Name: %s
        Rate($): %.2f""",
                convertFrom.getCountryName(), convertFrom.getCurrencyCode(),
                convertFrom.getCurrencyName(), convertFrom.getExchangeRate()));
        lblConvertFrom.setOpacity(1);
    }

    public void cbConvertToItemChanged(){
        convertTo = Currency.Find(currencies, cbConvertTo.getValue());

        if (convertTo == null) {
            lblConvertTo.setOpacity(0);
            lblConvertTo.setText("");
            return;
        }

        lblConvertTo.setText(String.format("""
             Country: %s
             Currency Code: %s
             Currency Name: %s
             Rate($): %.2f""",
             convertTo.getCountryName(), convertTo.getCurrencyCode(),
             convertTo.getCurrencyName(), convertTo.getExchangeRate()));

        lblConvertTo.setOpacity(1);

    }

    public void btnCalcClicked(){
        if(cbConvertFrom.getValue().isEmpty() || cbConvertTo.getValue().isEmpty() || spinnerExchangeAmount.getValue() == 0){
            showAlert(Alert.AlertType.ERROR, "Please fill all the fields", "Error");
            return;
        }

        double convertedAmount = convertFrom.ConvertToCurrency(spinnerExchangeAmount.getValue(), convertTo);
        lblResult.setText(spinnerExchangeAmount.getValue().toString() + ' ' + convertFrom.getCurrencyCode() + " = " +
                convertedAmount + ' ' + convertTo.getCurrencyCode());
        lblResult.setOpacity(1);

        btnReset.setOpacity(1);
        btnReset.setDisable(false);
    }

    public void btnReset(){
        updateScene();
    }
}