package com.kongx.nkuassistant;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View myView = null;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        myView = inflater.inflate(R.layout.fragment_home, container, false);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        TextView textView = (TextView)(myView.findViewById(R.id.textView_date));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        textView.setText(dateFormat.format(calendar.getTime()));
        if(year == 2017){
            if(weekOfYear == 1 || weekOfYear == 2){
                textView = (TextView)(myView.findViewById(R.id.textView_week));
                textView.setText("考试");
                textView = (TextView)(myView.findViewById(R.id.textView_period));
                textView.setText("2016-2017 第一学期");
                textView = (TextView)(myView.findViewById(R.id.textView_No_));
                textView.setText(null);
            }
            if(weekOfYear > 2 || weekOfYear <= 6){
                textView = (TextView)(myView.findViewById(R.id.textView_week));
                textView.setText(String.valueOf(weekOfYear-2));
                textView = (TextView)(myView.findViewById(R.id.textView_period));
                textView.setText("寒假");
            }
            if(weekOfYear > 6 || weekOfYear <= 22){
                textView = (TextView)(myView.findViewById(R.id.textView_week));
                textView.setText(String.valueOf(weekOfYear-6));
                textView = (TextView)(myView.findViewById(R.id.textView_period));
                textView.setText("2016-2017 第二学期");
            }
            if(weekOfYear > 22 || weekOfYear <= 24){
                textView = (TextView)(myView.findViewById(R.id.textView_week));
                textView.setText("考试");
                textView = (TextView)(myView.findViewById(R.id.textView_period));
                textView.setText("2016-2017 第二学期");
                textView = (TextView)(myView.findViewById(R.id.textView_No_));
                textView.setText(null);
            }
            if(weekOfYear > 24|| weekOfYear <= 28){
                textView = (TextView)(myView.findViewById(R.id.textView_week));
                textView.setText(String.valueOf(weekOfYear-24));
                textView = (TextView)(myView.findViewById(R.id.textView_period));
                textView.setText("2016-2017 夏季学期");
            }
            if(weekOfYear > 28 || weekOfYear <= 36){
                textView = (TextView)(myView.findViewById(R.id.textView_week));
                textView.setText(String.valueOf(weekOfYear-28));
                textView = (TextView)(myView.findViewById(R.id.textView_period));
                textView.setText("暑假");
            }
        }
        else if(year == 2016){
            if(weekOfYear >= 38 || weekOfYear <= 53){
                textView = (TextView)(myView.findViewById(R.id.textView_week));
                textView.setText(String.valueOf(weekOfYear-37));
                textView = (TextView)(myView.findViewById(R.id.textView_period));
                textView.setText("2016-2017 第一学期");
            }
            if(weekOfYear == 54){
                textView = (TextView)(myView.findViewById(R.id.textView_week));
                textView.setText("考试");
                textView = (TextView)(myView.findViewById(R.id.textView_period));
                textView.setText("2016-2017 第一学期");
                textView = (TextView)(myView.findViewById(R.id.textView_No_));
                textView.setText(null);
            }
        }
        return myView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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

    public View getMyView(){
        return myView;
    }
}