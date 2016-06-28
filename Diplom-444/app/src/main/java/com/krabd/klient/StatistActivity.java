package com.krabd.klient;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;

public class StatistActivity extends ListActivity implements OnRefreshListener {

	ListView lv;
	ResTask Res_T;
	DataBase sqh = new DataBase(this);
	Cursor cursor;
	SwipeRefreshLayout swipeLayout;
	SimpleCursorAdapter notes;
	StatistTask Statist_T;
	protected static String[] namet;
	protected static String[] rest;
	SQLiteDatabase sqdb;
	int length;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statist);
		SQLiteDatabase sqdb = sqh.getWritableDatabase();
		sqh.createRezTable(sqdb);
		Cursor cursoridt = sqh.getAllRezData();
		if (cursoridt.getCount() > 0) {
			String[] idt = new String[cursoridt.getCount()];
			int i = 0;
			while (cursoridt.moveToNext()) {
				idt[i] = cursoridt.getString(0);
				i++;
			}
			sqh.dropTable(sqdb, DataBase.Rez_TABLE);
			cursoridt.close();
			sqdb.close();
			sqh.close();
			String s5 = Variable.id;
			String s6 = Variable.group;
			for (i = 0; i < cursoridt.getCount(); i++) {
				Res_T = new ResTask();

				Res_T.execute(Variable.id, Variable.group, idt[i],
						Variable.stringresponse_oneres);

			}

		}
		super.onCreate(savedInstanceState);
		onRefresh();
		this.getResources().getLayout(R.layout.statist);
		setContentView(R.layout.statist);
		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		swipeLayout.setOnRefreshListener(StatistActivity.this);
		swipeLayout.isRefreshing();
		swipeLayout.post(new Runnable() {
			@Override
			public void run() {
				swipeLayout.setRefreshing(true);
			}
		});
		//swipeLayout.setColorScheme(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
		this.getListView();
		lv = getListView();
		lv.setEnabled(false);
		registerForContextMenu(getListView());
		fillData();


	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		boolean checkCon;
		checkCon = checkInternetConnection();
		if (checkCon) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					Statist_T = new StatistTask();
					Statist_T.execute(Variable.id, Variable.group,
							Variable.stringresponse_statist);
					swipeLayout.setRefreshing(false);
				}
			}, 4000);
		} else {
			swipeLayout.setRefreshing(false);
			AlertDialog.Builder builder = new AlertDialog.Builder(
					StatistActivity.this);
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
		}
	}

	private boolean checkInternetConnection() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null;
	}

	class StatistTask extends AsyncTask<String, Integer, String> {
		public String delegate=null;
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("id", params[0]));
			nameValuePairs.add(new BasicNameValuePair("gr", params[1]));
			delegate = POSTRequest.POST_Data(
					nameValuePairs, Variable.URL_statist);
			Log.e("TAG","Delegate="+delegate);
			return delegate;
		}

		protected void onPostExecute(String result) {
			try {
				SQLiteDatabase sqdb = sqh.getWritableDatabase();
				sqh.dropTable(sqdb, DataBase.STATIST_TABLE);
				sqh.createStatistTable(sqdb);
				Context context = StatistActivity.this;
				ParseJSON.parseStatist(delegate, context);
				sqdb.close();
				sqh.close();
				fillData();
				swipeLayout.setRefreshing(false);
			} catch (Exception e) {
				fillData();
				e.printStackTrace();
			}
		}

		protected void onProgressUpdate(Integer... progress) {

		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onBackPressed() {


		sqh.close();
		Intent intent = new Intent(StatistActivity.this, MenuActivity.class);
		startActivity(intent);
	}

	@SuppressWarnings("deprecation")
	private void fillData() {
		DataBase sqh = new DataBase(StatistActivity.this);
		cursor = sqh.getAllStatistData();
		startManagingCursor(cursor);
		String[] from = new String[]{DataBase.statistQUEST,
				DataBase.statistRES};
		int[] to = new int[]{R.id.lname, R.id.ldisc};
		// Теперь создадим адаптер массива и установим его для отображения наших
		// данных
		notes = new SimpleCursorAdapter(this, R.layout.list_item, cursor, from,
				to, 0);
		setListAdapter(notes);
	}

	private class ResTask extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
			nameValuePairs.add(new BasicNameValuePair("id_st", params[0]));
			nameValuePairs.add(new BasicNameValuePair("gr", params[1]));
			nameValuePairs.add(new BasicNameValuePair("res", params[2]));
			Variable.stringresponse_oneres = POSTRequest.POST_Data(
					nameValuePairs, Variable.URL_oneres);
			return Variable.stringresponse_oneres;
		}

		@Override
		protected void onPostExecute(String result) {
		}
	}
}