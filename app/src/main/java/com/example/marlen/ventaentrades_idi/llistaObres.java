package com.example.marlen.ventaentrades_idi;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class llistaObres extends AppCompatActivity implements RecyclerItemClickListener.OnItemClickListener {

    DbHelper baseDades;
    RecyclerView recView;
    LinearLayoutManager manager;
    MyCustomAdapter myCustomAdapter;

    ArrayList<Obra> obres = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        baseDades = new DbHelper(this);
        recView = (RecyclerView)findViewById(R.id.mRecyclerView);
        manager = new LinearLayoutManager(this);

        //baseDades.initDades();

        recView.setLayoutManager(manager);
        recView.addOnItemTouchListener(new RecyclerItemClickListener(this,this));

        //accedeixo a la BD per mostrar les dades que m'interessen de les obres
        //i anar emplenant l'arraylist d'obres


        myCustomAdapter = new MyCustomAdapter();
        recView.setAdapter(myCustomAdapter);
        myCustomAdapter.setData(obres);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            baseDades.initDades();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }

        Cursor c = baseDades.getAllObres();
        //comprovo que la BD no estigui buida
        if(c.moveToFirst()){
            do{
                String titolObra = c.getString(c.getColumnIndex(baseDades.CN_TITOL));
                String preu = c.getString(c.getColumnIndex(baseDades.CN_PREU));
                Obra newObra = new Obra();
                newObra.setTitol(titolObra);
                newObra.setPreu(preu);
                obres.add(newObra);
            }while(c.moveToNext());
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), afegirObra.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //per anar més ràpid pr borrar les dades actuals
        if (id == R.id.borrarBD) {
            baseDades.deleteBD();
            Intent intent = new Intent(getApplicationContext(), llistaObres.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(id == R.id.about){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("About");
            builder.setIcon(R.drawable.businesswoman);
            builder.setMessage(" La Sala\n Autora: Marlen Àvila\n Versió: 1.0\n " +
                    "marlen.avila@est.fib.upc.edu");
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
        //opció per insertar directament les 3 obres inicials per estalviar feina
        if(id == R.id.initData){
            baseDades.initDades();
            Intent intent = new Intent(getApplicationContext(), llistaObres.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(id == R.id.help){
            Intent intent = new Intent(getApplicationContext(), Help.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    //per clicar a una de les files del Recycler (mètodes de la classe RecyclerItemClickListener
    @Override
    public void onItemClick(View childView, int position) {
        Bundle b = new Bundle();
        b.putString("titol",obres.get(position).getTitol());
        Intent intent = new Intent(getApplicationContext(), infoObra.class);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }
}
