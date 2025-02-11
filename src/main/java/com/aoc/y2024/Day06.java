import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Day06 {

    private char[] directions = new char[] { '^', '>', 'V', '<' };

    private Map<Character, int[]> directionsMap = new HashMap<>() {
        {
            put('^', new int[] { -1, 0 });
            put('>', new int[] { 0, 1 });
            put('V', new int[] { 1, 0 });
            put('<', new int[] { 0, -1 });
        }
    };

    public static void main(String[] args) {
        new Day06().run();
    }

    private void run() {
        Object part1Answer = part1();
        System.out.println("Part 1: " + part1Answer);
        Object part2Answer = part2();
        System.out.println("Part 2: " + part2Answer);
    }

    private Object part1() {
        List<String> map = getInput().toList();
        List<String> mapCopy = new ArrayList<>(map);
        for (int y = 0; y < map.size(); y++) {
            String line = map.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '^') {
                    getGuardPath(mapCopy, new int[] { y, x });
                }
            }
        }

        int positions = 0;
        for (int y = 0; y < map.size(); y++) {
            String line = mapCopy.get(y);
            System.out.println(line);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == 'X') {
                    positions += 1;
                }
            }
        }
        return positions;
    }

    private Object part2() {
        return 0;
    }

    private void getGuardPath(List<String> map, int[] startPosition) {
        int[] currentPosition = Arrays.copyOf(startPosition, 2);
        char currentDirectionSymbol = map
            .get(startPosition[0])
            .charAt(startPosition[1]);
        int[] currentDirection = directionsMap.get(currentDirectionSymbol);

        while (
            isValidPosition(currentPosition, map.size(), map.get(0).length())
        ) {
            // mark current position with X
            markPosition(map, currentPosition, 'X');

            // look ahead to check for obstacle
            int[] nextPosition = Arrays.copyOf(currentPosition, 2);
            nextPosition[0] += currentDirection[0];
            nextPosition[1] += currentDirection[1];
            if (
                !isValidPosition(nextPosition, map.size(), map.get(0).length())
            ) {
                return;
            }
            if (map.get(nextPosition[0]).charAt(nextPosition[1]) == '#') {
                // change direction
                currentDirectionSymbol = getNextDirection(
                    currentDirectionSymbol
                );
                currentDirection = directionsMap.get(currentDirectionSymbol);
            }

            currentPosition[0] += currentDirection[0];
            currentPosition[1] += currentDirection[1];
        }
    }

    private void markPosition(List<String> map, int[] position, char marker) {
        String line = map.get(position[0]);
        String newLine =
            line.substring(0, position[1]) +
            Character.toString(marker) +
            line.substring(position[1] + 1);
        map.set(position[0], newLine);
    }

    private char getNextDirection(char direction) {
        int newCharIndex = -1;
        for (int i = 0; i < directions.length; i++) {
            if (directions[i] == direction) {
                newCharIndex = i + 1;
            }
        }
        if (newCharIndex == directions.length) {
            newCharIndex = 0;
        }
        return directions[newCharIndex];
    }

    private Boolean isValidPosition(int[] position, int yMax, int xMax) {
        int y = position[0];
        int x = position[1];
        if (y < 0 || y >= yMax || x < 0 || x >= xMax) {
            return false;
        }
        return true;
    }

    private Stream<String> getInput() {
        Path inputPath = Paths.get(
            "../../../../resources/2024/day06/input.txt"
        );
        try {
            return Files.lines(inputPath);
        } catch (IOException e) {
            System.out.println("Reading from input file: " + e);
        }
        return Stream.empty();
    }
}
