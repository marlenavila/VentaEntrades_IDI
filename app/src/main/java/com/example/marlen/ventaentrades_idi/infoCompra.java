package com.example.marlen.ventaentrades_idi;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class infoCompra extends AppCompatActivity implements View.OnClickListener {

    TextView titolObra, preu;
    long num;
    DbHelper baseDades;
    RadioButton CJove, CAturat, CUniv;
    ImageView done;
    int preuObra = 0;
    double aux2 = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_compra);

        baseDades = new DbHelper(this);

        titolObra = (TextView)findViewById(R.id.titolCompra);
        preu = (TextView)findViewById(R.id.preuCompra);

        done = (ImageView)findViewById(R.id.done);
        done.setOnClickListener(this);

        CJove = (RadioButton)findViewById(R.id.radiojove);
        CAturat = (RadioButton)findViewById(R.id.radioaturat);
        CUniv = (RadioButton)findViewById(R.id.radiouniversitari);

        Bundle b = getIntent().getExtras();
        num = b.getLong("numero");
        titolObra.setText(b.getString("titol"));
        int entrades = b.getInt("entrades");
        Cursor c = baseDades.getObra(titolObra.getText().toString());
        if(c.moveToFirst())
            preuObra = Integer.valueOf(c.getString(c.getColumnIndex(baseDades.CN_PREU)));
        preuObra = preuObra*entrades;
        aux2 = preuObra;
        preu.setText(aux2 + "€");

    }

    public void onRadioButtonClicked(View v){
        double desc;
        double aux;
        switch (v.getId()){
            case R.id.radiocap:
                preu.setText(String.valueOf(aux2) + "€");
                break;
            case R.id.radiojove:
                    desc = preuObra*0.20;
                    aux = preuObra - desc;
                    preu.setText(String.valueOf(aux) + "€");
                break;
            case R.id.radioaturat:
                    desc = preuObra*0.15;
                    aux = preuObra - desc;
                    preu.setText(String.valueOf(aux) + "€");
                break;
            case R.id.radiouniversitari:
                    desc = preuObra*0.15;
                    aux = preuObra - desc;
                    preu.setText(String.valueOf(aux) + "€");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.done):

                break;
        }
    }
}
