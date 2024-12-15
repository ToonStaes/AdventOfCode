package toonstaes.adventofcode.AoC2015;

import toonstaes.adventofcode.AoCSolver;

import java.io.IOException;
import java.net.URISyntaxException;

public class Day2 extends AoCSolver {

    public static void part1() throws URISyntaxException, IOException {
        var file = getFile("2015/input_day2.txt");
        var lines = readLines(file);
        var result = 0L;

        for (String line : lines) {
            var factors = line.split("x");
            var l = Long.parseLong(factors[0].trim());
            var w = Long.parseLong(factors[1].trim());
            var h = Long.parseLong(factors[2].trim());

            var surfaceArea = getSurfaceArea(l, w, h);
            var slackArea = getSlackArea(l, w, h);

            var totalArea = surfaceArea + slackArea;
            result += totalArea;
        }

        System.out.println("part1: " + result);
    }

    public static void part2() throws URISyntaxException, IOException {
        var file = getFile("2015/input_day2.txt");
        var lines = readLines(file);
        var result = 0L;

        for (String line : lines) {
            var factors = line.split("x");
            var l = Long.parseLong(factors[0].trim());
            var w = Long.parseLong(factors[1].trim());
            var h = Long.parseLong(factors[2].trim());

            var wrapRibbon = getWrapRibbon(l, w, h);
            var bowRibbon = getBowRibbon(l, w, h);

            var totalRibbon = wrapRibbon + bowRibbon;
            result += totalRibbon;
        }

        System.out.println("part2: " + result);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        Day2.part1();
        Day2.part2();
    }

    private static Long getSurfaceArea(Long l, Long w, Long h) {
        return (2 * l * w) + (2 * w * h) + (2 * h * l);
    }

    private static Long getSlackArea(Long l, Long w, Long h) {
        var lw = l * w;
        var lh = l * h;
        var wh = w * h;

        return Math.min(lw, Math.min(lh, wh));
    }

    private static Long getWrapRibbon(Long l, Long w, Long h) {
        long smallest, secondSmallest;

        if (l <= w && l <= h) {
            smallest = l;
            secondSmallest = Math.min(w, h);
        } else if (w <= l && w <= h) {
            smallest = w;
            secondSmallest = Math.min(l, h);
        } else {
            smallest = h;
            secondSmallest = Math.min(l, w);
        }

        return smallest + smallest + secondSmallest + secondSmallest;
    }

    private static Long getBowRibbon(Long l, Long w, Long h) {
        return l * w * h;
    }
}
