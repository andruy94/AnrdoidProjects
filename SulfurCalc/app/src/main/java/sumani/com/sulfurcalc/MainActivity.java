package sumani.com.sulfurcalc;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TempDatabase tempDatabase;
    Spinner timesSpinner;
    Spinner soiltypesSpinner;
    Spinner regionSpinner;
    Spinner soilsSpinner;
    Spinner culturesSpinner;
    CustomListAdapter customListAdapter;
    LinearLayout myListLL;
    ListView listView;
    EditText etContent;
    EditText etProductivity;
    EditText etAmountof;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tempDatabase=new TempDatabase();
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        myListLL = (LinearLayout)findViewById(R.id.MylistLL);
        Button btnCalc=(Button) findViewById(R.id.btnCalc);
        etContent=(EditText) findViewById(R.id.etContent);
        etProductivity=(EditText) findViewById(R.id.etProductivity);
        etAmountof=(EditText) findViewById(R.id.etAmountof);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        btnCalc.setOnClickListener(this);

        etProductivity.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etAmountof.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etContent.setImeOptions(EditorInfo.IME_ACTION_DONE);


        MyAdapter soiltypesAdapter = new MyAdapter(this,tempDatabase.soiltypes);
        soiltypesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        soiltypesSpinner = (Spinner) findViewById(R.id.spinnerSoiltypes);
        soiltypesSpinner.setAdapter(soiltypesAdapter);
        //---------
        MyAdapter regionAdapter = new MyAdapter(this, tempDatabase.regions);
        regionAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        regionSpinner = (Spinner) findViewById(R.id.spinnerRegions);
        regionSpinner.setAdapter(regionAdapter);
        //--------------------------
        MyAdapter culturesAdapter = new MyAdapter(this,tempDatabase.cultures);//(Objects) tempDatabase.cultures);
        culturesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        culturesSpinner = (Spinner) findViewById(R.id.spinnerCultures);
        culturesSpinner.setAdapter(culturesAdapter);
        //---------------------------------
        MyAdapter soilsAdapter = new MyAdapter(this,tempDatabase.soils);
        soilsAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        soilsSpinner = (Spinner) findViewById(R.id.spinnerSoils);
        soilsSpinner.setAdapter(soilsAdapter);
        //-----------------------
        MyAdapter timesAdapter = new MyAdapter(this, tempDatabase.times);
        timesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        timesSpinner = (Spinner) findViewById(R.id.spinnerTimes);
        timesSpinner.setAdapter(timesAdapter);
        //----------------------------



        customListAdapter = new CustomListAdapter(this, tempDatabase.fertilizers);
        listView.setAdapter(customListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCalc:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Важное сообщение!")
                        .setMessage(calculate())
                        .setIcon(R.drawable.logo)
                        .setCancelable(false)//запрет выхода по кнопке bzck
                        .setNegativeButton("ОК",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
                break;
            case R.id.button2:
                myListLL.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT, 666));
                listView.setVisibility(View.VISIBLE);
                break;
            case R.id.button3:
                myListLL.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT, 0));
                listView.setVisibility(View.VISIBLE);
                break;
        }
    }
    private String calculate(){
        StringBuilder output = new StringBuilder("");
        TempDatabase.SoilType currentSoilType = (TempDatabase.SoilType)soiltypesSpinner.getSelectedItem();
        float KCL =(etContent.getText().length()!= 0) ? Float.valueOf(etContent.getText().toString()) : 0;
        if (KCL > currentSoilType.high)
            return ("Содержание подвижной(сульфатной) серы высокое. Доп. внесения удобрений не требуется");
        else {
            if (KCL < currentSoilType.low)
                output.append("Содержание подвижной(сульфатной) серы низкое\n");//(1)
            else
                output.append("Содержание подвижной(сульфатной) серы среднее\n");//(2)
        }
        //-------------
        TempDatabase.Culture currentCulture = (TempDatabase.Culture)culturesSpinner.getSelectedItem();
        float yelds =(etProductivity.getText().length()!= 0) ? Float.valueOf(etProductivity.getText().toString()) :0;
        double BaseDose = currentCulture.quantity * yelds;
        output.append("Базовая доза серы равна " + String.format("%(.2f", BaseDose) + " кг./га.\n ");
        TempDatabase.Soil currentSoil = (TempDatabase.Soil)soilsSpinner.getSelectedItem();
        double BaseDoseWithSoil = BaseDose * currentSoil.quantity;
        output.append("Базовая доза серы c учетом гранулометрического типа почвы равна "
                + String.format("%(.2f",BaseDoseWithSoil) + " кг./га.\n ");
        // Считаем минеральные удобрения
        double allAmount = 0.0;
        boolean[] flags=customListAdapter.getIsCheck1();
        double[] amounts=customListAdapter.getAmount();
        for (int i = 0; i < 22; i++)
        {

            if (flags[i])
            {
                TempDatabase.Fertilizer curFert = tempDatabase.fertilizers[i];//хз что тут делать???
                double amount = amounts[i];
                amount = amount * curFert.quantity*0.01;
                allAmount += amount;
            }
        }

        // Считаем органические удобрения
        output.append("Доза минеральных удобрений равна " + String.format("%(.2f",allAmount) + " кг./га.\n ");
        double amountOrganic =(etAmountof.getText().length()!= 0) ? Double.valueOf(etAmountof.getText().toString()) : 0;
        TempDatabase.Time currentTime = (TempDatabase.Time)timesSpinner.getSelectedItem();
        amountOrganic = amountOrganic * currentTime.quantity*0.04;
        output.append("Доза органических удобрений равна " + String.format("%(.2f",amountOrganic)
                + " кг./га.\n ");
		//---------------
        TempDatabase.Region currentRegion = (TempDatabase.Region)regionSpinner.getSelectedItem();
        double amoutRegion = currentRegion.quantity;
        output.append("Общий баланс серы в регионе равен " + String.format("%(.2f",amoutRegion) +
                " кг./га.\n ");
        //
        double FinalDose = BaseDoseWithSoil - allAmount - amountOrganic - amoutRegion;
        if (FinalDose < 5) {
            output.append("Дополнительное внесение серы не нужно");
            return output.toString();
        }
        else
        {
            output.append("Расчетная доза \"чистой\" серы равна " +
                    String.format("%(.2f",FinalDose) + " кг./га. \n ");
            double temp = (FinalDose)/0.186;
            output.append("Необходимо дополнительно внести " + String.format("%(.2f",temp) + "кг./га. сульфата магния\n ");
            return output.toString();
        }
    }

}
