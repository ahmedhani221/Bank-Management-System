package com.oop.projectWithGUI;

import Classes.*;

import static com.oop.projectWithGUI.CSVController.*;
import static com.oop.projectWithGUI.LoginController.currentClient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class LoanController extends SwitchScenes {
    @FXML
    private Label lbGreeting;

    @FXML
    private Button SubmitButton;

    @FXML
    private Button PayInstallmentBtn;

    @FXML
    private Button ExtendLoanTermBtn;

    @FXML
    private TextField tfAmount;

    @FXML
    private TextField tfInterestRate;

    @FXML
    private TextField tfExtendedMonths;

    @FXML
    private RadioButton rbMale;

    @FXML
    private RadioButton rbFemale;

    @FXML
    private ChoiceBox<String> ChBox_LoanType;
    // Array for Loan Types.
    private final String[] LoanType = {"Personal", "Car", "House", "Business", "Project"};

    @FXML
    private ChoiceBox<String> ChBox_LoanTerm;
    // Array for Loan Term.
    private final String[] LoanTerm = {"12 Months", "24 Months", "36 Months", "48 Months", "60 Months"};

    @FXML
    private CheckBox TermsAndConditionsCheckBox;

    @FXML
    private Label lbFullName;

    @FXML
    private Label lbPhoneNum;

    @FXML
    private Label lbLoanID;

    @FXML
    private Label lbName;

    @FXML
    private Label lbLoanAmount;

    @FXML
    private Label lbLoanTerm;

    @FXML
    private Label lbLoanType;

    @FXML
    private Label lbInterestRate;

    @FXML
    private Label lbLoanStatus;

    @FXML
    private Label lbExpiryDate;

    @FXML
    private Label lbRemainingAmount;

    // Loan Action tab details.
    @FXML
    private Label lbLoanID1;

    @FXML
    private Label lbLoanAmount1;

    @FXML
    private Label lbLoanTerm1;

    @FXML
    private Label lbLoanType1;

    @FXML
    private Label lbInterestRate1;

    @FXML
    private Label lbLoanStatus1;

    @FXML
    private Label lbRemainingAmount1;

    @FXML
    private TabPane tpLoanPane;

    @FXML
    private Tab requestTab;

    @FXML
    private Tab loanDetailsTab;

    @FXML
    private Tab loanActionsTab;

    @FXML
    private Tab paymentHistoryTab;

    @FXML
    private TableView<LoanPayment> tvLoanPaymentDetails;

    @FXML
    private TableColumn<LoanPayment, Double> colAmount;

    @FXML
    private TableColumn<LoanPayment, String> colStatus;

    @FXML
    private TableColumn<LoanPayment, LocalDateTime> colDate;

    @FXML
    private TableColumn<LoanPayment, Integer> colMonths;

    // Create Loan object.
    Loan loan;

    public void initialize() {
        lbGreeting.setText(String.format("Hi, %s", currentClient.getFirstName()));

        ChBox_LoanTerm.getItems().addAll(LoanTerm);
        ChBox_LoanType.getItems().addAll(LoanType);

        lbFullName.setText(currentClient.getFullName());
        lbPhoneNum.setText(String.valueOf(currentClient.getPhoneNum()));
        loan = Loan.findLoans(loans, currentClient.getID());

        if(loan != null) {
            requestTab.setDisable(true);
            tpLoanPane.getSelectionModel().select(loanDetailsTab);
            LoanTableView();
        } else {
            loanDetailsTab.setDisable(true);
            loanActionsTab.setDisable(true);
            paymentHistoryTab.setDisable(true);
            tpLoanPane.getSelectionModel().select(requestTab);
        }
    }

    public void submitBtnClicked() {
        if(tfAmount.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please Enter Amount Value !!", "Error");
            return;
        }

        double amount = Double.parseDouble(tfAmount.getText());
        if (amount < 10000) {
            showAlert(Alert.AlertType.ERROR, "Can't Enter Amount less than 10000", "Error");
            return;
        }

        if(tfInterestRate.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please Enter Interest Rate !!", "Error");
            return;
        }

        double interestRate = Double.parseDouble(tfInterestRate.getText());
        if(interestRate < 0) {
            showAlert(Alert.AlertType.ERROR, "Interest Rate Must be greater than 0", "Error");
            return;
        }

        if(!rbMale.isSelected() && !rbFemale.isSelected()) {
            showAlert(Alert.AlertType.ERROR, "Please Select Your Gender !!", "Error");
            return;
        }

        if(ChBox_LoanType.getSelectionModel().getSelectedItem() == null) {
            showAlert(Alert.AlertType.ERROR, "Please Select Loan Type !!", "Error");
            return;
        }

        if(ChBox_LoanTerm.getSelectionModel().getSelectedItem() == null) {
            showAlert(Alert.AlertType.ERROR, "Please Select Loan Term !!", "Error");
            return;
        }

        if(!TermsAndConditionsCheckBox.isSelected()) {
            showAlert(Alert.AlertType.ERROR, "You Must Terms and Conditions before submit this form", "Error");
            return;
        }

        // Declare Loan fields.
        String borrowerName = lbFullName.getText();
        String loanType = ChBox_LoanType.getSelectionModel().getSelectedItem();
        double loanAmount = Double.parseDouble(tfAmount.getText());
        int loanTerm = switch (ChBox_LoanTerm.getSelectionModel().getSelectedItem()) {
            case "12 Months" -> 12;
            case "24 Months" -> 24;
            case "36 Months" -> 36;
            case "48 Months" -> 48;
            case "60 Months" -> 60;
            default -> 0;
        };
        int interest_Rate = Integer.parseInt(tfInterestRate.getText());

        // Create Loan Object and pass this fields to Constructor.
        loan = new Loan(borrowerName, loanType, loanAmount, interest_Rate, loanTerm, currentClient.getID());
        loans.add(loan);

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to request this Loan?",
                ButtonType.YES, ButtonType.NO);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setTitle("Loan Confirmation");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            // After check all conditions.
            showAlert(Alert.AlertType.INFORMATION, "Submitted Successfully", "Success");

            LoanDetails();
            LoanTableView();

            requestTab.setDisable(true);
            loanDetailsTab.setDisable(false);
            loanActionsTab.setDisable(false);
            paymentHistoryTab.setDisable(false);
            tpLoanPane.getSelectionModel().select(loanDetailsTab);
        }
    }

    private void showAlert(Alert.AlertType alertType, String message, String title) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.showAndWait();
    }

    public void LoanDetails() {
        // Loan Details view.
        lbLoanID.setText(String.valueOf(loan.getLoanID()));
        lbName.setText(currentClient.getFullName());
        lbLoanAmount.setText(String.valueOf(loan.getLoanAmount()) + " $");
        lbLoanStatus.setText(loan.getLoanStatus());
        lbLoanTerm.setText(String.valueOf(loan.getLoanTerm()) + " Months");
        lbLoanType.setText(loan.getLoanType());
        lbExpiryDate.setText(String.valueOf(loan.getExpiryDate()));
        lbRemainingAmount.setText(String.valueOf(loan.getRemainingBalance()) + " $");
        lbInterestRate.setText(String.valueOf(loan.getInterestRate() * 100) + " %");

        // Loan Actions view.
        lbLoanID1.setText(String.valueOf(loan.getLoanID()));
        lbLoanAmount1.setText(String.valueOf(loan.getLoanAmount()) + " $");
        lbLoanStatus1.setText(loan.getLoanStatus());
        lbLoanTerm1.setText(String.valueOf(loan.getLoanTerm()) + " Months");
        lbLoanType1.setText(loan.getLoanType());
        lbRemainingAmount1.setText(String.valueOf(loan.getRemainingBalance()) + " $");
        lbInterestRate1.setText(String.valueOf(loan.getInterestRate() * 100) + " %");
    }

    public void payInstallmentBtnClicked() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are You sure you want to pay Installment ?", ButtonType.YES, ButtonType.NO);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setTitle("Confirmation");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // Process the installment if confirmed.
        if (result.isPresent() && result.get() == ButtonType.YES) {
            loan.payMonthlyInstallment();
            showAlert(Alert.AlertType.CONFIRMATION, "Extended Successfully...", "Success");
        }

        LoanDetails();
        LoanTableView();
    }

    public void extendLoanTerm() {
        if (tfExtendedMonths.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please Enter Extended Months Value !!", "Error");
            return;
        }
        int extendedMonths = Integer.parseInt(tfExtendedMonths.getText());

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are You sure you want to extend your loan term ?", ButtonType.YES, ButtonType.NO);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setTitle("Confirmation");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            loan.extendLoanTerm(extendedMonths);
            loan.setRemainingMonths(extendedMonths);
            LoanDetails();
            LoanTableView();
            showAlert(Alert.AlertType.CONFIRMATION, "Extended Successfully...", "Success");
        }
    }

    public void LoanTableView() {
        if (loan != null) {
            ArrayList<LoanPayment> payments = LoanPayment.findPayments(loanHistory, currentClient.getID());

            ObservableList<LoanPayment> data = FXCollections.observableArrayList(payments);

            colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colMonths.setCellValueFactory(new PropertyValueFactory<>("months"));

            // Set data source for TableView
            tvLoanPaymentDetails.setItems(data);
        }
    }
}