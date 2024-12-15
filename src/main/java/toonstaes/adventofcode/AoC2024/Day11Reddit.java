package toonstaes.adventofcode.AoC2024;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static toonstaes.adventofcode.GetFile.getFile;
import static toonstaes.adventofcode.GetFile.readLines;

public class Day11Reddit {

    static Map<CacheKey, Long> cache = new ConcurrentHashMap<>();

    record CacheKey(Long stoneno, int cnt) {}

    record Stone(long number) {

        Stream<Stone> next() {
            if (number == 0) return Stream.of(new Stone(1));
            var s = "" + number;
            if (s.length() % 2 == 0)
                return Stream.of(
                        new Stone(Long.valueOf(s.substring(0, s.length() / 2))),
                        new Stone(Long.valueOf(s.substring(s.length() / 2, s.length()))));
            return Stream.of(new Stone(2024 * number));
        }
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
//        System.out.println(calc(25, readFile("day11.txt")));
        System.out.println(calc(75, readFile("day11.txt")));
    }

    static Long calc(int cnt, final Stream<Stone> stoneStream) {
        if (cnt == 0) return stoneStream.count();
        return stoneStream.mapToLong(stone -> calc(cnt, stone)).sum();
    }

    static Long calc(int cnt, Stone stone) {
        var key = new CacheKey(stone.number, cnt);
        var value = cache.get(key);
        if (value == null) {
            value = calc(cnt - 1, stone.next());
            cache.put(key, value);
        }
        return value;
    }

    static Stream<Stone> readFile(final String file) throws URISyntaxException, IOException {
        var fileoutput = getFile( "2024/input_day11.txt" );
        var line = readLines(fileoutput).get(0);

        return Arrays.stream(line.split(" "))
                .mapToLong(Long::valueOf)
                .mapToObj(Stone::new);
    }
}
