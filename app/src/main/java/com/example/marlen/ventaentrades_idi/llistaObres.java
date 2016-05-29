package com.example.marlen.ventaentrades_idi;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class llistaObres extends AppCompatActivity {

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

        recView.setLayoutManager(manager);

        //per poder clickar elements del recycler (les files)
        recView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Bundle b = new Bundle();
                        b.putString("titol",obres.get(position).getTitol());
                        Intent intent = new Intent(getApplicationContext(), infoObra.class);
                        intent.putExtras(b);
                        startActivity(intent);
                        finish();
                    }
                })
        );

        //accedeixo a la BD per mostrar les dades que m'interessen de les obres
        //i anar emplenant l'arraylist d'obres
        Cursor c = baseDades.getAllObres();
        //comprovo que la BD no estigui buida
        if(c.moveToFirst()){
            do{
                String titolObra = c.getString(c.getColumnIndex(baseDades.CN_TITOL));
                Integer preu = c.getInt(c.getColumnIndex(baseDades.CN_PREU));
                Obra newObra = new Obra();
                newObra.setTitol(titolObra);
                newObra.setPreu(preu);
                obres.add(newObra);
            }while(c.moveToNext());
        }

        myCustomAdapter = new MyCustomAdapter();
        recView.setAdapter(myCustomAdapter);
        myCustomAdapter.setData(obres);

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        //per anar més ràpid pr borrar les dades actuals
        if (id == R.id.borrarBD) {
            baseDades.deleteBD();
            Intent intent = new Intent(getApplicationContext(), llistaObres.class);
            startActivity(intent);
            finish();
            return true;
        }

        //opció per insertar directament les 3 obres inicials per estalviar feina
        if(id == R.id.initData){
            ContentValues values = new ContentValues();
            values.put(baseDades.CN_TITOL, String.valueOf("Grease")); //content values per passar valor a la BD
            values.put(baseDades.CN_PREU, String.valueOf("40"));
            values.put(baseDades.CN_DURADA,120);
            values.put(baseDades.CN_DESC,String.valueOf("La vida es una lenteja"));
            values.put(baseDades.CN_DATA,String.valueOf("31-02-3000"));
            baseDades.createObra(values, "Obra");

            values = new ContentValues();
            values.put(baseDades.CN_TITOL, String.valueOf("Hairspray")); //content values per passar valor a la BD
            values.put(baseDades.CN_PREU, String.valueOf("30"));
            values.put(baseDades.CN_DURADA,90);
            values.put(baseDades.CN_DESC,String.valueOf("Amigo mío si tuvieras laca"));
            values.put(baseDades.CN_DATA,String.valueOf("45-21-1975"));
            baseDades.createObra(values, "Obra");

            values = new ContentValues();
            values.put(baseDades.CN_TITOL, String.valueOf("Les Miserables")); //content values per passar valor a la BD
            values.put(baseDades.CN_PREU, String.valueOf("60"));
            values.put(baseDades.CN_DURADA,95);
            values.put(baseDades.CN_DESC,String.valueOf("Cosette me molas mucho teta"));
            values.put(baseDades.CN_DATA,String.valueOf("28-07-1993"));
            baseDades.createObra(values, "Obra");

            Intent intent = new Intent(getApplicationContext(), llistaObres.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}