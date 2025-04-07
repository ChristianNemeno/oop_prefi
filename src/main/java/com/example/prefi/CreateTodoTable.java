package com.example.prefi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class CreateTodoTable {

    public static final String URL = "jdbc:mysql://localhost:3306/prefi";
    public static final String USER = "root";
    public static final String PASSWORD = "";


    public static void main(String[] args) {

        //Connection conn = null;
        try(Connection conn =DriverManager.getConnection(URL,USER,PASSWORD)){
            Statement statement = conn.createStatement();

            statement.execute("DROP TABLE IF EXISTS tasks");
            String query = "CREATE TABLE prefi.tasks " +
                    "(task_id INT NOT NULL AUTO_INCREMENT ," +
                    "task varchar(255) NOT NULL , " +
                    "description varchar(255) NOT NULL , " +
                    "deadline DATE, " +
                    "is_completed BOOLEAN DEFAULT FALSE, " +
                    "date_created DATETIME DEFAULT CURRENT_TIMESTAMP, " + // Changed to DATETIME
                    "PRIMARY KEY (task_id))";


            statement.execute(query);

            System.out.println("Tasks table created successful");

        }catch(SQLException e){
            System.err.print(e);
        }
    }
}
