package com.oop.projectWithGUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import static com.oop.projectWithGUI.LoginController.currentEmployee;

public class EmployeeHomeController extends SwitchScenes{

    @FXML
    private Label lblGreeting;

    @FXML
    private Label lbAdd;

    @FXML
    private Label lbID;

    @FXML
    private Label lbFirstName;

    @FXML
    private Label lbLastName;

    @FXML
    private Label lbPosition;

    @FXML
    private Label lbUserName;

    @FXML
    private Label lbGraduatedCollage;

    @FXML
    private Label lbGrade;

    @FXML
    private Label lbYOG;

    @FXML
    private Button btUpdateDetails;

    public void initialize () {
        lblGreeting.setText(String.format("Hi, %s", currentEmployee.getFirstName()));

        setEmpDetails();
    }

    private void setEmpDetails () {
        lbID.setText(currentEmployee.getID());
        lbFirstName.setText(currentEmployee.getFirstName());
        lbLastName.setText(currentEmployee.getLastName());
        lbUserName.setText(currentEmployee.getUsername());
        lbPosition.setText(currentEmployee.getPosition());
        lbAdd.setText(currentEmployee.getAddress());
        lbGraduatedCollage.setText(currentEmployee.getGraduatedCollege());
        lbYOG.setText(currentEmployee.getYearOfGraduation());
        lbGrade.setText(currentEmployee.getTotalGrade());
    }
}
