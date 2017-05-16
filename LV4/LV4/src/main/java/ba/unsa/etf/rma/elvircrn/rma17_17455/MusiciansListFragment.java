package ba.unsa.etf.rma.elvircrn.rma17_17455;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MusiciansListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MusiciansListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusiciansListFragment extends Fragment implements SearchArtist.IOnMusicianSearchDone {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private ArrayList<Musician> musicians = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    OnItemClick oic;
    ListView listView;

    @Override
    public void onDone(MusicianDTO[] res) {
        if (res != null) {
            for (MusicianDTO musicianDTO : res) {
                musicians.add(new Musician(musicianDTO));
            }
            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        }
    }

    public interface OnItemClick {
        public void onItemClicked(int pos);
    }

    public MusiciansListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MusiciansListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MusiciansListFragment newInstance(String param1, String param2) {
        MusiciansListFragment fragment = new MusiciansListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.musicians_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView)getView().findViewById(R.id.listView);

        if (getArguments().containsKey("Alista")) {
            musicians = getArguments().getParcelableArrayList("Alista");
            listView.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.element_liste,
                    R.id.textView2, musicians));
        }

        try {
            oic = (OnItemClick)getActivity();
        } catch(ClassCastException e) {
            throw e;
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                oic.onItemClicked(position);
            }
        });

        Button button = (Button)getView().findViewById(R.id.button);
        final EditText editText = (EditText)getView().findViewById(R.id.editText);

        button.setOnClickListener(v -> {
            new SearchArtist(MusiciansListFragment.this).execute(editText.getText().toString());
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}