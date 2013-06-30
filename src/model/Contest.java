package model;

import util.Util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * This code is brought to you by
 *
 * @author Olshanikov Konstantin
 */
public class Contest {

    private static final int MAX_DIST = 3;

    public String name;

    public String link;

    public String article;

    public int rate = 0;

    public int count = 0;

    @Override
    public String toString() {
        return name + "(" + getCompliance() + ")";
    }

    public void calculateRate(String[] query) {
        String[] words = article.replaceAll("\n", "").split(" ");
        Map<String, Integer> wordCount = new HashMap<String, Integer>();
        for (String word : words) {
            if (!wordCount.containsKey(word)) {
                wordCount.put(word, 0);
            }
            wordCount.put(word, wordCount.get(word) + 1);
        }

        for (String word : wordCount.keySet()) {
            for (String queryWord : query) {
                if (Util.dist(word, queryWord) <= MAX_DIST) {
                    rate += wordCount.get(word);
                    continue;
                }
            }
            if (word.length() > 3) {
                count++;
            }
        }
    }

    public double getCompliance() {
        return new BigDecimal(rate).divide(new BigDecimal(count), 2, RoundingMode.UP).doubleValue();
    }

    public static class RateComparator implements Comparator<Contest> {

        public int compare(Contest contest, Contest contest1) {
            return contest.rate > contest1.rate ? -1 : 1;
        }
    }
}
