package com.example.prefi;

import javafx.scene.control.ListView;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseHelper {

    public static final String URL = "jdbc:mysql://localhost:3306/prefi";
    public static final String USER = "root";
    public static final String PASSWORD = "";

    public void read(ListView<Task> listView){
        try(Connection conn = DriverManager.getConnection(URL,USER, PASSWORD);
            Statement statement = conn.createStatement()
        ){
            String query = "SELECT * FROM tasks";
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                Task t = new Task(resultSet.getString("task"),
                        resultSet.getString("description"),
                        resultSet.getDate("deadline"),
                        resultSet.getBoolean("is_completed"),
                        resultSet.getDate("date_created")
                        );

                listView.getItems().add(t);
            }

        }catch (SQLException e){
            System.err.println(e);
        }
    }

    public void addTask(String task, String description, Date deadline) {

        // to do insert task
        // task table
        // task | description | deadline | is_completed | date_created
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(
                     "INSERT INTO tasks (task, description, deadline, is_completed, date_created) VALUES (?, ?, ?, ?, ?)"
             )) {
            statement.setString(1, task);
            statement.setString(2, description);
            statement.setDate(3, deadline);
            statement.setBoolean(4, false);
            statement.setDate(5, new Date(System.currentTimeMillis()));


            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Inserted successfully");
            }
        } catch (SQLException e) {
            System.err.println(e);
        }

    }

    public void removeTask(int id){
        try(Connection connection  = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("DELETE FROM tasks WHERE task_id = ?")
        ){
            statement.setInt(1, id);

            int rows = statement.executeUpdate();
            if(rows > 0){
                System.out.println("Succ");
            }else{
                System.out.println("no zuccc");
            }

        }catch (SQLException e){
            System.err.println(e);
        }
    }




}
