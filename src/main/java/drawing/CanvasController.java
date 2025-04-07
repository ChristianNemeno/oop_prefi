package drawing;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class CanvasController {

    Node selectedNode = null;

    ArrayList<Tracker> track;


    public AnchorPane apPane;

    final boolean[] wasDragged = new boolean[1];

    Polyline currentline;

    final boolean[] isDragging = new boolean[1];


    public void initialize() {
        load();
        setUpMoveable();
        setUpDrawing();

        Polyline line = new Polyline(1,5,6,7,8,8, 125,154,102,52,56,75,42);
        apPane.getChildren().add(line);


    }
    public void setUpMoveable(){
        apPane.sceneProperty().addListener((obs, oldScene, newScene) ->{
            if(newScene != null){
                newScene.setOnKeyPressed(keyEvent -> {
                    if(selectedNode != null){
                        switch(keyEvent.getCode()){
                            case UP:
                                selectedNode.setTranslateY(selectedNode.getTranslateY() - 10);
                                break;
                            case DOWN:
                                selectedNode.setTranslateY(selectedNode.getTranslateY() + 10);
                                break;
                            case LEFT:
                                selectedNode.setTranslateX(selectedNode.getTranslateX() - 10);
                                break;
                            case RIGHT:
                                selectedNode.setTranslateX(selectedNode.getTranslateX() + 10);
                                break;
                        }
                        keyEvent.consume();
                    }

                });
            }
        });
    }

    public void setUpDrawing(){
        apPane.setOnMousePressed(mouseEvent -> {
            if(selectedNode == null){
                currentline = new Polyline();
                currentline.setStroke(Color.BLACK);
                currentline.setStrokeWidth(2.0);
                currentline.getPoints().addAll(mouseEvent.getX(), mouseEvent.getY());
                apPane.getChildren().addAll(currentline);
            }

        });

        apPane.setOnMouseDragged(mouseEvent -> {
            if(currentline != null && selectedNode == null){
                currentline.getPoints().addAll(mouseEvent.getX(), mouseEvent.getY());
            }
        });

        apPane.setOnMouseReleased(mouseEvent -> {
            if(currentline != null && selectedNode == null){
                currentline = null;
            }
        });
    }

    private void makeNodeDraggable(StackPane node) {
        final double[] mouseAnchorX = new double[1];
        final double[] mouseAnchorY = new double[1];

        node.setOnMousePressed((MouseEvent mouseEvent) -> {
            mouseAnchorX[0] = mouseEvent.getX();
            mouseAnchorY[0] = mouseEvent.getY();
            isDragging[0] = false;
            mouseEvent.consume();
        });

        node.setOnMouseDragged((MouseEvent mouseEvent) -> {
            isDragging[0] = true;
            double newX = node.getLayoutX() + (mouseEvent.getX() - mouseAnchorX[0]);
            double newY = node.getLayoutY() + (mouseEvent.getY() - mouseAnchorY[0]);

            double nodeWidth = node.getBoundsInLocal().getWidth();
            double nodeHeight = node.getBoundsInLocal().getHeight();
            double parentWidth = apPane.getWidth();
            double parentHeight = apPane.getHeight();

            newX = Math.max(0, Math.min(parentWidth - nodeWidth, newX));
            newY = Math.max(0, Math.min(parentHeight - nodeHeight, newY));


            node.setLayoutX(newX);
            node.setLayoutY(newY);
            node.toFront();

            mouseEvent.consume();
        });

        node.setOnMouseReleased(mouseEvent -> {
            if(!isDragging[0]){
                moveable(node);
            }
        });



    }

    public void moveable(StackPane node){
        node.setOnMouseClicked(mouseEvent -> {
            if(selectedNode != null){
                Shape oldShape = (Shape)((StackPane)selectedNode).getChildren().get(0);
                oldShape.setFill(Color.LIGHTBLUE);
            }

            if(selectedNode == node){
                Shape oldShape = (Shape)((StackPane)selectedNode).getChildren().get(0);
                oldShape.setFill(Color.LIGHTBLUE);
                selectedNode = null;

            }else{
                selectedNode = node;
                Shape shape = (Shape)((StackPane)selectedNode).getChildren().get(0);
                shape.setFill(Color.RED);
                node.toFront();
                apPane.requestFocus();


            }
            mouseEvent.consume();




        });


    }

    public void addRectangle(ActionEvent event) {
        final String[] input = new String[1];
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Rectangle Name");
        dialog.setHeaderText("Enter a name for the rectangle:");
        dialog.showAndWait().ifPresent(new Consumer<String>() {
            @Override
            public void accept(String string) {
                input[0] = string;
            }
        });

        StackPane sp = new StackPane();
        Rectangle r = new Rectangle();
        r.setWidth(120);
        r.setHeight(50);
        r.setFill(Color.LIGHTBLUE);

        Text t = new Text();
        t.setText(input[0] != null ? input[0] : "Rectangle");

        sp.getChildren().addAll(r, t);
        sp.setLayoutX(50);  // Initial position
        sp.setLayoutY(50);

        makeNodeDraggable(sp);  // Apply dragging
        apPane.getChildren().add(sp);
    }

    public void addCircle(ActionEvent event) {
        final String[] input = new String[1];
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Circle Name");
        dialog.setHeaderText("Enter a name for the circle:");
        dialog.showAndWait().ifPresent(new Consumer<String>() {
            @Override
            public void accept(String string) {
                input[0] = string;
            }
        });

        StackPane sp = new StackPane();
        Circle c = new Circle();
        c.setRadius(30);
        c.setFill(Color.LIGHTBLUE);

        Text t = new Text();
        t.setText(input[0] != null ? input[0] : "Circle");

        sp.getChildren().addAll(c, t);
        sp.setLayoutX(Math.random() * 400);
        sp.setLayoutY(Math.random() * 600);

        //moveable(sp);
        makeNodeDraggable(sp);  // Apply dragging
        apPane.getChildren().add(sp);
    }

    public void onClickSave(ActionEvent event) {
        track = new ArrayList<>();
        for (Node n : apPane.getChildren()) {
            if (n instanceof StackPane) {
                double x = n.getLayoutX();
                double y = n.getLayoutY();
                Text text = (Text) ((StackPane) n).getChildren().get(1);
                String textContent = text.getText();

                Shape shape = (Shape) ((StackPane) n).getChildren().get(0);
                String shapeType = (shape instanceof Rectangle) ? "Rectangle" : "Circle";

                track.add(new Tracker(x, y, textContent, shapeType));
            }
            if(n instanceof Polyline){

            }
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("canvas.txt"))) {
            oos.writeObject(track);
            System.out.println("Serialized");
        } catch (IOException e) {
            System.err.println("Save failed: " + e.getMessage());
        }
    }

    public void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("canvas.txt"))) {
            track = (ArrayList<Tracker>) ois.readObject();

            for (Tracker t : track) {
                StackPane sp = new StackPane();
                Shape shape = null;

                switch (t.getShape()) {
                    case "Rectangle":
                        shape = new Rectangle();
                        ((Rectangle) shape).setWidth(120);
                        ((Rectangle) shape).setHeight(50);
                        break;
                    case "Circle":
                        shape = new Circle();
                        ((Circle) shape).setRadius(30);
                        break;
                }

                if (shape != null) {
                    shape.setFill(Color.LIGHTBLUE);
                    Text text = new Text();
                    text.setText(t.getText());

                    sp.getChildren().addAll(shape, text);
                    sp.setLayoutX(t.getX());
                    sp.setLayoutY(t.getY());

                    makeNodeDraggable(sp);  // Apply dragging
                    //moveable(sp);
                    apPane.getChildren().add(sp);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Load failed: " + e.getMessage());
        }
    }
}