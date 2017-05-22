package ba.unsa.etf.rma.elvircrn.rma17_17455;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Amar.B on 16.05.2017..
 */

public class MyService extends IntentService {

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;
    public MyService() {
        super(null);
    }

    public MyService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");

        Bundle bundle = new Bundle();

        String params = intent.getStringExtra("textQuerya");
        String query;
        URL url;
        String results = null;
        try {
            query = URLEncoder.encode(params, "utf-8");
            String url1 = "https://api.spotify.com/v1/search?q=" + query + "&type=artist";
            url = new URL(url1);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String result = Helpers.convertStreamToString(in);
            JsonObject jObj = (JsonObject)new JsonParser().parse(result);
            results = jObj.getAsJsonObject("artists").getAsJsonArray("items").toString();
            bundle.putString("results", results);
            receiver.send(STATUS_FINISHED, bundle);
        } catch (Exception e) {
            bundle.putString(Intent.EXTRA_TEXT, e.toString());
            receiver.send(STATUS_ERROR, bundle);

        }
    }


}
