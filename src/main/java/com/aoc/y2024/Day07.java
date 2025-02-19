import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day07 {

    public static void main(String[] args) {
        new Day07().run();
    }

    private void run() {
        Object part1Answer = part1();
        System.out.println("Part 1: " + part1Answer);
        Object part2Answer = part2();
        System.out.println("Part 2: " + part2Answer);
    }

    private Object part1() {
        List<String> lines = getInput().toList();
        long totalCalibrationResult = 0;
        for (String line : lines) {
            String[] equation = line.split(":");
            String testValue = equation[0];
            String[] values = equation[1].trim().split(" ");

            List<String> possiblePositions = new ArrayList<>();
            generateOperationPositions(possiblePositions, new char[]{'*', '+'}, "", values.length - 1);

            for (String position : possiblePositions) {
                long a = Long.parseLong(values[0]);
                long b = Long.parseLong(values[1]);
                a = evaluate(a, b, position.charAt(0));
                for (int i = 1; i < position.length(); i++) {
                    b = Long.parseLong(values[i+1]);
                    a = evaluate(a, b, position.charAt(i));

                }

                if (a == Long.parseLong(testValue)) {
                    totalCalibrationResult += a;
//                    System.out.println(Arrays.toString(values) + "|" + position + "=" + a);
                    break;
                }
            }
        }
        return totalCalibrationResult;
    }

    private Object part2() {
        List<String> lines = getInput().toList();
        long totalCalibrationResult = 0;
        for (String line : lines) {
            String[] equation = line.split(":");
            String testValue = equation[0];
            String[] values = equation[1].trim().split(" ");

            List<String> possiblePositions = new ArrayList<>();
            generateOperationPositions(possiblePositions, new char[]{'*', '+', '|'}, "", values.length - 1);

            for (String position : possiblePositions) {
                long a = Long.parseLong(values[0]);
                long b = Long.parseLong(values[1]);
                a = evaluate(a, b, position.charAt(0));
                for (int i = 1; i < position.length(); i++) {
                    b = Long.parseLong(values[i+1]);
                    a = evaluate(a, b, position.charAt(i));

                }

                if (a == Long.parseLong(testValue)) {
                    totalCalibrationResult += a;
//                    System.out.println(Arrays.toString(values) + "|" + position + "=" + a);
                    break;
                }
            }
        }
        return totalCalibrationResult;
    }

    private void generateOperationPositions(List<String> possiblePositions, char[] operators, String positions, int length) {
        if (positions.length() == length) {
            possiblePositions.add(positions);
        } else {
            for (char operator : operators) {
                generateOperationPositions(possiblePositions, operators, positions + operator, length);
            }
        }
    }

    private long evaluate(long a, long b, char operator) {
        return switch (operator) {
            case '*' -> a * b;
            case '+' -> a + b;
            case '|' -> Long.parseLong(a + "" + b); // cheat way? maybe can challenge myself to generate this via math
            default -> 0;
        };
    }

    private Stream<String> getInput() {
        Path inputPath = Paths.get(
                "../../../../resources/2024/day07/input.txt"
        );
        try {
            return Files.lines(inputPath);
        } catch (IOException e) {
            System.out.println("Reading from input file: " + e);
        }
        return Stream.empty();
    }
}
