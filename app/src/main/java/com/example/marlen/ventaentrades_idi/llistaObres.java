package com.example.marlen.ventaentrades_idi;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

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

        recView.setLayoutManager(manager);
        recView.addOnItemTouchListener(new RecyclerItemClickListener(this,this));

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
            values.put(baseDades.CN_BUTAQUES,String.valueOf(1204));
            baseDades.createObra(values, "Obra");

            values = new ContentValues();
            values.put(baseDades.CN_TITOL, String.valueOf("Hairspray")); //content values per passar valor a la BD
            values.put(baseDades.CN_PREU, String.valueOf("30"));
            values.put(baseDades.CN_DURADA,90);
            values.put(baseDades.CN_DESC,String.valueOf("Amigo mío si tuvieras laca"));
            values.put(baseDades.CN_DATA,String.valueOf("45-21-1975"));
            values.put(baseDades.CN_BUTAQUES,String.valueOf(2));
            baseDades.createObra(values, "Obra");

            values = new ContentValues();
            values.put(baseDades.CN_TITOL, String.valueOf("Les Miserables")); //content values per passar valor a la BD
            values.put(baseDades.CN_PREU, String.valueOf("60"));
            values.put(baseDades.CN_DURADA,95);
            values.put(baseDades.CN_DESC,String.valueOf("Cosette me molas mucho teta"));
            values.put(baseDades.CN_DATA,String.valueOf("28-07-1993"));
            values.put(baseDades.CN_BUTAQUES,String.valueOf(3896));
            baseDades.createObra(values, "Obra");

            Intent intent = new Intent(getApplicationContext(), llistaObres.class);
            startActivity(intent);
            finish();
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
        long num = 0;
        int i = 3;
        num |= (1<<i);
        num^=(1<<i);
        Boolean hola = (num&(1<<i)) != 0;
        Toast.makeText(getApplicationContext(), hola.toString(), Toast.LENGTH_SHORT).show();
    }
}
