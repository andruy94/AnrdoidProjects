package com.krabd.klient;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultForTeachersActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener {
    ListView listView;
    List<String[]> globalResult;
    List<String[]> globalTest;
    String group;
    // used to store app title
    private CharSequence myTitle;// просто титул
    final String ATTRIBUTE_NAME_Name="Name";
    final  String ATTRIBUTE_NAME_Surname="Surname";
    int state=0;
    SwipeRefreshLayout swipeLayout;

    DataBase sqh;
    List<String> sGroups;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stud_table);
        myTitle=getTitle();
        setTitle(myTitle +" Группы: ");
        swipeLayout=(SwipeRefreshLayout) findViewById(R.id.swipe_container2);
        swipeLayout.setOnRefreshListener(this);

        sqh=new DataBase(this);
        SQLiteDatabase sqdb = sqh.getWritableDatabase();
        sqh.createTmpTable(sqdb);

        listView = (ListView) findViewById(R.id.listforteacher);
        SimpleCursorAdapter simpleCursorAdapter=new SimpleCursorAdapter(getApplicationContext(),
                R.layout.list_st,
                sqh.getTmpData(),
                new String[]{DataBase.studNAME,DataBase.studGROUP},
                new int[]{R.id.lname,R.id.lname}
        );
        listView.setAdapter(simpleCursorAdapter);
        swipeLayout.setRefreshing(false);

        listView.setOnItemClickListener(this);


    }

    @Override
    public void onRefresh() {
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (state){
            case 0:
                group=((TextView)view).getText().toString();//.replaceAll("[^0-9]", "");;
                Log.e("TAG", group);
               // group=Integer.valueOf(group).toString();
                new GetAllTest().execute(group);
                getActionBar().setTitle("Доступные тесты: ");
                state=1;
                break;
            case 1:
                new GetAllProgress().execute(group,globalResult.get(position)[ParseJSON.ID_TS]);
                getActionBar().setTitle("Результаты студентов: ");
                state =2;
                break;
            case 2:
                break;
        }


    }





    private class GetAllTest extends AsyncTask<String, Integer, List<String[]>> {// получает все доступные тесты
        @Override
        protected List<String[]> doInBackground(String... params) {
            // TODO Auto-generated method stub
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("group", params[0]));
            //nameValuePairs.add(new BasicNameValuePair("", params[1]));
            // nameValuePairs.add(new BasicNameValuePair("pss", params[2]));
            Variable.stringresponse_stud = POSTRequest.POST_Data(
                    nameValuePairs, "http://kushelev.ru/action/getAllTest.php");
            Log.d("TAG", "Variable.stringresponse_stud=" + Variable.stringresponse_stud);
            return ParseJSON.parseTest(Variable.stringresponse_stud);
        }

        protected void onPostExecute(List<String[]> result) {
            globalResult=result;
            Log.d("TAG","GetAllTest="+result.toString());

            sGroups = new ArrayList<>();
            for (String[] obj  : result){
                    sGroups.add(obj[ParseJSON.TEST_NAME]);
            }
            listView.setAdapter(new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,sGroups));
            swipeLayout.setRefreshing(false);
        }


    }



    private class GetAllProgress extends AsyncTask<String, Integer, List<String[]>> {//получает результаты за it_ый test
        @Override
        protected List<String[]> doInBackground(String... params) {
            // TODO Auto-generated method stub
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("group", params[0]));
            nameValuePairs.add(new BasicNameValuePair("id_ts", params[1]));
            // nameValuePairs.add(new BasicNameValuePair("pss", params[2]));
            Variable.stringresponse_stud = POSTRequest.POST_Data(
                    nameValuePairs, "http://kushelev.ru/action/getAllStudProgress.php");
            return ParseJSON.parseStatist(Variable.stringresponse_stud);
        }

        protected void onPostExecute(List<String[]> result) {
            globalTest=result;
            for (String[] obj  : result) {//пишем оценки в БД
                sqh.updateTmpTableResult(obj[0],obj[1]);
            }

            SimpleCursorAdapter simpleCursorAdapter=new SimpleCursorAdapter(getApplicationContext(),
                    R.layout.stud_item,
                    sqh.getTmpDataAll(group),
                    new String[]{DataBase.studSURN,DataBase.studNAME,DataBase.studRES},
                    new int[]{R.id.tvSurName,R.id.tvName,R.id.tvMark}
            );
            listView.setAdapter(simpleCursorAdapter);

            //listView.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, sGroups));
            swipeLayout.setRefreshing(false);
        }


    }

    @Override
    public void onBackPressed() {
        SimpleCursorAdapter simpleCursorAdapter;
        switch (state){
            case 0:
                super.onBackPressed();
                break;
            case 1:
                getActionBar().setTitle(myTitle +" Группы: ");
                sqh=new DataBase(getApplicationContext());
                SQLiteDatabase sqdb = sqh.getWritableDatabase();
                sqh.createTmpTable(sqdb);

                 simpleCursorAdapter=new SimpleCursorAdapter(getApplicationContext(),
                        R.layout.list_st,
                        sqh.getTmpData(),
                        new String[]{DataBase.studNAME,DataBase.studGROUP},
                        new int[]{R.id.lname,R.id.lname}
                );
                listView.setAdapter(simpleCursorAdapter);
                state=0;
                break;
            case 2:
                swipeLayout.setRefreshing(true);
                for (String[] obj  : globalTest) {
                    sqh.updateTmpTableResult(obj[0],getApplicationContext().getString(R.string.noWrite));
                }
                new GetAllTest().execute(group);
                getActionBar().setTitle("Доступные тесты: ");
                state=1;
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}