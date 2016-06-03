package com.example.marlen.ventaentrades_idi;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class llistaCompradors extends AppCompatActivity {

    private ListView lv;
    DbHelper baseDades;
    String titol, data, correus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llista_compradors);

        lv = (ListView)findViewById(R.id.listView);

        baseDades = new DbHelper(this);

        Bundle b = getIntent().getExtras();
        titol = b.getString("titol");
        data = b.getString("data");

        Cursor c = baseDades.getFuncio(titol,data);
        if(c.moveToFirst())
            correus = c.getString(c.getColumnIndex(baseDades.CN_CORREUS));

        String[] aux = correus.split("~");
        List<String> correus_list = new ArrayList<String>();
        if(aux != null){
            for(int i = 0; i < aux.length; i++){
                correus_list.add(aux[i]);
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,correus_list);
            lv.setAdapter(arrayAdapter);
        }





    }
}
