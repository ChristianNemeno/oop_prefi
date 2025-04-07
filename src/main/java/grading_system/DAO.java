package grading_system;

import com.example.prefi.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;

public class DAO {
    public static final String URL = "jdbc:mysql://localhost:3306/prefinal";
    public static final String USER = "root";
    public static final String PASSWORD = "";


    public int authenticate(String user, String password){
        boolean val = false;
        int id = -1;

        try(Connection conn = DriverManager.getConnection(URL,USER, PASSWORD);
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT id,COUNT(*) FROM user WHERE username =? AND password =?"
            );
        ){

            statement.setString(1,user);
            statement.setString(2,password);

            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                int count = rs.getInt(2);
                if(count > 0){
                     id = rs.getInt("id");
                }

            }
            System.out.println("sad");

        }catch (SQLException e){
            System.err.println(e);
        }

        return id;
    }

    public ObservableList<student_data> studentInterface(String lastName, String firstName){
        ObservableList<student_data> studs = FXCollections.observableArrayList();
        String query = "SELECT " +
                "(SELECT code FROM subject WHERE id = g.subject_id) as coursecode, " +
                "(SELECT name FROM subject WHERE id = g.subject_id) as coursename, " +
                "score as grade FROM grade g " +
                "WHERE g.student_id = " +
                "(SELECT id FROM user WHERE lastname = (?) AND firstname = (?))";
        try(Connection conn = DriverManager.getConnection(URL,USER, PASSWORD);
            PreparedStatement statement = conn.prepareStatement(query)
        ){

            statement.setString(1, lastName);
            statement.setString(2,firstName);

            ResultSet resultSet = statement.executeQuery();


            while(resultSet.next()){
                String c_code = resultSet.getString("coursecode");
                String c_name = resultSet.getString("coursename");
                double grade = resultSet.getInt("grade ");
                studs.add(new student_data(c_code,c_name,grade));
            }



        }catch (SQLException e){
            System.err.println(e);
        }

        return studs;
    }




}
