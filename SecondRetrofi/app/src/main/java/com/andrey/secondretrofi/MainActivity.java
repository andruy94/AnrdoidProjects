package com.andrey.secondretrofi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.andrey.secondretrofi.interfaces.AuthEndpoint;
import com.andrey.secondretrofi.models.AnswerTest;
import com.andrey.secondretrofi.models.ApiRequest;
import com.andrey.secondretrofi.models.Rebus;
import com.andrey.secondretrofi.retrofit.ServiceGenerator;
import com.andrey.secondretrofi.retrofit.ServiceGenerator2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private AuthEndpoint client;//Интерфейс адаптер
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        findViewById(R.id.btnTest).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {


        ServiceGenerator2.getInstance().getAnswerTest(new AnswerTest("andruy94")).enqueue(new Callback<AnswerTest>() {
            @Override
            public void onResponse(Call<AnswerTest> call, Response<AnswerTest> response) {
                Log.e("TAG",response.body().login);
            }

            @Override
            public void onFailure(Call<AnswerTest> call, Throwable t) {
                Log.e("TAG",t.toString());
            }
        });
    }
}
