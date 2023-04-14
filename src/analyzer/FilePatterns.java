package analyzer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilePatterns {

    public static List<FilePattern> getPatternListFromFile(String filePath) {
        List<FilePattern> patternList = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Path.of(filePath))) {
            patternList = stream.map(FilePatterns::getPatternFromLine).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        patternList.sort(Comparator.comparingInt(FilePattern::priorityLevel).reversed());
        return patternList;
    }

    private static FilePattern getPatternFromLine(String line) {
        String[] parts = line.replaceAll("\"", "").split(";");
        int priority = Integer.parseInt(parts[0]);
        String pattern = parts[1];
        String name = parts[2];
        return new FilePattern(name, pattern, priority);
    }
}
