package analyzer;

public class RKAlgorithm implements SearchAlgorithm{
    private final int A = 21;
    private final int M = 31;

    private final String text;
    private final String pattern;
    private final int patternHash;

    public RKAlgorithm(byte[] bytes, String pattern) {
        this.text = new String(bytes);
        this.pattern = pattern;
        this.patternHash = getHash(pattern);
    }

    @Override
    public boolean find() {
        if (pattern.length() > text.length()) return false;
        int endIndex = text.length();
        int startIndex = endIndex - pattern.length();        
        int subStringHash = getHash(text.substring(startIndex));
        
        while (true) {
            if (subStringHash == patternHash) {
                if (text.substring(startIndex, endIndex).equals(pattern)) return true;
            } else if (startIndex == 0) {
                return false;
            } else {
                startIndex--;
                endIndex--;
                subStringHash = getNextHash(subStringHash, startIndex, endIndex);
            }
        }
    }

    private int getNextHash(int lastHash, int startIndex, int endIndex) {
        return (int) (
                (
                        (lastHash - (text.charAt(endIndex) * Math.pow(A, endIndex - startIndex - 1)))
                        * A + text.charAt(startIndex)
                ) % M
        );
    }

    private int getHash(String string) {
        int hash = 0;
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            hash += chars[i] * Math.pow(A, i);
        }
        return hash % M;
    }
}
