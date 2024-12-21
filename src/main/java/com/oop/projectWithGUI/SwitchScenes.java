package com.oop.projectWithGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SwitchScenes {
    private Parent root;
    private Stage stage;
    private Scene scene;
    private double x = 0;
    private double y = 0;

    public void SwitchToHome(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.show();
    }

    public void SwitchToTransaction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Transactions.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Transactions");
        stage.show();
    }

    public void SwitchToAdminHome(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminHome.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.show();
    }

    public void SwitchToAdminClients(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminClients.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Manage Clients");
        stage.show();
    }

    public void SwitchToAdminEmployees(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminEmployees.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Manage Employees");
        stage.show();
    }

    public void SwitchToAdminTransactions(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AdminTransactions.fxml"));

        Stage stage;
        if (event.getSource() instanceof MenuItem) {
            // If the source is a MenuItem, get the window via the menu's owning scene
            stage = (Stage)((MenuItem)event.getSource()).getParentPopup().getOwnerWindow();
        } else {
            // Handle other cases (e.g., Button or Node sources)
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        }

        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Transactions");
        stage.show();
    }

    public void SwitchToEmployee(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("EmployeeManage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToEmployeeHome(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("EmployeeHome.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.show();
    }

    public void SwitchToEmployeeClients(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("EmployeeClients.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Manage Clients");
        stage.show();
    }

    public void SwitchToEmployeeTransactions(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("EmployeeTransactions.fxml"));
        Stage stage;
        if (event.getSource() instanceof MenuItem) {
            // If the source is a MenuItem, get the window via the menu's owning scene
            stage = (Stage)((MenuItem)event.getSource()).getParentPopup().getOwnerWindow();
        } else {
            // Handle other cases (e.g., Button or Node sources)
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        }

        // Set the new scene
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Transactions");
        stage.show();
    }

    public void SwitchToLoan(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("LoanScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Loans");
        stage.show();
    }

    public void SwitchToBankCard(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("BankCard.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToLogin(ActionEvent event) throws IOException {
        CSVController.SaveToCSVFiles();

        root = FXMLLoader.load(getClass().getResource("login-screen.fxml"));
        Stage newStage = new Stage();
        Scene scene = new Scene(root);

        newStage.initStyle(StageStyle.TRANSPARENT); // Set the style before showing
        newStage.setTitle("Login");
        newStage.setScene(scene);
        newStage.show();

        // Close the old stage
        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    public void SwitchToUser(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ClientManage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
