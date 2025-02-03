import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day02 {

    public static void main(String[] args) {
        new Day02().run();
    }

    public void run() {
        Object part1Answer = part1();
        System.out.println("Part 1: " + part1Answer);
        Object part2Answer = part2();
        System.out.println("Part 2: " + part2Answer);
    }

    public Object part1() {
        int totalSafe = 0;
        List<String> reports = parseInput();
        for (String report : reports) {
            if (isSafe(report)) {
                totalSafe += 1;
            }
        }

        return totalSafe;
    }

    public Object part2() {
        int totalSafe = 0;
        List<String> reports = parseInput();
        for (String report : reports) {
            if (isSafeWithDampener(report)) {
                totalSafe += 1;
            }
        }

        return totalSafe;
    }

    private boolean isSafe(String report) {
        String[] levels = report.split(" ");
        for (int i = 1; i < levels.length; i++) {
            int diff =
                Integer.parseInt(levels[i]) - Integer.parseInt(levels[i - 1]);
            if (
                Math.abs(diff) < 1 ||
                Math.abs(diff) > 3 ||
                // current pair is descending, ensure the previous pair is descending order
                (diff > 0 &&
                    i > 1 &&
                    Integer.parseInt(levels[i - 1]) <
                    Integer.parseInt(levels[i - 2])) ||
                // current pair is ascending, ensure the previous pair is ascending
                (diff < 0 &&
                    i > 1 &&
                    Integer.parseInt(levels[i - 1]) >
                    Integer.parseInt(levels[i - 2]))
            ) {
                // System.out.println(
                //     "mark as unsafe for levels " + Arrays.toString(levels)
                // );
                return false;
            }
        }
        return true;
    }

    private String removeLevel(String[] levels, int index) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < levels.length; i++) {
            if (i != index) {
                sb.append(levels[i]).append(" ");
            }
        }
        return sb.toString().trim();
    }

    private boolean isSafeWithDampener(String report) {
        if (isSafe(report)) {
            return true;
        }
        String[] levels = report.split(" ");
        for (int i = 0; i < levels.length; i++) {
            if (isSafe(removeLevel(levels, i))) {
                return true;
            }
        }
        return false;
    }

    private Stream<String> getInput() {
        // TODO: figure out a way to get the file properly?, i think there should be a better method
        Path path = Paths.get("../../../../resources/2024/day02/input.txt");
        try {
            return Files.lines(path);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        return Stream.empty();
    }

    private List<String> parseInput() {
        List<String> reports = getInput().filter(l -> !l.isBlank()).toList();
        return reports;
    }
}
