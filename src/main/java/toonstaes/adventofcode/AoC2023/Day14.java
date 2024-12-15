package toonstaes.adventofcode.AoC2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day14 {
    public static void day14_part1() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src\\main\\resources\\input_day14.txt"));
        List<String> columns = rowsToColumns(lines);
        var maxWeight = lines.size();
        var totalWeight = 0;
        for (String column : columns) {
            int weight = maxWeight;
            var charactersDone = 0;
            var split = List.of(column.split("#"));
            int hashtagsPassed = 0;
            for (String beforeSquare : split) {
                if (!Objects.equals(beforeSquare, "")) {
                    for (char character : beforeSquare.toCharArray()) {
                        if (character == 'O') {
                            totalWeight += weight;
                            weight--;
                        }
                        charactersDone++;
                    }
                }

                hashtagsPassed++;

                weight = maxWeight - charactersDone - hashtagsPassed;
            }

        }


        System.out.println(totalWeight);
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
