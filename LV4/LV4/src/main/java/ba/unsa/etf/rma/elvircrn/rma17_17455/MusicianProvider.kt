package ba.unsa.etf.rma.elvircrn.rma17_17455

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri

val ALLROWS = 1
val ONEROW = 2

var matcher = UriMatcher(UriMatcher.NO_MATCH)
    get() {
        var uM = UriMatcher(UriMatcher.NO_MATCH)
        uM.addURI("rma.provider.musicians", "elements", ALLROWS)
        uM.addURI("rma.provider.muzicari", "elements/#", ONEROW)
        return uM
    }

class MusicianProvider : ContentProvider() {
    override fun insert(uri: Uri?, values: ContentValues?): Uri? {
        return null
    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        var baza: SQLiteDatabase?
        try {
            baza = mHelper?.writableDatabase
        } catch (e: Exception) {
            baza = mHelper?.readableDatabase
        }
        var squery = SQLiteQueryBuilder()
        if (matcher.match(uri) == ONEROW) {
            var idreda = uri?.pathSegments?.get(1)
            squery.appendWhere("${MuzicarDBOpenHelper.MUZICAR_ID} = $idreda")
        }
        squery.tables = MuzicarDBOpenHelper.DATABASE_TABLE
        return squery.query(baza, projection, selection, selectionArgs, null, null, sortOrder)
    }

    override fun onCreate(): Boolean {
        mHelper = MuzicarDBOpenHelper(context,
                MuzicarDBOpenHelper.DATABASE_NAME,
                null,
                MuzicarDBOpenHelper.DATABASE_VERSION)
        return false
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun getType(uri: Uri?): String {
        when (matcher.match(uri)) {
            ALLROWS -> {
                return "vnd.android.cursor.dir/vnd.rma.elemental"
            }
            ONEROW -> {
                return "vnd.android.cursor.item/vnd.rma.elemental"
            }
            else -> {
                throw IllegalArgumentException("Unsupported uri: " + uri.toString())
            }
        }
    }

    var mHelper: MuzicarDBOpenHelper? = null

}