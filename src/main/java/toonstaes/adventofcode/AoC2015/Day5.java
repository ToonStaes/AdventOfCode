package toonstaes.adventofcode.AoC2015;

import toonstaes.adventofcode.AoCSolver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class Day5 extends AoCSolver {
    public static void part1() throws URISyntaxException, IOException {
        var file = getFile("2015/input_day5.txt");
        var lines = readLines(file);
        var niceCounter = 0;

        for (String line : lines) {
            if (isNiceStringPart1(line)) {
                niceCounter++;
            }
        }

        System.out.println(niceCounter);
    }

    public static void part2() throws URISyntaxException, IOException {
        var file = getFile("2015/input_day5.txt");
        var lines = readLines(file);
        var niceCounter = 0;

        for (String line : lines) {
            if (isNiceStringPart2(line)) {
                niceCounter++;
            }
        }

        System.out.println(niceCounter);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        part1();
        part2();
    }

    public static boolean isNiceStringPart1(String str) {
        return hasAtLeastThreeVowels(str)
                && hasRepeatingLetter(str)
                && doesNotContainForbiddenStrings(str);
    }

    public static boolean isNiceStringPart2(String str) {
        return hasNonOverlappingPair(str) && hasRepeatingLetterWithOneBetween(str);
    }

    private static boolean hasAtLeastThreeVowels(String str) {
        int vowelCount = 0;
        for (char c : str.toCharArray()) {
            if ("aeiou".indexOf(c) >= 0) {
                vowelCount++;
            }
            if (vowelCount >= 3) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasRepeatingLetter(String str) {
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == str.charAt(i - 1)) {
                return true;
            }
        }
        return false;
    }

    private static boolean doesNotContainForbiddenStrings(String str) {
        Set<String> forbidden = Set.of("ab", "cd", "pq", "xy");
        for (String bad : forbidden) {
            if (str.contains(bad)) {
                return false;
            }
        }
        return true;
    }

    private static boolean hasNonOverlappingPair(String str) {
        for (int i = 0; i < str.length() - 1; i++) {
            String pair = str.substring(i, i + 2);
            int nextOccurrence = str.indexOf(pair, i + 2);
            if (nextOccurrence != -1) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasRepeatingLetterWithOneBetween(String str) {
        for (int i = 0; i < str.length() - 2; i++) {
            if (str.charAt(i) == str.charAt(i + 2)) {
                return true;
            }
        }
        return false;
    }
}
