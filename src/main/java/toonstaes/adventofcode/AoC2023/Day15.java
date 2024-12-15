package toonstaes.adventofcode.AoC2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day15 {

    public static void day15_part1() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src\\main\\resources\\input_day15.txt"));
        String input = lines.get(0);
        List<String> sequences = new ArrayList<>(List.of(input.split(",")));

        var result = sequences.stream().map(Day15::getHashForSequence).mapToLong(i -> i).sum();
        System.out.println(result);
    }

    public static void day15_part2() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src\\main\\resources\\input_day15.txt"));
        String input = lines.get(0);
        List<String> sequences = new ArrayList<>(List.of(input.split(",")));
        Map<Long, List<String>> boxes = new HashMap<>();
        for (String sequence : sequences) {
            if (sequence.contains("=")) {
                var split = sequence.split("=");
                var toHash = split[0];
                var lens = split[1];
                long box = getHashForSequence(toHash);
                String valueToAdd = toHash + " " + lens;
                List<String> value;
                if (boxes.containsKey(box)) {
                    value = boxes.get(box);
                    boolean hasBeenAdded = false;
                    for (String boxEntry : value) {
                        if (boxEntry.contains(toHash)) {
                            var index = value.indexOf(boxEntry);
                            boxEntry = valueToAdd;
                            value.set(index, boxEntry);
                            hasBeenAdded = true;
                        }
                    }
                    if (!hasBeenAdded) {
                        value.add(valueToAdd);
                    }

                } else {
                    value = new ArrayList<>(Collections.singleton(valueToAdd));
                }
                boxes.put(box, value);
            } else if (sequence.contains("-")) {
                var split = sequence.split("-");
                var toHash = split[0];
                long box = getHashForSequence(toHash);
                List<String> lenses = boxes.get(box);
                if (lenses != null) {
                    lenses.removeIf(lens -> lens.contains(toHash));
                }
            }
        }

        long focusingPower = 0;

        for (Map.Entry<Long, List<String>> entry : boxes.entrySet()) {
            long boxNumber = entry.getKey();
            List<String> value = entry.getValue();
            for (String lens : value) {
                var slot = value.indexOf(lens) + 1;
                long focalLength = Long.parseLong(lens.split(" ")[1]);
                focusingPower += (1 + boxNumber) * slot * focalLength;
            }
        }

        System.out.println(focusingPower);
    }

    private static long getHashForSequence(String sequence) {
        long result = 0;
        var characters = sequence.toCharArray();
        for (char character : characters) {
            result += character;
            result *= 17;
            result %= 256;
        }
        return result;
    }

}
