package com.oop.projectWithGUI;

import Classes.Client;
import Classes.Employee;
import Classes.User;
import Classes.UserUtility;
import static com.oop.projectWithGUI.CSVController.clients;
import static com.oop.projectWithGUI.CSVController.employees;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {
    
    @FXML
    private Button close;

    @FXML
    public Button login;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label passwordTrials;
    
    private int noOfTrials = 3;
    private Parent root;
    private Stage stage;
    private Scene scene;
    public static Client currentClient;
    public static Employee currentEmployee;

    public void close(){
        System.exit(0);
    }

    private void resetLoginScreen(){
        username.setText("");
        password.setText("");
        noOfTrials = 3;
    }

    private void SwitchToMainMenu() throws IOException {
        login.getScene().getWindow().hide();
        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void SwitchToAdminHome() throws IOException {
        login.getScene().getWindow().hide();
        root = FXMLLoader.load(getClass().getResource("AdminHome.fxml"));
        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void SwitchToEmployeeHome() throws IOException {
        login.getScene().getWindow().hide();
        root = FXMLLoader.load(getClass().getResource("EmployeeHome.fxml"));
        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void login() {
        try {
            Alert alert;

            if(username.getText().isEmpty() || password.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the empty fields");
                alert.showAndWait();
                return;
            }

            ArrayList<User> user = UserUtility.Find(username.getText(), password.getText());
            if (user != null) {
                if(user.getFirst().getID().contains("C")){
                    currentClient = Client.Find(clients, user.getFirst().getID());

                    resetLoginScreen();

                    SwitchToMainMenu();
                }
                else if (user.getFirst().getID().contains("E")){
                    currentEmployee = Employee.Find(employees, user.getFirst().getID());

                    resetLoginScreen();

                    SwitchToEmployeeHome();
                }
                else if(user.getFirst().getID().contains("A")){
                    currentEmployee = Employee.Find(employees, user.getFirst().getID());

                    resetLoginScreen();

                    SwitchToAdminHome();
                }
                System.out.println("Login Success");
                System.out.printf("UserID: %s%n", user.getFirst().getID());

            } else {
                noOfTrials--;
                if (noOfTrials > 0) {
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText(String.format("Invalid Username or Password\nYou have %d Attempt(s) left", noOfTrials));
                    alert.showAndWait();

                    username.clear();
                    password.clear();
                    username.requestFocus();
                } else {
                    resetLoginScreen();

                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Your Account is Locked\nPlease Contact the admin to unlock your account");
                    alert.showAndWait();

                    username.setDisable(true);
                    password.setDisable(true);
                    login.setDisable(true);
                }
            }
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred while login");
            alert.setContentText(ex.getMessage());
            System.out.println(ex.getMessage());
            alert.showAndWait();
        }
    }

}
