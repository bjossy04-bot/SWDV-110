package edu.cwi.swdv110.music;

import java.util.List;
import java.util.function.UnaryOperator;

public final class PlayCleaner {
    private PlayCleaner() {}

    public static String titleCase(String text) {
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;

        for (char c : text.trim().toLowerCase().toCharArray()) {
            if (capitalizeNext && Character.isLetter(c)) {
                result.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                result.append(c);
            }

            if (c == '-' || c == ' ') {
                capitalizeNext = true;
            }
        }

        return result.toString();
    }

    public static int clean(List<Play> plays) {
        int originalSize = plays.size();

        plays.removeIf(play -> play.seconds() <= 0 || play.song().isBlank());
        UnaryOperator<Play> formatter = play -> new Play(play.user().toLowerCase(), play.song(), play.artist(), titleCase(play.genre()), play.date(), play.seconds());
        plays.replaceAll(formatter);
        return originalSize - plays.size();
    }
}
