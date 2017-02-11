package eu.laramartin.popularmovies.api;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by lara on 24/1/17.
 */

public class NetworkUtils {

    // http://api.themoviedb.org/3/movie/popular?api_key=[YOUR_API_KEY]
    private static final String LOG_TAG = NetworkUtils.class.getCanonicalName();

    public static URL buildUrl(String apiKey, String filterType) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(filterType)
                .appendQueryParameter("api_key", apiKey);
        URL url = null;
        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(LOG_TAG, "Built URI " + url);
        return url;
    }

    public static String getResponseFromHttpUrl(URL moviesRequestUrl) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) moviesRequestUrl.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String buildPosterUrl(String posterPath) {
        return "http://image.tmdb.org/t/p/w500/" + posterPath;
    }

    public static URL buildTrailersOrReviewsUrl(String apiKey, String id, String trailerOrReview) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(id)
                .appendPath(trailerOrReview)
                .appendQueryParameter("api_key", apiKey);
        URL url = null;
        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(LOG_TAG, "Built trailers URI " + url);
        return url;
    }

    public static String buildYouTubeUrl(String key) {
        String baseUrl = "https://www.youtube.com/watch?v=";
        return baseUrl + key;
    }
}
