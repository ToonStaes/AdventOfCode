package toonstaes.adventofcode.AoC2024;

import toonstaes.adventofcode.AoC2015.Day6;
import toonstaes.adventofcode.AoCSolver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Day22 extends AoCSolver {

    public static void part1() throws URISyntaxException, IOException {
        var file = getFile("2024/input_day22.txt");
        var lines = readLines(file);

        var numbersList = lines.stream().map(Long::parseLong).toList();
        int i = 0;
        while (i < 2000) {
            var newNumbers = new ArrayList<Long>();
            for (Long number : numbersList) {
                newNumbers.add(stepsPart1(number));
            }
            numbersList = newNumbers;
            i++;
        }

        var result = 0L;
        for (Long number : numbersList) {
            result += number;
        }
        System.out.println("Part 1: " + result);
    }

    public static void part2() throws URISyntaxException, IOException {



    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        part1();
        part2();
    }

    public static Long stepsPart1(Long number){
        var afterStep1 = part1_step1(number);
        var afterStep2 = part1_step2(afterStep1);
        return part1_step3(afterStep2);
    }

    public static Long part1_step1(Long secret) {
        return prune(mix(secret, secret*64));
    }

    public static Long part1_step2(Long secret) {
        return prune(mix(secret, (long) Math.floor((float) secret /32)));
    }

    public static Long part1_step3(Long secret) {
        return prune(mix(secret, secret * 2048));
    }

    public static Long mix(Long secret, Long number) {
        return secret ^ number;
    }

    public static Long prune(Long secret) {
        return secret % 16777216;
    }
}
