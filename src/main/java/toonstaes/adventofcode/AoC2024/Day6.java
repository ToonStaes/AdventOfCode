package toonstaes.adventofcode.AoC2024;

import toonstaes.adventofcode.GetFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.*;

public class Day6 extends GetFile {

    private static final int UP = 0;
    private static final int RIGHT = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;

    public static void day6_part1() throws URISyntaxException, IOException {
        var startTime = Instant.now();
        var file = getFile( "2024/input_day6.txt" );
        var lines = readLines(file);
        var row = 0;
        var locations = new ArrayList<Location>();

        for (String line : lines) {
            List<String> charList = List.of(line.split(""));
            var column = 0;
            for (String character : charList) {
                var location = new Location(row, column, character);
                locations.add(location);
                column++;
            }
            row++;

            if (row % 10 == 0) {
                var j = 0;
            }
        }

        var currentLocation = locations.stream().filter(location -> Objects.equals(location.character, "^")).findFirst().get();


        var direction = UP;

        try{
            while (true) {
                Location lastLocation = move(locations, direction, currentLocation, row);
                direction = getNextDirection(direction);
                currentLocation = lastLocation;

            }
        }
        catch (NoSuchElementException exception) {
            var startRow = currentLocation.getRow();
            var startColumn = currentLocation.getColumn();
            List<Location> locationsToUpdate;
            if (direction == UP) {
                locationsToUpdate = locations.stream()
                        .filter(location -> location.getRow() <= startRow)
                        .filter(location -> location.getColumn() == startColumn)
                        .toList();
            } else if (direction == RIGHT) {
                locationsToUpdate = locations.stream()
                        .filter(location -> location.getColumn() >= startColumn )
                        .filter(location -> location.getRow() == startRow)
                        .toList();
            } else if (direction == DOWN) {
                locationsToUpdate = locations.stream()
                        .filter(location -> location.getRow() >= startRow)
                        .filter(location -> location.getColumn() == startColumn)
                        .toList();
            } else {
                locationsToUpdate = locations.stream()
                        .filter(location -> location.getColumn() <= startColumn)
                        .filter(location -> location.getRow() == startRow)
                        .toList();
            }

            // replace passed locations with X
            for (Location location : locationsToUpdate) {
                location.setCharacter("X");
            }
        }

        var result = locations.stream().filter(location -> Objects.equals(location.getCharacter(), "X")).toList().size();

        System.out.println(result);
        var endTime = Instant.now();
        System.out.println("startTime: " + startTime);
        System.out.println("endTime: " + endTime);
    }

    public static void day6_part2() throws URISyntaxException, IOException {
    }

    private static int getNextDirection(int direction) {
        return switch (direction) {
            case UP -> RIGHT;
            case RIGHT -> DOWN;
            case DOWN -> LEFT;
            default -> UP;
        };
    }

    private static Location move(List<Location> locations, int direction, Location currentLocation, int rows) {
        var startRow = currentLocation.getRow();
        var startColumn = currentLocation.getColumn();
        Location blockingLocation;
        List<Location> possibleBlockingLocations;
        List<Location> locationsToUpdate;
        if (direction == UP) {
            possibleBlockingLocations = locations.stream()
                    .filter(location -> location.getColumn() == startColumn)
                    .filter(location -> location.getRow() <= startRow)
                    .filter(location -> Objects.equals(location.getCharacter(), "#"))
                    .toList();

            blockingLocation = possibleBlockingLocations.stream()
                    .max(Comparator.comparingInt(Location::getRow)).get();

            var blockRow = blockingLocation.getRow();

            locationsToUpdate = locations.stream()
                    .filter(location -> location.getRow() <= startRow && location.getRow() > blockRow)
                    .filter(location -> location.getColumn() == startColumn)
                    .toList();
        } else if (direction == RIGHT) {
            possibleBlockingLocations = locations.stream()
                    .filter(location -> location.getColumn() >= startColumn)
                    .filter(location -> location.getRow() == startRow)
                    .filter(location -> Objects.equals(location.getCharacter(), "#"))
                    .toList();

            blockingLocation = possibleBlockingLocations.stream()
                    .min(Comparator.comparingInt(Location::getColumn)).get();

            var blockColumn = blockingLocation.getColumn();

            locationsToUpdate = locations.stream()
                    .filter(location -> location.getColumn() >= startColumn && location.getColumn() < blockColumn)
                    .filter(location -> location.getRow() == startRow)
                    .toList();
        } else if (direction == DOWN) {
            possibleBlockingLocations = locations.stream()
                    .filter(location -> location.getColumn() == startColumn)
                    .filter(location -> location.getRow() >= startRow)
                    .filter(location -> Objects.equals(location.getCharacter(), "#"))
                    .toList();

            blockingLocation = possibleBlockingLocations.stream()
                    .min(Comparator.comparingInt(Location::getRow)).get();

            var blockRow = blockingLocation.getRow();

            locationsToUpdate = locations.stream()
                    .filter(location -> location.getRow() >= startRow && location.getRow() < blockRow)
                    .filter(location -> location.getColumn() == startColumn)
                    .toList();
        } else {
            possibleBlockingLocations = locations.stream()
                    .filter(location -> location.getColumn() <= startColumn)
                    .filter(location -> location.getRow() == startRow)
                    .filter(location -> Objects.equals(location.getCharacter(), "#"))
                    .toList();

            blockingLocation = possibleBlockingLocations.stream()
                    .max(Comparator.comparingInt(Location::getColumn)).get();

            var blockColumn = blockingLocation.getColumn();

            locationsToUpdate = locations.stream()
                    .filter(location -> location.getColumn() <= startColumn && location.getColumn() > blockColumn)
                    .filter(location -> location.getRow() == startRow)
                    .toList();
        }

        // replace passed locations with X
        for (Location location : locationsToUpdate) {
            location.setCharacter("X");
        }

        var returnLocation = new Location(blockingLocation.getRow(), blockingLocation.getColumn(), "X");
        if (direction == UP) {
            returnLocation.setRow(blockingLocation.getRow() + 1);
        } else if (direction == RIGHT) {
            returnLocation.setColumn(blockingLocation.getColumn() - 1);
        } else if (direction == DOWN) {
            returnLocation.setRow(blockingLocation.getRow() - 1);
        } else {
            returnLocation.setColumn(blockingLocation.getColumn() + 1);
        }

        return returnLocation;
    }

    private static class Location {
        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getColumn() {
            return column;
        }

        public void setColumn(int column) {
            this.column = column;
        }

        public String getCharacter() {
            return character;
        }

        public void setCharacter(String character) {
            this.character = character;
        }

        public Location(int row, int column, String character) {
            this.row = row;
            this.column = column;
            this.character = character;
        }

        int row;
        int column;
        String character;
    }
}
