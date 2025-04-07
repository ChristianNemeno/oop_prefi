package com.example.prefi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;

public class TodoController {

    public TextField tfDescription;
    public TextField tfTask;
    public javafx.scene.control.DatePicker DatePicker;
    public ListView<Task> myList;


    DatabaseHelper data = new DatabaseHelper();

    public void initialize(){
        data.read(myList);
    }



    public void onAddTaskClick(ActionEvent event) {
        try{
            String task = tfTask.getText();
            String descript = tfDescription.getText();
            Date date = Date.valueOf(DatePicker.getValue());

            data.addTask(task,descript,date);
            myList.getItems().clear();
            data.read(myList);
        }catch(Exception e){
            System.err.println(e);
        }
    }

    public void onDeleteTaskClick(ActionEvent event) {


    }

    public void onUpdateTaskClick(ActionEvent event) {
    }
}