package analyzer;

public class ArgsParser {
    private String filesDirPath;
    private String patternsFilePath;

    public void parse(String[] args) {
        if (args.length < 2) {
            System.out.println("Unknown arguments provided");
        } else {
            this.filesDirPath = args[0];
            this.patternsFilePath = args[1];
        }
    }

    public String getFilesPath() {
        return filesDirPath;
    }

    public String getPatternFilePath() {
        return patternsFilePath;
    }
}
