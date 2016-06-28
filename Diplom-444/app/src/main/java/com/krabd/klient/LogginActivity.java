package com.krabd.klient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class LogginActivity extends Activity implements View.OnClickListener{
    EditText etLogin;
    EditText etPassword;
    Button btnSignIn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin);
        progressBar=(ProgressBar) findViewById(R.id.pbTeachers);
        etLogin=(EditText) findViewById(R.id.etLogin);
        etPassword=(EditText) findViewById(R.id.etPassword);
        btnSignIn=(Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);
        btnSignIn.setEnabled(true);
        progressBar.setVisibility(View.INVISIBLE);
    }



    private boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null;
    }

    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        btnSignIn.setEnabled(false);
        Variable.login = etLogin.getText().toString();
        Variable.password = etPassword.getText().toString();
        if (Variable.login.equals("") || Variable.password.equals("")) {
            btnSignIn.setEnabled(true);
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    this);
            builder.setTitle("Ошибка")
                    .setMessage("Необходимо заполнить все поля")
                    .setCancelable(false)
                    .setNegativeButton("Продолжить заполнение",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
            btnSignIn.setEnabled(true);
            progressBar.setVisibility(View.INVISIBLE);
        }
        else {
            boolean checkCon;
            checkCon = checkInternetConnection();
            if (checkCon) {
                new AuthTask().execute(Variable.login,Variable.password);
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        this);
                builder.setTitle("Ошибка")
                        .setMessage("Нет подключения к интернету")
                        .setCancelable(false)
                        .setNegativeButton("Попробовать позже",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
                btnSignIn.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }


    private class AuthTask extends AsyncTask<String, Integer, Integer> {// получает всех студентов
        @Override
        protected Integer doInBackground(String... params) {
            // TODO Auto-generated method stub
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("login", params[0]));
            nameValuePairs.add(new BasicNameValuePair("password", params[1]));
            Variable.stringresponse_stud = POSTRequest.POST_Data(
                    nameValuePairs, Variable.URL_TEST);
            DataBase sqh; sqh=new DataBase(getApplicationContext());
            SQLiteDatabase sqdb = sqh.getWritableDatabase();
            sqh.dropTable(sqh.getWritableDatabase(),DataBase.TMP_TABLE);
            sqh.createTmpTable(sqdb);


            return ParseJSON.parseStud(Variable.stringresponse_stud,getApplicationContext());
        }

        protected void onPostExecute(Integer result) {
            if (result.equals(0)){
                startActivity(new Intent(getApplicationContext(),TeachersMenu.class));
            }
            else {
                Toast.makeText(getApplicationContext(),"Не правильный логин/пароль",Toast.LENGTH_SHORT).show();
                btnSignIn.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }
}
