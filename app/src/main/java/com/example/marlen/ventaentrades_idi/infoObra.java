package com.example.marlen.ventaentrades_idi;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class infoObra extends AppCompatActivity implements View.OnClickListener {

    TextView titol, et_preu, preu, et_durada, durada, et_descr, descr;
    Button comprar;
    DbHelper baseDades;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_obra);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_infoObra);
        setSupportActionBar(toolbar);

        baseDades = new DbHelper(this);

        titol = (TextView)findViewById(R.id.titolObra);
        et_preu = (TextView)findViewById(R.id.etiq_preu);
        preu = (TextView)findViewById(R.id.preuValor);
        et_durada = (TextView)findViewById(R.id.etiq_durada);
        durada = (TextView)findViewById(R.id.duradaValor);
        et_descr = (TextView)findViewById(R.id.etiq_sinopsi);
        descr = (TextView)findViewById(R.id.descrValor);

        comprar = (Button)findViewById(R.id.comprarEntrada);
        comprar.setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        String titolRec = b.getString("titol");
        titol.setText(titolRec);

        Cursor c = baseDades.getObra(titolRec);
        if(c.moveToFirst()){
            preu.setText(c.getString(c.getColumnIndex(baseDades.CN_PREU)) + "€");
            durada.setText(c.getString(c.getColumnIndex(baseDades.CN_DURADA)) + "min");
            descr.setText(c.getString(c.getColumnIndex(baseDades.CN_DESC)));
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_obra, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.llistarCompradors){
            return true;
        }
        if (id == R.id.modificarDades){
            return true;
        }
        if (id == R.id.eliminarObra){
            baseDades.deleteObra(titol.getText().toString());
            intent = new Intent(getApplicationContext(), llistaObres.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed(){
        intent = new Intent(getApplicationContext(), llistaObres.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comprarEntrada:
                Bundle b = new Bundle();
                b.putString("titol",titol.getText().toString());
                intent = new Intent(getApplicationContext(), llistaFuncions.class);
                intent.putExtras(b);
                startActivity(intent);
                break;
        }
    }
}
