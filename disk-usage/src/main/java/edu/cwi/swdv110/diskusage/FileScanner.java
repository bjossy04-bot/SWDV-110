package edu.cwi.swdv110.diskusage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FileScanner {
    // Base case: If this is a file, return it's size.
    // Recursion case: If this is a filder, add the size of it's children.
    public static long totalSize(File f) {
        if (f.isFile()) {
            return f.length();
        }

        long total = 0;

        File[] children = f.listFiles();

        if (children != null) {
            for (File child : children) {
                total += totalSize(child);
            }
        }

        return total;
    }

    // Base case: If this is a file, count it as 1.
    // Recursion case: If this is a folder, count the files in its children.
    public static int countFiles(File f) {
        if (f.isFile()) {
            return 1;
        }

        int count = 0;

        File[] children = f.listFiles();

        if (children != null) {
            for (File child : children) {
                count += countFiles(child);
            }
        }

        return count;
    }

    // Base case: If this is a file or an empty folder, return 0.
    // Recursion case: Find the deepest child folder and add 1.
    public static int maxDepth(File f) {
        if (f.isFile()) {
            return 0;
        }

        File[] children = f.listFiles();

        if (children == null || children.length == 0) {
            return 0;
        }

        int max = 0;

        for (File child : children) {
            int depth = 1 + maxDepth(child);
            max = Math.max(max, depth);
        }

        return max;
    }

    public static List<FileInfo>listAll(File root) {
        List<FileInfo> files = new ArrayList<>();

        // Start the recursion search
        listAllHelper(root, "", files);
        
        return files;
    }

    // Base case: If this is a file, add it's path and size.
    // Recursion case: Search each child inside a folder.
    private static void listAllHelper(File f, String prefix, List<FileInfo> files) {
        if (f.isFile()) {
            files.add(new FileInfo(prefix, f.length()));
            return;
        }

        File[] children = sortedChildren(f);

        if (children != null) {
            for (File child : children) {
                String path = prefix.isEmpty() ? child.getName() : prefix + "/" + child.getName();
                listAllHelper(child, path, files);
            }
        }
    }

    private static File[] sortedChildren(File folder) {
        File[] children = folder.listFiles();

        if (children == null) {
            return new File[0];
        }

        Arrays.sort(children, Comparator.comparing(File::getName));

        return children;
    }

    // Find files with a specific extension
    public static List<FileInfo> findByExtension(File root, String extension) {
    List<FileInfo> matches = new ArrayList<>();

    for (FileInfo file : listAll(root)) {
        if (file.path().toLowerCase().endsWith("." + extension.toLowerCase())) {
                matches.add(file);
            }
        }

        return matches;
    }

    public static String humanSize(long bytes) {
        return humanSizeHelper((double)bytes, 0);
    }

    private static String humanSizeHelper(double size, int unit) {
        String[] units = {"B", "KB", "MB", "GB", "TB"};

        // Base case: The size is small enough to display.
        if (size < 1024 || unit == units.length - 1) {
            if (unit == 0) {
                return String.format("%.0f B", size);
            }
            return String.format(java.util.Locale.US, "%.1f %s", size, units[unit]);
        }

        // Recursion case: Divide by 1024 and move to the next unit.
        return humanSizeHelper(size / 1024, unit + 1);
    }

    public static String tree(File root, int maxDepth) {
        StringBuilder result = new StringBuilder();
        treeHelper(root, 0, maxDepth, result);
        return result.toString();
    }

    // Base case: Print the current file or folder.
    // Recursion case: Visit children until maxDepth.
    private static void treeHelper(File f, int depth, int maxDepth, StringBuilder result) {
        result.append("  ".repeat(depth)).append(f.getName());

        if (f.isDirectory()) {
            result.append("/");
        }

        result.append(" (").append(humanSize(totalSize(f))).append(")").append(System.lineSeparator());

        if (f.isDirectory() && depth < maxDepth) {
            for (File child : sortedChildren(f)) {
                treeHelper(child, depth + 1, maxDepth, result);
            }
        }
    }

    public static Map<String, Integer> countByExtension(File root) {
        Map<String, Integer> counts = new TreeMap<>();

        List<FileInfo> files = listAll(root);

        for (FileInfo file : files) {
            String path = file.path();
            int dot = path.lastIndexOf('.');

            if (dot != -1) {
                String extension = path.substring(dot + 1).toLowerCase();
                counts.put(extension, counts.getOrDefault(extension, 0) + 1);
            }
        }

        return counts;
    }
}
