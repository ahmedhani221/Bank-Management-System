package com.oop.projectWithGUI;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

import static com.oop.projectWithGUI.LoginController.currentEmployee;

public class EmployeeManageController extends SwitchScenes{

    @FXML
    private Label lbEditEmpID;

    @FXML
    private Label lbEditPosition;

    @FXML
    private Label lbEditUserName;

    @FXML
    private Label lblGreeting;

    @FXML
    private TextField tfEditFname;

    @FXML
    private TextField tfEditLname;

    @FXML
    private TextField tfEditAdd;

    @FXML
    private TextField tfEditCollage;

    @FXML
    private TextField tfEditYOG;

    @FXML
    private ChoiceBox<String> chBoxEditGrade;

    String[] Grades = {"A+", "A", "B+", "B", "C+", "C"};

    public void initialize () {
        lblGreeting.setText(String.format("Hi, %s", currentEmployee.getFirstName()));
        chBoxEditGrade.getItems().addAll(Grades);

        setEditEmpDetails();
    }

    private void setEditEmpDetails() {
        lbEditEmpID.setText(currentEmployee.getID());
        lbEditUserName.setText(currentEmployee.getUsername());
        lbEditPosition.setText(currentEmployee.getPosition());
    }

    public void updateEmpDetails() {
        if (tfEditFname.getText().isEmpty() || tfEditLname.getText().isEmpty() || tfEditAdd.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "You Must Fill All Text Fields!", "Empty Fields");
            return;
        }

        String newFirstName = tfEditFname.getText();
        String newLastName = tfEditLname.getText();
        String newAddress = tfEditAdd.getText();
        String newCollage = tfEditCollage.getText();
        String newYOG = tfEditYOG.getText();
        String newGrade = chBoxEditGrade.getSelectionModel().getSelectedItem();

        currentEmployee.setFirstName(newFirstName);
        currentEmployee.setLastName(newLastName);
        currentEmployee.setAddress(newAddress);
        currentEmployee.setYearOfGraduation(newYOG);
        currentEmployee.setGraduatedCollege(newCollage);
        currentEmployee.setTotalGrade(newGrade);

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to make this Edit ?",
                ButtonType.YES, ButtonType.NO);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setTitle("Confirmation");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            showAlert(Alert.AlertType.INFORMATION, "You Edit Info Successfully :)", "Edit Info");
        }

        initialize();
        resetScreen();
    }

    public void resetScreen() {
        tfEditFname.setText("");
        tfEditLname.setText("");
        tfEditAdd.setText("");
        tfEditCollage.setText("");
        tfEditYOG.setText("");
        chBoxEditGrade.getSelectionModel().select(null);

    }

    private void showAlert(Alert.AlertType alertType, String message, String title) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.showAndWait();
    }
}