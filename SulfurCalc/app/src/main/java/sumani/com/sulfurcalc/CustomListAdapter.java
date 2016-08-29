package sumani.com.sulfurcalc;

/**
 * Created by Андрей on 04.02.2016.
 */


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<TempDatabase.Fertilizer> {

    private final Activity context;
    TempDatabase.Fertilizer[] fertilizers;
    CheckBox chWEntered;
    TextView tvName;
    boolean[] isCheck1;
    boolean[] isCheck2;
    double[] Amount;

    public boolean[] getIsCheck1() {
        return isCheck1;
    }

    public boolean[] getIsCheck2() {
        return isCheck2;
    }

    public double[] getAmount() {
        return Amount;
    }



    public CustomListAdapter(Activity context, TempDatabase.Fertilizer[] fertilizers) {
        super(context, R.layout.mylist, fertilizers);
        // TODO Auto-generated constructor stub
        this.context=context;
        this.fertilizers=fertilizers;
        isCheck1=new boolean[fertilizers.length];
        isCheck2=new boolean[fertilizers.length];
        Amount=new double[fertilizers.length];
        for(int i=0;i<fertilizers.length;i++){
            isCheck1[i]=false;
            isCheck2[i]=false;
            Amount[i]=0;
        }
    }


    @Override
    public View getView(final int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null, true);
        final EditText etAmount=(EditText) rowView.findViewById(R.id.etAmount);
        tvName=(TextView) rowView.findViewById(R.id.tvName);
        final CheckBox chEntered=(CheckBox) rowView.findViewById(R.id.chEntered);
        chWEntered=(CheckBox) rowView.findViewById(R.id.chWEntered);
        chEntered.setOnClickListener( new View.OnClickListener() {//OnChacedChangeLisnter -плохо
            @Override
            public void onClick(View v) {
                if(etAmount.getText().length()==0) {
                    chEntered.setChecked(false);
                    return;
                }
                isCheck1[position]=!isCheck1[position];
                Amount[position]=(etAmount.getText().length()!=0) ? Double.valueOf(etAmount.getText().toString()) : 0;;
            }
        });
        chWEntered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCheck2[position]=!isCheck2[position];
            }
        });

        tvName.setText(fertilizers[position].name);
        if(isCheck1[position]){
            chEntered.setChecked(true);
            etAmount.setText(String.valueOf(Amount[position]));
        }
        if(isCheck2[position]) {
            chWEntered.setChecked(true);
        }
        return rowView;

    }



}