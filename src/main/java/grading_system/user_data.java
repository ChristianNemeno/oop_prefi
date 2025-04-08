package grading_system;

public class user_data {
    String fname;
    String lname;

    public user_data(String fname, String lname) {
        this.fname = fname;
        this.lname = lname;
    }

    @Override
    public String toString() {
        return lname+","+fname;
    }
}
