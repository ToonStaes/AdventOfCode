package toonstaes.adventofcode.AoC2024;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import toonstaes.adventofcode.AoCSolver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Day24 extends AoCSolver {
    public static void part1() throws URISyntaxException, IOException {
        var file = getFile("2024/input_day24.txt");
        var lines = readLines(file);
        List<Input> inputs = new ArrayList<>();
        List<OutputLine> outputs = new ArrayList<>();

        for (String line : lines) {
            if (!line.contains("->")) {
                if (!line.trim().isEmpty()) {
                    var split = line.split(":");
                    var name = split[0];
                    var value = split[1].trim();
                    var input = new Input(value, name);
                    inputs.add(input);
                }
            } else {
                var split = line.split(" ");
                var input1 = split[0];
                var operator = split[1];
                var input2 = split[2];
                var output = split[4];

                var outputLine = new OutputLine(input1, input2, operator, output);
                outputs.add(outputLine);
            }
        }

        var outputsToCheck = new ArrayList<OutputLine>();
        outputsToCheck.addAll(outputs);
        boolean noMoreOutputs = outputsToCheck.isEmpty();

        while (!noMoreOutputs) {
            List<OutputLine> outputsToRedo = new ArrayList<>();
            for (OutputLine output : outputsToCheck) {
                var input1 = output.getInput1();
                var input2 = output.getInput2();
                var operator = output.getOperator();
                if (isKnownInput(input1, inputs) && isKnownInput(input2, inputs)) {
                    var newInputName = output.getOutput();
                    var value = getValue(operator, input1, input2, inputs);
                    var newInput = new Input(value, newInputName);
                    inputs.add(newInput);

                } else {
                    outputsToRedo.add(output);
                }
            }
            noMoreOutputs = outputsToRedo.isEmpty();
            if (!noMoreOutputs) {
                outputsToCheck.clear();
                outputsToCheck.addAll(outputsToRedo);
            }
        }

        var useFullInputs = inputs.stream()
                .filter(input -> input.getName().startsWith("z"))
                .sorted(Comparator.comparing(Input::getName).reversed())
                .toList();

        StringBuilder bitStringBuilder = new StringBuilder();

        for (Input input : useFullInputs) {
            bitStringBuilder.append(input.getValue());
        }

        var bitString = bitStringBuilder.toString();
        Long result = Long.parseLong(bitString, 2);

        System.out.println("Part 1: " + result);
    }

    private static String getValue(String operator, String input1Name, String input2Name, List<Input> inputs) {
        var value = "0";
        var input1 = inputs.stream().filter(o -> o.getName().equals(input1Name)).findFirst().get();
        var input2 = inputs.stream().filter(o -> o.getName().equals(input2Name)).findFirst().get();
        switch (operator) {
            case "AND" -> {
                if (input1.getValue().equals(input2.getValue()) && input1.getValue().equals("1")) {
                    value = "1";
                }
            }
            case "OR" -> {
                if (input1.getValue().equals("1") || input2.getValue().equals("1")) {
                    value = "1";
                }
            }
            case "XOR" -> {
                if (!Objects.equals(input1.getValue(), input2.getValue())) {
                    value = "1";
                }
            }
        }
        return value;
    }

    public static void part2() throws URISyntaxException, IOException {
        var file = getFile("2024/input_day24.txt");
        var lines = readLines(file);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        part1();
        part2();
    }

    private static boolean isKnownInput(String input, List<Input> inputs) {
        return inputs.stream().anyMatch(knownInput -> knownInput.getName().equals(input));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Input {
        String value;
        String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OutputLine {
        String input1;
        String input2;
        String operator;
        String output;
    }
}
