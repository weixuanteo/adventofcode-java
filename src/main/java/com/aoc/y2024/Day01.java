import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class Day01 {

    public static void main(String[] args) {
        new Day01().run();
    }

    public void run() {
        Object part1Answer = part1();
        System.out.println("Part 1: " + part1Answer);
        Object part2Answer = part2();
        System.out.println("Part 2: " + part2Answer);
    }

    public Object part1() {
        List<List<Integer>> listOfPairs = parseInput();
        List<Integer> aList = listOfPairs.get(0);
        List<Integer> bList = listOfPairs.get(1);

        Collections.sort(aList);
        Collections.sort(bList);

        int totalDistances = 0;

        for (int i = 0; i < aList.size(); i++) {
            int diff = Math.abs(aList.get(i) - bList.get(i));
            totalDistances += diff;
        }

        return totalDistances;
    }

    public Object part2() {
        List<List<Integer>> listOfPairs = parseInput();
        List<Integer> aList = listOfPairs.get(0);
        List<Integer> bList = listOfPairs.get(1);
        int similarityScore = 0;

        // build a count map for bList
        HashMap<Integer, Integer> bListFrequencyMap = new HashMap<
            Integer,
            Integer
        >();
        for (int num : bList) {
            bListFrequencyMap.put(
                num,
                bListFrequencyMap.getOrDefault(num, 0) + 1
            );
        }

        for (int num : aList) {
            similarityScore += num * bListFrequencyMap.getOrDefault(num, 0);
        }

        return similarityScore;
    }

    private Stream<String> getInput() {
        // TODO: figure out a way to get the file properly?, i think there should be a better method
        Path path = Paths.get("../../../../resources/2024/day01/input.txt");
        try {
            return Files.lines(path);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        return Stream.empty();
    }

    private List<List<Integer>> parseInput() {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List<Integer> aList = new ArrayList<Integer>();
        List<Integer> bList = new ArrayList<Integer>();
        // TODO: feel like there's a one liner way to put them into two list
        List<String> pairs = getInput().filter(l -> !l.isBlank()).toList();
        for (String pair : pairs) {
            String[] pairList = pair.trim().split("\\s+");
            int a, b;
            try {
                a = Integer.parseInt(pairList[0]);
                b = Integer.parseInt(pairList[1]);
                aList.add(a);
                bList.add(b);
            } catch (NumberFormatException e) {
                a = 0;
                b = 0;
            }
        }
        result.add(aList);
        result.add(bList);

        return result;
    }
}
