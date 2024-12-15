package toonstaes.adventofcode.AoC2015;

import toonstaes.adventofcode.AoCSolver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day4 extends AoCSolver {
    public static void part1() {
        var input = "bgvyzdsv";
        Long number = 0L;
        var hashFound = false;

        while (!hashFound) {
            var hash = getMD5Hash(input + number);
            if (hash.startsWith("00000")){
                hashFound = true;
            } else {
                number++;
            }
        }

        System.out.println(number);
    }

    public static void part2() {
        var input = "bgvyzdsv";
        Long number = 0L;
        var hashFound = false;

        while (!hashFound) {
            var hash = getMD5Hash(input + number);
            if (hash.startsWith("000000")){
                hashFound = true;
            } else {
                number++;
            }
        }

        System.out.println(number);

    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        part1();
        part2();
    }

    public static String getMD5Hash(String input) {
        try {
            // Get the MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Compute the hash (returns an array of bytes)
            byte[] hashBytes = md.digest(input.getBytes());

            // Convert the byte array into a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                // Convert each byte to hex and ensure two characters (e.g., 0a instead of a)
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }
}
