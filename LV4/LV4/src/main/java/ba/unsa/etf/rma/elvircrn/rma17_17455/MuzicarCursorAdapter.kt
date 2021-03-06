package ba.unsa.etf.rma.elvircrn.rma17_17455

import android.app.LoaderManager
import android.content.Context
import android.database.Cursor
import android.view.View
import android.widget.ResourceCursorAdapter
import android.widget.SimpleCursorAdapter
import android.widget.TextView
import ba.unsa.etf.rma.elvircrn.rma17_17455.MuzicarDBOpenHelper.Companion.MUZICAR_IME

class MuzicarCursorAdapter(context: Context, layout: Int, c: Cursor, flags: Int) : UpdateAdapter, ResourceCursorAdapter(context, layout, c, flags) {
    override fun update(cs: Cursor?) {
        // sa.swapCursor()
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        var infoTextView = view?.findViewById(R.id.info) as TextView

        val name = cursor.getString(cursor.getColumnIndex(MUZICAR_IME))
        infoTextView.text = name
    }

}