package ba.unsa.etf.rma.elvircrn.rma17_17455

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.support.v4.os.ResultReceiver
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

class MyService : IntentService {
    constructor() : super(null) {}

    constructor(name: String) : super(name) {}

    override fun onCreate() {
        super.onCreate()

    }

    override fun onHandleIntent(intent: Intent?) {
        val receiver = intent!!.getParcelableExtra<ResultReceiver>("receiver")

        val bundle = Bundle()

        val params = intent.getStringExtra("textQuerya")
        val query: String
        val url: URL
        var results: String? = null
        try {
            query = URLEncoder.encode(params, "utf-8")
            val url1 = "https://api.spotify.com/v1/search?q=$query&type=artist"
            url = URL(url1)
            val urlConnection = url.openConnection() as HttpURLConnection
            val `in` = BufferedInputStream(urlConnection.inputStream)
            val result = Helpers.convertStreamToString(`in`)
            val jObj = JsonParser().parse(result) as JsonObject
            results = jObj.getAsJsonObject("artists").getAsJsonArray("items").toString()
            bundle.putString("results", results)
            receiver.send(STATUS_FINISHED, bundle)
        } catch (e: Exception) {
            bundle.putString(Intent.EXTRA_TEXT, e.toString())
            receiver.send(STATUS_ERROR, bundle)

        }

    }

    companion object {

        val STATUS_RUNNING = 0
        val STATUS_FINISHED = 1
        val STATUS_ERROR = 2
    }


}
