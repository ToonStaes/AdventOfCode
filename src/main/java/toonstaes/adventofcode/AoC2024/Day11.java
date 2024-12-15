package toonstaes.adventofcode.AoC2024;

import toonstaes.adventofcode.GetFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class Day11 extends GetFile {

    static Long result = 0L;

    public static void day11_part1() throws URISyntaxException, IOException {
        var file = getFile( "2024/input_day11.txt" );
        var line = readLines(file).get(0);

        var numbers = List.of(line.split(" "));
        var newNumbers = new ArrayList<String>();
        var moves = 0;

        while (moves < 25) {
            System.out.println("i have blinked this amount of times: " + moves);

            newNumbers = new ArrayList<>();
            for (String number : numbers) {
                var numberSplit = List.of(number.split(""));
                if (Objects.equals(number, "0")) {
                    newNumbers.add("1");
                } else if (numberSplit.size() % 2 == 0) {
                    var part1 = Long.parseLong(number.substring(0, numberSplit.size()/2));
                    var part2 = Long.parseLong(number.substring(numberSplit.size()/2));
                    newNumbers.add(String.valueOf(part1));
                    newNumbers.add(String.valueOf(part2));
                } else {
                    Long numberToAdd = Long.parseLong(number) * 2024L;
                    newNumbers.add(String.valueOf(numberToAdd));
                }
            }
            System.out.println("After " + (moves + 1) + " blinks: " + newNumbers);
            numbers = List.copyOf(newNumbers);
            moves++;
        }

        var result = numbers.size();
        System.out.println(result);
    }
    public static void day11_part2() throws URISyntaxException, IOException {
        // TODO: Fix OOM problem
        var file = getFile( "2024/input_day11.txt" );
        var line = readLines(file).get(0);

        var numbers = List.of(line.split(" "));
        var blinks = 0;
        var cache = new HashMap<CacheEntry, Long>();

        Long result = 0L;
        for (String number : numbers){
            result += process(number, cache, blinks);
            System.out.println("number processed");
        }

        System.out.println(result);
    }

    private static Long process(String number, Map<CacheEntry, Long> cache, int blinks) {
        if (Objects.equals(number, "-1")){
            return 0L;
        }
        if (blinks >= 75){
            return 1L;
        }

        var cacheEntry = new CacheEntry(number, blinks);
        var possibleCacheResult = cache.get(cacheEntry);
        if (possibleCacheResult != null) {
            return possibleCacheResult;
        }

        var numberSplit = List.of(number.split(""));

        var part1 = "0";
        var part2 = "-1";

        if (Objects.equals(number, "0")) {
            part1 = "1";
        } else if (numberSplit.size() % 2 == 0) {
            var part1Long = Long.parseLong(number.substring(0, numberSplit.size() / 2));
            var part2Long = Long.parseLong(number.substring(numberSplit.size() / 2));
            part1 = String.valueOf(part1Long);
            part2 = String.valueOf(part2Long);
        } else {
            long numberToAdd = Long.parseLong(number) * 2024L;
            part1 = Long.toString(numberToAdd);
        }
        blinks++;
        result = process(part1, cache, blinks) + process(part2, cache, blinks);

        cache.put(cacheEntry, result);
        return result;
    }

    static class CacheEntry {
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getDepth() {
            return depth;
        }

        public void setDepth(int depth) {
            this.depth = depth;
        }

        public CacheEntry(String value, int depth) {
            this.value = value;
            this.depth = depth;
        }

        String value;
        int depth;
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            return (this.getDepth() == ((CacheEntry) o).getDepth() && Objects.equals(this.getValue(), ((CacheEntry) o).getValue()));
        }
    }
}
