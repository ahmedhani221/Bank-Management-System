package com.oop.projectWithGUI;

import Classes.Client;
import Classes.CurrentAccount;
import Classes.SavingsAccount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.Optional;

import static com.oop.projectWithGUI.CSVController.*;
import static com.oop.projectWithGUI.LoginController.currentEmployee;

public class EmployeeClientsController extends SwitchScenes {
    @FXML
    private ChoiceBox<String> cbSavingsAddInterestType;

    @FXML
    private ChoiceBox<String> cbUpdInterestType;

    private final String[] interestType = {"Monthly", "Yearly"};

    @FXML
    private TableColumn<Client, String> colClientID;

    @FXML
    private TableColumn<Client, String> colFullName;

    @FXML
    private TableColumn<Client, String> colPassword;

    @FXML
    private TableColumn<Client, String> colPhoneNum;

    @FXML
    private TableColumn<Client, String> colUsername;

    @FXML
    private TableColumn<Client, String> colSAccount;

    @FXML
    private TableColumn<Client, String> colCAccountNo;

    @FXML
    private TableView<Client> ClientsTable;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnAddCurrent;

    @FXML
    private Button btnAddSavings;

    @FXML
    private Button btnUpdate;

    @FXML
    private RadioButton rbAddCurrent;

    @FXML
    private RadioButton rbAddSaving;

    @FXML
    private RadioButton rbUpdateCurrent;

    @FXML
    private RadioButton rbUpdateSaving;

    @FXML
    private TextField tfAddFirstName;

    @FXML
    private TextField tfAddLastName;

    @FXML
    private TextField tfAddPassword;

    @FXML
    private TextField tfAddUsername;

    @FXML
    private TextField tfAddPhone;

    @FXML
    private TextField tfCurrentAddFirstName;

    @FXML
    private TextField tfCurrentAddLastName;

    @FXML
    private TextField tfCurrentAddOverdraftLimit;

    @FXML
    private TextField tfCurrentAddPassword;

    @FXML
    private TextField tfCurrentAddUsername;

    @FXML
    private TextField tfCurrentAddWithdrawLimit;

    @FXML
    private TextField tfSavingsAddFirstName;

    @FXML
    private TextField tfSavingsAddInterestRate;

    @FXML
    private TextField tfSavingsAddLastName;

    @FXML
    private TextField tfSavingsAddPassword;

    @FXML
    private TextField tfSavingsAddUsername;

    @FXML
    private TextField tfUpdFirstName;

    @FXML
    private TextField tfUpdInterestRate;

    @FXML
    private TextField tfUpdLastName;

    @FXML
    private TextField tfUpdOverdraftLimit;

    @FXML
    private TextField tfUpdPassword;

    @FXML
    private TextField tfUpdUsername;

    @FXML
    private TextField tfUpdWithdrawLimit;

    @FXML
    private ComboBox<String> cbCurrentClientID;

    @FXML
    private ComboBox<String> cbSavingsClientID;

    @FXML
    private ComboBox<String> cbUpdateClientID;

    @FXML
    private Label lblGreeting;

    @FXML
    private Label lblInterestRate;

    @FXML
    private Label lblInterestType;

    @FXML
    private Label lblOverdraft;

    @FXML
    private Label lblWithdraw;

    @FXML
    private Tab tabUpdateClient;

    @FXML
    private Tab tabAddSaving;

    @FXML
    private Tab tabAddCurrent;

    @FXML
    private TabPane tpClients;

    @FXML
    private Spinner<Double> spinnerCurrentBalance;

    @FXML
    private Spinner<Double> spinnerSavingsBalance;

    public static Client selectedClient = null;

//-----------------------------------------------------------------------------

