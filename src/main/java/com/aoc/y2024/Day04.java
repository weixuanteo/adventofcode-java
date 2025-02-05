import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day04 {

    private int found = 0;

    // top, bottom, left, right, diag top left, diag top right, diag bot left, diag bot right (y, x format)
    private int[][] directions = new int[][] {
        { -1, 0 },
        { 1, 0 },
        { 0, -1 },
        { 0, 1 },
        { -1, -1 },
        { -1, 1 },
        { 1, -1 },
        { 1, 1 },
    };

    public static void main(String[] args) {
        new Day04().run();
    }

    private void run() {
        Object part1Answer = part1();
        System.out.println("Part 1: " + part1Answer);
        Object part2Answer = part2();
        System.out.println("Part 2: " + part2Answer);
    }

    private Object part1() {
        List<String> words = getInput().toList();
        boolean[][] grid = new boolean[words.size()][words.get(0).length()];
        for (int i = 0; i < words.size(); i++) {
            for (int y = 0; y < words.get(0).length(); y++) {
                grid[i][y] = false;
            }
        }

        for (int y = 0; y < words.size(); y++) {
            for (int x = 0; x < words.get(y).length(); x++) {
                int[] currentIndex = new int[] { y, x };
                wordSearch(currentIndex, words, "XMAS", grid);
            }
        }

        // for debugging
        // for (int i = 0; i < words.size(); i++) {
        //     String line = "";
        //     for (int y = 0; y < words.get(0).length(); y++) {
        //         if (grid[i][y]) {
        //             line += " x ";
        //         } else {
        //             line += " . ";
        //         }
        //     }
        //     System.out.println(line);
        // }

        return found;
    }

    private Object part2() {
        return 0;
    }

    // TODO: clean this up, its messy now
    private void wordSearch(
        int[] currentPosition,
        List<String> words,
        String searchWord,
        boolean[][] visited
    ) {
        if (
            words.get(currentPosition[0]).charAt(currentPosition[1]) !=
            searchWord.charAt(0)
        ) {
            return;
        }
        for (int[] direction : directions) {
            List<int[]> searchPositions = new ArrayList<>();
            int y = currentPosition[0];
            int x = currentPosition[1];
            String word = "";
            for (int i = 0; i < searchWord.length(); i++) {
                if (
                    !isValidPosition(
                        new int[] { y, x },
                        words.size(),
                        words.get(0).length()
                    )
                ) {
                    break;
                }
                word += words.get(y).charAt(x);
                searchPositions.add(new int[] { y, x });
                y += direction[0];
                x += direction[1];
            }
            if (word.length() != searchWord.length()) {
                continue;
            }
            if (!word.equals(searchWord)) {
                continue;
            }
            found += 1;
            for (int[] searchPosition : searchPositions) {
                visited[searchPosition[0]][searchPosition[1]] = true;
            }
        }

        return;
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
            "../../../../resources/2024/day04/input.txt"
        );
        try {
            return Files.lines(inputPath);
        } catch (IOException e) {
            System.out.println("Reading from input file: " + e);
        }
        return Stream.empty();
    }
}
