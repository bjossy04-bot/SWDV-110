package edu.cwi.swdv110.music;

import java.time.DayOfWeek;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public final class MusicStats {
    private MusicStats() {}

    public static final Comparator<Play> NEWEST_FIRST = Comparator.comparing(Play::date).reversed().thenComparing(Play::artist).thenComparing(Play::song);
    public static long totalMinutes(List<Play> plays, Predicate<Play> filter) {
        return plays.stream().filter(filter).mapToLong(Play::seconds).sum() / 60;
    }

    public static Map<String, Long> playsPerGenre(List<Play> plays) {
        return plays.stream().collect(Collectors.groupingBy(play -> play.genre().trim(), TreeMap::new, Collectors.counting()));
    }

    public static Map<String, Integer> secondsPerUser(List<Play> plays) {
    return plays.stream()
        .collect(Collectors.groupingBy(
            Play::user,
            LinkedHashMap::new,
            Collectors.summingInt(Play::seconds)
        ));
}


    public static List<String> topArtists(List<Play> plays, int n) {
    return plays.stream().collect(Collectors.groupingBy(Play::artist,Collectors.counting())).entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey())).limit(n).map(Map.Entry::getKey).toList();
    }
    
    public static Optional<Play> longestPlay(List<Play> plays) {
        return plays.stream().max(Comparator.comparingInt(Play::seconds));
    }

    public static List<String> songsBy(List<Play> plays, String artist) {
        return plays.stream().filter(play -> play.artist().equalsIgnoreCase(artist)).map(Play::song).distinct().sorted().toList();
    }

    public static List<Play> recent(List<Play> plays, int n) {
        return plays.stream().sorted(NEWEST_FIRST).limit(n).toList();
    }

    public static Map<DayOfWeek, Long> playsByDay(List<Play> plays) {
        return plays.stream().collect(Collectors.groupingBy(play -> play.date().getDayOfWeek(), TreeMap::new, Collectors.counting()));
    }

    public static double skipRate(List<Play> plays, String user) {
        long total = plays.stream().filter(PlayFilters.byUser(user)).count();

        if (total == 0) {
            return 0.0;
        }

        long skipped = plays.stream().filter(PlayFilters.byUser(user)).filter(PlayFilters.skipped()).count();
        
        return (double) skipped / total;
    }

    public static Optional<String> topGenre(List<Play> plays, String user) {
        return plays.stream().filter(PlayFilters.byUser(user)).collect(Collectors.groupingBy(Play::genre, Collectors.counting())).entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey())).map(Map.Entry::getKey).findFirst();
    }
}

