package edu.cwi.swdv110.music;

import java.time.LocalDate;
import java.util.function.Predicate;

public final class PlayFilters {

    private PlayFilters() {}

    /** Matches plays by this user, ignoring case. */
    public static Predicate<Play> byUser(String user) {
        return play -> play.user().equalsIgnoreCase(user);
    }

    /** Matches plays in this genre, ignoring case. */
    public static Predicate<Play> byGenre(String genre) {
        return play -> play.genre().equalsIgnoreCase(genre);
    }

    /** Matches plays from 'from' through 'to', inclusive. */
    public static Predicate<Play> between(LocalDate from, LocalDate to) {
        return play -> !play.date().isBefore(from) && !play.date().isAfter(to);
    }

    /** A skip is any play shorter than 30 seconds. */
    public static Predicate<Play> skipped() {
        return play -> play.seconds() < 30;
    }
}
