package com.example.marlen.ventaentrades_idi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class llistaFuncions extends AppCompatActivity implements RecyclerItemClickListener.OnItemClickListener {

    DbHelper baseDades;
    RecyclerView recView;
    LinearLayoutManager manager;
    MyCustomAdapterDays myCustomAdapterDays;
    String titolRec;
    Intent intent;
    int pos;

    ArrayList<Obra> obres = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llistafuncions);

        baseDades = new DbHelper(this);
        recView = (RecyclerView)findViewById(R.id.mRecyclerViewDays);
        manager = new LinearLayoutManager(this);

        recView.setLayoutManager(manager);
        recView.addOnItemTouchListener(new RecyclerItemClickListener(this, this));

        Bundle b = getIntent().getExtras();
        titolRec = b.getString("titol");

        Cursor c = baseDades.getAllDies(titolRec);
        int cont = 0;
        //comprovo que la BD no estigui buida
        if(c.moveToFirst()){
            do{
                String dataFuncio = c.getString(c.getColumnIndex(baseDades.CN_DATA));
                Integer butaques = c.getInt(c.getColumnIndex(baseDades.CN_BUTAQUES_DISP));
                Long numero = c.getLong(c.getColumnIndex(baseDades.CN_BUTAQUES));
                Obra newObra = new Obra();
                newObra.setData(dataFuncio);
                newObra.setButDisp(butaques);
                newObra.setNumBut(numero);
                obres.add(newObra);
                cont++;
            }while(c.moveToNext());
        }
        Toast.makeText(getApplicationContext(), String.valueOf(cont), Toast.LENGTH_SHORT).show();

        myCustomAdapterDays = new MyCustomAdapterDays();
        recView.setAdapter(myCustomAdapterDays);
        myCustomAdapterDays.setData(obres);
    }

    @Override
    public void onItemClick(View childView, int position) {
        Bundle b = new Bundle();
        b.putString("titol",titolRec);
        b.putInt("butaques", obres.get(position).getButDisp());
        b.putLong("numero", obres.get(position).getNumBut());
        b.putString("data", obres.get(position).getData());
        intent = new Intent(getApplicationContext(), PatiButaques.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    //Per fer una acció concreta si una de les files es manté clickada (mètode classe RecyclerItemClickListener)
    @Override
    public void onItemLongPress(View childView, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Vols veure els compradors d'aquesta funció?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle b = new Bundle();
                b.putString("titol",titolRec);
                b.putString("data",obres.get(position).getData());
                intent = new Intent(getApplicationContext(), llistaCompradors.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
