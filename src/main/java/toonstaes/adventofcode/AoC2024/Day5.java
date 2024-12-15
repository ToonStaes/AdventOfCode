package toonstaes.adventofcode.AoC2024;

import toonstaes.adventofcode.GetFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day5 extends GetFile {
    public static void day5_part1() throws URISyntaxException, IOException {
        var file = getFile( "2024/input_day5.txt" );
        var lines = readLines(file);

        var rules = lines.stream().filter(line-> line.contains("|")).toList();
        var updates = lines.stream().filter(line-> line.contains(",")).toList();
        var validUpdates = new ArrayList<String>();

        Map<String, List<String>> numbersAfter = new HashMap<>();
        Map<String, List<String>> numbersBefore = new HashMap<>();

        for (String rule : rules) {
            var split = rule.split("\\|");
            var first = split[0];
            var second = split[1];

            if (numbersAfter.containsKey(first)){
                numbersAfter.get(first).add(second);
            } else {
                numbersAfter.put(first, new ArrayList<>());
                numbersAfter.get(first).add(second);
            }

            if (numbersBefore.containsKey(second)){
                numbersBefore.get(second).add(first);
            } else {
                numbersBefore.put(second, new ArrayList<>());
                numbersBefore.get(second).add(first);
            }
        }

        for (String update : updates) {
            boolean validUpdate = true;
            var split = update.split(",");
            var splitList = List.of(split);

            for (String number : splitList) {
                var index = splitList.indexOf(number);

                // numbersAfter
                var numbersAfterNumber = splitList.subList(index, splitList.size());
                for (String numberAfter : numbersAfterNumber) {
                    var numbersThatShouldBeBefore = numbersBefore.get(number);
                    if ( numbersThatShouldBeBefore != null && !numbersThatShouldBeBefore.isEmpty() && numbersThatShouldBeBefore.contains(numberAfter)){
                        validUpdate = false;
                    }
                }

                // numbersBefore
                var numbersBeforeNumber = splitList.subList(0, index);
                for (String numberBefore : numbersBeforeNumber) {
                    var numbersThatShouldBeAfter = numbersAfter.get(number);
                    if ( numbersThatShouldBeAfter != null && !numbersThatShouldBeAfter.isEmpty() && numbersThatShouldBeAfter.contains(numberBefore)){
                        validUpdate = false;
                    }
                }
            }

            if (validUpdate) {
                validUpdates.add(update);
            }
        }

        var result = 0;
        for (String validUpdate : validUpdates) {
            var split = List.of(validUpdate.split(","));
            var middleNumber = split.get(split.size()/2);
            result += Integer.parseInt(middleNumber);
        }

        System.out.println(result);
    }

    public static void day5_part2() throws URISyntaxException, IOException {
        var file = getFile( "2024/input_day5.txt" );
        var lines = readLines(file);

        var rules = lines.stream().filter(line-> line.contains("|")).toList();
        var updates = lines.stream().filter(line-> line.contains(",")).toList();
        var invalidUpdates = new ArrayList<String>();

        Map<String, List<String>> numbersAfter = new HashMap<>();
        Map<String, List<String>> numbersBefore = new HashMap<>();

        for (String rule : rules) {
            var split = rule.split("\\|");
            var first = split[0];
            var second = split[1];

            if (numbersAfter.containsKey(first)){
                numbersAfter.get(first).add(second);
            } else {
                numbersAfter.put(first, new ArrayList<>());
                numbersAfter.get(first).add(second);
            }

            if (numbersBefore.containsKey(second)){
                numbersBefore.get(second).add(first);
            } else {
                numbersBefore.put(second, new ArrayList<>());
                numbersBefore.get(second).add(first);
            }
        }

        for (String update : updates) {
            boolean validUpdate = true;
            var split = update.split(",");
            var splitList = List.of(split);

            for (String number : splitList) {
                var index = splitList.indexOf(number);

                // numbersAfter
                var numbersAfterNumber = splitList.subList(index, splitList.size());
                for (String numberAfter : numbersAfterNumber) {
                    var numbersThatShouldBeBefore = numbersBefore.get(number);
                    if ( numbersThatShouldBeBefore != null && !numbersThatShouldBeBefore.isEmpty() && numbersThatShouldBeBefore.contains(numberAfter)){
                        validUpdate = false;
                    }
                }

                // numbersBefore
                var numbersBeforeNumber = splitList.subList(0, index);
                for (String numberBefore : numbersBeforeNumber) {
                    var numbersThatShouldBeAfter = numbersAfter.get(number);
                    if ( numbersThatShouldBeAfter != null && !numbersThatShouldBeAfter.isEmpty() && numbersThatShouldBeAfter.contains(numberBefore)){
                        validUpdate = false;
                    }
                }
            }

            if (!validUpdate) {
                invalidUpdates.add(update);
            }
        }

        var updatedInvalidUpdates = new ArrayList<String>();

        for (String invalidUpdate : invalidUpdates) {
            var split = List.of(invalidUpdate.split(","));
            var updatedList = new ArrayList<String>();
            for (String number : split) {
                var index = split.indexOf(number);
                var insertIndex = -2;
                if (index == 0) {
                    updatedList.add(number);
                } else {
                    while (insertIndex == -2) {
                        for (String updatedListItem : updatedList) {
                            var numbersThatShouldBeBefore = numbersBefore.get(number);
                            var numbersThatShouldBeAfter = numbersAfter.get(number);
                            if ( numbersThatShouldBeBefore != null && !numbersThatShouldBeBefore.isEmpty() && numbersThatShouldBeBefore.contains(updatedListItem)) {
                                insertIndex = updatedList.indexOf(updatedListItem) + 1;
                            } else if (numbersThatShouldBeAfter != null && !numbersThatShouldBeAfter.isEmpty() && numbersThatShouldBeAfter.contains(updatedListItem)){
                                insertIndex = updatedList.indexOf(updatedListItem);
                                break;
                            }
                        }
                        if (insertIndex == -2) {
                            insertIndex = updatedList.size()-1;
                        }
                    }
                    updatedList.add(insertIndex, number);
                }
            }
            var updatedListString = String.join(", ", updatedList);
            updatedInvalidUpdates.add(updatedListString);
        }

        var result = 0;
        for (String updatedUpdates : updatedInvalidUpdates) {
            var split = List.of(updatedUpdates.split(","));
            var middleNumber = split.get(split.size()/2);
            result += Integer.parseInt(middleNumber.trim());
        }

        System.out.println(result);
    }
}
