package edu.cwi.swdv110.music;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class App{
    public static void main(String[] args) throws IOException {
        List<Play> plays = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        plays = Files.lines(Path.of("plays.csv")).skip(1).filter(line -> !line.isBlank()).map(Play::parse).collect(Collectors.toCollection(ArrayList::new));

        int removed = PlayCleaner.clean(plays);
        System.out.println("Loaded " + plays.size() + " plays (" + removed + " bad rows removed)");

        PlayFormatter compact = play -> play.song() + " - " + play.artist();
        PlayFormatter detailed = play -> play.date() + " " + play.user() + " " + play.song() + " " + play.artist() + " " + String.format("%d:%02d", play.seconds() / 60, play.seconds() % 60);
        
        System.out.println("\n=== MUSIC LISTENING REPORT ===");

        System.out.println("\nMinutes per user:");
        MusicStats.secondsPerUser(plays).forEach((user, seconds) -> {
            System.out.println(user + "    " + (seconds / 60) + " min");
        });

        System.out.println("\nPlays Per Genre:");
        MusicStats.playsPerGenre(plays).forEach((genre, count) -> {
             System.out.println(genre + "   " + "#".repeat(count.intValue()));
        });

        System.out.println("\nPlays by day:");
        MusicStats.playsByDay(plays).forEach((day, count) -> {
        System.out.println(day + " " + count);
        });

        System.out.println("\nTop 3 Artists:");
        MusicStats.topArtists(plays, 3).forEach(System.out::println);

        System.out.println("\nLongest Play:");
        MusicStats.longestPlay(plays).ifPresent(play -> System.out.println(compact.format(play)));

        System.out.println("\nMost recent:");

        MusicStats.recent(plays, 5).forEach(play -> {
        System.out.println("" + detailed.format(play));
        });

        while (true) {
            System.out.println("\nUser to summarize (or 'quit'): ");
            String user = scanner.nextLine().trim();

            if (user.equalsIgnoreCase("quit")) {
                break;
            }

            long totalSeconds = plays.stream().filter(play -> play.user().equalsIgnoreCase(user)).mapToLong(play -> play.seconds()).sum();
            
            boolean userExists = plays.stream().anyMatch(play -> play.user().equalsIgnoreCase(user));

            long playCount = plays.stream().filter(play -> play.user().equalsIgnoreCase(user)).count();
            
            String topGenre = plays.stream().filter(play -> play.user().equalsIgnoreCase(user)).collect(Collectors.groupingBy(Play::genre, Collectors.counting())).entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("None");
            
            long skippedPlays = plays.stream().filter(play -> play.user().equalsIgnoreCase(user)).filter(PlayFilters.skipped()).count();

            long skipRate = playCount == 0 ? 0 : Math.round(skippedPlays * 100.0 / playCount);

            long indieSeconds = plays.stream().filter(play -> play.user().equalsIgnoreCase(user)).filter(play -> play.genre().equalsIgnoreCase("Indie")).mapToLong(Play::seconds).sum();

            String fullListens = plays.stream().filter(play -> play.user().equalsIgnoreCase(user)).filter(PlayFilters.skipped().negate()).map(play -> play.song() + " - " + play.artist()).distinct().sorted().collect(Collectors.joining("; "));

            if (userExists) {
                System.out.println(user + ": " + playCount + " plays, " + (totalSeconds / 60) + " min, top genre " + topGenre + ", skip rate " + skipRate + "%");
                System.out.println("Indie minutes: " + (indieSeconds / 60));
                System.out.println("Full listens: " + fullListens);
            } else {
                System.out.println("User not found: " + user);
            }
        }
    }
}
