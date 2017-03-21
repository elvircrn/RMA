package ba.unsa.etf.rma.elvircrn.rma17_17455;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Pocetni extends AppCompatActivity {
    ArrayList<Muzicar> unosi = new ArrayList<>();
    TextReceiver textReceiver = new TextReceiver();
    private IntentFilter filter = new IntentFilter("ACTION_SEND");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocetni);

        Button button = (Button)findViewById(R.id.button);
        final ListView listView = (ListView)findViewById(R.id.listView);
        final EditText editText = (EditText)findViewById(R.id.editText);

        listView.setAdapter(new ArrayAdapter<>(this, R.layout.element_liste, R.id.textView2, unosi));

        button.setOnClickListener(v -> {
            unosi.add(0, new Muzicar(editText.getText().toString()));
            ((ArrayAdapter)listView.getAdapter()).notifyDataSetChanged();
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(Pocetni.this, Detalji.class);
                myIntent.putExtra("Muzicar", unosi.get(position));
                Pocetni.this.startActivity(myIntent);
            }
        });
        registerReceiver(textReceiver, filter);
   }

    public class TextReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            EditText editText = (EditText)findViewById(R.id.editText);
            editText.setText(intent.getDataString());
        }
    }
}
