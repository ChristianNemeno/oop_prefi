package grading_system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
        coursecode_col.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        coursename_col.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        grade_col.setCellValueFactory(new PropertyValueFactory<>("grade"));
        String[] csv;
        acc_id track = acc_id.getInstance();

        if(track.getId() > 0){
            user_data usd = db.getter(track.getId());
            tfName.setText(usd.toString());
            csv = usd.toString().trim().split(",");

            ObservableList<student_data> xx = FXCollections.observableArrayList();
            xx = db.studentInterface(csv[0],csv[1]);

            tblView.setItems(xx);
        }


    }


    public void onLogoutClicked(ActionEvent event) {
    }




}
