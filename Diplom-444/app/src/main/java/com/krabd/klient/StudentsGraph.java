package com.krabd.klient;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentsGraph extends Activity {
    LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_graph);


        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setDrawGridBackground(false);
        //------
        mChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"Олллололол",Toast.LENGTH_SHORT).show();
            }
        });
        //-----
        // no description text
        mChart.setDescription("Олололо");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(true);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.setAxisMaxValue(220f);
        leftAxis.setAxisMinValue(0);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);

        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        new GetAllProgress().execute();
    }






    private class GetAllProgress extends AsyncTask<String, Integer, List<String[]>> {//получает результаты за  testы
        @Override
        protected List<String[]> doInBackground(String... params) {
            // TODO Auto-generated method stub
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            Variable.stringresponse_stud = POSTRequest.POST_Data(
                    nameValuePairs, "http://kushelev.ru/action/getAllMarksForTest.php");
            return ParseJSON.parseStatistAll(Variable.stringresponse_stud,getApplicationContext());
        }

        protected void onPostExecute(List<String[]> result) {
            List<String[]> strings=result;

            HashMap<String,AvgMark> hashMap=new HashMap<>();
            ArrayList<String> xVals = new ArrayList<>();// тут номера групп
            AvgMark avgMark;
            for (String[] res:result){
                if(hashMap.containsKey(res[0])){//если уже группу записали, то добаляем к сумме
                    hashMap.put(res[0], (hashMap.get(res[0])).add(Float.valueOf(res[1])));
                }
                else{
                    avgMark=new AvgMark(Float.valueOf(res[1]));
                    hashMap.put(res[0],avgMark);//пишем сюда элемент средней оценки
                    xVals.add(res[0]);//пишем номер группы в массив
                }
            }

            ArrayList<Entry> yVals = new ArrayList<Entry>(xVals.size());//тут их оценки

            for (int i = 0; i < hashMap.size(); i++) {
                float val = hashMap.get(xVals.get(i)).getAvg();//отдаём среднее значение
                yVals.add(new Entry(val, i));// значение, позиция
            }

            LineDataSet set1;


            if (mChart.getData() != null &&
                    mChart.getData().getDataSetCount() > 0) {
                set1 = (LineDataSet)mChart.getData().getDataSetByIndex(0);
                set1.setYVals(yVals);
                mChart.getData().setXVals(xVals);
                mChart.getData().notifyDataChanged();
                mChart.notifyDataSetChanged();
            } else {
                set1 = new LineDataSet(yVals, "DataSet 1");
                set1.enableDashedLine(10f, 5f, 0f);
                set1.enableDashedHighlightLine(10f, 5f, 0f);
                set1.setColor(Color.BLACK);
                set1.setCircleColor(Color.BLACK);
                set1.setLineWidth(1f);
                set1.setCircleRadius(3f);
                set1.setDrawCircleHole(false);
                set1.setValueTextSize(9f);
                set1.setDrawFilled(true);
                    set1.setFillColor(Color.BLACK);
                ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                dataSets.add(set1); // add the datasets
                // create a data object with the datasets
                LineData data = new LineData(xVals, dataSets);
                // set data
                mChart.setData(data);
                mChart.invalidate();
                Log.e("TAG","Данные готовы");
                getActionBar().setTitle(getActionBar().getTitle()+" График успеваемости:");


        }


    }
    }
    public static class AvgMark{//обёртка для среднего результат
        Float sum;
        Float count;
        public AvgMark(Float element){
            this.sum=element;
            this.count=1F;
        }
        AvgMark add(Float element){//добавить один элемент
            this.sum+=element;
            this.count++;//увеличим количство элеметов
            return this;//класс отдаёт себя
        }
        Float getAvg(){
            return sum/count;
        }
    }
}
