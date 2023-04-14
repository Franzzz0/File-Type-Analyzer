package analyzer;

import java.util.regex.Pattern;

public class MatcherAlg implements SearchAlgorithm{
    private final String text;
    private final Pattern pattern;

    public MatcherAlg(byte[] bytes, String pattern) {
        this.text = new String(bytes);
        this.pattern = Pattern.compile(pattern);
    }

    @Override
    public boolean find() {
        return pattern.matcher(this.text).find();
    }
}
