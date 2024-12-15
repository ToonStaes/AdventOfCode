package toonstaes.adventofcode.AoC2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class day13_reddit {
    private static List<List<String>> getPatterns(final List<String> input) {
        List<List<String>> patterns = new ArrayList<>();
        List<String> pattern = new ArrayList<>();
        for (String line : input) {
            if (line.isBlank()) {
                patterns.add(pattern);
                pattern = new ArrayList<>();
            } else {
                pattern.add(line);
            }
        }
        patterns.add(pattern);
        return patterns;
    }

    private static String getColumn(int x, final List<String> pattern) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < pattern.size(); y++) {
            String line = pattern.get(y);
            sb.append(line.charAt(x));
        }
        return sb.toString();
    }

    private static int getNrSmudges(String s1, String s2) {
        int total = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                total++;
            }
        }
        return total;
    }

    private static int getNrBeforeReflection(final List<String> pattern, boolean columns) {
        int max = columns ? pattern.get(0).length() : pattern.size();
        for (int i = 1; i < max; i++) {
            String prev = columns ? getColumn(i - 1, pattern) : pattern.get(i - 1);
            String current = columns ? getColumn(i, pattern) : pattern.get(i);
            // First, find a match between two rows/columns.
            if (prev.equals(current)) {
                // Match found, now find out how many rows/columns we need to check.
                int elementsToCheck = Math.min(i - 1, max - i - 1);
                boolean matches = true;
                // Now, just check left&right/up&down from the index we found above. Repeat
                // until we have checked all columns/rows.
                for (int el = 0; el < elementsToCheck; el++) {
                    String first = columns ? getColumn(i - el - 2, pattern) : pattern.get(i - el - 2);
                    String second = columns ? getColumn(i + el + 1, pattern) : pattern.get(i + el + 1);
                    if (!first.equals(second)) {
                        matches = false;
                        break;
                    }
                }
                if (matches) {
                    return i;
                }
            }
        }
        return 0;
    }

    private static int getNrBeforeSmudgeReflection(final List<String> pattern, boolean columns) {
        int max = columns ? pattern.get(0).length() : pattern.size();
        for (int i = 1; i < max; i++) {
            String prev = columns ? getColumn(i - 1, pattern) : pattern.get(i - 1);
            String current = columns ? getColumn(i, pattern) : pattern.get(i);
            // First, find two rows/columns which are the same or are different by one
            // smudge.
            int initialSmudges = getNrSmudges(prev, current);
            if (initialSmudges == 0 || initialSmudges == 1) {
                // Match found, now find out how many rows/columns we need to check.
                int elementsToCheck = Math.min(i - 1, max - i - 1);
                boolean matches = true;
                boolean smudgeFound = false;
                for (int el = 0; el < elementsToCheck; el++) {
                    String first = columns ? getColumn(i - el - 2, pattern) : pattern.get(i - el - 2);
                    String second = columns ? getColumn(i + el + 1, pattern) : pattern.get(i + el + 1);
                    int nrSmudges = getNrSmudges(first, second);
                    // If the nr of smudges is greater than 1, there are too many smudges to
                    // continue.
                    // If we have 1 smudge, but the initial pair of rows also contains a smudge,
                    // there are also too many smudges to continue.
                    // If we have 1 smudge, no smudges in the initial pair but we have already seen
                    // a different pair which has 1 smudge difference, there are also too many
                    // smudges to continue.
                    if (nrSmudges > 1 || (initialSmudges == 1 && nrSmudges == 1) || (nrSmudges == 1 && smudgeFound)) {
                        matches = false;
                        break;
                    } else if (nrSmudges == 1) {
                        // We have found a smudge, count it.
                        smudgeFound = true;
                    }
                }
                // We need to have one smudge, otherwise we are counting the previous reflection
                // lines.
                if (matches && (initialSmudges == 1 ^ smudgeFound)) {
                    return i;
                }
            }
        }
        return 0;

    }
    protected static String runPart2(final List<String> input) {
        return String.valueOf(getPatterns(input).stream()
                .mapToInt(p -> 100 * getNrBeforeSmudgeReflection(p, false) + getNrBeforeSmudgeReflection(p, true))
                .sum());
    }

    protected static String runPart1(final List<String> input) {
        return String.valueOf(getPatterns(input).stream()
                .mapToInt(p -> 100 * getNrBeforeReflection(p, false) + getNrBeforeReflection(p, true)).sum());
    }

    public static void main(String... args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src\\main\\resources\\input_day13.txt"));
//        System.out.println(runPart1(lines));
        System.out.println(runPart2(lines));
    }
}
