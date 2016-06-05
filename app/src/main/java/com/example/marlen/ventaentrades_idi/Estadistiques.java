package com.example.marlen.ventaentrades_idi;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class Estadistiques extends AppCompatActivity {

    TextView totalentrades, totalrecap, dill_entr, dima_entr, dime_entr, dij_entr, div_entr;
    TextView diss_entr, dium_entr;

    DbHelper baseDades;

    String titol;

    DecimalFormat cosas = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadistiques);

        baseDades = new DbHelper(this);

        totalentrades = (TextView)findViewById(R.id.totalentrades);
        totalrecap = (TextView)findViewById(R.id.totalrecap);
        dill_entr = (TextView)findViewById(R.id.dilluns_est);
        dima_entr = (TextView)findViewById(R.id.dimarts_est);
        dime_entr = (TextView)findViewById(R.id.dimecres_est);
        dij_entr = (TextView)findViewById(R.id.dijous_est);
        div_entr = (TextView)findViewById(R.id.divendres_est);
        diss_entr = (TextView)findViewById(R.id.dissabte_est);
        dium_entr = (TextView)findViewById(R.id.diumenge_est);

        Bundle b = getIntent().getExtras();
        titol = b.getString("titol");

        int totalentrades1 = 0, numentradesfuncio, numDillFunc, numDill = 0, numDimaFunc,
                numDima = 0, numDimeFunc, numDime = 0, numDijFunc, numDij = 0,
                numDivFunc, numDiv = 0, numDissFunc, numDiss = 0, numDiuFunc, numDiu = 0,
                totaldetodo = 0, todofuncio;
        double recapfuncio, recaptotal1 = 0.0;

        Cursor c = baseDades.getAllEverything();
        if(c.moveToFirst()){
            do{
                todofuncio = 40 - c.getInt(c.getColumnIndex(baseDades.CN_BUTAQUES_DISP));
                totaldetodo += todofuncio;
                if(c.getString(c.getColumnIndex(baseDades.CN_TITOL)).equals(titol)){
                    numentradesfuncio = 40 - c.getInt(c.getColumnIndex(baseDades.CN_BUTAQUES_DISP));
                    recapfuncio = numentradesfuncio*(c.getDouble(c.getColumnIndex(baseDades.CN_PREU)));
                    totalentrades1 += numentradesfuncio;
                    recaptotal1 +=recapfuncio;
                    if("Dilluns".equals(c.getString(c.getColumnIndex(baseDades.CN_DIA)))){
                        numDillFunc = 40 - c.getInt(c.getColumnIndex(baseDades.CN_BUTAQUES_DISP));
                        numDill += numDillFunc;
                    }
                    else if(c.getString(c.getColumnIndex(baseDades.CN_DIA)).equals("Dimarts")){
                        numDimaFunc = 40 - c.getInt(c.getColumnIndex(baseDades.CN_BUTAQUES_DISP));
                        numDima += numDimaFunc;
                    }
                    else if(c.getString(c.getColumnIndex(baseDades.CN_DIA)).equals("Dimecres")){
                        numDimeFunc = 40 - c.getInt(c.getColumnIndex(baseDades.CN_BUTAQUES_DISP));
                        numDime += numDimeFunc;
                    }
                    else if(c.getString(c.getColumnIndex(baseDades.CN_DIA)).equals("Dijous")){
                        numDijFunc = 40 - c.getInt(c.getColumnIndex(baseDades.CN_BUTAQUES_DISP));
                        numDij += numDijFunc;
                    }
                    else if(c.getString(c.getColumnIndex(baseDades.CN_DIA)).equals("Divendres")){
                        numDivFunc = 40 - c.getInt(c.getColumnIndex(baseDades.CN_BUTAQUES_DISP));
                        numDiv += numDivFunc;
                    }
                    else if(c.getString(c.getColumnIndex(baseDades.CN_DIA)).equals("Dissabte")){
                        numDissFunc = 40 - c.getInt(c.getColumnIndex(baseDades.CN_BUTAQUES_DISP));
                        numDiss += numDissFunc;
                    }
                    else{
                        numDiuFunc = 40 - c.getInt(c.getColumnIndex(baseDades.CN_BUTAQUES_DISP));
                        numDiu += numDiuFunc;
                    }
                }
            }
            while(c.moveToNext());
        }

        if(totaldetodo != 0 && totalentrades1 != 0) {
            totalentrades.setText(String.valueOf(totalentrades1) + " (" +
                    cosas.format((totalentrades1 / (double)totaldetodo) * 100) + "%)");
            totalrecap.setText(String.valueOf(recaptotal1) + "€");
            int aux = 1;
            aux = ((numDill * 100) / totalentrades1);
            dill_entr.setText(String.valueOf(numDill) + " (" + cosas.format((numDill / (double)totalentrades1)*100) + "%)");
            dima_entr.setText(String.valueOf(numDima) + " (" + cosas.format((numDima / (double)totalentrades1) * 100) + "%)");
            dime_entr.setText(String.valueOf(numDime) + " (" + cosas.format((numDime / (double)totalentrades1) * 100) + "%)");
            dij_entr.setText(String.valueOf(numDij) + " (" + cosas.format((numDij / (double)totalentrades1) * 100) + "%)");
            div_entr.setText(String.valueOf(numDiv) + " (" + cosas.format((numDiv / (double)totalentrades1) * 100) + "%)");
            diss_entr.setText(String.valueOf(numDiss) + " (" + cosas.format((numDiss / (double)totalentrades1) * 100) + "%)");
            dium_entr.setText(String.valueOf(numDiu) + " (" + cosas.format((numDiu / (double)totalentrades1) * 100) + "%)");
        }
        else{
            totalentrades.setText(0 + " (" + 0 + "%)");
            totalrecap.setText(0 + "€");
            dill_entr.setText(0 + " (" + 0 + "%)");
            dima_entr.setText(0 + " (" + 0 + "%)");
            dime_entr.setText(0 + " (" + 0 + "%)");
            dij_entr.setText(0 + " (" + 0 + "%)");
            div_entr.setText(0 + " (" + 0 + "%)");
            diss_entr.setText(0 + " (" + 0 + "%)");
            dium_entr.setText(0 + " (" + 0 + "%)");
        }
    }
}