    @FXML
    public void initialize(){
        lblGreeting.setText(String.format("Hi, %s", currentEmployee.getFirstName()));

        SpinnerValueFactory<Double> valueFactorySavings = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 999999999);
        SpinnerValueFactory<Double> valueFactoryCurrent = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 999999999);
        spinnerSavingsBalance.setValueFactory(valueFactorySavings);
        spinnerCurrentBalance.setValueFactory(valueFactoryCurrent);

        hideAll();

        cbSavingsAddInterestType.getItems().addAll(interestType);
        cbUpdInterestType.getItems().addAll(interestType);

        populateTableView();
        populateComboBoxes();
    }

    private void showAlert(Alert.AlertType alertType, String message, String title) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.showAndWait();
    }

    private void populateTableView(){
        // Convert ArrayList to ObservableList
        ObservableList<Client> data = FXCollections.observableArrayList(clients);

        // Link TableColumns to model properties
        colClientID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colCAccountNo.setCellValueFactory(new PropertyValueFactory<>("cAccountNum"));
        colSAccount.setCellValueFactory(new PropertyValueFactory<>("sAccountNum"));
        colPhoneNum.setCellValueFactory(new PropertyValueFactory<>("PhoneNum"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("Password"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("Username"));
        colFullName.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getFullName()));

        // Populate TableView
        ClientsTable.getItems().clear();
        ClientsTable.setItems(data);
    }

    private void populateComboBoxes(){
        ObservableList<String> clientIDs = FXCollections.observableArrayList();
        ObservableList<String> savingsClientIDs = FXCollections.observableArrayList();
        ObservableList<String> currentClientIDs = FXCollections.observableArrayList();

        for(Client c : clients){
            clientIDs.add(c.getID());

            if (c.savingsAcc == null)
                savingsClientIDs.add(c.getID());
            if(c.currentAcc == null)
                currentClientIDs.add(c.getID());
        }
        cbUpdateClientID.getItems().clear();
        cbUpdateClientID.setItems(clientIDs);

        cbSavingsClientID.getItems().clear();
        cbSavingsClientID.setItems(savingsClientIDs);

        cbCurrentClientID.getItems().clear();
        cbCurrentClientID.setItems(currentClientIDs);
    }

    private void updateScene() {
        resetScene();

        populateComboBoxes();
        populateTableView();
    }

    private void resetScene(){
        tfUpdInterestRate.clear();
        tfUpdUsername.clear();
        tfUpdPassword.clear();
        tfUpdFirstName.clear();
        tfUpdLastName.clear();
        tfUpdOverdraftLimit.clear();
        tfUpdWithdrawLimit.clear();
        tfAddUsername.clear();
        tfAddPassword.clear();
        tfAddFirstName.clear();
        tfAddLastName.clear();
        tfAddPhone.clear();
        tfSavingsAddFirstName.clear();
        tfSavingsAddLastName.clear();
        tfSavingsAddUsername.clear();
        tfSavingsAddPassword.clear();
        tfSavingsAddInterestRate.clear();
        tfCurrentAddFirstName.clear();
        tfCurrentAddLastName.clear();
        tfCurrentAddUsername.clear();
        tfCurrentAddPassword.clear();
        tfCurrentAddWithdrawLimit.clear();
        tfCurrentAddOverdraftLimit.clear();
        cbSavingsAddInterestType.setValue("");
        cbUpdInterestType.setValue("");

        spinnerCurrentBalance.getValueFactory().setValue(0.0);
        spinnerSavingsBalance.getValueFactory().setValue(0.0);

        rbUpdateSaving.setSelected(false);
        rbUpdateCurrent.setSelected(false);
        hideAll();
    }

    private void setVisibility(TextField t){
        t.setDisable(false);
        t.setOpacity(1);
    }

    private void setVisibility(ChoiceBox<String> c){
        c.setDisable(false);
        c.setOpacity(1);
    }

    private void setVisibility(Label l){
        l.setOpacity(1);
    }

    private void hide(TextField t){
        t.setDisable(true);
        t.setOpacity(0);
    }

    private void hide(ChoiceBox<String> c){
        c.setDisable(true);
        c.setOpacity(0);
    }

    private void hide(Label l){
        l.setOpacity(0);
    }

    private void hideAll(){
        hide(tfUpdWithdrawLimit);
        hide(tfUpdOverdraftLimit);
        hide(tfUpdInterestRate);
        hide(cbUpdInterestType);
        hide(lblOverdraft);
        hide(lblInterestRate);
        hide(lblInterestType);
        hide(lblWithdraw);
    }

    public void UpdateAccountOption(){
        if(selectedClient == null){
            showAlert(Alert.AlertType.ERROR, "Please Select a Client First", "Error");

            rbUpdateCurrent.setSelected(false);
            rbUpdateSaving.setSelected(false);

            return;
        }

        cbUpdInterestType.setValue(null);

        if(rbUpdateSaving.isSelected()){
            if(selectedClient.savingsAcc != null){
                setVisibility(lblInterestRate);
                setVisibility(tfUpdInterestRate);
                setVisibility(lblInterestType);
                setVisibility(cbUpdInterestType);

                hide(lblOverdraft);
                hide(lblWithdraw);
                hide(tfUpdOverdraftLimit);
                hide(tfUpdWithdrawLimit);
            }
            else{
                showAlert(Alert.AlertType.ERROR, "You don't have savings account", "Error");
                rbUpdateSaving.setSelected(false);
                hideAll();
            }
        }
        else if(rbUpdateCurrent.isSelected()){
            if(selectedClient.currentAcc != null){
                setVisibility(tfUpdOverdraftLimit);
                setVisibility(lblOverdraft);
                setVisibility(tfUpdWithdrawLimit);
                setVisibility(lblWithdraw);

                hide(lblInterestRate);
                hide(lblInterestType);
                hide(tfUpdInterestRate);
                hide(cbUpdInterestType);
            }
            else{
                showAlert(Alert.AlertType.ERROR, "You don't have current account", "Error");
                rbUpdateCurrent.setSelected(false);
                hideAll();
            }
        }
    }

    private boolean ValidateSelection(){
        selectedClient = ClientsTable.getSelectionModel().getSelectedItem();

        if(selectedClient == null) {
            showAlert(Alert.AlertType.ERROR, "Please Select Client First", "Error");
            return false;
        }
        return true;
    }

    public void DeleteClientMenuItemClicked(){
        if(ValidateSelection()){

            if(selectedClient.savingsAcc != null){
                selectedClient.savingsAcc.Delete(savingsAccounts);
            }
            if (selectedClient.currentAcc != null){
                selectedClient.currentAcc.Delete(currentAccounts);
            }

            if(selectedClient.Delete(clients)){
                showAlert(Alert.AlertType.INFORMATION, "Client has been Deleted Successfully", "Deletion Completed");
            }
            else{
                showAlert(Alert.AlertType.INFORMATION, "Client hasn't been Deleted", "Deletion Failed");
            }

            updateScene();
        }
    }

    public void UpdateClientMenuItemClicked(){
        if(ValidateSelection()){
            tfUpdFirstName.setText(selectedClient.getFirstName());
            tfUpdLastName.setText(selectedClient.getLastName());
            tfUpdPassword.setText(selectedClient.getPassword());
            tfUpdUsername.setText(selectedClient.getUsername());
            cbUpdateClientID.setValue(colClientID.getCellData(selectedClient));

            tpClients.getSelectionModel().select(tabUpdateClient);
        }
    }

    public void TransactionsMenuItemClicked(ActionEvent event) throws IOException {
        if(ValidateSelection()){
            SwitchToEmployeeTransactions(event);
        }
    }

    public void SavingsMenuItemClicked(){
        if(ValidateSelection()){
            if(selectedClient.savingsAcc != null){
                showAlert(Alert.AlertType.INFORMATION, "This Client Already have Savings Account", "Info");
                return;
            }

            tfSavingsAddFirstName.setText(selectedClient.getFirstName());
            tfSavingsAddLastName.setText(selectedClient.getLastName());
            tfSavingsAddPassword.setText(selectedClient.getPassword());
            tfSavingsAddUsername.setText(selectedClient.getUsername());
            cbSavingsClientID.getSelectionModel().select(selectedClient.getID());

            tpClients.getSelectionModel().select(3);
        }
    }

    public void CurrentMenuItemClicked(){
        if(ValidateSelection()){
            if(selectedClient.currentAcc != null){
                showAlert(Alert.AlertType.INFORMATION, "This Client Already have Current Account", "Info");
                return;
            }

            tfCurrentAddFirstName.setText(selectedClient.getFirstName());
            tfCurrentAddLastName.setText(selectedClient.getLastName());
            tfCurrentAddPassword.setText(selectedClient.getPassword());
            tfCurrentAddUsername.setText(selectedClient.getUsername());
            cbCurrentClientID.getSelectionModel().select(selectedClient.getID());

            tpClients.getSelectionModel().select(4);
        }
    }

    public void cbUpdateClientIDItemChanged(){
        resetScene();

        selectedClient = Client.Find(clients, cbUpdateClientID.getValue());

        if(selectedClient != null){
            tfUpdFirstName.setText(selectedClient.getFirstName());
            tfUpdLastName.setText(selectedClient.getLastName());
            tfUpdPassword.setText(selectedClient.getPassword());
            tfUpdUsername.setText(selectedClient.getUsername());
        }
    }

    public void cbSavingsClientIDItemChanged(){
        selectedClient = Client.Find(clients, cbSavingsClientID.getValue());

        if(selectedClient != null){
            tfSavingsAddFirstName.setText(selectedClient.getFirstName());
            tfSavingsAddLastName.setText(selectedClient.getLastName());
            tfSavingsAddPassword.setText(selectedClient.getPassword());
            tfSavingsAddUsername.setText(selectedClient.getUsername());
        }
    }

    public void cbCurrentClientIDItemChanged(){
        selectedClient = Client.Find(clients, cbCurrentClientID.getValue());

        if(selectedClient != null){
            tfCurrentAddFirstName.setText(selectedClient.getFirstName());
            tfCurrentAddLastName.setText(selectedClient.getLastName());
            tfCurrentAddPassword.setText(selectedClient.getPassword());
            tfCurrentAddUsername.setText(selectedClient.getUsername());
        }
    }

    public void btnUpdateClientClicked(){
        if(!isAllUpdateFieldsAreFilled()){
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
            selectedClient.setFirstName(tfUpdFirstName.getText());
            selectedClient.setLastName(tfUpdLastName.getText());
            selectedClient.setUsername(tfUpdUsername.getText());
            selectedClient.setPassword(tfUpdPassword.getText());

            if(rbUpdateSaving.isSelected()){
                selectedClient.savingsAcc.setAccountInterestRate(Double.parseDouble(tfUpdInterestRate.getText()));
                selectedClient.savingsAcc.setAccountInterestType(cbUpdInterestType.getValue());

                if (selectedClient.savingsAcc.Update(savingsAccounts) && selectedClient.Save(clients)){
                    isUpdated = true;
                }
            }

            else if (rbUpdateCurrent.isSelected()) {
                selectedClient.currentAcc.setOverdraftLimit(Double.parseDouble(tfUpdOverdraftLimit.getText()));
                selectedClient.currentAcc.setWithdrawLimit(Double.parseDouble(tfUpdWithdrawLimit.getText()));

                if (selectedClient.currentAcc.Update(currentAccounts) && selectedClient.Save(clients)){
                    isUpdated = true;
                }
            }
        }

        if(isUpdated){
            showAlert(Alert.AlertType.INFORMATION, "Client has been updated", "Updated");
            updateScene();
        }
        else{
            showAlert(Alert.AlertType.ERROR, "Updating Failed!", "Error");
        }
    }

    private boolean isAllUpdateFieldsAreFilled() {
        boolean areCommonFieldsFilled =
                !tfUpdFirstName.getText().isEmpty() &&
                        !tfUpdLastName.getText().isEmpty() &&
                        !tfUpdUsername.getText().isEmpty() &&
                        !tfUpdPassword.getText().isEmpty();

        if (!areCommonFieldsFilled) {
            return false;
        }

        // Validate fields based on the selected account type
        if (rbUpdateSaving.isSelected()) {
            return !tfUpdInterestRate.getText().isEmpty() && cbUpdInterestType.getValue() != null;
        } else if (rbUpdateCurrent.isSelected()) {
            return !tfUpdOverdraftLimit.getText().isEmpty() && !tfUpdWithdrawLimit.getText().isEmpty();
        }

        return false; // Neither account type selected
    }

    public void btnAddClientClicked(){
        if(tfAddFirstName.getText().isEmpty() || tfAddLastName.getText().isEmpty()  || tfAddPassword.getText().isEmpty()  ||
                tfAddUsername.getText().isEmpty())
        {
            showAlert(Alert.AlertType.ERROR, "Please fill all the fields", "Error");
            return;
        }

        // Confirmation Alert of the Transaction
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to add this Client?",
                ButtonType.YES, ButtonType.NO);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setTitle("Confirmation");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            if(Client.FindByUsername(clients, tfAddUsername.getText())){
                showAlert(Alert.AlertType.WARNING, "Username already in use\nPlease use another one", "Invalid Username");
                return;
            }

            selectedClient = createClientFromInput();

            if (selectedClient.Save(clients)) {
                showAlert(Alert.AlertType.INFORMATION,
                        String.format("Client, with [%s] ID, has been added successfully", selectedClient.getID()),
                        "Client has been added");

                updateScene();

                if (rbAddSaving.isSelected()) {
                    cbSavingsClientID.setValue(selectedClient.getID());
                    cbSavingsClientIDItemChanged();
                    tpClients.getSelectionModel().select(tabAddSaving);
                }
                else if (rbAddCurrent.isSelected()) {
                    cbCurrentClientID.setValue(selectedClient.getID());
                    cbCurrentClientIDItemChanged();
                    tpClients.getSelectionModel().select(tabAddCurrent);
                }
            }
            else{
                showAlert(Alert.AlertType.ERROR,
                        "Unable to add the client. Ensure all required fields are filled correctly and try again.",
                        "Error: Client Not Added");
            }
        }
    }

    private Client createClientFromInput() {
        Client c = new Client();
        c.setID("C" + (clients.size() + 11));
        if (Client.FindByID(clients, "C" + (clients.size() + 11))) {
            c.setID("C" + (clients.size() + 12));
        }

        c.setFirstName(tfAddFirstName.getText());
        c.setLastName(tfAddLastName.getText());
        c.setUsername(tfAddUsername.getText());
        c.setPassword(tfAddPassword.getText());
        c.setPhoneNum(tfAddPhone.getText());
        c.setsAccountNum("-1");
        c.setcAccountNum("-1");

        return c;
    }

    public void btnAddSavingsClicked(){
        if(cbSavingsClientID.getValue().isEmpty() || cbSavingsAddInterestType.getValue().isEmpty() ||
                tfSavingsAddInterestRate.getText().isEmpty())
        {
            showAlert(Alert.AlertType.ERROR, "Please fill all of the fields", "Error");
            return;
        }

        // Confirmation Alert of the Transaction
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to add this Savings Account?",
                ButtonType.YES, ButtonType.NO);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setTitle("Confirmation");

        // Show confirmation dialog and process response
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            SavingsAccount s = new SavingsAccount("S" + (savingsAccounts.size() + 101), selectedClient.getID());

            selectedClient.setsAccountNum(s.getAccountNumber());

            s.setAccountInterestType(cbSavingsAddInterestType.getValue());

            try{
                s.setAccountInterestRate(Double.parseDouble(tfSavingsAddInterestRate.getText()));
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid number format.", "Error");
                return;
            }

            if (spinnerSavingsBalance.getValue() == null || spinnerSavingsBalance.getValue() == 0.0) {
                showAlert(Alert.AlertType.WARNING, "Please enter a balance", "Insufficient Balance");
                spinnerSavingsBalance.getValueFactory().setValue(0.0); // Reset spinner value
                spinnerSavingsBalance.requestFocus();
                return;
            }

            s.setBalance(spinnerSavingsBalance.getValue());

            selectedClient.setSavingsAcc(s);
            selectedClient.Save(clients);

            savingsAccounts.add(s);

            showAlert(Alert.AlertType.INFORMATION,
                    String.format("Saving Account with [%s] number has been made successfully", s.getAccountNumber()),
                    "Account has been made");

            updateScene();
        }
    }

    public void btnAddCurrentClicked(){
        if(cbCurrentClientID.getValue().isEmpty()  || tfCurrentAddOverdraftLimit.getText().isEmpty()  ||
                tfCurrentAddWithdrawLimit.getText().isEmpty())
        {
            showAlert(Alert.AlertType.ERROR, "Please fill all of the fields", "Error");
            return;
        }

        // Confirmation Alert of the Transaction
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to add this Current Account?",
                ButtonType.YES, ButtonType.NO);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setTitle("Confirmation");

        // Show confirmation dialog and process response
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            CurrentAccount c = new CurrentAccount("C" + (currentAccounts.size() + 101), selectedClient.getID());

            selectedClient.setcAccountNum(c.getAccountNumber());

            try
            {
                c.setWithdrawLimit(Double.parseDouble(tfCurrentAddWithdrawLimit.getText()));
                c.setOverdraftLimit(Double.parseDouble(tfCurrentAddOverdraftLimit.getText()));
            }
            catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid number format.", "Error");
                return;
            }

            if (spinnerCurrentBalance.getValue() == null || spinnerCurrentBalance.getValue() < 3000) {
                showAlert(Alert.AlertType.WARNING, "Minimum balance for Current Account is 3000.\nPlease enter another balance.", "Insufficient Balance");
                spinnerCurrentBalance.getValueFactory().setValue(0.0); // Reset spinner value
                spinnerCurrentBalance.requestFocus();
                return;
            }

            c.setBalance(spinnerCurrentBalance.getValue());

            selectedClient.setCurrentAcc(c);
            selectedClient.Save(clients);

            currentAccounts.add(c);

            showAlert(Alert.AlertType.INFORMATION,
                    String.format("Current Account with [%s] number has been made successfully", c.getAccountNumber()),
                    "Account has been made");

            updateScene();
        }
    }
}