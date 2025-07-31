package model;

import java.time.LocalTime;

public class Task {

    private String title;
    private LocalTime time;

    public Task (String title, LocalTime time) {
        this.time = time;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    public LocalTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "    - [" + time + "] " + title;
    }

}
