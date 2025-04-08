package grading_system;

import com.mysql.cj.conf.StringProperty;
import javafx.beans.property.DoubleProperty;

public class student_data {
    String courseCode;
    String courseName;
    Double grade;

    public student_data(String courseCode, String courseName, Double grade) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.grade = grade;
    }
    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public Double getGrade() {
        return grade;
    }
}
