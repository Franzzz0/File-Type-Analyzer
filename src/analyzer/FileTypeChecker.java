package analyzer;

import java.io.File;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.*;

public class FileTypeChecker {
    private final String baseDirPath;
    private final List<FilePattern> patternList;

    public FileTypeChecker(ArgsParser parser) {
        this.baseDirPath = parser.getFilesPath();
        this.patternList = FilePatterns.getPatternListFromFile(parser.getPatternFilePath());
    }

    public void checkAllFilesInDirectory() {
        File baseDir = new File(baseDirPath);
        File[] files = baseDir.listFiles();
        if (!baseDir.isDirectory() || files == null) {
            System.out.println("Not a directory!");
            return;
        }
        if (files.length == 0) {
            System.out.println("No files found in directory!");
            return;
        }
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<String> results = new ArrayList<>();
        try {
            List<Future<String>> futureResults = executor.invokeAll(getCallables(files));
            for (Future<String> future : futureResults) {
                results.add(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        results.forEach(System.out::println);
    }

    private List<Callable<String>> getCallables(File[] files) {
        return Arrays.stream(files).map(f -> (Callable<String>) () -> {
            byte[] bytes = Files.readAllBytes(f.toPath());
            for (FilePattern pattern : patternList) {
                SearchAlgorithm algorithm = new KMPAlgorithm(bytes, pattern.pattern());
                if (algorithm.find()) {
                    return f.getName() + ": " + pattern.name();
                }
            }
            return f.getName() + ": Unknown file type";
        }).toList();
    }
}
