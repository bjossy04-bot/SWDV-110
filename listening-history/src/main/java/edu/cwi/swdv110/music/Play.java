package edu.cwi.swdv110.music;

import java.time.LocalDate;

/** One time a user played a song. */
public record Play(
        String user,
        String song,
        String artist,
        String genre,
        LocalDate date,
        int seconds) {

    public static Play parse(String csvLine) {
        String[] fields = csvLine.split(",", -1);

        if (fields.length != 6) {
            throw new IllegalArgumentException("Expected 6 fields");
        }

        String user = fields[0].trim();
        String song = fields[1].trim();
        String artist = fields[2].trim();
        String genre = fields[3].trim();
        LocalDate date = LocalDate.parse(fields[4].trim());
        int seconds = Integer.parseInt(fields[5].trim());

        return new Play(user, song, artist, genre, date, seconds);
    }
}

