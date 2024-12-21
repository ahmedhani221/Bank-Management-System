package com.oop.projectWithGUI;

import Classes.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import static com.oop.projectWithGUI.CSVController.employees;
import static com.oop.projectWithGUI.LoginController.currentEmployee;

import java.util.Optional;

public class AdminEmployeesController extends SwitchScenes {
    @FXML
    private Button btnAddEmployee;

    @FXML
    private Button btnUpdateEmployee;

    @FXML
    private ChoiceBox<String> cbAddGrade;

    @FXML
    private ChoiceBox<String> cbUpdateGrade;

    String[] gradeElements = {"A+", "A", "B+", "B", "C+", "C"};

    @FXML
    private ContextMenu cmEmployeesActions;

    @FXML
    private MenuItem miDeleteEmp;

    @FXML
    private MenuItem miUpdateEmp;

    @FXML
    private Label lblGreeting;

    @FXML
    private RadioButton rbAdmin;

    @FXML
    private RadioButton rbEmployee;

    @FXML
    private TextField tfAddAddress;

    @FXML
    private TextField tfAddCollege;

    @FXML
    private TextField tfAddFirst;

    @FXML
    private TextField tfAddLast;

    @FXML
    private TextField tfAddPassword;

    @FXML
    private TextField tfAddUsername;

    @FXML
    private TextField tfAddYOG;

    @FXML
    private TextField tfUpdateAddress;

    @FXML
    private TextField tfUpdateCollege;

    @FXML
    private TextField tfUpdateFirst;

    @FXML
    private TextField tfUpdateLast;

    @FXML
    private TextField tfUpdatePassword;

    @FXML
    private TextField tfUpdateUsername;

    @FXML
    private TextField tfUpdateYOG;

    @FXML
    private TableColumn<Employee, String> colAddress;

    @FXML
    private TableColumn<Employee, String> colCollege;

    @FXML
    private TableColumn<Employee, String> colEmployeeID;

    @FXML
    private TableColumn<Employee, String> colFullName;

    @FXML
    private TableColumn<Employee, String> colGrade;

    @FXML
    private TableColumn<Employee, String> colPassword;

    @FXML
    private TableColumn<Employee, String> colPosition;

    @FXML
    private TableColumn<Employee, String> colUsername;

    @FXML
    private TableColumn<Employee, String> colYOG;

    @FXML
    private TableView<Employee> tvEmployees;

    @FXML
    private Tab tabUpdateEmp;

    @FXML
    private TabPane tpEmp;

    @FXML
    private ComboBox<String> cbEmployeeID;

    private Employee selectedEmployee;


    @FXML
    public void initialize(){
        lblGreeting.setText(String.format("Hi, %s", currentEmployee.getFirstName()));
        cbAddGrade.getItems().addAll(gradeElements);
        cbUpdateGrade.getItems().addAll(gradeElements);

        populateTableView();
        populateComboBox();
    }

