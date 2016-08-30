package com.example.macbookair.myapplication;

import android.content.Context;
import android.media.MediaActionSound;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macbookair.myapplication.interfaces.StudEndpoint;
import com.example.macbookair.myapplication.models.Esse;
import com.example.macbookair.myapplication.services.ServiceGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.Sort;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity {

    private Realm realm;
    private RealmConfiguration realmConfig;

    private String searchlang = "ru";
    private String searchInput;
    private StudEndpoint client;//Интерфейс адаптер
    private ListView lvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // Create the Realm configuration
        realmConfig = new RealmConfiguration.Builder(this).build();

        lvResult = (ListView)findViewById(R.id.lvResult);

        Bundle extras = getIntent().getExtras();
        searchInput = extras.getString("searchInput");//получаем то что ввели
        searchlang= extras.getString("searchlang");
        client = ServiceGenerator.createService(StudEndpoint.class);//получаем адаптер от наших данных
        Call<List<Esse>> call = client.getEsseList(searchInput, searchlang);

        call.enqueue(new Callback<List<Esse>>() {
            @Override
            public void onResponse(Call<List<Esse>> call, Response<List<Esse>> response) {
                final List<Esse> esses = response.body();
                //-------
                realm = Realm.getInstance(realmConfig);
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        for(Esse esse : esses) {
                            esse.setSearchInput(searchInput);
                            realm.insertOrUpdate(esse);
                        }
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Log.i("TAG", "Succes");
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Log.i("TAG", "Error");
                    }
                });
                //-----
                if (!esses.isEmpty()){
                    EsseAdapter adapter = new EsseAdapter(SearchResultActivity.this, esses);
                    lvResult.setAdapter(adapter);
                } else {
                    Toast.makeText(SearchResultActivity.this, "Рефераты по данной теме не найдены :(", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Esse>> call, Throwable t) {
                realm = Realm.getInstance(realmConfig);
                Toast.makeText(SearchResultActivity.this, "Произошла ошибка :(, но я успел сохранить это:", Toast.LENGTH_LONG).
                        show();
                List<Esse> esseList=realm.where(Esse.class).
                        contains("searchInput",searchInput, Case.SENSITIVE).
                        contains("languageId",searchlang.equals("ru")? "2" : "1").
                        findAllSorted("referatId");
                lvResult.setAdapter(new EsseAdapter(SearchResultActivity.this,esseList));
            }
        });

    }

    private class EsseAdapter extends ArrayAdapter<Esse> {

        private List<Esse> esseList;

        public EsseAdapter(Context context, List<Esse> esses) {
            super(context, R.layout.activity_search_result, esses);
            esseList = esses;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());

            View item = inflater.inflate(R.layout.list_item, null);


            TextView txtTitle = (TextView)item.findViewById(R.id.tvTitle);
            txtTitle.setText(esseList.get(position).getTitle());

            TextView txtDiscipline = (TextView)item.findViewById(R.id.tvDiscipline);
            txtDiscipline.setText(esseList.get(position).getDisciplineId());

            return item;
        }
    }


    public List<Esse> getAllFromDataBase(){
        // Open the default realm. All threads must use it's own reference to the realm.
        // Those can not be transferred across threads.
        realm = Realm.getInstance(realmConfig);
        return realm.where(Esse.class).isNotNull("referatId").findAll();
    }
}