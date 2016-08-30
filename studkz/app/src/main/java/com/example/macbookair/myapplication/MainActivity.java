package com.example.macbookair.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {



    EditText eText;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner=(Spinner) findViewById(R.id.spLang);
        spinner.setAdapter(new ArrayAdapter<String>(getApplication(),android.R.layout.simple_dropdown_item_1line,new String[]{"русский",
                "казацкий"}));

        Button searchBtn = (Button)findViewById(R.id.search_button);
        eText = (EditText) findViewById(R.id.editText);
        searchBtn.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String searchInput = eText.getText().toString();
            Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
            //добавить потом передачу через putExtra язык
            intent.putExtra("searchInput", searchInput);
            intent.putExtra("searchlang",(spinner.getSelectedItem().toString().equals("русский")? "ru" : "kz"));
            startActivity(intent);
        }
    };
}