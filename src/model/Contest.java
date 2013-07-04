package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.PageLoader;
import util.Util;

import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This code is brought to you by
 *
 * @author Krivinchenko Oxana
 */
public class Contest {

    public static Logger logger = LoggerFactory.getLogger(Contest.class);

    private static final int MAX_DIST = 3;

    public String name;

    public String link;

    public String article;

    public int rate = 0;

    public int count = 0;

    public String date;

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

    public String getInfoFileName() {
        if (link != null) {
            return PageLoader.CONTESTS_FOLDER + Util.md5(link);
        }
        return null;
    }

    public boolean getArticleFromFile() {
        try {
            StringBuffer fileData = new StringBuffer();
            BufferedReader reader = new BufferedReader(
                    new FileReader(getInfoFileName()));
            char[] buf = new char[1024];
            int numRead = 0;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
            }
            reader.close();
            this.article = fileData.toString();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void saveContestInfo() {
        if (link == null) {
            return;
        }
        File dir = new File(PageLoader.CONTESTS_FOLDER);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(getInfoFileName());
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    file.createNewFile();
                    FileWriter writer = new FileWriter(file);
                    writer.write(article);
                    writer.close();
                } else {
                    logger.error("Can't create file " + file.getName());
                }
            } catch (Exception e) {
                logger.error("Error while writing contest info to file");
            }
        }
    }

    public double getCompliance() {
        return new BigDecimal(rate).divide(new BigDecimal(count), 2, RoundingMode.UP).doubleValue();
    }

    public static class RateComparator implements Comparator<Contest> {

        public int compare(Contest contest, Contest contest1) {
            return contest.getCompliance() > contest1.getCompliance() ? -1 : 1;
        }
    }
}
