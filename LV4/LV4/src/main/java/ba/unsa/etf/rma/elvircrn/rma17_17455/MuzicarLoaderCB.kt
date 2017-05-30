package ba.unsa.etf.rma.elvircrn.rma17_17455

import android.app.LoaderManager
import android.content.Context
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.widget.SimpleCursorAdapter

val LOADER1_ID = 1

class MuzicarLoaderCB(cont: Context, ua: UpdateAdapter) : LoaderManager.LoaderCallbacks<Cursor> {
    var cont: Context? = null
    var ua: UpdateAdapter? = null
    var scAdapter: SimpleCursorAdapter? = null
    var mUri = Uri.parse("content://rma.provider.muzicari/elements")
    val PROJECTION = arrayOf(
            MuzicarDBOpenHelper.MUZICAR_ID,
            MuzicarDBOpenHelper.MUZICAR_IME,
            MuzicarDBOpenHelper.MUZICAR_ZANR
    )

    override fun onCreateLoader(i: Int, bundle: Bundle): Loader<Cursor>? =
            if (i == LOADER1_ID)
                CursorLoader(cont, mUri, PROJECTION, null, null, null)
            else
                null


    override fun onLoaderReset(loader: Loader<Cursor>?) {
        ua?.update(null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        if (loader?.id == LOADER1_ID)
            ua?.update(data)
    }
}