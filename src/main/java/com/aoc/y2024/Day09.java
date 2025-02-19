import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day09 {

    public static void main(String[] args) {
        new Day09().run();
    }

    private void run() {
        long part1StartTime = System.currentTimeMillis();
        Object part1Answer = part1();
        System.out.println("Part 1: " + part1Answer + ", Time taken: " + (System.currentTimeMillis() - part1StartTime) + " ms");
        long part2StartTime = System.currentTimeMillis();
        Object part2Answer = part2();
        System.out.println("Part 2: " + part2Answer + ", Time taken: " + (System.currentTimeMillis() - part2StartTime) + " ms");
    }

    private Object part1() {
        String diskMap = getInput().toList().getFirst();

        int id = 0;
        List<String> layout = new ArrayList<>();
        for (int i = 0; i < diskMap.length(); i += 2) {
            int length = Integer.parseInt(Character.toString(diskMap.charAt(i)));
            int freeSpace = 0;
            if ((i+1) < diskMap.length()) {
                freeSpace = Integer.parseInt(Character.toString(diskMap.charAt(i+1)));
            }
            for (int j = 0; j < length; j++) {
                layout.add(Integer.toString(id));
            }
            for (int j = 0; j < freeSpace; j++) {
                layout.add(".");
            }
            id += 1;
        }

        int l = 0;
        int r = layout.size() - 1;

        while (l < r) {
            while (l < r && !layout.get(l).equals(".")) {
                l++;
            }
            while (l < r && layout.get(r).equals(".")) {
                r--;
            }
            layout.set(l, layout.get(r));
            layout.set(r, ".");
            l++;
            r--;
        }

        return checksum(layout);
    }

    private Object part2() {
        String diskMap = getInput().toList().getFirst();

        int id = 0;
        Map<String,Integer> idSize = new HashMap<>();
        List<String> layout = new ArrayList<>();
        for (int i = 0; i < diskMap.length(); i += 2) {
            int length = Integer.parseInt(Character.toString(diskMap.charAt(i)));
            int freeSpace = 0;
            if ((i+1) < diskMap.length()) {
                freeSpace = Integer.parseInt(Character.toString(diskMap.charAt(i+1)));
            }
            for (int j = 0; j < length; j++) {
                layout.add(Integer.toString(id));
            }
            for (int j = 0; j < freeSpace; j++) {
                layout.add(".");
            }
            idSize.put(Integer.toString(id), length);
            id += 1;
        }

        // TODO: refine this in the future, can make it faster
        id--;
        while (id > 0) {
            int l = 0;
            int requiredSpace = idSize.get(Integer.toString(id));
            int currentId = id;
            int idp = 0;
            while (idp < layout.size() && !layout.get(idp).equals(Integer.toString(id))) {
                idp++;
            }
            while (l < idp) {
                while (l < idp && !layout.get(l).equals(".")) {
                    l++;
                }
                int freeSpace = 0;
                int fsp = l;
                while (fsp < idp && layout.get(fsp).equals(".")) {
                    freeSpace++;
                    fsp++;
                }
                if (freeSpace < requiredSpace) {
                    l = fsp;
                } else {
                    for (int i = 0; i < requiredSpace; i++) {
                        layout.set(l+i, Integer.toString(id));
                        layout.set(idp+i, ".");
                    }
                    id--;
                    l = idp; // exit
                }
            }
            if (currentId == id) {
                // did not find an available space
                id--;
            }
        }

        return checksum(layout);
    }

    private Long checksum(List<String> layout) {
        long checksum = 0;
        for (int i = 0; i < layout.size(); i++) {
            if (layout.get(i).equals(".")) {
                continue;
            }
            checksum += i * Long.parseLong(layout.get(i));
        }
        return checksum;
    }

    private Stream<String> getInput() {
        Path inputPath = Paths.get(
                "../../../../resources/2024/day09/input.txt"
        );
        try {
            return Files.lines(inputPath);
        } catch (IOException e) {
            System.out.println("Reading from input file: " + e);
        }
        return Stream.empty();
    }

}
