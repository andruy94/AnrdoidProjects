package sumani.com.sulfurcalc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

/**
 * Created by Андрей on 21.05.2016.
 */
public class MyAdapter extends ArrayAdapter<Object> {

    Context context;
    Object[] objects;
    LayoutInflater inflater;

    public MyAdapter(Context context, Object[] objects) {
        super(context,android.R.layout.simple_spinner_item,objects);
        this.context = context;
        this.objects = objects;
        inflater = (LayoutInflater.from(context));
    }



    @Override
    public int getCount() {
        return objects.length;
    }

    @Override
    public Object getItem(int i) {
        return objects[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(android.R.layout.simple_spinner_item, null);
        TextView names = (TextView) view.findViewById(android.R.id.text1);
        names.setText(objects[i].toString());
        return view;
    }
}
