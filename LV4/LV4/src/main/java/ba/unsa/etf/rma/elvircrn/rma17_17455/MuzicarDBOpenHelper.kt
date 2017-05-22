package ba.unsa.etf.rma.elvircrn.rma17_17455

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class MuzicarDBOpenHelper(context: Context, name: String = DATABASE_NAME, factory: CursorFactory? = null, version: Int = 10) :
        SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        seed(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        seed(db)
    }

    fun addMusician(db: SQLiteDatabase?, musician: Musician) {
        val newMusician: ContentValues = ContentValues()
        newMusician.put(MUZICAR_IME, musician.name)
        newMusician.put(MUZICAR_ZANR, musician.genre)
        db?.insert(DATABASE_TABLE, null, newMusician)
    }

    fun clearTable() {
        var db = super.getWritableDatabase()
        db.execSQL("DELETE FROM $DATABASE_TABLE")
    }

    fun dropTable(db: SQLiteDatabase?) {
        db?.execSQL("DROP TABLE IF EXISTS $DATABASE_TABLE")
    }

    fun updateMusician(musician: Musician) {
        val updatedMusician = ContentValues()
        val id = musician.id
        val db = super.getReadableDatabase()

        updatedMusician.put(MUZICAR_ID, id)
        updatedMusician.put(MUZICAR_IME, musician.name)
        updatedMusician.put(MUZICAR_ZANR, musician.genre)

        db.update(DATABASE_TABLE, updatedMusician, "$MUZICAR_ID = $id", null)
    }

    companion object {
        @JvmStatic val DATABASE_NAME: String = "mojaBaza.db"
        @JvmStatic val DATABASE_TABLE: String = "Muzicari"
        @JvmStatic val DATABASE_VERSION: Int = 1
        @JvmStatic val MUZICAR_ID: String = "_id"
        @JvmStatic val MUZICAR_IME: String = "ime"
        @JvmStatic val MUZICAR_ZANR: String = "zanr"
        @JvmStatic val KEY_NAME: String = "MUZICARI"
        val DATABASE_CREATE: String =
                "create table $DATABASE_TABLE ($MUZICAR_ID integer primary key autoincrement, " +
                        "$MUZICAR_IME text not null, $MUZICAR_ZANR text not null)"

    }

    public fun seed(db: SQLiteDatabase?) {
        dropTable(db)
        db?.execSQL(DATABASE_CREATE)
        val musician = Musician(-1, "Sinan", "sakic", "Narodna")
        for (i in 0..4)
            addMusician(db, musician)
    }


}