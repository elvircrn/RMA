package ba.unsa.etf.rma.elvircrn.rma17_17455

import android.app.Fragment
import android.app.FragmentManager
import android.app.LoaderManager
import android.content.*
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.view.View
import android.widget.*
import com.facebook.stetho.Stetho

import java.util.ArrayList

class Pocetni : AppCompatActivity(), MusiciansListFragment.OnItemClick {
    internal var unosi = ArrayList<Musician>()
    internal var textReceiver = TextReceiver()
    private val filter = IntentFilter("ACTION_SEND")
    var dbHelper: MuzicarDBOpenHelper? = null

    internal var widerL: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Stetho.initializeWithDefaults(this)

        dbHelper = MuzicarDBOpenHelper(this)
        dbHelper?.writableDatabase

        setContentView(R.layout.activity_pocetni)
/*
        val button = findViewById(R.id.button) as Button
        val listView = findViewById(R.id.listView) as ListView
        val editText = findViewById(R.id.editText) as EditText

        listView.setAdapter(new ArrayAdapter<>(this, R.layout.element_liste, R.id.textView2, unosi));

        button.setOnClickListener(v -> {
            unosi.add(0, new Musician(editText.getText().toString()));
            ((ArrayAdapter)listView.getAdapter()).notifyDataSetChanged();
        });*/

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(Pocetni.this, Detalji.class);
                myIntent.putExtra("Musician", unosi.get(position));
                Pocetni.this.startActivity(myIntent);
            }
        });
        registerReceiver(textReceiver, filter);*/

        val fm = fragmentManager
        val lDetails = findViewById(R.id.placeF2)

        if (lDetails is FrameLayout) {
            widerL = true

            val fragment = fm.findFragmentById(R.id.placeF2)
            val detailsFragment: DetailsFragment?
            if (fragment == null) {
                detailsFragment = DetailsFragment()
                fm.beginTransaction().replace(R.id.placeF2, detailsFragment).commit()
            } else {
                detailsFragment = fragment as DetailsFragment
            }
        }

        var musiciansListFragment: MusiciansListFragment? = fm.findFragmentByTag("List") as MusiciansListFragment?

        if (musiciansListFragment == null) {
            musiciansListFragment = MusiciansListFragment()
            val arguments = Bundle()



            var cr = contentResolver
            var kolone = arrayOf(MuzicarDBOpenHelper.MUZICAR_ID,
                    MuzicarDBOpenHelper.MUZICAR_IME,
                    MuzicarDBOpenHelper.MUZICAR_ZANR)
            var adresa = ContentUris.withAppendedId(Uri.parse("content://rma.provider.muzicari/elements"), 1)
            var cur = cr.query(adresa, kolone, null, null, null)

            if (cur != null) {
                while (cur.moveToNext()) {
                    unosi.add(Musician(id = cur.getInt(0),
                            name = cur.getString(1),
                            genre = cur.getString(2)))
                }
            }






            arguments.putParcelableArrayList("Alista", unosi)
            musiciansListFragment.arguments = arguments
            fm.beginTransaction().replace(R.id.placeF1, musiciansListFragment, "List").commit()
        } else {
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }


    }

    override fun onItemClicked(pos: Int) {
        val arguments = Bundle()
        arguments.putParcelable("muzicar", unosi[pos])
        val detailsFragment = DetailsFragment()
        detailsFragment.arguments = arguments
        if (widerL!!) {
            fragmentManager.beginTransaction().replace(R.id.placeF2, detailsFragment)
                    .commit()
        } else {
            fragmentManager.beginTransaction().replace(R.id.placeF1, detailsFragment)
                    .addToBackStack(null).commit()
        }
    }

    inner class TextReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val editText = findViewById(R.id.editText) as EditText
            editText.setText(intent.dataString)
        }
    }

    override fun onStop() {
        super.onStop()
        dbHelper?.clearTable()
    }
}
