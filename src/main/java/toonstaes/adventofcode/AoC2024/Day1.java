package toonstaes.adventofcode.AoC2024;

import toonstaes.adventofcode.AdventOfCodeApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 {

    private static File getFile(String filename) throws URISyntaxException {
        URL resource = AdventOfCodeApplication.class.getClassLoader().getResource(filename);
        File file = null;
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            file = new File(resource.toURI());
        }
        return file;
    }

    public static void day1_part1() throws URISyntaxException, IOException {
        var file = getFile("2024/input_day1.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> lines = new ArrayList<>();
        String st;

        while ((st = br.readLine()) != null) {
            lines.add(st);
        }

        List<Integer> firstColumn = new ArrayList<>();
        List<Integer> secondColumn = new ArrayList<>();

        for (String string : lines) {
            var split = string.split("   ");
            firstColumn.add(Integer.parseInt(split[0].trim()));
            secondColumn.add(Integer.parseInt(split[1].trim()));
        }

        Collections.sort(firstColumn);
        Collections.sort(secondColumn);


        var counter = 0;
        var result = 0;
        for (int first : firstColumn) {
            List<Integer> secondColumnList = new ArrayList<>(secondColumn);
            int second = secondColumnList.get(counter);

            int diff = first - second;
            if (diff <= 0) {
                diff *= -1;
            }
            result += diff;
            counter++;
        }

        System.out.println("day 1 part 1: " + result);
    }

    public static void day1_part2() throws URISyntaxException, IOException {
        var file = getFile("2024/input_day1.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> lines = new ArrayList<>();
        String st;

        while ((st = br.readLine()) != null) {
            lines.add(st);
        }

        List<Integer> firstColumn = new ArrayList<>();
        List<Integer> secondColumn = new ArrayList<>();

        for (String string : lines) {
            var split = string.split("   ");
            firstColumn.add(Integer.parseInt(split[0].trim()));
            secondColumn.add(Integer.parseInt(split[1].trim()));
        }

        var result = 0;
        for (int first : firstColumn) {
            int amount = Collections.frequency(secondColumn, first);
            result += amount * first;
        }

        System.out.println("day 1 part 2: " + result);
    }
}

