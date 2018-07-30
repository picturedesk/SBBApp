package ch.picturedesk.sbbapp;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Train implements Serializable {

    private String name;
    private String to;
    private Stop stop;

    public String getName() {
        return name;
    }

    public String getTo() {
        return to;
    }
;
    public String getDeparture() {
        LocalDateTime date = stop.getDate();
        String departure = String.valueOf(date.getHour()) + ":" + String.valueOf(date.getMinute());
        return departure;
    }
}
