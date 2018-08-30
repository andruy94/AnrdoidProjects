package com.krabd.klient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

/*
 * Класс для анализа ответа сервера в формате JSON.
 */
public class ParseJSON {

	/*--------------------------------------------------------------------------------------------------*/
	/* Stud Parse */
	/*
	 * Метод для анализа ответа сервера на запрос информации о студенте и записи
	 * расшифрованой строки в БД. Входные данные: stringresponse: Ответ сервера;
	 * context: Активность, в которой вызывается метод; group: Группа студента.
	 */
	public static void parseStud(String stringresponse, Context context,
								 String group) {
		DataBase sqh = new DataBase(context);
		String OutputData = "";
		String OutputData1 = "";
		String OutputData3 = "";
		JSONObject jsonResponse;
		try {
			jsonResponse = new JSONObject(stringresponse);
			OutputData3 = jsonResponse.getString("id_st");
			String stid = new String(OutputData3.getBytes("ISO-8859-1"),
					"UTF-8");
			OutputData = jsonResponse.getString("name");
			String stname = new String(OutputData.getBytes("ISO-8859-1"),
					"UTF-8");
			OutputData1 = jsonResponse.getString("surname");
			String stsurn = new String(OutputData1.getBytes("ISO-8859-1"),
					"UTF-8");
			sqh.insertStudTable(stid, stname, stsurn, "true", group);
		}
		catch (Exception e) {
		}
	}

	public static final int ID_ST=0;
	public static final int NAME=1;
	public static final int SURNAME=2;
	public static final int GROUP=3;
	public static final int RESULT=4;
	private static final int DSCR_SIZE=5;

	public static int parseStud(String stringresponse,Context context) {// Георгий
		String[] info;
		int answer=-1;
		DataBase sqh = new DataBase(context);
		JSONObject jsonResponse;
		try {

			JSONArray jsonArray=new JSONArray(stringresponse);
			for (int i=0;i<jsonArray.length();i++) {
				info=new String[DSCR_SIZE];
				jsonResponse = jsonArray.getJSONObject(i);
				info[ID_ST]=new String(jsonResponse.getString("id_st").getBytes("ISO-8859-1"),
						"UTF-8");
				//info[GROUP]=
				String tmp=new String(jsonResponse.getString("group").getBytes("ISO-8859-1"),
						"UTF-8");
				tmp=tmp.replaceAll("[^0-9]", "");// уберём все символы лишние
				/*if (tmp.charAt(0)=='0')//уберём 0 впередиы
					tmp=tmp.substring(1,info[GROUP].length());*/
				int tmpInt=Integer.valueOf(tmp);
				info[GROUP]=String.format(context.getString(R.string.studTemplte),
						(tmpInt-tmpInt%100)/100, tmpInt%100);
				info[NAME]= new String(jsonResponse.getString("name").getBytes("ISO-8859-1"),
						"UTF-8");
				info[SURNAME]=new String(jsonResponse.getString("surname").getBytes("ISO-8859-1"),
						"UTF-8");
				info[RESULT]=context.getString(R.string.noWrite);

				sqh.insertTmpTable(info[ID_ST],info[NAME],info[SURNAME],info[RESULT],info[GROUP]);

				answer=0;
			}
		}
		catch (Exception e) {
			Log.e("TAG","Parse="+e.toString());
		}
		return answer;
	}



	/*--------------------------------------------------------------------------------------------------*/
	/* Lec Parse */
	/*
	 * Метод для анализа ответа сервера на запрос информации о лекциях и записи
	 * расшифрованых строк в БД. Входные данные: stringresponse: Ответ сервера;
	 * context: Активность, в которой вызывается метод.
	 */
	public static void parseLec(String stringresponse, Context context) {
		DataBase sqh = new DataBase(context);
		String OutputData = "";
		String OutputData1 = "";
		String OutputData2 = "";
		try {
			JSONArray jr = new JSONArray(stringresponse);
			for (int i = 0; i < jr.length(); i++) {
				JSONObject jb = jr.getJSONObject(i);
				OutputData = jb.getString("name");
				String lecname = new String(OutputData.getBytes("ISO-8859-1"),
						"UTF-8");
				OutputData1 = jb.getString("description");
				String lecdisc = new String(OutputData1.getBytes("ISO-8859-1"),
						"UTF-8");
				OutputData2 = jb.getString("url");
				String lecurl = new String(OutputData2.getBytes("ISO-8859-1"),
						"UTF-8");
				sqh.insertLecTable(lecname, lecdisc, lecurl);
			}
		}
		catch (Exception e) {
		}
	}

