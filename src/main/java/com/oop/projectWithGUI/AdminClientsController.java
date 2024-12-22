package com.oop.projectWithGUI;

import Classes.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.Optional;

import static com.oop.projectWithGUI.CSVController.*;
import static com.oop.projectWithGUI.LoginController.currentEmployee;


public class AdminClientsController extends SwitchScenes {
    private String[] interestType = {"Monthly", "Yearly"};

    public static Client selectedClient = null;

    @FXML
    private Button AddButt;

    @FXML
    private ChoiceBox<String> CbSavingsAddInterestType;

    @FXML
    private ComboBox<String> CbCurrentCID;

    @FXML
    private ComboBox<String> CbSavingCID;

    @FXML
    private ChoiceBox<String> CbUpdInterestType;

    @FXML
    private TableColumn<Client, String> ClientIDColumn;

    @FXML
    private Button ClientsButt;

    @FXML
    private TableView<Client> ClientsTable;

    @FXML
    private Button CurrentAddButt;

    @FXML
    private Button EmployeesButt;

    @FXML
    private TableColumn<Client, String> FullNameColumn;

    @FXML
    private Button HomeButt;

    @FXML
    private TableColumn<Client, String> PasswordColumn;

    @FXML
    private TableColumn<Client, Integer> PhoneNumberColumn;

    @FXML
    private RadioButton RbAddCurrent;

    @FXML
    private RadioButton RbAddSaving;

    @FXML
    private Button SavingsAddButt;

    @FXML
    private TextField TfAddFirstName;

    @FXML
    private TextField TfAddLastName;

    @FXML
    private TextField TfAddPassword;

    @FXML
    private TextField TfAddUsername;

    @FXML
    private TextField TfCurrentAddFirstName;

    @FXML
    private TextField TfCurrentAddLastName;

    @FXML
    private TextField TfCurrentAddOverdraftLimit;

    @FXML
    private TextField TfCurrentAddPassword;

    @FXML
    private TextField TfCurrentAddUsername;

    @FXML
    private TextField TfCurrentAddWithdrawLimit;

    @FXML
    private TextField TfSavingsAddFirstName;

    @FXML
    private TextField TfSavingsAddInterestRate;

    @FXML
    private TextField TfSavingsAddLastName;

    @FXML
    private TextField TfSavingsAddPassword;

    @FXML
    private TextField TfSavingsAddUsername;

    @FXML
    private TextField TfUpdFirstName;

    @FXML
    private TextField TfUpdInterestRate;

    @FXML
    private TextField TfUpdLastName;

    @FXML
    private TextField TfUpdOverdraftLimit;

    @FXML
    private TextField TfUpdPassword;

    @FXML
    private TableColumn<Client, String> CurrentAccountNoColumn;

    @FXML
    private TableColumn<Client, String> SavingAccountNoColumn;

    @FXML
    private TextField TfUpdUsername;

    @FXML
    private TextField TfUpdWithdrawLimit;

    @FXML
    private Button UpdButt;

    @FXML
    private TableColumn<String, String> UsernameColumn;

    @FXML
    private Label lblGreeting;

    @FXML
    private Button logout;

    @FXML
    private ToggleGroup rbTransactionGroup;

    @FXML
    private Button transaction;

    @FXML
    private Tab tabCurrent;

    @FXML
    private Tab tabSavings;

    @FXML
    private Tab tabUpdate;

    @FXML
    private TabPane tpClients;

    @FXML
    private ComboBox<String> CbUpdateCID;

    @FXML
    private RadioButton RbUpdateCurrent;

    @FXML
    private RadioButton RbUpdateSaving;

    @FXML
    private Label lblInterestRate;

    @FXML
    private Label lblInterestType;

    @FXML
    private Label lblOverdraft;

    @FXML
    private Label lblWithdraw;

    @FXML
    private TextField TfAddPhone;

    @FXML
    private TextField TfCurrentAddPhone;

    @FXML
    private TextField TfSavingsAddPhone;

    @FXML
    private TextField TfUpdPhone;

    @FXML
    private Spinner<Double> spinnerCurrentBalance;

    @FXML
    private Spinner<Double> spinnerSavingBalance;

