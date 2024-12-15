package toonstaes.adventofcode.AoC2015;

import toonstaes.adventofcode.GetFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Day1 extends GetFile {

    public static void day1_part1() throws URISyntaxException, IOException {
        var file = getFile("2015/input_day1.txt");
        var line = readLines(file).get(0);
        var chars = List.of(line.split(""));
        var moveUp = chars.stream().filter(character -> character.equals("(")).count();
        var moveDown = chars.stream().filter(character -> character.equals(")")).count();

        var result = moveUp - moveDown;
        System.out.println(result);
    }

    public static void day1_part2() throws URISyntaxException, IOException {
        var file = getFile("2015/input_day1.txt");
        var line = readLines(file).get(0);
        var chars = List.of(line.split(""));

        var floor = 0;
        var inBasement = false;
        var result = 0;

        while (!inBasement) {
            for (String character : chars) {
                result++;
                if (character.equals("(")) {
                    floor++;
                } else if (character.equals(")")) {
                    floor--;
                }
                if (floor < 0) {
                    inBasement = true;
                    break;
                }
            }
        }

        System.out.println(result);
    }

    public static void main( String[] args ) throws IOException, URISyntaxException {
        day1_part2();
    }
}
