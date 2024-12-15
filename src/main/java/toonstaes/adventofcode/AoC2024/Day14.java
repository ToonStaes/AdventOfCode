//package toonstaes.adventofcode.AoC2024;
//
//import lombok.Getter;
//import lombok.Setter;
//import toonstaes.adventofcode.GetFile;
//
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Day14 extends GetFile {
//    public static final int xCount = 101;
//    public static final int yCount = 103;
//
//    public static void day14_part1() throws URISyntaxException, IOException {
//        var file = getFile("2024/input_day14.txt");
//        var lines = readLines(file);
//        List<Robot> robots = new ArrayList<>();
//        var robotCounter = 0;
//
//        for (String line : lines) {
//            var split = line.split(" ");
//            var position = split[0];
//            var velocity = split[1];
//
//            var positionSplit = List.of(position.split(","));
//            var x = Integer.parseInt(List.of(positionSplit.get(0).split("=")).get(1));
//            var y = Integer.parseInt(positionSplit.get(1));
//
//            var velocitySplit = List.of(velocity.split(","));
//            var xVelo = Integer.parseInt(List.of(velocitySplit.get(0).split("=")).get(1));
//            var yVelo = Integer.parseInt(velocitySplit.get(1));
//
//            Robot robot = new Robot(x, y, xVelo, yVelo);
//            robot.setName(robotCounter + "");
//            robotCounter++;
//            robots.add(robot);
//        }
//
//        for (Robot robot : robots) {
////            System.out.println(robot.getName() + " start location: (" + robot.getX() + ";" + robot.getY() +")");
//            System.out.println(robot.getName() + " velo: (" + robot.getXVelo() + ";" + robot.getYVelo() + ")");
//        }
//
//        List<Robot> topLeft = new ArrayList<>();
//        List<Robot> topRight = new ArrayList<>();
//        List<Robot> botLeft = new ArrayList<>();
//        List<Robot> botRight = new ArrayList<>();
//
//        var middley = (int) Math.floor((double) yCount / 2);
//        var middlex = (int) Math.floor((double) xCount / 2);
//
//        for (Robot robot : robots) {
//            var moves = 0;
//            while (moves < 100) {
//                robot.move();
//                moves++;
//            }
//
//            if (robot.getX() > middlex) {
//                if (robot.getY() > middley) {
//                    botRight.add(robot);
//                } else if (robot.getY() < middley) {
//                    topRight.add(robot);
//                }
//            } else if (robot.getX() < middlex) {
//                if (robot.getY() > middley) {
//                    botLeft.add(robot);
//                } else if (robot.getY() < middley) {
//                    topLeft.add(robot);
//                }
//            }
//        }
//
//        var result = topLeft.size() * topRight.size() * botLeft.size() * botRight.size();
//        System.out.println(result);
//        for (Robot robot : robots) {
//            System.out.println(robot.getName() + " final location: (" + robot.getX() + ";" + robot.getY() + ")");
//        }
//    }
//
//    public static void day14_part2() throws URISyntaxException, IOException {
//        var file = getFile("2024/input_day14.txt");
//        var lines = readLines(file);
//        List<Robot> robots = new ArrayList<>();
//        var robotCounter = 0;
//
//        for (String line : lines) {
//            var split = line.split(" ");
//            var position = split[0];
//            var velocity = split[1];
//
//            var positionSplit = List.of(position.split(","));
//            var x = Integer.parseInt(List.of(positionSplit.get(0).split("=")).get(1));
//            var y = Integer.parseInt(positionSplit.get(1));
//
//            var velocitySplit = List.of(velocity.split(","));
//            var xVelo = Integer.parseInt(List.of(velocitySplit.get(0).split("=")).get(1));
//            var yVelo = Integer.parseInt(velocitySplit.get(1));
//
//            Robot robot = new Robot(x, y, xVelo, yVelo);
//            robot.setName(robotCounter + "");
//            robotCounter++;
//            robots.add(robot);
//        }
//
//        boolean isChristmasTree = false;
//        var moves = 0;
//
//        while (!isChristmasTree) {
//            for (Robot robot : robots) {
//                robot.move();
//            }
//            moves++;
//            var hasNeighbourCounter = 0;
//            for (Robot robot : robots) {
//                var find = robots.stream()
//                        .filter(r ->
//                                (r.getX() == robot.getX() && r.getY() == robot.getY() - 1) ||
//                                        (r.getX() == robot.getX() && r.getY() == robot.getY() + 1) ||
//                                        (r.getX() == robot.getX() + 1 && r.getY() == robot.getY()) ||
//                                        (r.getX() == robot.getX() - 1 && r.getY() == robot.getY())
//                        )
//                        .findAny();
//                if (find.isPresent()) {
//                    hasNeighbourCounter++;
//                }
//            }
//
//            if (hasNeighbourCounter > robots.size() * 0.55) {
//                isChristmasTree = true;
//            }
//        }
//
//        System.out.println(moves);
//    }
//
//
//    @Getter
//    private static class Robot {
//        public Robot(int x, int y, int xVelo, int yVelo) {
//            this.x = x;
//            this.y = y;
//            this.xVelo = xVelo;
//            this.yVelo = yVelo;
//        }
//
//        @Setter
//        private int x;
//        @Setter
//        private int y;
//        private int xVelo;
//        private int yVelo;
//        @Setter
//        private String name;
//
//
//        void move() {
//            var newX = x + xVelo;
//            if (newX < 0) {
//                newX = newX + xCount;
//            } else if (newX >= xCount) {
//                newX = newX - xCount;
//            }
//
//            var newY = y + yVelo;
//            if (newY < 0) {
//                newY = newY + yCount;
//            } else if (newY >= yCount) {
//                newY = newY - yCount;
//            }
//            setX(newX);
//            setY(newY);
//        }
//    }
//}
