package ba.unsa.etf.rma.elvircrn.rma17_17455

import android.os.AsyncTask
import android.util.Log

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser

import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

class SearchArtist(private val caller: SearchArtist.IOnMusicianSearchDone) : AsyncTask<String, Int, Void>() {
    internal var musicians: Array<MusicianDTO>? = null

    override fun onPostExecute(v: Void) {
        caller.onDone(musicians!!)
    }

    override fun doInBackground(vararg params: String): Void? {
        val query: String
        val url: URL
        try {
            query = URLEncoder.encode(params[0], "utf-8")
            val url1 = "https://api.spotify.com/v1/search?q=$query&type=artist"
            url = URL(url1)
            val urlConnection = url.openConnection() as HttpURLConnection
            val inStream = BufferedInputStream(urlConnection.inputStream)
            val result = Helpers.convertStreamToString(inStream)
            val jObj = JsonParser().parse(result) as JsonObject
            val str = jObj.getAsJsonObject("artists").getAsJsonArray("items").toString()
            Log.d("jObj: ", str)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    interface IOnMusicianSearchDone {
        fun onDone(res: Array<MusicianDTO>)
    }
}
