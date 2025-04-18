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


    public user_data authenticate(String user, String password){
        //int id = -1;
        user_data inLoginData = null;
        try(Connection conn = DriverManager.getConnection(URL,USER, PASSWORD);
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT id,usertype_id FROM user WHERE username =? AND password =?"
            );
        ){

            statement.setString(1,user);
            statement.setString(2,password);

            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                inLoginData = new user_data(rs.getInt("usertype_id"),rs.getInt("id"));


            }

        }catch (SQLException e){
            System.err.println(e);
        }

        return inLoginData;
    }

    public user_data getter(int id){

        user_data usd = null;
        try(Connection conn = DriverManager.getConnection(URL,USER, PASSWORD);
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT firstname, lastname FROM user WHERE id = ?"
            );
        ){

            statement.setInt(1,id);


            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                usd = new user_data(
                        rs.getString("firstname"),
                        rs.getString("lastname")
                        );

            }

        }catch (SQLException e){
            System.err.println(e);
        }
        return usd;
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
                double grade = resultSet.getDouble("grade");
                studs.add(new student_data(c_code,c_name,grade));
            }



        }catch (SQLException e){
            System.err.println(e);
        }

        return studs;
    }



    public ObservableList<teacher_data> teacherInterface(int teacher_id){
        ObservableList<teacher_data> teachs = FXCollections.observableArrayList();
        String query = "SELECT code as coursecode, name as coursename " +
                "FROM subject " +
                "WHERE teacher_id = ?";
        try(Connection conn = DriverManager.getConnection(URL,USER, PASSWORD);
            PreparedStatement statement = conn.prepareStatement(query)
        ){

            statement.setInt(1, teacher_id);


            ResultSet resultSet = statement.executeQuery();


            while(resultSet.next()){
                String c_code = resultSet.getString("coursecode");
                String c_name = resultSet.getString("coursename");

                teachs.add(new teacher_data(c_code,c_name));
            }



        }catch (SQLException e){
            System.err.println(e);
        }

        return teachs;
    }



}
