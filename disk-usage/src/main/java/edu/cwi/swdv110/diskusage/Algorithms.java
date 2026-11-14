package edu.cwi.swdv110.diskusage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Algorithms {
    public static final Comparator<FileInfo> BY_PATH = Comparator.comparing(FileInfo::path);
    public static <T> List<T> selectionSort(List<T> input, Comparator<? super T> comparator) {

        // Copy the list so the original is not modified.
        List<T> sorted = new ArrayList<>(input);

        for (int i = 0; i < sorted.size() - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < sorted.size(); j++) {
                if (comparator.compare(sorted.get(j), sorted.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }

            T temp = sorted.get(i);
            sorted.set(i, sorted.get(minIndex));
            sorted.set(minIndex, temp);
        }

        return sorted;
    }

    public static <T> List<T> mergeSort(
        List<T> input, Comparator<? super T> comparator) {

        // Base case: A list with 0 or 1 items is sorted.
        if (input.size() <= 1) {
            return new ArrayList<>(input);
        }

        // Split the list into two halves.
        int middle = input.size() / 2;

        List<T> left = mergeSort(input.subList(0, middle), comparator);

        List<T> right = mergeSort(input.subList(middle, input.size()), comparator);

        // Merge the two sorted halves.
        List<T> result = new ArrayList<>();

        int i = 0;
        int j = 0;

        while (i < left.size() && j < right.size()) {
            if (comparator.compare(left.get(i), right.get(j)) <= 0) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }

        while (i < left.size()) {
            result.add(left.get(i++));
        }

        while (j < right.size()) {
            result.add(right.get(j++));
        }

        return result;
    }

    public static List<FileInfo>largest(List<FileInfo> files, int n) {
        List<FileInfo> sorted = mergeSort(files, Comparator.comparingLong(FileInfo::size).reversed());
        return new ArrayList<>(sorted.subList(0, Math.min(n, sorted.size())));
    }

    public static int binarySearchByPath(
        List<FileInfo> files, String target) {

        int left = 0;
        int right = files.size() - 1;

        while (left <= right) {
            int middle = left + (right - left) / 2;

            int comparison = files.get(middle).path().compareTo(target);

            if (comparison == 0) {
                return middle;
            } else if (comparison < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        return -1;
    }
}
