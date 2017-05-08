package ba.unsa.etf.rma.elvircrn.rma17_17455;

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class Pocetni extends AppCompatActivity implements MusiciansListFragment.OnItemClick {
    ArrayList<Musician> unosi = new ArrayList<>();
    TextReceiver textReceiver = new TextReceiver();
    private IntentFilter filter = new IntentFilter("ACTION_SEND");

    Boolean widerL = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocetni);

        Button button = (Button)findViewById(R.id.button);
        final ListView listView = (ListView)findViewById(R.id.listView);
        final EditText editText = (EditText)findViewById(R.id.editText);

        /*listView.setAdapter(new ArrayAdapter<>(this, R.layout.element_liste, R.id.textView2, unosi));

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

        FragmentManager fm = getFragmentManager();
        FrameLayout lDetails = (FrameLayout)findViewById(R.id.placeF2);

        if (lDetails != null) {
            widerL = true;
            DetailsFragment detailsFragment = (DetailsFragment)fm.findFragmentById(R.id.placeF2);
            if (detailsFragment == null) {
                detailsFragment = new DetailsFragment();
                fm.beginTransaction().replace(R.id.placeF2, detailsFragment).commit();
            }
        }

        MusiciansListFragment musiciansListFragment = (MusiciansListFragment)fm.findFragmentByTag("List");

        if (musiciansListFragment == null) {
            musiciansListFragment = new MusiciansListFragment();
            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList("Alista", unosi);
            musiciansListFragment.setArguments(arguments);
            fm.beginTransaction().replace(R.id.placeF1, musiciansListFragment, "List").commit();
        } else {
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
   }

    @Override
    public void onItemClicked(int pos) {
        Bundle arguments = new Bundle();
        arguments.putParcelable("muzicar", unosi.get(pos));
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(arguments);
        if (widerL) {
            getFragmentManager().beginTransaction().replace(R.id.placeF2, detailsFragment)
                    .commit();
        } else {
            getFragmentManager().beginTransaction().replace(R.id.placeF1, detailsFragment)
                    .addToBackStack(null).commit();
        }
    }

    public class TextReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            EditText editText = (EditText)findViewById(R.id.editText);
            editText.setText(intent.getDataString());
        }
    }
}
