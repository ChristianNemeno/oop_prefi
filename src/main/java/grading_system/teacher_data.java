package grading_system;


public class teacher_data {
    String courseCode;
    String courseName;
    //Double grade;

    public teacher_data(String courseCode, String courseName) {
        this.courseCode = courseCode;
        this.courseName = courseName;

    }
    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }


}