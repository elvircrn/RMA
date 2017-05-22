package ba.unsa.etf.rma.elvircrn.rma17_17455

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Detalji : AppCompatActivity() {

    internal var musician: Musician = Musician()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalji)

        musician = intent.getSerializableExtra("Musician") as Musician

        val imetv = findViewById(R.id.detaljiIme) as TextView
        imetv.text = musician.name

        imetv.setOnClickListener { v ->
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(musician.web)
            startActivity(i)
        }
    }
}
