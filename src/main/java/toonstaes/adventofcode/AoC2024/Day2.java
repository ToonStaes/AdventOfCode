package toonstaes.adventofcode.AoC2024;

import toonstaes.adventofcode.AdventOfCodeApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Day2 {
    private static File getFile(String filename ) throws URISyntaxException {
        URL resource = AdventOfCodeApplication.class.getClassLoader().getResource( filename );
        File file = null;
        if ( resource == null ) {
            throw new IllegalArgumentException( "file not found!" );
        } else {
            file = new File( resource.toURI() );
        }
        return file;
    }

    private static boolean isAsc(int difference) {
        return difference < 0;
    }

    public static void day2_part1() throws URISyntaxException, IOException {
        var file = getFile( "2024/input_day2.txt" );
        BufferedReader br = new BufferedReader( new FileReader( file ) );
        List<String> lines = new ArrayList<>();
        String st;

        while (  (st = br.readLine()) != null ){
            lines.add(st);
        }

        var result = 0;

        for (String string : lines) {
            var split = string.split(" ");
            List<String> splitList = List.of(split);
            List<Integer> numbers = splitList.stream().map(Integer::parseInt).toList();

            if (isSafe(numbers)) {
                result ++;
            }
        }

        System.out.println( "day 2 part 1: " + result );
    }

    private static boolean isSafe(List<Integer> numbers) {
        int index = 0;
        boolean isSafe = true;
        int first = numbers.get(0);
        int second = numbers.get(1);
        boolean shouldAlwaysBeAsc = isAsc(first - second);
        while (index < numbers.size()-1 && isSafe) {
            first = numbers.get(index);
            second = numbers.get(index +1);
            var diff = first - second;
            if (diff == 0) {
                isSafe = false;
            }
            if (isAsc(diff) != shouldAlwaysBeAsc){
                isSafe = false;
            }
            if (diff < 0) {
                diff *= -1;
            }
            if (diff > 3){
                isSafe = false;
            }
            index++;
        }

        return isSafe;
    }

    public static void day2_part2() throws URISyntaxException, IOException {
        var file = getFile( "2024/input_day2.txt" );
        BufferedReader br = new BufferedReader( new FileReader( file ) );
        List<String> lines = new ArrayList<>();
        String st;

        while (  (st = br.readLine()) != null ){
            lines.add(st);
        }

        var result = 0;

        for (String string : lines) {
            var split = string.split(" ");
            List<String> splitList = List.of(split);
            List<Integer> numbers = splitList.stream().map(Integer::parseInt).toList();
            int index = 0;
            boolean isSafe = false;
            while (index < numbers.size() && !isSafe){
                var numbersCopy = new ArrayList<>(List.copyOf(numbers));
                numbersCopy.remove(index);
                if (isSafe(numbersCopy)){
                    isSafe = true;
                }
                index++;
            }

            if (isSafe) {
                result++;
            }
        }


        System.out.println( "day 2 part 2: " + result );
    }
}
