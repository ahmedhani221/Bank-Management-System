module com.oop_project.oop_project_withgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.oop.projectWithGUI to javafx.fxml;
    exports com.oop.projectWithGUI;

    opens Classes to javafx.base;
}