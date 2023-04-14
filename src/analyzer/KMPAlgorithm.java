package analyzer;

public class KMPAlgorithm implements SearchAlgorithm{
    private final String text;
    private final char[] pattern;

    public KMPAlgorithm(byte[] bytes, String pattern) {
        this.pattern = pattern.toCharArray();
        this.text = new String(bytes);
    }

    @Override
    public boolean find() {
        int[] p = getPrefixFunc();
        String subString = text;
        int index = 0;
        while (true) {
            int matchCount = 0;
            subString = subString.substring(index);
            if (subString.length() < pattern.length) {
                break;
            }
            for (int i = 0; i < pattern.length; i++) {
                if (pattern[i] == subString.charAt(i)) {
                    if (++matchCount == pattern.length) return true;
                } else if (matchCount == 0) {
                    index = 1;
                    break;
                } else {
                    index = matchCount - p[matchCount - 1];
                    break;
                }
            }
        }
        return false;
    }

    private int[] getPrefixFunc() {
        int[] prefixFunc = new int[pattern.length];
        prefixFunc[0] = 0;
        for (int i = 1; i < pattern.length; i++) {
            int j = prefixFunc[i - 1];
            while (true) {
                if (pattern[j] == pattern[i]) {
                    prefixFunc[i] = j + 1;
                    break;
                } else if (j == 0) {
                    prefixFunc[i] = 0;
                    break;
                } else {
                    j = prefixFunc[j - 1];
                }
            }
        }
        return prefixFunc;
    }
}
