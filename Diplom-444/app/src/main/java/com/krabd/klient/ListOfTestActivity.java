package com.krabd.klient;

/**
 * Created by Андрей on 10.06.2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;



public class ListOfTestActivity extends Activity implements AdapterView.OnItemClickListener {
    ListView listView;
    List<String[]> globalResult;
    String group;


    DataBase sqh;
    List<String> sGroups;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stud_table);

        listView = (ListView) findViewById(R.id.listforteacher);
        listView.setOnItemClickListener(this);
        new GetAllTest().execute();


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       Variable.id_ts=globalResult.get(position)[ParseJSON.ID_TS];
        Variable.testName=((TextView) view).getText().toString();
        startActivity(new Intent(getApplicationContext(),StudentsGraph2.class));


    }





    private class GetAllTest extends AsyncTask<String, Integer, List<String[]>> {// получает все доступные тесты
        @Override
        protected List<String[]> doInBackground(String... params) {
            // TODO Auto-generated method stub
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
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
            listView.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,sGroups));
        }


    }
}
