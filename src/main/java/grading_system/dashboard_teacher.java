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

public class dashboard_teacher {


    public TableView<teacher_data> tblView;
    public TableColumn<teacher_data, String> coursecode_col;
    public TableColumn<teacher_data, String> coursename_col;
    public TableColumn<teacher_data , Button> grade_col;
    public Button logoutBtn;
    public TextField tfName;

    DAO db = new DAO();

    public void initialize(){
        coursecode_col.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        coursename_col.setCellValueFactory(new PropertyValueFactory<>("courseName"));

        grade_col.setCellFactory(col -> {
            return new TableCell<teacher_data, Button>() {
                private final Button gradeButton = new Button("Grade");

                {
                    gradeButton.setOnAction(event -> {
                        //teacher_data data = getTableView().getItems().get(getIndex());

                    });
                }

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(gradeButton);
                    }
                }
            };
        });

        String[] csv;
        acc_id track = acc_id.getInstance();

        if(track.getId() > 0){
            user_data usd = db.getter(track.getId());
            tfName.setText(usd.toString());
            csv = usd.toString().trim().split(",");

            ObservableList<teacher_data> xx = FXCollections.observableArrayList();
            xx = db.teacherInterface(acc_id.getInstance().getId());

            tblView.setItems(xx);
        }



    }
//
//    SELECT
//    u.lastname,
//    u.firstname,
//    g.score
//            FROM
//    user u
//    INNER JOIN
//    grade g ON u.id = g.student_id
//    INNER JOIN
//    subject s ON g.subject_id = s.id
//            WHERE
//    s.teacher_id = [teacher_id] <- the id
//    AND u.usertype_id = 1
//    ORDER BY
//    u.lastname, u.firstname;











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
