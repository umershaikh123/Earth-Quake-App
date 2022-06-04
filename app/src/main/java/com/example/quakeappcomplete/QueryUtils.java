package com.example.quakeappcomplete;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public final class QueryUtils {

    /** Sample JSON response for a USGS query */

    public static final String LOG_TAG = MainActivity.class.getName();
    private QueryUtils() {
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);;
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }




    public static  List<Earthquake> extractFeatureFromJson(String earthquakeJSON) {

         List<Earthquake> earthquakes = new ArrayList<>();
        String Location = "";
        String city = "";

        try {

            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
            JSONArray earthquakeArray = baseJsonResponse.optJSONArray("features");

            for (int i = 0; i < Objects.requireNonNull(earthquakeArray).length(); i++) {

                JSONObject currentObject = earthquakeArray.getJSONObject(i);
                JSONObject properties = currentObject.getJSONObject("properties");

                double magnitude = properties.optDouble("mag")  ;
                DecimalFormat formatter = new DecimalFormat("0.00");
                double mag = Double.parseDouble(formatter.format(magnitude));


                String location = properties.optString("place") ;
                String spliter = " of ";

                if(location.contains(spliter)) {

                    String[] place = location.split(spliter);
                    Location = place[0]+" OF ";
                    city = place[1];
                    // F_place = " "+Location+" of\n"+city;
                }



                long timeInMilliseconds = properties.optInt("time");

                Date dateObject = new Date(timeInMilliseconds);
                SimpleDateFormat dateFormatter = new SimpleDateFormat("LLL dd, yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");

                String date = dateFormatter.format(dateObject);
                String time = timeFormat.format(dateObject);

                String url = properties.optString("url") ;

                Earthquake earthquake = new Earthquake(mag , Location , city ,date, time , url);
                earthquakes.add(earthquake);

            }

        }

        catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }


        return earthquakes;
    }//method


    public static  List<Earthquake> fetchEarthquakeData(String requestUrl) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Create URL object
        Log.i("QueryUtils" , "fetchEarthqakeData is initialized ");
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
         List<Earthquake> earthquakes = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return earthquakes;

    }


}//class