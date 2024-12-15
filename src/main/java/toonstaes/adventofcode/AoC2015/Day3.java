package toonstaes.adventofcode.AoC2015;

import lombok.*;
import toonstaes.adventofcode.AoCSolver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day3 extends AoCSolver {
    public static void part1() throws URISyntaxException, IOException {
        var file = getFile("2015/input_day3.txt");
        var lines = readLines(file);
        var instructionList = String.join("", lines).split("");

        Map<Location, Integer> locations  = new HashMap<>();
        var startLocation = new Location(0,0);
        locations.put(startLocation, 1);
        var prevLocation = startLocation;

        for (String instruction : instructionList) {
            var newLocation = new Location(prevLocation.getX(), prevLocation.getY());
            moveAndAddToMap( locations, instruction, newLocation);
            prevLocation = newLocation;
        }

        var result = locations.size();
        System.out.println(result);
    }

    public static void part2() throws URISyntaxException, IOException {
        var file = getFile("2015/input_day3.txt");
        var lines = readLines(file);
        var instructionList = List.of(String.join("", lines).split(""));

        Map<Location, Integer> locations  = new HashMap<>();
        var santaStartLocation = new Location(0,0);
        locations.put(santaStartLocation, 2);
        var santaPrevLocation = santaStartLocation;
        var robotPrevLocation = new Location(0, 0);
        var instructionsDone = 1;

        for (String instruction : instructionList) {
            Location newLocation;
            if (instructionsDone % 2 == 0) {
                newLocation = new Location(santaPrevLocation.getX(), santaPrevLocation.getY());
            } else {
                newLocation = new Location(robotPrevLocation.getX(), robotPrevLocation.getY());
            }

            moveAndAddToMap( locations, instruction, newLocation);
            if (instructionsDone % 2 == 0) {
                santaPrevLocation = newLocation;
            } else {
                robotPrevLocation = newLocation;
            }
            instructionsDone++;
        }

        var result = locations.size();
        System.out.println(result);
    }

    private static void moveAndAddToMap(Map<Location, Integer> locations, String instruction, Location newLocation) {
        switch (instruction) {
            case "^" -> newLocation.goNorth();
            case "v" -> newLocation.goSouth();
            case ">" -> newLocation.goEast();
            case "<" -> newLocation.goWest();
        }

        if (locations.containsKey(newLocation)) {
            locations.put(newLocation, locations.get(newLocation) + 1);
        } else {
            locations.put(newLocation, 1);
        }
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        part1();
        part2();
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @AllArgsConstructor // Public all-args constructor
    @NoArgsConstructor  // Public no-args constructor
    private static class Location {
        int x;
        int y;

        private void goNorth() {
            this.y++;
        }

        private void goSouth() {
            this.y--;
        }

        private void goEast() {
            this.x++;
        }

        private void goWest() {
            this.x--;
        }
    }
}
