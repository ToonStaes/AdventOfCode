package toonstaes.adventofcode.AoC2024;

import toonstaes.adventofcode.GetFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day9 extends GetFile {
    public static void day9_part1() throws URISyntaxException, IOException {
        var file = getFile( "2024/input_day9.txt" );
        var line = readLines(file).get(0);

        var split = List.of(line.split(""));
        int i = 0;
        List<String> fileLine = new ArrayList<>();
        int id = 0;

        while (i < split.size()) {

            int number = Integer.parseInt(split.get(i));
            int j = 0;
            if (i % 2 == 0) {
                // is even
                while (j < number){
                    fileLine.add(String.valueOf(id));
                    j++;
                }
                id++;
            } else {
                // is uneven
                while (j < number){
                    fileLine.add(".");
                    j++;
                }
            }
            i++;
        }

        List<String> resultLine = new ArrayList<>();

        var index = 0;
        var lastIndex = fileLine.size()-1;
        while (index < lastIndex + 1 ) {
            var entry = fileLine.get(index);
            if (Objects.equals(entry, ".")){
                var addedEntry = false;
                while (!addedEntry) {
                    if ( Objects.equals(fileLine.get(lastIndex), ".")){
                        lastIndex--;
                    } else {
                        var lastNumber = fileLine.get(lastIndex);
                        resultLine.add(lastNumber);
                        lastIndex--;
                        addedEntry = true;
                    }
                }
            } else {
                resultLine.add(entry);
            }
            index++;
        }

        Long result = 0L;
        index = 0;

        for (String entry : resultLine) {
            var number = Long.parseLong(entry);
            var addingValue = index * number;
            result += addingValue;
            index++;
        }

        System.out.println(result);

    }

    private static class File {
        public List<String> getFile() {
            return file;
        }

        public void setFile(List<String> file) {
            this.file = file;
        }

        public boolean isAttemptedMove() {
            return attemptedMove;
        }

        public void setAttemptedMove(boolean attemptedMove) {
            this.attemptedMove = attemptedMove;
        }

        public File(List<String> file) {
            this.file = file;
            this.attemptedMove = false;
        }

        List<String> file;
        boolean attemptedMove;
    }
}
