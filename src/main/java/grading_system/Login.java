package grading_system;

import com.example.prefi.Todo;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;

public class Login {
    public Button signinBtn;
    public TextField usernameTF;
    public TextField passwordTF;
    public Label usernameLabel;
    public Label passwordLabel;
    public Label errorLabel;
    private user_credentials userData;
    DAO dbAccess = new DAO();




    public void initialize(){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("creds.txt"))){
            userData = (user_credentials) ois.readObject();
            usernameTF.setText(userData.getUsername());
            passwordTF.setText(userData.getPassword());

            javafx.application.Platform.runLater(() -> signinBtn.fire());


        }catch (IOException e){
            System.err.println(e);
            System.out.println("huhu");
        } catch (ClassNotFoundException e) {
            System.out.println("huhu");
            throw new RuntimeException(e);
        }


        errorLabel.setVisible(false);

        usernameTF.textProperty().addListener(((observableValue, string, t1) -> errorLabel.setVisible(false)));

        passwordTF.textProperty().addListener(((observableValue, string, t1) -> errorLabel.setVisible(false)));


    }


    public void switchToDashboard(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(Todo.class.getResource("dashboard.fxml"));
            Parent root = loader.load();


            Stage stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    public void onSigninClicked(ActionEvent event) {
        // authenticate
        // serialized if successful


        String user = usernameTF.getText();
        String password = passwordTF.getText();

        try{
            if(usernameTF.getText().isBlank() || passwordTF.getText().isBlank()){
                throw new IllegalArgumentException();
            }
        }catch (IllegalArgumentException e){
            Alert a = new Alert(Alert.AlertType.ERROR, "username or password cant be blank");
            a.showAndWait();
        }



        int id = dbAccess.authenticate(usernameTF.getText(), passwordTF.getText());

        acc_id track = acc_id.getInstance();
        track.setId(id);

        if(id > -1){
            System.out.println("there exists");
            try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("creds.txt"))){
                userData = new user_credentials(user, password);
                oos.writeObject(userData);

            }catch(IOException e){
                System.err.println(e);
            }

            switchToDashboard(event);
        }else {
            errorLabel.setVisible(true);

        }

    }
}
