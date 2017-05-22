package ba.unsa.etf.rma.elvircrn.rma17_17455;

import android.os.AsyncTask;
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

public class SearchArtist extends AsyncTask<String, Integer, Void> {
    MusicianDTO[] musicians;

    private IOnMusicianSearchDone caller;

    public SearchArtist(IOnMusicianSearchDone caller) {
        this.caller = caller;
    }

    @Override
    protected void onPostExecute(Void v) {
        caller.onDone(musicians);
    }

    @Override
    protected Void doInBackground(String... params) {
        String query;
        URL url;
        try {
            query = URLEncoder.encode(params[0], "utf-8");
            String url1 = "https://api.spotify.com/v1/search?q=" + query + "&type=artist";
            url = new URL(url1);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String result = Helpers.convertStreamToString(in);

            JsonObject jObj = (JsonObject)new JsonParser().parse(result);
            String str = jObj.getAsJsonObject("artists").getAsJsonArray("items").toString();
            Log.d("jObj: ", str);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface IOnMusicianSearchDone {
        public void onDone(MusicianDTO[] res);
    }
}
