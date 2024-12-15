package toonstaes.adventofcode.AoC2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day11 {

    public static void day11_part1() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src\\main\\resources\\input_day11.txt"));
        var numRows = lines.size();
        var numCols = lines.get(0).length();
        List<Coord> galaxies = new ArrayList<>();
        for(int row = 0; row < lines.size(); row++) {
            String line = lines.get(row);
            for(int col = 0; col < numCols; col++) {
                if(line.charAt(col) == '#') {
                    galaxies.add(new Coord(row, col));
                }
            }
        }
        expand(2, galaxies, numRows, numCols);

        System.out.println(computeDistances(galaxies));
    }

    public static void day11_part2() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src\\main\\resources\\input_day11.txt"));
        var numRows = lines.size();
        var numCols = lines.get(0).length();
        List<Coord> galaxies = new ArrayList<>();
        for(int row = 0; row < lines.size(); row++) {
            String line = lines.get(row);
            for(int col = 0; col < numCols; col++) {
                if(line.charAt(col) == '#') {
                    galaxies.add(new Coord(row, col));
                }
            }
        }
        expand(1000000, galaxies, numRows, numCols);

        System.out.println(computeDistances(galaxies));
    }


    public static void expand(int expansionFactor, List<Coord> galaxyPositions, int numRows, int numCols) {
        Set<Integer> emptyRows = emptyRows(galaxyPositions, numRows);
        Set<Integer> emptyCols = emptyCols(galaxyPositions, numCols);

        for(Coord galaxy : galaxyPositions) {
            galaxy.x += (expansionFactor - 1) * numLessThan(emptyRows, galaxy.x);
            galaxy.y += (expansionFactor - 1) * numLessThan(emptyCols, galaxy.y);
        }
    }

    public static long computeDistances(List<Coord> galaxies) {
        long result = 0;
        for(int i = 0, size=galaxies.size(); i < size; i++) {
            for(int j = i + 1; j < size; j++) {
                result += galaxies.get(i)
                        .distanceFrom(galaxies.get(j));
            }
        }
        return result;
    }


    private static Set<Integer> emptyRows(List<Coord> galaxyPositions, int numRows) {
        Set<Integer> rowsWithGalaxies = galaxyPositions.stream()
                .map(Coord::getX)
                .collect(Collectors.toSet());
        Set<Integer> result = complementValues(rowsWithGalaxies, 0, numRows);
        return result;
    }

    private static Set<Integer> emptyCols(List<Coord> galaxyPositions, int numCols) {
        Set<Integer> colsWithGalaxies = galaxyPositions.stream()
                .map(Coord::getY)
                .collect(Collectors.toSet());
        Set<Integer> result = complementValues(colsWithGalaxies, 0, numCols);
        return result;
    }

    private static int numLessThan(Set<Integer> list, int val) {
        return (int) list.stream().filter(x -> x < val).count();
    }

    private static Set<Integer> complementValues(Set<Integer> list, int min, int max) {
        Set<Integer> result = IntStream.range(min, max).boxed().collect(Collectors.toSet());
        result.removeAll(list);
        return result;
    }
}

class Coord {
    int x, y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public long distanceFrom(Coord coord) {
        return Math.abs(coord.x - x) + Math.abs(coord.y - y);
    }

    @Override
    public boolean equals(Object obj) {
        if(! (obj instanceof Coord)) return false;
        Coord coord = (Coord) obj;
        return coord.x == x && coord.y == y;
    }
}
