package toonstaes.adventofcode.AoC2015;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import toonstaes.adventofcode.AoCSolver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day6 extends AoCSolver {
    public static void part1() throws URISyntaxException, IOException {
        var file = getFile("2015/input_day6.txt");
        var lines = readLines(file);
        List<LightPart1> lightPart1s = new ArrayList<>();

        var i = 0;
        var j = 0;
        while (i < 1000){
            while (j < 1000){
                var light = new LightPart1(i, j, false);
                lightPart1s.add(light);
                j++;
            }
            i++;
            j = 0;
        }

        for (String line : lines) {
            var split = List.of(line.split(" "));

            var instruction = "";
            if (Objects.equals(split.get(0), "toggle")) {
                instruction = "toggle";
                split = split.subList(1, split.size());
            } else {
                instruction = split.get(0) + " " + split.get(1);
                split = split.subList(2, split.size());
            }

            var coord1 = split.get(0);
            var coord2 = split.get(2);

            var minRow = Integer.parseInt(coord1.split(",")[1]);
            var minCol = Integer.parseInt(coord1.split(",")[0]);

            var maxRow = Integer.parseInt(coord2.split(",")[1]);
            var maxCol = Integer.parseInt(coord2.split(",")[0]);

            var lightsToDoAction = lightPart1s.stream()
                    .filter(lightPart1 -> lightPart1.getRow() >= minRow && lightPart1.getRow() <= maxRow)
                    .filter(lightPart1 -> lightPart1.getCol() >= minCol && lightPart1.getCol() <= maxCol)
                    .toList();

            for (LightPart1 lightPart1 : lightsToDoAction) {
                lightPart1.doInstruction(instruction);
            }
        }

        var result = lightPart1s.stream().filter(LightPart1::isOn).count();
        System.out.println("lights on at end: " + result);
    }

    public static void part2() throws URISyntaxException, IOException {
        var file = getFile("2015/input_day6.txt");
        var lines = readLines(file);
        List<LightPart2> lights = new ArrayList<>();

        var i = 0;
        var j = 0;
        while (i < 1000){
            while (j < 1000){
                var light = new LightPart2(i, j, 0);
                lights.add(light);
                j++;
            }
            i++;
            j = 0;
        }

        for (String line : lines) {
            var split = List.of(line.split(" "));

            var instruction = "";
            if (Objects.equals(split.get(0), "toggle")) {
                instruction = "toggle";
                split = split.subList(1, split.size());
            } else {
                instruction = split.get(0) + " " + split.get(1);
                split = split.subList(2, split.size());
            }

            var coord1 = split.get(0);
            var coord2 = split.get(2);

            var minRow = Integer.parseInt(coord1.split(",")[1]);
            var minCol = Integer.parseInt(coord1.split(",")[0]);

            var maxRow = Integer.parseInt(coord2.split(",")[1]);
            var maxCol = Integer.parseInt(coord2.split(",")[0]);

            var lightsToDoAction = lights.stream()
                    .filter(lightPart1 -> lightPart1.getRow() >= minRow && lightPart1.getRow() <= maxRow)
                    .filter(lightPart1 -> lightPart1.getCol() >= minCol && lightPart1.getCol() <= maxCol)
                    .toList();

            for (LightPart2 light : lightsToDoAction) {
                light.doInstruction(instruction);
            }
        }
        var result = lights.stream().mapToInt(LightPart2::getBrightness).sum();
        System.out.println(result);

    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        part1();
        part2();
    }

    @AllArgsConstructor
    @Getter
    @Setter
    private static class LightPart1 {
        int row;
        int col;
        boolean on;

        void doInstruction(String instruction) {
            switch (instruction) {
                case "toggle" -> on = !on;
                case "turn on" -> on = true;
                case "turn off" -> on = false;
            }
        }
    }

    @AllArgsConstructor
    @Getter
    @Setter
    private static class LightPart2 {
        int row;
        int col;
        int brightness;

        void doInstruction(String instruction) {
            switch (instruction) {
                case "toggle" -> brightness += 2;
                case "turn on" -> brightness ++;
                case "turn off" -> {
                    if (brightness > 0) {
                        brightness --;
                    }
                }
            }
        }
    }
}
