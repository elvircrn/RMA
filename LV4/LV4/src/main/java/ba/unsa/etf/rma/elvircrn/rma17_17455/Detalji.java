package ba.unsa.etf.rma.elvircrn.rma17_17455;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Detalji extends AppCompatActivity {

    Musician musician;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalji);

        musician = (Musician)getIntent().getSerializableExtra("Musician");

        TextView imetv = (TextView)findViewById(R.id.detaljiIme);
        imetv.setText(musician.getName());

        imetv.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(musician.getWeb()));
            startActivity(i);
        });
    }
}