    private void populateTableView(){

        // Convert ArrayList to ObservableList
        ObservableList<Employee> data = FXCollections.observableArrayList(employees);

        // Link TableColumns to model properties
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCollege.setCellValueFactory(new PropertyValueFactory<>("graduatedCollege"));
        colEmployeeID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("totalGrade"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("Password"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("Username"));
        colYOG.setCellValueFactory(new PropertyValueFactory<>("yearOfGraduation"));
        colFullName.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getFullName()));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));

        // Populate TableView
        tvEmployees.getItems().clear();
        tvEmployees.setItems(data);
    }

    private void populateComboBox(){
        ObservableList<String> empIDs = FXCollections.observableArrayList();

        for (Employee e: employees){
            empIDs.add(e.getID());
        }

        cbEmployeeID.getItems().clear();
        cbEmployeeID.setItems(empIDs);
    }

    private void showAlert(Alert.AlertType alertType, String message, String title) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.showAndWait();
    }

    private void updateScene() {
        rbEmployee.setSelected(true);

        clearAllFields();
        cbEmployeeID.setValue(null);

        populateTableView();
        populateComboBox();
    }

    private void clearAllFields() {
        // Clear all input fields and choice box
        tfAddYOG.clear();
        tfAddUsername.clear();
        tfAddLast.clear();
        tfAddFirst.clear();
        tfAddCollege.clear();
        tfAddPassword.clear();
        tfAddAddress.clear();
        cbAddGrade.setValue(null);

        tfUpdateCollege.clear();
        tfUpdateAddress.clear();
        tfUpdateYOG.clear();
        tfUpdateFirst.clear();
        tfUpdateLast.clear();
        tfUpdateUsername.clear();
        tfUpdatePassword.clear();
        cbUpdateGrade.setValue(null);
    }

    public void AddEmployeeBtnClicked(){
        // Check if any of the fields are empty
        if (isAnyOfFieldsEmpty()){
            showAlert(Alert.AlertType.ERROR, "Please fill all the empty fields", "Error");
            return;
        }

        // Confirmation Alert of the Transaction
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to add this Employee?",
                ButtonType.YES, ButtonType.NO);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setTitle("Confirmation");

        // Show confirmation dialog and process response
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            Employee e = createEmployeeFromInput();

            if(e.Save(employees)){
                showAlert(Alert.AlertType.INFORMATION, String.format("New Employee with [%s] ID have been added", e.getID()), "Employee added");
                updateScene();
            }
            else{
                showAlert(Alert.AlertType.ERROR,
                        "Unable to add the employee. Ensure all required fields are filled correctly and try again.",
                        "Error: Employee Not Added");

            }
        }
    }

    private boolean isAnyOfFieldsEmpty() {
        return tfAddAddress.getText().isEmpty() || tfAddCollege.getText().isEmpty() ||
                tfAddFirst.getText().isEmpty() || tfAddLast.getText().isEmpty() ||
                tfAddUsername.getText().isEmpty() || tfAddYOG.getText().isEmpty() ||
                cbAddGrade.getValue() == null;
    }

    private Employee createEmployeeFromInput() {
        Employee e = new Employee();
        e.setUsername(tfAddUsername.getText());
        e.setPassword(tfAddPassword.getText());
        e.setFirstName(tfAddFirst.getText());
        e.setLastName(tfAddLast.getText());
        e.setAddress(tfAddAddress.getText());
        e.setTotalGrade(cbAddGrade.getValue());
        e.setGraduatedCollege(tfAddCollege.getText());
        e.setYearOfGraduation(tfAddYOG.getText());

        if (rbEmployee.isSelected()) {
            e.setID("E" + employees.size() + 101);
            e.setPosition("Employee");
        } else if (rbAdmin.isSelected()) {
            e.setID("A" + employees.size() + 101);
            e.setPosition("Admin");
        }
        return e;
    }

    private boolean ValidateSelection(){
        selectedEmployee = tvEmployees.getSelectionModel().getSelectedItem();

        if(selectedEmployee == null) {
            showAlert(Alert.AlertType.ERROR, "Please Select Employee First", "Error");
            return false;
        }

        return true;
    }

    public void DeleteEmpMenuItemClicked(){
        if (ValidateSelection()){
            if(selectedEmployee.Delete(employees)){
                showAlert(Alert.AlertType.INFORMATION, "Employee has been Deleted Successfully", "Deletion Completed");
            }
            else{
                showAlert(Alert.AlertType.INFORMATION, "Employee hasn't been Deleted", "Deletion Failed");
            }

            updateScene();
        }
    }

    public void UpdateEmpMenuItemSelected(){
        if (ValidateSelection()){
            cbEmployeeID.setValue(selectedEmployee.getID());
            cbEmployeeIDItemChanged();
            tpEmp.getSelectionModel().select(tabUpdateEmp);
        }
    }

    public void cbEmployeeIDItemChanged(){
        clearAllFields();

        selectedEmployee = Employee.Find(employees, cbEmployeeID.getValue());

        if(selectedEmployee != null) {
            tfUpdateAddress.setText(selectedEmployee.getAddress());
            tfUpdateCollege.setText(selectedEmployee.getGraduatedCollege());
            tfUpdateFirst.setText(selectedEmployee.getFirstName());
            tfUpdateLast.setText(selectedEmployee.getLastName());
            tfUpdatePassword.setText(selectedEmployee.getPassword());
            tfUpdateUsername.setText(selectedEmployee.getUsername());
            tfUpdateYOG.setText(selectedEmployee.getYearOfGraduation());
            cbUpdateGrade.getSelectionModel().select(selectedEmployee.getTotalGrade());
        }
    }

    public void btnUpdateEmployeeClicked(){
        if (isAnyOfUpdateFieldEmpty()){
            showAlert(Alert.AlertType.ERROR, "Please fill all the empty fields", "Error");
            return;
        }

        // Confirmation Alert of the Transaction
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to update this Employee?",
                ButtonType.YES, ButtonType.NO);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setTitle("Confirmation");

        // Show confirmation dialog and process response
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            selectedEmployee.setFirstName(tfUpdateFirst.getText());
            selectedEmployee.setLastName(tfUpdateLast.getText());
            selectedEmployee.setUsername(tfUpdateUsername.getText());
            selectedEmployee.setPassword(tfUpdatePassword.getText());
            selectedEmployee.setYearOfGraduation(tfUpdateYOG.getText());
            selectedEmployee.setGraduatedCollege(tfUpdateCollege.getText());
            selectedEmployee.setAddress(tfUpdateAddress.getText());
            selectedEmployee.setTotalGrade(cbUpdateGrade.getValue());

            if(selectedEmployee.Save(employees)){
                showAlert(Alert.AlertType.INFORMATION, String.format("Employee with [%s] ID has been updated", selectedEmployee.getID()), "Employee updated");
                updateScene();
            }
            else{
                showAlert(Alert.AlertType.ERROR,
                        "Unable to update the employee. Ensure all required fields are filled correctly and try again.",
                        "Error: Employee Not Updated");

            }
        }
    }

    private boolean isAnyOfUpdateFieldEmpty(){
        return tfUpdateFirst.getText().isEmpty() || tfUpdateLast.getText().isEmpty() ||
               tfUpdateAddress.getText().isEmpty() || tfUpdateYOG.getText().isEmpty() ||
               tfUpdateUsername.getText().isEmpty() || tfUpdatePassword.getText().isEmpty() ||
               tfUpdateCollege.getText().isEmpty() || cbUpdateGrade.getValue() == null ||
                cbEmployeeID.getValue() == null;

    }
}
