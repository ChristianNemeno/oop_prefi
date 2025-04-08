package grading_system;

public class acc_id {
    private static acc_id instance;

    private int id;

    private acc_id() {
        id = 0;
    }

    public static acc_id getInstance() {
        if (instance == null) {
            instance = new acc_id();
        }
        return instance;
    }

    public int getId() {
        return id;
    }

    public void setId(int newId) {
        this.id = newId;
    }
}

