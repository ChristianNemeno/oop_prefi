package grading_system;

import com.example.prefi.Todo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class dashboard {
    public TableView<student_data> tblView;
    public TableColumn<student_data, String > coursecode_col;
    public TableColumn<student_data, String> coursename_col;
    public TableColumn<student_data, Double> grade_col;
    public Button logoutBtn;
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
        acc_id.getInstance().setId(0);

        try {
            File ff = new File("creds.txt");
            if(ff.exists()){
                if(ff.delete()){
                    System.out.println("success");
                }else{
                    System.out.println("not deleted file");
                }
            }


            FXMLLoader loader = new FXMLLoader(Todo.class.getResource("login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}
