package ch.picturedesk.sbbapp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Stop implements Serializable {
    private String departure;

    public LocalDateTime getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        LocalDateTime dateTime = LocalDateTime.parse(departure, formatter);
        return dateTime;
    }
}