    @FXML
    public void initialize() {
        lblGreeting.setText(String.format("Hi, %s", currentEmployee.getFirstName()));
        updateScene();
        populateTableView();
        CbUpdInterestType.getItems().addAll(interestType);
        CbSavingsAddInterestType.getItems().addAll(interestType);

        // Initialize TableView selection handling
        //handleTableViewSelection();

        // Set selection mode to single
        ClientsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        SpinnerValueFactory<Double> valueFactorySaving = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 999999999);
        SpinnerValueFactory<Double> valueFactoryCurrent = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 999999999);
        spinnerSavingBalance.setValueFactory(valueFactorySaving);
        spinnerCurrentBalance.setValueFactory(valueFactoryCurrent);


    }

    private void updateScene() {
        clearAllFields();
        resetScene();
        populateTableView();
        populateComboBoxes();
    }

    private void resetScene() {
        TfUpdInterestRate.clear();
        TfUpdUsername.clear();
        TfUpdPassword.clear();
        TfUpdFirstName.clear();
        TfUpdLastName.clear();
        TfUpdOverdraftLimit.clear();
        TfUpdWithdrawLimit.clear();
        TfAddUsername.clear();
        TfAddPassword.clear();
        TfAddFirstName.clear();
        TfAddLastName.clear();
        TfAddPhone.clear();
        TfCurrentAddPhone.clear();
        TfSavingsAddPhone.clear();
        TfUpdPhone.clear();
        TfSavingsAddFirstName.clear();
        TfSavingsAddLastName.clear();
        TfSavingsAddUsername.clear();
        TfSavingsAddPassword.clear();
        TfSavingsAddInterestRate.clear();
        TfCurrentAddFirstName.clear();
        TfCurrentAddLastName.clear();
        TfCurrentAddUsername.clear();
        TfCurrentAddPassword.clear();
        TfCurrentAddWithdrawLimit.clear();
        TfCurrentAddOverdraftLimit.clear();
        CbSavingsAddInterestType.setValue("");
        CbUpdInterestType.setValue("");

        if (spinnerCurrentBalance.getValueFactory() != null) {
            spinnerCurrentBalance.getValueFactory().setValue(0.0);
        }
        if (spinnerSavingBalance.getValueFactory() != null) {
            spinnerSavingBalance.getValueFactory().setValue(0.0);
        }

        RbAddSaving.setSelected(true);

        RbUpdateSaving.setSelected(false);
        RbUpdateCurrent.setSelected(false);
        hideAll();
    }

    private void setVisibility(TextField t) {
        t.setDisable(false);
        t.setOpacity(1);
    }

    private void setVisibility(ChoiceBox<String> c) {
        c.setDisable(false);
        c.setOpacity(1);
    }

    private void setVisibility(Label l) {
        l.setOpacity(1);
    }

    private void hide(TextField t) {
        t.setDisable(true);
        t.setOpacity(0);
    }

    private void hide(ChoiceBox<String> c) {
        c.setDisable(true);
        c.setOpacity(0);
    }

    private void hide(Label l) {
        l.setOpacity(0);
    }

    private void hideAll() {
        hide(TfUpdWithdrawLimit);
        hide(TfUpdOverdraftLimit);
        hide(TfUpdInterestRate);
        hide(CbUpdInterestType);
        hide(lblOverdraft);
        hide(lblInterestRate);
        hide(lblInterestType);
        hide(lblWithdraw);
    }

    public void UpdateAccountOption() {
        if(selectedClient == null) {
            showAlert(Alert.AlertType.ERROR, "Please Select a Client First", "Error");

            RbUpdateCurrent.setSelected(false);
            RbUpdateSaving.setSelected(false);

            return;
        }

        if(RbUpdateSaving.isSelected()) {
            if(selectedClient.savingsAcc != null) {
                setVisibility(lblInterestRate);
                setVisibility(TfUpdInterestRate);
                setVisibility(lblInterestType);
                setVisibility(CbUpdInterestType);

                hide(lblOverdraft);
                hide(lblWithdraw);
                hide(TfUpdOverdraftLimit);
                hide(TfUpdWithdrawLimit);
            } else {
                showAlert(Alert.AlertType.ERROR, "You don't have savings account", "Error");
                RbUpdateSaving.setSelected(false);
                hideAll();
            }
        } else if(RbUpdateCurrent.isSelected()) {
            if(selectedClient.currentAcc != null) {
                setVisibility(TfUpdOverdraftLimit);
                setVisibility(lblOverdraft);
                setVisibility(TfUpdWithdrawLimit);
                setVisibility(lblWithdraw);

                hide(lblInterestRate);
                hide(lblInterestType);
                hide(TfUpdInterestRate);
                hide(CbUpdInterestType);
            } else {
                showAlert(Alert.AlertType.ERROR, "You don't have current account", "Error");
                RbUpdateCurrent.setSelected(false);
                hideAll();
            }
        }
    }

    public void populateTableView() {

        ObservableList<Client> data = FXCollections.observableArrayList(clients);

        // Set up table columns
        ClientIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        //FullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        FullNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getFullName()));

        UsernameColumn.setCellValueFactory(new PropertyValueFactory<>("Username"));
        PasswordColumn.setCellValueFactory(new PropertyValueFactory<>("Password"));
        SavingAccountNoColumn.setCellValueFactory(new PropertyValueFactory<>("sAccountNum"));
        CurrentAccountNoColumn.setCellValueFactory(new PropertyValueFactory<>("cAccountNum"));
        PhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("PhoneNum"));

        // Populate TableView
        ClientsTable.getItems().clear();
        ClientsTable.setItems(data);
    }

    private void showAlert(Alert.AlertType alertType, String message, String title) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.showAndWait();
    }

    private void populateComboBoxes() {
        ObservableList<String> clientIDs = FXCollections.observableArrayList();
        ObservableList<String> savingsClientIDs = FXCollections.observableArrayList();
        ObservableList<String> currentClientIDs = FXCollections.observableArrayList();

        for(Client c : clients) {
            clientIDs.add(c.getID());
            if (c.savingsAcc == null)
                savingsClientIDs.add(c.getID());
            if(c.currentAcc == null)
                currentClientIDs.add(c.getID());
        }


        CbUpdateCID.getItems().clear();
        CbSavingCID.getItems().clear();
        CbCurrentCID.getItems().clear();
        CbUpdateCID.setItems(clientIDs);
        CbSavingCID.setItems(savingsClientIDs);
        CbCurrentCID.setItems(currentClientIDs);
    }

    private boolean ValidateSelection() {
        selectedClient = ClientsTable.getSelectionModel().getSelectedItem();

        if(selectedClient == null) {
            showAlert(Alert.AlertType.ERROR, "Please Select Client First", "Error");
            return false;
        }
        return true;
    }

    public void DeleteClientMenuItemClicked() {
        if(ValidateSelection()) {
            if(selectedClient.savingsAcc != null) {
                selectedClient.savingsAcc.Delete(savingsAccounts);
            }

            if (selectedClient.currentAcc != null) {
                selectedClient.currentAcc.Delete(currentAccounts);
            }

            if(selectedClient.Delete(clients)) {
                showAlert(Alert.AlertType.INFORMATION, "Client has been Deleted Successfully", "Deletion Completed");
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Client hasn't been Deleted", "Deletion Failed");
            }

            updateScene();
        }
    }

    public void UpdateClientMenuItemSelected() {
        if(ValidateSelection()) {
            TfUpdFirstName.setText(selectedClient.getFirstName());
            TfUpdLastName.setText(selectedClient.getLastName());
            TfUpdPassword.setText(selectedClient.getPassword());
            TfUpdUsername.setText(selectedClient.getUsername());
            TfUpdPhone.setText(selectedClient.getPhoneNum());
            CbUpdateCID.setValue(ClientIDColumn.getCellData(selectedClient));

            tpClients.getSelectionModel().select(tabUpdate);
        }
    }

    public void TransactionsMenuItemClicked(ActionEvent event) throws IOException {
        if(ValidateSelection()) {
            SwitchToAdminTransactions(event);
        }
    }

    public void SavingsMenuItemClicked() {
        if(ValidateSelection()) {
            if(selectedClient.savingsAcc != null) {
                showAlert(Alert.AlertType.INFORMATION, "This Client Already have Savings Account", "Info");
                return;
            }

            TfSavingsAddFirstName.setText(selectedClient.getFirstName());
            TfSavingsAddLastName.setText(selectedClient.getLastName());
            TfSavingsAddPassword.setText(selectedClient.getPassword());
            TfSavingsAddUsername.setText(selectedClient.getUsername());
            CbSavingCID.getSelectionModel().select(selectedClient.getID());

            tpClients.getSelectionModel().select(3);
        }
    }

    public void CurrentMenuItemClicked() {
        if(ValidateSelection()) {
            if(selectedClient.currentAcc != null) {
                showAlert(Alert.AlertType.INFORMATION, "This Client Already have Current Account", "Info");
                return;
            }

            TfCurrentAddFirstName.setText(selectedClient.getFirstName());
            TfCurrentAddLastName.setText(selectedClient.getLastName());
            TfCurrentAddPassword.setText(selectedClient.getPassword());
            TfCurrentAddUsername.setText(selectedClient.getUsername());
            CbCurrentCID.getSelectionModel().select(selectedClient.getID());

            tpClients.getSelectionModel().select(4);
        }
    }

    public void cbUpdateClientIDItemChanged() {
        resetScene();

        selectedClient = Client.Find(clients, CbUpdateCID.getValue());

        if(selectedClient != null) {
            TfUpdFirstName.setText(selectedClient.getFirstName());
            TfUpdLastName.setText(selectedClient.getLastName());
            TfUpdPassword.setText(selectedClient.getPassword());
            TfUpdUsername.setText(selectedClient.getUsername());
            TfUpdPhone.setText(selectedClient.getPhoneNum());
        }
    }

    public void cbSavingsClientIDItemChanged() {
        selectedClient = Client.Find(clients, CbSavingCID.getValue());

        if(selectedClient != null) {
            TfSavingsAddFirstName.setText(selectedClient.getFirstName());
            TfSavingsAddLastName.setText(selectedClient.getLastName());
            TfSavingsAddPassword.setText(selectedClient.getPassword());
            TfSavingsAddUsername.setText(selectedClient.getUsername());
            TfSavingsAddPhone.setText(selectedClient.getPhoneNum());
        }
    }

    public void cbCurrentClientIDItemChanged() {
        selectedClient = Client.Find(clients, CbCurrentCID.getValue());

        if(selectedClient != null) {
            TfCurrentAddFirstName.setText(selectedClient.getFirstName());
            TfCurrentAddLastName.setText(selectedClient.getLastName());
            TfCurrentAddPassword.setText(selectedClient.getPassword());
            TfCurrentAddUsername.setText(selectedClient.getUsername());
            TfCurrentAddPhone.setText(selectedClient.getPhoneNum());
        }
    }

    private boolean addClientIsAnyOfFieldsEmpty() {
        return TfAddUsername.getText().isEmpty() || TfAddPassword.getText().isEmpty() ||
                TfAddLastName.getText().isEmpty() || TfAddFirstName.getText().isEmpty() || TfAddPhone.getText().isEmpty();
    }

    private boolean addSavingClientIsAnyOfFieldsEmpty() {
        return TfSavingsAddInterestRate.getText().isEmpty() || TfSavingsAddFirstName.getText().isEmpty() ||
                TfSavingsAddLastName.getText().isEmpty() || TfSavingsAddUsername.getText().isEmpty() ||
                TfSavingsAddPassword.getText().isEmpty() || CbSavingsAddInterestType.getValue().isEmpty() ||
                CbSavingCID.getValue().isEmpty() || TfSavingsAddPhone.getText().isEmpty();
    }

    private boolean addCurrentClientIsAnyOfFieldsEmpty() {
        return TfCurrentAddWithdrawLimit.getText().isEmpty() || TfCurrentAddFirstName.getText().isEmpty() ||
                TfCurrentAddLastName.getText().isEmpty() || TfCurrentAddUsername.getText().isEmpty() ||
                TfCurrentAddPassword.getText().isEmpty() || TfCurrentAddOverdraftLimit.getText().isEmpty() ||
                CbCurrentCID.getValue().isEmpty() || TfCurrentAddPhone.getText().isEmpty();
    }

    private void clearAllFields() {
        // Clear all input fields and choice box
        //add is 4
        TfAddFirstName.clear();
        TfAddLastName.clear();
        TfAddUsername.clear();
        TfAddPassword.clear();
        TfAddPhone.clear();

        //current is 7
        TfCurrentAddFirstName.clear();
        TfCurrentAddLastName.clear();
        TfCurrentAddUsername.clear();
        TfCurrentAddPassword.clear();
        CbCurrentCID.setValue(null);
        TfCurrentAddOverdraftLimit.clear();
        TfCurrentAddWithdrawLimit.clear();
        TfCurrentAddPhone.clear();

        //savings is 6
        TfSavingsAddFirstName.clear();
        TfSavingsAddLastName.clear();
        TfSavingsAddUsername.clear();
        TfSavingsAddPassword.clear();
        CbSavingCID.setValue(null);
        TfSavingsAddInterestRate.clear();
        CbSavingsAddInterestType.setValue(null);
        TfSavingsAddPhone.clear();

    }

    private Client createClientFromInput() {
        Client c = new Client();

        c.setFirstName(TfAddFirstName.getText());
        c.setLastName(TfAddLastName.getText());
        c.setUsername(TfAddUsername.getText());
        c.setPassword(TfAddPassword.getText());
        c.setPhoneNum(TfAddPhone.getText());
        c.setID("C" + (clients.size()+11));
        c.setcAccountNum("-1");
        c.setsAccountNum("-1");


        if(RbAddCurrent.isSelected()) {

            tpClients.getSelectionModel().select(tabCurrent);
            TfCurrentAddFirstName.setText(TfAddFirstName.getText());
            TfCurrentAddLastName.setText(TfAddLastName.getText());
            TfCurrentAddUsername.setText(TfAddUsername.getText());
            TfCurrentAddPassword.setText(TfAddPassword.getText());
            TfCurrentAddPhone.setText(TfAddPhone.getText());
            CbCurrentCID.setValue(c.getID());

        }

        if (RbAddSaving.isSelected()) {

            tpClients.getSelectionModel().select(tabSavings);
            TfSavingsAddFirstName.setText(TfAddFirstName.getText());
            TfSavingsAddLastName.setText(TfAddLastName.getText());
            TfSavingsAddUsername.setText(TfAddUsername.getText());
            TfSavingsAddPassword.setText(TfAddPassword.getText());
            TfSavingsAddPhone.setText(TfAddPhone.getText());
            CbSavingCID.setValue(c.getID());
        }

        return c;
    }

    public void AddClientButtClicked () {

        // Check if any of the fields are empty
        if (addClientIsAnyOfFieldsEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill all the empty fields", "Error");
            return;
        }

        // Confirmation Alert of the event
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to add this Client?",
                ButtonType.YES, ButtonType.NO);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setTitle("Confirmation");

        // Show confirmation dialog and process response
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            Client c = createClientFromInput();
            selectedClient = c;
        }


    }

    public void AddSavingsClientButt () {
        // Check if any of the fields are empty
        if (addSavingClientIsAnyOfFieldsEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill all the empty fields", "Error");
            return;
        }

        if (spinnerSavingBalance.getValue() == null || spinnerSavingBalance.getValue() == 0.0) {
            showAlert(Alert.AlertType.WARNING, "Please enter a balance", "Insufficient Balance");
            spinnerSavingBalance.getValueFactory().setValue(0.0); // Reset spinner value
            spinnerSavingBalance.requestFocus();
            return;
        }

        // Confirmation Alert of the event
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to add Saving Account?",
                ButtonType.YES, ButtonType.NO);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setTitle("Confirmation");

        // Show confirmation dialog and process response
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            SavingsAccount s = new SavingsAccount("S" + (savingsAccounts.size()+101), selectedClient.getID());



            s.setBalance(spinnerSavingBalance.getValue());
            selectedClient.setsAccountNum(s.getAccountNumber());
            s.setAccountInterestType(CbSavingsAddInterestType.getValue());
            s.setAccountInterestRate(Double.parseDouble(TfSavingsAddInterestRate.getText()));

            selectedClient.setSavingsAcc(s);
            selectedClient.Save(clients);
            savingsAccounts.add(s);

            showAlert(Alert.AlertType.INFORMATION, String.format("Saving Account with [%s] number has been made successfully",
                    s.getAccountNumber()), "Account has been made");

            updateScene();
            populateTableView();
        }

    }

    public void AddCurrentClientButt () {
        // Check if any of the fields are empty
        if (addCurrentClientIsAnyOfFieldsEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill all the empty fields", "Error");
            return;
        }

        if (spinnerCurrentBalance.getValue() == null || spinnerCurrentBalance.getValue() < 3000) {
            showAlert(Alert.AlertType.WARNING, "Minimum balance for Current Account is 3000.\nPlease enter another balance.", "Insufficient Balance");
            spinnerCurrentBalance.getValueFactory().setValue(0.0); // Reset spinner value
            spinnerCurrentBalance.requestFocus();
            return;
        }

        // Confirmation Alert of the event
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to add Current Account?",
                ButtonType.YES, ButtonType.NO);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setTitle("Confirmation");

        // Show confirmation dialog and process response
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            CurrentAccount c = new CurrentAccount("C" + (currentAccounts.size()+101), selectedClient.getID());
            selectedClient.setcAccountNum(c.getAccountNumber());

            c.setOverdraftLimit(Double.parseDouble(TfCurrentAddOverdraftLimit.getText()));
            c.setWithdrawLimit(Double.parseDouble(TfCurrentAddWithdrawLimit.getText()));



            c.setBalance(spinnerCurrentBalance.getValue());

            selectedClient.setCurrentAcc(c);
            selectedClient.Save(clients);

            currentAccounts.add(c);


            showAlert(Alert.AlertType.INFORMATION, String.format("Current Account with [%s] number has been made successfully",
                    c.getAccountNumber()), "Account has been made");

            updateScene();
            populateTableView();
        }
    }

    public void btnUpdateClientClicked() {
        if(!isAllUpdateFieldsAreFilled()) {
            showAlert(Alert.AlertType.ERROR, "Please fill all the fields", "Error");
            return;
        }

        boolean isUpdated = false;

        // Confirmation Alert of the Transaction
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to update this client?",
                ButtonType.YES, ButtonType.NO);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setTitle("Confirmation");

        // Show confirmation dialog and process response
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            selectedClient.setFirstName(TfUpdFirstName.getText());
            selectedClient.setLastName(TfUpdLastName.getText());
            selectedClient.setUsername(TfUpdUsername.getText());
            selectedClient.setPassword(TfUpdPassword.getText());

            if(RbUpdateSaving.isSelected()) {
                selectedClient.savingsAcc.setAccountInterestRate(Double.parseDouble(TfUpdInterestRate.getText()));
                selectedClient.savingsAcc.setAccountInterestType(CbUpdInterestType.getValue());

                if (selectedClient.savingsAcc.Update(savingsAccounts) && selectedClient.Save(clients)) {
                    isUpdated = true;
                }
            } else if (RbUpdateCurrent.isSelected()) {
                selectedClient.currentAcc.setOverdraftLimit(Double.parseDouble(TfUpdOverdraftLimit.getText()));
                selectedClient.currentAcc.setWithdrawLimit(Double.parseDouble(TfUpdWithdrawLimit.getText()));

                if (selectedClient.currentAcc.Update(currentAccounts) && selectedClient.Save(clients)) {
                    isUpdated = true;
                }
            }
        }

        if(isUpdated) {
            showAlert(Alert.AlertType.INFORMATION, "Client has been updated", "Updated");
            updateScene();
        } else {
            showAlert(Alert.AlertType.ERROR, "Updating Failed!", "Error");
        }
    }

    private boolean isAllUpdateFieldsAreFilled() {
        boolean areCommonFieldsFilled =
                !TfUpdFirstName.getText().isEmpty() &&
                        !TfUpdLastName.getText().isEmpty() &&
                        !TfUpdUsername.getText().isEmpty() &&
                        !TfUpdPassword.getText().isEmpty();

        if (!areCommonFieldsFilled) {
            return false;
        }

        // Validate fields based on the selected account type
        if (RbUpdateSaving.isSelected()) {
            return !TfUpdInterestRate.getText().isEmpty() && CbUpdInterestType.getValue() != null;
        } else if (RbUpdateCurrent.isSelected()) {
            return !TfUpdOverdraftLimit.getText().isEmpty() && !TfUpdWithdrawLimit.getText().isEmpty();
        }

        return false; // Neither account type selected
    }

}