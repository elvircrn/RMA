package ba.unsa.etf.rma.elvircrn.rma17_17455;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Detalji extends AppCompatActivity {

    Muzicar muzicar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalji);

        muzicar = (Muzicar)getIntent().getSerializableExtra("Muzicar");

        TextView imetv = (TextView)findViewById(R.id.detaljiIme);
        imetv.setText(muzicar.getIme());

        imetv.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(muzicar.getWeb()));
            startActivity(i);
        });
    }
}
