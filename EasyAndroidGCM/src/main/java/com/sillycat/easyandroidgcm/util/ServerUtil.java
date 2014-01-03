package com.sillycat.easyandroidgcm.util;


import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/**
 * Created by carl on 1/3/14.
 */
public class ServerUtil {
    private static final String TAG = "ServerUtil";

    private static final int MAX_ATTEMPTS = 5;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();

    /**
     * Register this account/device pair within the server.
     */
    public static void register(final String email, final String regId) {
        //Log.i(TAG, "registering device (regId = " + regId + ")");
        String serverUrl = "serverurl" + "/register";
        Map<String, String> params = new HashMap<String, String>();
        params.put("fromemail", email);
        params.put("regid", regId);
        // Once GCM returns a registration id, we need to register it in the
        // demo server. As the server might be down, we will retry it a couple
        // times.
        try {
            post(serverUrl, params, MAX_ATTEMPTS);
        } catch (IOException e) {
        }
    }

    /**
     * Unregister this account/device pair within the server.
     */
    public static void unregister(final String email) {
        //Log.i(TAG, "unregistering device (email = " + email + ")");
        String serverUrl = "serverurl" + "/unregister";
        Map<String, String> params = new HashMap<String, String>();
        params.put("fromemail", email);
        try {
            post(serverUrl, params, MAX_ATTEMPTS);
        } catch (IOException e) {
            // At this point the device is unregistered from GCM, but still
            // registered in the server.
            // We could try to unregister again, but it is not necessary:
            // if the server tries to send a message to the device, it will get
            // a "NotRegistered" error message and should unregister the device.
        }
    }

    /**
     * Send a message.
     */
    public static void send(String msg, String to) throws IOException {
        //Log.i(TAG, "sending message (msg = " + msg + ")");
        String serverUrl = "serverurl" + "/send";
        Map<String, String> params = new HashMap<String, String>();
        params.put("message", msg);
        params.put("fromemail", "fromemail");
        params.put("toemail", to);

        post(serverUrl, params, MAX_ATTEMPTS);
    }

    /**
     * Issue a POST request to the server.
     *
     * @param endpoint POST address.
     * @param params   request parameters.
     * @throws IOException propagated from POST.
     */
    private static void post(String endpoint, Map<String, String> params) throws IOException {
        URL url;
        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
        // constructs the POST body using the parameters
        while (iterator.hasNext()) {
            Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=').append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }
        String body = bodyBuilder.toString();
        //Log.v(TAG, "Posting '" + body + "' to " + url);
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            // handle the response
            int status = conn.getResponseCode();
            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * Issue a POST with exponential backoff
     */
    private static void post(String endpoint, Map<String, String> params, int maxAttempts) throws IOException {
        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
        for (int i = 1; i <= maxAttempts; i++) {
            //Log.d(TAG, "Attempt #" + i);
            try {
                post(endpoint, params);
                return;
            } catch (IOException e) {
                //Log.e(TAG, "Failed on attempt " + i + ":" + e);
                if (i == maxAttempts) {
                    throw e;
                }
                try {
                    Thread.sleep(backoff);
                } catch (InterruptedException e1) {
                    Thread.currentThread().interrupt();
                    return;
                }
                backoff *= 2;
            } catch (IllegalArgumentException e) {
                throw new IOException(e.getMessage(), e);
            }
        }
    }

}
