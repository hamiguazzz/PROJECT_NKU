package com.kongx.nkuassistant;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ExamFragment extends Fragment implements Connectable,SwipeRefreshLayout.OnRefreshListener{
    private View myView = null;
    private ListView mExamList;
    private SwipeRefreshLayout mRefresh;
    private TextView mNoText;
    private Pattern pattern;
    private Matcher matcher;
    private Activity m_activity;
    private ArrayList<HashMap<String,String>> tmpExamList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_exam, container, false);
        mExamList = (ListView) myView.findViewById(R.id.exam_list);
        mRefresh = (SwipeRefreshLayout) myView.findViewById(R.id.exam_refresh);
        mRefresh.setOnRefreshListener(this);
        mNoText = (TextView) myView.findViewById(R.id.textView_noExam);
        return myView;
    }


    @Override
    public void onResume() {
        super.onResume();
        m_activity = getActivity();
        if(Information.examCount == -1){
            mExamList.setVisibility(View.GONE);
            onRefresh();
        }else {
            mNoText.setVisibility(View.GONE);
            mExamList.setAdapter(new MyAdapter(m_activity));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        m_activity = null;
    }

    @Override
    public void onRefresh() {
        mRefresh.setRefreshing(true);
        tmpExamList = new ArrayList<>();
        new Connect(ExamFragment.this,0,null).execute(Information.WEB_URL +"/xxcx/stdexamarrange/listAction.do");
    }

    private void updateUI(){
        Information.exams = tmpExamList;
        Information.examCount = Information.exams.size();
        storeExams();
        mRefresh.setRefreshing(false);
        mExamList.setAdapter(new MyAdapter(m_activity));
    }

    @Override
    public void onTaskComplete(Object o, int type) {
        if(o == null){
            Log.e("APP", "What the fuck?");
        }else if(o.getClass() == BufferedInputStream.class) {
            BufferedInputStream is = (BufferedInputStream)o ;
            String returnString = "";
            try{
                returnString = new Scanner(is, "GB2312").useDelimiter("\\A").next();
            }catch (NoSuchElementException e){
                e.printStackTrace();
            }
            pattern = Pattern.compile("<strong>(.+)(<\\/strong>)");
            matcher = pattern.matcher(returnString);
            if(returnString.equals("")){
                mExamList.setVisibility(View.INVISIBLE);
                mNoText.setText(getString(R.string.no_exam_info));
            }
            else if(matcher.find()){
                if(matcher.group(1).equals("本学期考试安排未发布！")){
                    mExamList.setVisibility(View.INVISIBLE);
                    mNoText.setText(getString(R.string.no_exam_info));
                }
            }
            else {
                mNoText.setVisibility(View.INVISIBLE);
                tmpExamList = new ArrayList<>();
                mNoText.setVisibility(View.GONE);
                pattern = Pattern.compile("<td align=\"center\" class=\"NavText\">(.*)(</td>)");
                matcher = pattern.matcher(returnString);
                String tmpDate;
                Pattern datePattern = Pattern.compile("\\d\\d\\d\\d-(\\d\\d)-(\\d\\d)");
                Matcher dateMather;
                HashMap<String,String> map;
                while(matcher.find()){
                    map = new HashMap<>();
                    map.put("name",matcher.group(1));
                    matcher.find();
                    matcher.find();
                    matcher.find();
                    matcher.find();
                    map.put("startTime",matcher.group(1));
                    matcher.find();
                    map.put("endTime",matcher.group(1));
                    matcher.find();
                    map.put("classRoom",matcher.group(1));
                    matcher.find();
                    dateMather = datePattern.matcher(matcher.group(1));
                    dateMather.find();
                    tmpDate = dateMather.group(1)+"月"+dateMather.group(2)+"日";
                    map.put("date",tmpDate);
                    matcher.find();
                    matcher.find();
                    tmpExamList.add(map);
                }
                updateUI();
            }
        }else if(o.getClass() == Integer.class){
            Integer code = (Integer)o;
            if(code == 302){
                this.startActivity(new Intent(getActivity(),EduLoginActivity.class));
                getActivity().finish();
            }
        }else if(o.getClass() == SocketTimeoutException.class){
            Log.e("APP","SocketTimeoutException!");
        }
    }

    boolean storeExams() {
        SharedPreferences settings = m_activity.getSharedPreferences(Information.EXAM_PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("examCount", Information.examCount);
        for (int i = 0; i < Information.examCount; i++) {
            editor.putString("name" + i, Information.exams.get(i).get("name"));
            editor.putString("startTime" + i, Information.exams.get(i).get("startTime"));
            editor.putString("endTime" + i, Information.exams.get(i).get("endTime"));
            editor.putString("classRoom" + i, Information.exams.get(i).get("classRoom"));
            editor.putString("date" + i, Information.exams.get(i).get("date"));
        }
        return editor.commit();
    }

    private class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return Information.examCount;
        }

        @Override
        public Object getItem(int position) {
            return Information.exams.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                convertView = mInflater.inflate(R.layout.exam_list_item,null);
                holder = new ViewHolder();
                holder.date = (TextView) convertView.findViewById(R.id.examDateView);
                holder.time = (TextView) convertView.findViewById(R.id.examPeriodView);
                holder.name = (TextView) convertView.findViewById(R.id.examNameView);
                holder.classroom = (TextView) convertView.findViewById(R.id.examLocationView);
                convertView.setTag(holder);//绑定ViewHolder对象
            }
            else{
                holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
            }
            holder.date.setText(Information.exams.get(position).get("date"));
            holder.time.setText(Information.startTime[Integer.parseInt(Information.exams.get(position).get("startTime"))]+"-"+
                    Information.endTime[Integer.parseInt(Information.exams.get(position).get("endTime"))]);
            holder.name.setText(Information.exams.get(position).get("name"));
            holder.classroom.setText(Information.exams.get(position).get("classRoom"));

            return convertView;
        }

    }
    class ViewHolder{
        TextView date;
        TextView time;
        TextView name;
        TextView classroom;
    }
}
