package ba.unsa.etf.rma.elvircrn.rma17_17455

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast

import com.google.gson.Gson

import java.util.ArrayList


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MusiciansListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MusiciansListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MusiciansListFragment : Fragment(), SearchArtist.IOnMusicianSearchDone, MyResultReceiver.Receiver {

    private var musicians = ArrayList<Musician>()

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null
    private var mReceiver: MyResultReceiver? = null

    internal var oic: OnItemClick? = null
    internal var listView: ListView? = null

    override fun onDone(res: Array<MusicianDTO>) {
        for (musicianDTO in res) {
            musicians.add(Musician(musicianDTO))
        }
        (listView!!.adapter as BaseAdapter).notifyDataSetChanged()
    }

    interface OnItemClick {
        fun onItemClicked(pos: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.musicians_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        listView = view!!.findViewById(R.id.listView) as ListView

        if (arguments.containsKey("Alista")) {
            musicians = arguments.getParcelableArrayList<Musician>("Alista")
            listView!!.adapter = ArrayAdapter(activity, R.layout.element_liste,
                    R.id.info, musicians)
        }

        try {
            oic = activity as OnItemClick
        } catch (e: ClassCastException) {
            throw e
        }

        listView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> oic!!.onItemClicked(position) }

        val button = view!!.findViewById(R.id.button) as Button
        val editText = view!!.findViewById(R.id.editText) as EditText

        button.setOnClickListener { v ->

            /*new SearchArtist(MusiciansListFragment.this).execute(editText.getText().toString());*/
            musicians.clear()
            val intent = Intent(Intent.ACTION_SYNC, null, activity, MyService::class.java)
            mReceiver = MyResultReceiver(Handler())
            mReceiver!!.setReceiver(this@MusiciansListFragment)
            intent.putExtra("textQuerya", editText.text.toString().replace(" ", "%20"))
            intent.putExtra("receiver", mReceiver)
            activity.startService(intent)


        }

    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle) {

        when (resultCode) {
            MyService.STATUS_FINISHED -> {
                val gson = Gson()
                val res = gson.fromJson(resultData.getString("results"), Array<MusicianDTO>::class.java)
                for (musicianDTO in res) {
                    musicians.add(Musician(musicianDTO))
                }
                (listView!!.adapter as BaseAdapter).notifyDataSetChanged()
            }
            MyService.STATUS_ERROR -> {
                val error = resultData.getString(Intent.EXTRA_TEXT)
                Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @param param1 Parameter 1.
         * *
         * @param param2 Parameter 2.
         * *
         * @return A new instance of fragment MusiciansListFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): MusiciansListFragment {
            val fragment = MusiciansListFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
