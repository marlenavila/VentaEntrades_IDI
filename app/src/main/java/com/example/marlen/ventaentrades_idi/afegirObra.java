package com.example.marlen.ventaentrades_idi;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class afegirObra extends AppCompatActivity implements View.OnClickListener {
    ImageView btAfegir;
    EditText titol, preu, durada, descr;
    TextView titol_et, preu_et, dur_et, descr_et, dataIni, dataFi;
    DbHelper baseDades;
    String data1, data2, diaIni, diaFi, mesIni, mesFi, anyIni, anyFi;
    int diaInicial, diaFinal, mesInicial, mesFinal, cont;
    long dataMilisegs;
    Switch dilluns, dimarts, dimecres, dijous, divendres, dissabte, diumenge;
    boolean trobat;

    //per les dates d'inici i final
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    //format de la data que guardem agafada del calendari
    private SimpleDateFormat dateFormatter, getDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_obra);

        //concretem el format de la data
        dateFormatter = new SimpleDateFormat("dd-MM-yy");
        getDia = new SimpleDateFormat("c"); //per mostrar el dia de la setmana

        dilluns = (Switch) findViewById(R.id.dilluns);
        dimarts = (Switch) findViewById(R.id.dimarts);
        dimecres = (Switch) findViewById(R.id.dimecres);
        dijous = (Switch) findViewById(R.id.dijous);
        divendres = (Switch) findViewById(R.id.divendres);
        dissabte = (Switch) findViewById(R.id.dissabte);
        diumenge = (Switch) findViewById(R.id.diumenge);


        btAfegir = (ImageView) findViewById(R.id.afegeix);

        titol = (EditText) findViewById(R.id.titol);
        preu = (EditText) findViewById(R.id.preu);
        durada = (EditText) findViewById(R.id.durada);
        descr = (EditText) findViewById(R.id.descripcio);

        titol_et = (TextView) findViewById(R.id.titol_etiqueta);
        preu_et = (TextView) findViewById(R.id.preu_etiqueta);
        dur_et = (TextView) findViewById(R.id.dur_etiqueta);
        descr_et = (TextView) findViewById(R.id.desc_etiqueta);
        dataIni = (TextView) findViewById(R.id.dataInit);
        dataFi = (TextView) findViewById(R.id.dataFi);

        btAfegir.setOnClickListener(this);
        dataIni.setOnClickListener(this);
        dataFi.setOnClickListener(this);

        baseDades = new DbHelper(this);

        //es declara el calendari i guardem la data que apretem als dos textViews
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dataIni.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dataFi.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


    void calcul_data() {
        //agafo els valors de la data inicial i final per poder calcular l'interval de dies
        //triat per l'usuari
        data1 = dataIni.getText().toString();
        //ho separo per dia, mes i any per poder fer els càlculs i concatenacions corresponents
        diaIni = String.valueOf(data1.charAt(0)) + String.valueOf(data1.charAt(1)); //dia
        mesIni = String.valueOf(data1.charAt(3)) + String.valueOf(data1.charAt(4)); //mes
        anyIni = String.valueOf(data1.charAt(6)) + String.valueOf(data1.charAt(7)); //any
        diaInicial = Integer.valueOf(diaIni); //dia passat a integer per poder fer les sumes
        mesInicial = Integer.valueOf(mesIni); //mes """"""
        data2 = dataFi.getText().toString();
        diaFi = String.valueOf(data2.charAt(0)) + String.valueOf(data2.charAt(1)); //dia
        mesFi = String.valueOf(data2.charAt(3)) + String.valueOf(data2.charAt(4)); //mes
        anyFi = String.valueOf(data2.charAt(6)) + String.valueOf(data2.charAt(7)); //any
        diaFinal = Integer.valueOf(diaFi);
        mesFinal = Integer.valueOf(mesFi);

        if (mesInicial < mesFinal) {
            while (mesInicial < mesFinal) {
                //cas amb els mesos que tenen 31 dies
                if (mesInicial == 1 || mesInicial == 3 || mesInicial == 5 || mesInicial == 7
                        || mesInicial == 8 || mesInicial == 10 || mesInicial == 12)
                    calcul_dies(diaInicial, 31);
                    //cas amb els mesos que tenen 30 dies
                else if (mesInicial == 4 || mesInicial == 6 || mesInicial == 9 || mesInicial == 11)
                    calcul_dies(diaInicial, 30);
                    //cas febrer 28 dies
                else calcul_dies(diaInicial, 28);
                diaInicial = 1; //en aquest punt s'haurà acabat 1 més i començarem al següent, per tant el dia inicial sempre serà 1
                ++mesInicial; //passem al mes següent
            }
            calcul_dies(diaInicial, diaFinal); //cas final en qualsevol dels casos, que ja estem al mateix mes
        } else if (diaInicial <= diaFinal) calcul_dies(diaInicial, diaFinal);
        else Toast.makeText(getApplicationContext(), "Data incorrecta", Toast.LENGTH_SHORT).show();

    }

    void calcul_dies(int dia1, int dia2) {
        trobat = false;
        Cursor c = baseDades.getAllObres();
        if (c.moveToFirst()) {
            do {
                String aux = c.getString(c.getColumnIndex(baseDades.CN_TITOL));
                if (aux.equals(titol.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Aquesta obra ja existeix", Toast.LENGTH_SHORT).show();
                    trobat = true;
                    return;
                }
            } while (c.moveToNext());
        }
        int i;
        cont = 0;
        for (i = dia1; i <= dia2; ++i) {
            //creem una fila per cada funció (mateixa obra amb dia diferent)
            String dataTotal = i + "-" + mesInicial + "-" + anyIni;
            //traduim a milis per poder ordenar la data de vella a nova desde la BD (per quan es mostri al Recycler de les funcions)
            try {
                Date oldDate = dateFormatter.parse(dataTotal);
                dataMilisegs = oldDate.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ContentValues values = new ContentValues();
            values.put(baseDades.CN_TITOL, String.valueOf(titol.getText())); //content values per passar valor a la BD
            values.put(baseDades.CN_PREU, String.valueOf(preu.getText()));
            values.put(baseDades.CN_DURADA, String.valueOf(durada.getText()));
            values.put(baseDades.CN_DESC, String.valueOf(descr.getText()));
            values.put(baseDades.CN_DATA, dataTotal);
            values.put(baseDades.CN_MILIS, String.valueOf(dataMilisegs));
            values.put(baseDades.CN_BUTAQUES, String.valueOf(0));
            values.put(baseDades.CN_BUTAQUES_DISP, (40));
            values.put(baseDades.CN_CORREUS, ("~"));
            String dia = getDia.format(new java.sql.Date(dataMilisegs)); //agafa el dia en l'idioma que estigui el sistema
            if (dilluns.isChecked()) {
                if (dia.equals("Mon") || dia.equals("dl.")) {
                    values.put(baseDades.CN_DIA, ("Dilluns"));
                    baseDades.createObra(values, "Obra");
                }
            }
            if (dimarts.isChecked()) {
                if (dia.equals("Tue") || dia.equals("dt.")) {
                    values.put(baseDades.CN_DIA, ("Dimarts"));
                    baseDades.createObra(values, "Obra");
                }
            }
            if (dimecres.isChecked()) {
                if (dia.equals("Wed") || dia.equals("dc.")) {
                    values.put(baseDades.CN_DIA, ("Dimecres"));
                    baseDades.createObra(values, "Obra");
                }
            }
            if (dijous.isChecked()) {
                if (dia.equals("Thu") || dia.equals("dj.")) {
                    values.put(baseDades.CN_DIA, ("Dijous"));
                    baseDades.createObra(values, "Obra");
                }
            }
            if (divendres.isChecked()) {
                if (dia.equals("Fri") || dia.equals("dv.")) {
                    values.put(baseDades.CN_DIA, ("Divendres"));
                    baseDades.createObra(values, "Obra");
                }
            }
            if (dissabte.isChecked()) {
                if (dia.equals("Sat") || dia.equals("ds.")) {
                    values.put(baseDades.CN_DIA, ("Dissabte"));
                    baseDades.createObra(values, "Obra");
                }
            }
            if (diumenge.isChecked()) {
                if (dia.equals("Sun") || dia.equals("dg.")) {
                    values.put(baseDades.CN_DIA, ("Diumenge"));
                    baseDades.createObra(values, "Obra");
                }
            }

        }
        Intent intent = new Intent(getApplicationContext(), llistaObres.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.afegeix:
                calcul_data();
                //  if(!trobat) {
                   /* Intent intent = new Intent(getApplicationContext(), llistaObres.class);
                    startActivity(intent);
                    finish();*/
                //}
                break;
            case R.id.dataInit:
                fromDatePickerDialog.show();
                break;
            case R.id.dataFi:
                toDatePickerDialog.show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), llistaObres.class);
        startActivity(intent);
        finish();
    }
}
