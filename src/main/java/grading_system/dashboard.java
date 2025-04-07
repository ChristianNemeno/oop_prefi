package grading_system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class dashboard {
    public TableView<student_data> tblView;
    public TableColumn<student_data, String > coursecode_col;
    public TableColumn<student_data, String> coursename_col;
    public TableColumn<student_data, Double> grade_col;
    public Button logoutBtn;
    public Button okBtn;
    public TextField tfName;


    DAO db = new DAO();

    public void initialize(){
        tfName.setEditable(false);

        tfName.setText("Sad");


        String[] csv;
        String out = tfName.getText();
        csv = out.trim().split(",");

        ObservableList<student_data> xx = FXCollections.observableArrayList();
        xx = db.studentInterface(csv[0],csv[1]);

        tblView.setItems(xx);
    }


    public void onLogoutClicked(ActionEvent event) {
    }


}