	/*--------------------------------------------------------------------------------------------------*/
	/* Test Parse */
	/*
	 * Метод для анализа ответа сервера на запрос информации о тестах и записи
	 * расшифрованых строк в БД. Входные данные: stringresponse: Ответ сервера;
	 * context: Активность, в которой вызывается метод.
	 */
	public static void parseTest(String stringresponse, Context context) {
		DataBase sqh = new DataBase(context);
		String OutputData = "";
		String OutputData1 = "";
		String OutputData2 = "";
		try {
			JSONArray jr = new JSONArray(stringresponse);
			for (int i = 0; i < jr.length(); i++) {
				JSONObject jb = jr.getJSONObject(i);
				OutputData = jb.getString("name");
				String testname = new String(OutputData.getBytes("ISO-8859-1"),
						"UTF-8");
				OutputData1 = jb.getString("recom");
				String testdisc = new String(
						OutputData1.getBytes("ISO-8859-1"), "UTF-8");
				OutputData2 = jb.getString("id_ts");
				String testid = new String(OutputData2.getBytes("ISO-8859-1"),
						"UTF-8");
				sqh.insertTestTable(testid, testname, testdisc);
			}
		}
		catch (Exception e) {
		}
	}


	//-----
	public final static int ID_TS=0;
	public final static int TEST_NAME=1;
	public static List<String[]> parseTest(String stringresponse) {//Георгий
		Log.d("TAG","parseTest"+stringresponse);
		List<String[]> result=new ArrayList<>();
		String[] info;
		try {
			JSONArray jr = new JSONArray(stringresponse);
			for (int i = 0; i < jr.length(); i++) {
				info=new String[2];
				JSONObject jb = jr.getJSONObject(i);
				info[ID_TS] = new String(jb.getString("id_ts").getBytes("ISO-8859-1"),
						"UTF-8");
				info[TEST_NAME] = new String(jb.getString("name").getBytes("ISO-8859-1"),
						"UTF-8");
				result.add(info);
			}
		}
		catch (Exception e) {
		}
		return result;
	}
	//-----
	/*--------------------------------------------------------------------------------------------------*/
	/* Quest Parse */
	/*
	 * Метод для анализа ответа сервера на запрос информации о вопросах к тестам
	 * и записи расшифрованых строк в БД. Входные данные: stringresponse: Ответ
	 * сервера; context: Активность, в которой вызывается метод.
	 */
	public static void parseQuest(String stringresponse, Context context) {
		DataBase sqh = new DataBase(context);
		String OutputData = "";
		String OutputData1 = "";
		String OutputData2 = "";
		String OutputData3 = "";
		String OutputData4 = "";
		String OutputData5 = "";
		String OutputData6 = "";
		String OutputData7 = "";
		String OutputData8 = "";
		String OutputData9 = "";
		try {
			JSONArray jr = new JSONArray(stringresponse);
			for (int i = 0; i < jr.length(); i++) {
				JSONObject jb = jr.getJSONObject(i);
				OutputData = jb.getString("id_ts");
				String testid = new String(OutputData.getBytes("ISO-8859-1"),
						"UTF-8");
				OutputData1 = jb.getString("type");
				String questtype = new String(
						OutputData1.getBytes("ISO-8859-1"), "UTF-8");
				OutputData2 = jb.getString("question");
				String questtext = new String(
						OutputData2.getBytes("ISO-8859-1"), "UTF-8");
				OutputData3 = jb.getString("var1");
				String var1 = new String(OutputData3.getBytes("ISO-8859-1"),
						"UTF-8");
				OutputData4 = jb.getString("var2");
				String var2 = new String(OutputData4.getBytes("ISO-8859-1"),
						"UTF-8");
				OutputData5 = jb.getString("var3");
				String var3 = new String(OutputData5.getBytes("ISO-8859-1"),
						"UTF-8");
				OutputData6 = jb.getString("var4");
				String var4 = new String(OutputData6.getBytes("ISO-8859-1"),
						"UTF-8");
				OutputData7 = jb.getString("imgUrl");
				String imgurl = new String(OutputData7.getBytes("ISO-8859-1"),
						"UTF-8");
				OutputData8 = jb.getString("answ");
				String questanswer = new String(
						OutputData8.getBytes("ISO-8859-1"), "UTF-8");
				OutputData9 = jb.getString("hits");
				String urlhit = new String(
						OutputData9.getBytes("ISO-8859-1"), "UTF-8");
				sqh.insertQuestTable(testid, questtype, questtext, var1, var2,
						var3, var4, imgurl, questanswer, urlhit);
			}
		}
		catch (Exception e) {
		}
	}

