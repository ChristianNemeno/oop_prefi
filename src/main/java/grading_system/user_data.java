package grading_system;

public class user_data {
    String fname;
    String lname;
    int usertype;
    int id;

    public user_data(int usertype, int id) {
        this.usertype = usertype;
        this.id = id;
    }

    public user_data(String fname, String lname, int usertype, int id) {
        this.fname = fname;
        this.lname = lname;
        this.usertype = usertype;
        this.id = id;
    }

    public user_data(String fname, String lname, int usertype) {
        this.fname = fname;
        this.lname = lname;
        this.usertype = usertype;
    }

    public user_data(String fname, String lname) {
        this.fname = fname;
        this.lname = lname;
    }

    @Override
    public String toString() {
        return lname+","+fname;
    }
}
