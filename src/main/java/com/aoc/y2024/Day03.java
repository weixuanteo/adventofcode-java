import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day03 {

    public static void main(String[] args) {
        new Day03().run();
    }

    private void run() {
        Object part1Answer = part1();
        System.out.println("Part 1: " + part1Answer);
        Object part2Answer = part2();
        System.out.println("Part 2: " + part2Answer);
    }

    private Object part1() {
        List<String> instructions = getInput().toList();
        int result = 0;
        for (String instruction : instructions) {
            for (int i = 0; i < instruction.length(); i++) {
                if (instruction.charAt(i) != 'm') {
                    continue;
                }
                // walk to see if it's a valid instruction
                int endPos = walkChar(i, instruction);
                if (i != endPos) {
                    System.out.println(
                        "valid instruction found: " +
                        instruction.substring(i, endPos + 1)
                    );
                    String[] split = instruction
                        .substring(i, endPos + 1)
                        .split(",");
                    int left = 0;
                    int right = 0;
                    // should be safe
                    left = Integer.parseInt(split[0].substring(4));
                    right = Integer.parseInt(
                        split[1].substring(0, split[1].length() - 1)
                    );
                    result += left * right;
                }
            }
        }
        return result;
    }

    private Object part2() {
        return 0;
    }

    // TODO: this can be refined into using a tokenizer and then parse it
    private int walkChar(int startPos, String instructions) {
        String mulToken = "mul";
        char lParen = '(';
        char rParen = ')';
        char comma = ',';
        int readPos = startPos;
        readPos += 2;
        if (readPos + 1 > instructions.length()) {
            return startPos;
        }
        if (!instructions.substring(startPos, readPos + 1).equals(mulToken)) {
            return startPos;
        }
        readPos += 1;
        if (readPos >= instructions.length()) {
            return startPos;
        }
        if (instructions.charAt(readPos) != lParen) {
            return startPos;
        }

        readPos += 1;
        // split into two parts, walk till "," is found
        // then find the second part by walking till ")" is found

        int leftStartPos = readPos;
        int leftEndPos = readPos;
        while (readPos < instructions.length()) {
            if (instructions.charAt(readPos) == comma) {
                leftEndPos = readPos - 1;
                break;
            }
            readPos += 1;
        }

        readPos += 1;
        int rightStartPos = readPos;
        int rightEndPos = readPos;
        while (readPos < instructions.length()) {
            if (instructions.charAt(readPos) == rParen) {
                rightEndPos = readPos - 1;
                break;
            }
            readPos += 1;
        }

        // check left and right are valid
        if (
            isValidDigits(
                instructions.substring(leftStartPos, leftEndPos + 1)
            ) &&
            isValidDigits(
                instructions.substring(rightStartPos, rightEndPos + 1)
            )
        ) {
            return readPos;
        }

        // debug: check if i missed anything
        // System.out.println(
        //     "possible miss: " + instructions.substring(startPos, readPos + 1)
        // );

        return startPos; // not found
    }

    private Boolean isValidDigits(String digits) {
        if (digits.length() > 3) {
            return false;
        }
        try {
            Integer.parseInt(digits);
        } catch (NumberFormatException e) {
            System.out.printf("parse int %s, %s", digits, e);
            return false;
        }
        return true;
    }

    private Stream<String> getInput() {
        // TODO: figure out a way to get the file properly?, i think there should be a better method
        Path path = Paths.get("../../../../resources/2024/day03/input.txt");
        try {
            return Files.lines(path);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        return Stream.empty();
    }
}
