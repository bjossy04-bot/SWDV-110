package edu.cwi.swdv110.diskusage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Folder to scan (Enter for this project): ");
        String folder = scanner.nextLine().trim();

        Path root;
        
        if (folder.isEmpty()) {
            root = Paths.get(".");
        } else {
            root = Paths.get(folder);
        }

        System.out.println("Scanning:" + root.toAbsolutePath());

        File rootFolder = root.toFile();
        System.out.println(FileScanner.tree(rootFolder, 2));

        int fileCount = FileScanner.countFiles(rootFolder);
        long totalSize = FileScanner.totalSize(rootFolder);
        int deepest = FileScanner.maxDepth(rootFolder);

        System.out.println();
        System.out.println("Files: " + fileCount + "   Total: " + FileScanner.humanSize(totalSize) 
        + "   Deepest nesting: " + deepest + " levels");

        System.out.println();
        System.out.println("Largest files:");

        List<FileInfo> allFiles = FileScanner.listAll(rootFolder);
        List<FileInfo> largest = Algorithms.largest(allFiles, 5);

        for (FileInfo file : largest) {
            System.out.println("  " + FileScanner.humanSize(file.size()) + "  " + file.path());
        }

        System.out.println();
        System.out.println("Files by extension:");

        Map<String, Integer> extensions = FileScanner.countByExtension(rootFolder);
        System.out.println(extensions);

        List<FileInfo> sortedFiles =
        Algorithms.mergeSort(allFiles, Algorithms.BY_PATH);

        String searchPath = "";

        while (!searchPath.equalsIgnoreCase("quit")) {
            System.out.println();
            System.out.print("Find path (or 'race' / 'quit'): ");

            searchPath = scanner.nextLine().trim();

            if (searchPath.equalsIgnoreCase("quit")) {
                break;
            }

            if (searchPath.equalsIgnoreCase("race")) {

                System.out.println("Running sorting race...");

                long startSelection = System.nanoTime();

                List<FileInfo> selectionResult = Algorithms.selectionSort(allFiles, Algorithms.BY_PATH);

                long endSelection = System.nanoTime();

                long startMerge = System.nanoTime();

                List<FileInfo> mergeResult = Algorithms.mergeSort(allFiles, Algorithms.BY_PATH);

                long endMerge = System.nanoTime();

                long selectionTime = endSelection - startSelection;
                long mergeTime = endMerge - startMerge;

                System.out.println("Selection Sort: " + selectionTime + " ns");
                System.out.println("Merge Sort: " + mergeTime + " ns");

                if (selectionTime < mergeTime) {
                    System.out.println("Winner: Selection Sort!");
                } else if (mergeTime < selectionTime) {
                    System.out.println("Winner: Merge Sort!");
                } else {
                    System.out.println("It's a tie!");
                }

                continue;
            }

            int index = Algorithms.binarySearchByPath(sortedFiles, searchPath);

            if (index >= 0) {
                FileInfo found = sortedFiles.get(index);

                System.out.println("Found at index " + index + ": " + FileScanner.humanSize(found.size()));
            } else {
                System.out.println("File not found.");
            }
        }

        scanner.close();
    }
}
