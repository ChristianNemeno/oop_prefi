package drawing;

import com.example.prefi.Todo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

import java.io.IOException;

public class Canvas extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Todo.class.getResource("canvas.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Hello!");





        stage.setScene(scene);

        stage.show();
    }
}
