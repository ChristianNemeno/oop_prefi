module com.example.prefi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.prefi to javafx.fxml;
    opens drawing to javafx.fxml;
    opens collision to javafx.fxml;
    opens grading_system to javafx.fxml;
    exports grading_system;
    exports com.example.prefi;
    exports drawing;
    exports collision;

}