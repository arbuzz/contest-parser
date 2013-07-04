package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * This code is brought to you by
 *
 * @author Krivinchenko Oxana
 */
public class Util {

    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    private static final String QUERY_FILE_NAME = "query.conf";

    public static String[] getQuery() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(QUERY_FILE_NAME), "UTF-8"));
        try {
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();

            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
            return sb.toString().split(",");
        } finally {
            reader.close();
        }
    }

    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes(), 0, input.length());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            logger.error("This really should not happen! Error while getting MD5", e);
        }
        return null;
    }


    public static int dist(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[] D1;
        int[] D2 = new int[n + 1];

        for (int i = 0; i <= n; i++)
            D2[i] = i;

        for (int i = 1; i <= m; i++) {
            D1 = D2;
            D2 = new int[n + 1];
            for (int j = 0; j <= n; j++) {
                if (j == 0) D2[j] = i;
                else {
                    int cost = (s1.charAt(i - 1) != s2.charAt(j - 1)) ? 1 : 0;
                    if (D2[j - 1] < D1[j] && D2[j - 1] < D1[j - 1] + cost)
                        D2[j] = D2[j - 1] + 1;
                    else if (D1[j] < D1[j - 1] + cost)
                        D2[j] = D1[j] + 1;
                    else
                        D2[j] = D1[j - 1] + cost;
                }
            }
        }
        return D2[n];
    }


}
