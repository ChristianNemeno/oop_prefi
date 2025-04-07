package com.example.prefi;

import java.time.LocalDate;
import java.util.Date;

public class Task {
    String task;
    String description;
    Date deadline;
    Boolean is_completed;
    Date date_created;



    public Task(String task, String description, Date deadline,Boolean is_completed, Date date_created) {
        this.task = task;
        this.description = description;
        this.deadline = deadline;
        this.is_completed = is_completed;
        this.date_created = date_created;
    }

    @Override
    public String toString() {
        return "Task: "+task + "\n\tStatus:" + is_completed + "\n\tdeadline:" + deadline;
    }
}
