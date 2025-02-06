import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Day05 {

    private class SafetyManual {

        private Map<String, List<String>> orderRules;
        private List<String[]> updates;

        SafetyManual() {
            orderRules = new HashMap<>();
            updates = new ArrayList<>();
        }

        private void addOrderRule(String x, String y) {
            List<String> rules = new ArrayList<>();
            if (orderRules.containsKey(x)) {
                rules = orderRules.get(x);
            }
            rules.add(y);
            orderRules.put(x, rules);
        }

        private void addUpdate(String[] update) {
            updates.add(update);
        }

        private List<String> getOrderRules(String pageNumber) {
            return orderRules.getOrDefault(pageNumber, new ArrayList<>());
        }

        private List<String[]> getUpdates() {
            return updates;
        }
    }

    public static void main(String[] args) {
        new Day05().run();
    }

    private void run() {
        Object part1Answer = part1();
        System.out.println("Part 1: " + part1Answer);
        Object part2Answer = part2();
        System.out.println("Part 2: " + part2Answer);
    }

    private Object part1() {
        SafetyManual safetyManual = parseInput();
        int totalPageNumber = 0;
        for (String[] update : safetyManual.getUpdates()) {
            String middle = checkUpdate(update, safetyManual);
            if (middle.isBlank()) {
                continue;
            }
            totalPageNumber += Integer.parseInt(middle);
        }
        return totalPageNumber;
    }

    private Object part2() {
        SafetyManual safetyManual = parseInput();
        int totalPageNumber = 0;
        for (String[] update : safetyManual.getUpdates()) {
            String middle = checkUpdate(update, safetyManual);
            if (middle.isBlank()) {
                String ok = middle;
                // super hacky way to get the answer (keep doing a first pass sort until the update is valid)
                // TODO: i feel like this can do better (maybe the sorting method is wrong)
                while (ok.isEmpty()) {
                    // "bubble sort" into a correct order and get the middle
                    for (int i = update.length - 1; i >= 0; i--) {
                        List<String> numOrderRule = safetyManual.getOrderRules(
                            update[i]
                        );
                        int newIndex = -1;
                        for (int j = i - 1; j >= 0; j--) {
                            if (numOrderRule.contains(update[j])) {
                                newIndex = j;
                            }
                        }
                        if (newIndex != -1) {
                            String tmp = update[i];
                            update[i] = update[newIndex];
                            update[newIndex] = tmp;
                        }
                    }
                    ok = checkUpdate(update, safetyManual);
                }

                totalPageNumber += Integer.parseInt(
                    update[Math.round(update.length / 2)]
                );
            }
        }
        return totalPageNumber;
    }

    private String checkUpdate(String[] update, SafetyManual safetyManual) {
        Set<String> seen = new HashSet<>();
        for (String pageNumber : update) {
            List<String> pageNumberRules = safetyManual.getOrderRules(
                pageNumber
            );
            for (String pageNumberRule : pageNumberRules) {
                if (seen.contains(pageNumberRule)) {
                    return "";
                }
            }
            seen.add(pageNumber);
        }
        // return the middle
        return update[Math.round(update.length / 2)];
    }

    private Stream<String> getInput() {
        Path inputPath = Paths.get(
            "../../../../resources/2024/day05/input.txt"
        );
        try {
            return Files.lines(inputPath);
        } catch (IOException e) {
            System.out.println("Reading from input file: " + e);
        }
        return Stream.empty();
    }

    private SafetyManual parseInput() {
        Boolean updateOp = false;
        SafetyManual safetyManual = new SafetyManual();
        List<String> lines = getInput().toList();
        for (String line : lines) {
            if (line.isBlank()) {
                updateOp = true;
                continue;
            }
            if (updateOp) {
                safetyManual.addUpdate(line.split(","));
                continue;
            }

            String[] orderRule = line.split("\\|");
            safetyManual.addOrderRule(orderRule[0], orderRule[1]);
        }

        return safetyManual;
    }
}
