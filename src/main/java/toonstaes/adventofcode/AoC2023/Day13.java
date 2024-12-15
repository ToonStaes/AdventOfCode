package toonstaes.adventofcode.AoC2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day13 {

    public static void day13_part1() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src\\main\\resources\\input_day13.txt"));
        Map<Long, List<String>> patternsMap = new HashMap<>();
        long patternKey = 0;
        int itr = 0;
        List<String> pattern = new ArrayList<>();
        long sum = 0L;

        // Get Patterns
        for (String line : lines) {
            if (line.equals("")) {
                patternsMap.put(patternKey, pattern);
                pattern = new ArrayList<>();
                patternKey++;
            } else {
                pattern.add(line);
            }
            if (itr == lines.size() - 1) {
                patternsMap.put(patternKey, pattern);
            }
            itr++;
        }

        // Get scores for pattern
        for (List<String> rows : patternsMap.values()) {
            // Get lineScore
            long mirrorAfterLineIndex = -1L;
            for (int i = 0; i < rows.size() - 1; i++) {
                var thisLine = rows.get(i);
                var nextLine = rows.get(i + 1);
                if (thisLine.equals(nextLine)) {
                    boolean hasRowMirror = hasMirror(i, i + 1, rows);
                    if (hasRowMirror) {
                        mirrorAfterLineIndex = i;
                    }
                }
            }

            if (mirrorAfterLineIndex > -1) {
                sum += (mirrorAfterLineIndex + 1) * 100; // plus one because indexes start 0;
            } else {
                // Get columnScore
                long mirrorAfterColumnIndex = -1L;
                List<String> columns = rowsToColumns(rows);

                for (int i = 0; i < columns.size() - 1; i++) {
                    var thisLine = columns.get(i);
                    var nextLine = columns.get(i + 1);
                    if (thisLine.equals(nextLine)) {
                        boolean hasColumnMirror = hasMirror(i, i + 1, columns);
                        if (hasColumnMirror) {
                            mirrorAfterColumnIndex = (long) i;
                        }
                    }
                }

                if (mirrorAfterColumnIndex > -1) {
                    sum += (mirrorAfterColumnIndex + 1); // plus one because indexes start 0;
                }
            }
        }

        System.out.println("total score: " + sum);
    }

    public static void day13_part2() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src\\main\\resources\\input_day13.txt"));
        Map<Long, List<String>> patternsMap = new HashMap<>();
        long patternKey = 0;
        int itr = 0;
        List<String> pattern = new ArrayList<>();
        long sum = 0L;

        // Get Patterns
        for (String line : lines) {
            if (line.equals("")) {
                patternsMap.put(patternKey, pattern);
                pattern = new ArrayList<>();
                patternKey++;
            } else {
                pattern.add(line);
            }
            if (itr == lines.size() - 1) {
                patternsMap.put(patternKey, pattern);
            }
            itr++;
        }

        // Get scores for pattern
        for (List<String> rows : patternsMap.values()) {
            // Get lineScore
            long mirrorAfterLineIndex = -1L;
            for (int i = 0; i < rows.size() - 1; i++) {
                var thisLine = rows.get(i);
                var nextLine = rows.get(i + 1);
                boolean hasRowMirror = hasMirrorWithSmudge(i, i + 1, rows);
                if (hasRowMirror) {
                    mirrorAfterLineIndex = i;
                    break;
                } else {
                    List<Integer> indexesToSwap = new ArrayList<>();
                    for (int j = 0; j < thisLine.length(); j++) {
                        if (thisLine.charAt(j) != nextLine.charAt(j)) {
                            indexesToSwap.add(j);
                        }
                    }
                    if (indexesToSwap.size() == 1) {
                        thisLine = replaceSmudge(thisLine, indexesToSwap.get(0));
                        hasRowMirror = hasMirror(i, i + 1, rows);
                        if (hasRowMirror) {
                            mirrorAfterLineIndex = i;
                        } else {
                            thisLine = replaceSmudge(thisLine, indexesToSwap.get(0));
                            nextLine = replaceSmudge(nextLine, indexesToSwap.get(0));
                            if (thisLine.equals(nextLine)) {
                                hasRowMirror = hasMirror(i, i + 1, rows);
                                if (hasRowMirror) {
                                    mirrorAfterLineIndex = i;
                                }
                            }
                        }
                    }
                }
            }

            if (mirrorAfterLineIndex > -1) {
                sum += (mirrorAfterLineIndex + 1) * 100; // plus one because indexes start 0;
            } else {
                // Get columnScore
                long mirrorAfterColumnIndex = -1L;
                List<String> columns = rowsToColumns(rows);

                for (int i = 0; i < columns.size() - 1; i++) {
                    var thisColumn = columns.get(i);
                    var nextColumn = columns.get(i + 1);
                    boolean hasColumnMirror = hasMirrorWithSmudge(i, i + 1, columns);
                    if (hasColumnMirror) {
                        mirrorAfterColumnIndex = i;
                    } else {
                        List<Integer> indexesToSwap = new ArrayList<>();
                        for (int j = 0; j < thisColumn.length(); j++) {
                            if (thisColumn.charAt(j) != nextColumn.charAt(j)) {
                                indexesToSwap.add(j);
                            }
                        }
                        if (indexesToSwap.size() == 1) {
                            thisColumn = replaceSmudge(thisColumn, indexesToSwap.get(0));
                            hasColumnMirror = hasMirrorWithSmudge(i, i + 1, rows);
                            if (hasColumnMirror) {
                                mirrorAfterColumnIndex = i;
                            } else {
                                thisColumn = replaceSmudge(thisColumn, indexesToSwap.get(0));
                                nextColumn = replaceSmudge(nextColumn, indexesToSwap.get(0));
                                if (thisColumn.equals(nextColumn)) {
                                    hasColumnMirror = hasMirror(i, i + 1, rows);
                                    if (hasColumnMirror) {
                                        mirrorAfterColumnIndex = i;
                                    }
                                }

                            }
                        }
                    }
                    if (mirrorAfterColumnIndex > -1) {
                        break;
                    }
                }

                if (mirrorAfterColumnIndex > -1) {
                    sum += (mirrorAfterColumnIndex + 1); // plus one because indexes start 0;
                }
            }
        }

        System.out.println("total score: " + sum);
    }

    private static boolean hasMirror(int possibleMirrorIndexTop, int possibleMirrorIndexBottom, List<String> rows) {
        var rowsSize = rows.size();
        boolean isDuplicate = true;
        for (int i = 1; i <= possibleMirrorIndexTop && i + possibleMirrorIndexBottom < rowsSize; i++) {
            var belowMirror = rows.get(possibleMirrorIndexBottom + i);
            var aboveMirror = rows.get(possibleMirrorIndexTop - i);
            if (!belowMirror.equals(aboveMirror)) {
                isDuplicate = false;
                break;
            }
        }

        return isDuplicate;
    }

    private static boolean hasMirrorWithSmudge(int possibleMirrorIndexTop, int possibleMirrorIndexBottom, List<String> rows) {
        var rowsSize = rows.size();
        boolean isDuplicate = true;
        var replaceCounter = 0;
        for (int i = 1; i <= possibleMirrorIndexTop && i + possibleMirrorIndexBottom < rowsSize; i++) {
            List<Integer> indexesToSwap = new ArrayList<>();
            var belowMirror = rows.get(possibleMirrorIndexBottom + i);
            var aboveMirror = rows.get(possibleMirrorIndexTop - i);

            for (int j = 0; j < belowMirror.length(); j++) {
                if (belowMirror.charAt(j) != aboveMirror.charAt(j)) {
                    indexesToSwap.add(j);
                }
            }
            if (indexesToSwap.size() == 1 && replaceCounter == 0) {
                belowMirror = replaceSmudge(belowMirror, indexesToSwap.get(0));
                if (!belowMirror.equals(aboveMirror)) {
                    belowMirror = replaceSmudge(belowMirror, indexesToSwap.get(0));
                    aboveMirror = replaceSmudge(aboveMirror, indexesToSwap.get(0));
                    if (!belowMirror.equals(aboveMirror)) {
                        isDuplicate = false;
                    }
                }
                replaceCounter++;
            }
        }

        return isDuplicate;
    }

    private static String replaceSmudge(String line, int indexToReplace) {
        char[] charArray = line.toCharArray();
        char characterToSwap = charArray[indexToReplace];
        if (characterToSwap == '.') {
            charArray[indexToReplace] = '#';
        } else {
            charArray[indexToReplace] = '.';
        }
        line = new String(charArray);
        return line;
    }

    private static List<String> rowsToColumns(List<String> rows) {
        List<String> columns = new ArrayList<>();

        if (rows.isEmpty()) {
            return columns; // No rows to transpose
        }

        int numRows = rows.size();
        int numCols = rows.get(0).length(); // Assuming all rows have the same length

        for (int col = 0; col < numCols; col++) {
            StringBuilder columnBuilder = new StringBuilder();
            for (int row = 0; row < numRows; row++) {
                char ch = rows.get(row).charAt(col);
                columnBuilder.append(ch);
            }
            columns.add(columnBuilder.toString());
        }

        return columns;
    }
}
