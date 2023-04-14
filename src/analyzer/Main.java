package analyzer;

public class Main {
    public static void main(String[] args) {
        ArgsParser parser = new ArgsParser();
        parser.parse(args);
        FileTypeChecker checker = new FileTypeChecker(parser);
        checker.checkAllFilesInDirectory();
    }
}
