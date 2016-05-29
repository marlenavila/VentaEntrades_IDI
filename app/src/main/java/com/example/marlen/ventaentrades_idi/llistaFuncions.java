package com.example.marlen.ventaentrades_idi;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class llistaFuncions extends AppCompatActivity {

    DbHelper baseDades;
    RecyclerView recView;
    LinearLayoutManager manager;
    MyCustomAdapterDays myCustomAdapterDays;

    ArrayList<Obra> obres = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llistafuncions);

        baseDades = new DbHelper(this);
        recView = (RecyclerView)findViewById(R.id.mRecyclerViewDays);
        manager = new LinearLayoutManager(this);

        recView.setLayoutManager(manager);

        Bundle b = getIntent().getExtras();
        final String titolRec = b.getString("titol");

        //per poder clickar elements del recycler (les files)
        recView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Bundle b = new Bundle();
                        b.putString("titol",titolRec);
                        b.putInt("butaques", obres.get(position).getButDisp());
                        Intent intent = new Intent(getApplicationContext(), PatiButaques.class);
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                })
        );

        Cursor c = baseDades.getAllDies(titolRec);
        //comprovo que la BD no estigui buida
        if(c.moveToFirst()){
            do{
                String dataFuncio = c.getString(c.getColumnIndex(baseDades.CN_DATA));
                Integer butaques = c.getInt(c.getColumnIndex(baseDades.CN_BUTAQUES_DISP));
                Obra newObra = new Obra();
                newObra.setData(dataFuncio);
                newObra.setButDisp(butaques);
                obres.add(newObra);
            }while(c.moveToNext());
        }

        myCustomAdapterDays = new MyCustomAdapterDays();
        recView.setAdapter(myCustomAdapterDays);
        myCustomAdapterDays.setData(obres);
    }
}
