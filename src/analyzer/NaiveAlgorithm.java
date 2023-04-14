package analyzer;

public class NaiveAlgorithm implements SearchAlgorithm{
    private final String text;
    private final String pattern;

    public NaiveAlgorithm(byte[] bytes, String pattern) {
        this.text = new String(bytes);
        this.pattern = pattern;
    }

    @Override
    public boolean find() {
        int lastIndex = this.text.length() - pattern.length();
        String subString = text;
        for (int i = 0; i < lastIndex; i++) {
            subString = subString.substring(1);
            System.out.println(subString.length());
            if (subString.startsWith(pattern)) return true;
        }
        return false;
    }
}