	/*--------------------------------------------------------------------------------------------------*/
	/* Statist Parse */
	/*
	 * Метод для анализа ответа сервера на запрос информации об оценках студента
	 * и записи расшифрованых строк в БД. Входные данные: stringresponse: Ответ
	 * сервера; context: Активность, в которой вызывается метод.
	 */
	public static void parseStatist(String stringresponse, Context context) {
		DataBase sqh = new DataBase(context);
		String OutputData = "";
		try {
			JSONObject actor = new JSONObject(stringresponse);
			JSONObject progress = actor.getJSONObject("progress");
			Iterator<?> keys = progress.keys();
			int j = progress.length();
			for (int i = 0; i < j; i++) {
				String key = (String) keys.next();
				OutputData = progress.getString(key);
				String statistres = new String(
						OutputData.getBytes("ISO-8859-1"), "UTF-8");
				Log.d("TAG", key + statistres);
				sqh.insertStatistTable(key, statistres);
			}
		}
		catch (Exception e) {
		}
	}

	public static List<String[]> parseStatist(String stringresponse) {//Георгий
		String OutputData = "";
		List<String[]> statistres=new ArrayList<>();
		try {
			String[] info;
			JSONObject actor = new JSONObject(stringresponse);
			JSONObject progress = actor.getJSONObject("progress");
			Iterator<?> keys = progress.keys();
			int j = progress.length();
			for (int i = 0; i < j; i++) {
				info=new String[2];
				String key = (String) keys.next();
				OutputData = progress.getString(key);
				info[0]=key;
				info[1]=(new String(
						OutputData.getBytes("ISO-8859-1"), "UTF-8"));
				statistres.add(info);
				Log.d("TAG", "parseStatist()=" + info[0] + info[1]);

			}


		}
		catch (Exception e) {
		}
		return statistres;
	}

	public static List<String[]> parseStatistAll(String stringresponse,Context context) {//Георгий
		List<String[]> result=new ArrayList<>();
		String[] info;
		try {
			JSONArray jr = new JSONArray(stringresponse);
			JSONObject jsonObject;
			for (int i = 0; i < jr.length(); i++) {

				jsonObject = jr.getJSONObject(i);
				info=new String[2];
				String tmp=new String(
						jsonObject.getString("group").getBytes("ISO-8859-1"), "UTF-8");
				tmp=tmp.replaceAll("[^0-9]", "");// уберём все символы лишние
				int tmpInt=Integer.valueOf(tmp);
				info[0]=String.format(context.getString(R.string.studTemplte),
						(tmpInt-tmpInt%100)/100, tmpInt%100);
				info[1]=(new String(
						jsonObject.getString("res").getBytes("ISO-8859-1"), "UTF-8"));

				result.add(info);
				Log.d("TAG", "parseStatist()=" + info[0] + info[1]);
			}


		}
		catch (Exception e) {
		}
		return result;
	}

	/*--------------------------------------------------------------------------------------------------*/
	/* One Res Parse */
	/*
	 * Метод для анализа ответа сервера на запрос информации об оценке за
	 * пройденный тест и записи расшифрованых строк в БД. Входные данные:
	 * stringresponse: Ответ сервера; context: Активность, в которой вызывается
	 * метод.
	 */
	public static String parseRes(String stringresponse, Context context) {
		String OutputData = "";
		String proc = "exc";
		try {
			JSONObject jb = new JSONObject(stringresponse);
			OutputData = jb.getString("proc");
			proc = new String(OutputData.getBytes("ISO-8859-1"), "UTF-8");
		}
		catch (Exception e) {
		}
		return proc;
	}
}