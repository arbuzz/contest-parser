package util;

import com.ning.http.client.AsyncHttpClient;

/**
 * This code is brought to you by
 *
 * @author Krivinchenko Oxana
 */
public class HttpClientHolder {

    private static volatile AsyncHttpClient instance;

        public static AsyncHttpClient getInstance() {
        AsyncHttpClient localInstance = instance;
        if (localInstance == null) {
            synchronized (AsyncHttpClient.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new AsyncHttpClient();
                }
            }
        }
        return localInstance;
    }
}
