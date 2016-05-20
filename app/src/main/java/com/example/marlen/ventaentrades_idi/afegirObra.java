package com.example.marlen.ventaentrades_idi;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class afegirObra extends AppCompatActivity implements View.OnClickListener {
    Button btAfegir;
    EditText titol, preu, durada, descr;
    TextView titol_et, preu_et, dur_et, descr_et, dataIni, dataFi;
    DbHelper baseDades;

    //per les dates d'inici i final
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    //format de la data que guardem agafada del calendari
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_obra);

        //concretem el format de la data
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        btAfegir = (Button)findViewById(R.id.afegeix);

        titol = (EditText)findViewById(R.id.titol);
        preu = (EditText)findViewById(R.id.preu);
        durada = (EditText)findViewById(R.id.durada);
        descr = (EditText)findViewById(R.id.descripcio);

        titol_et = (TextView)findViewById(R.id.titol_etiqueta);
        preu_et = (TextView)findViewById(R.id.preu_etiqueta);
        dur_et = (TextView)findViewById(R.id.dur_etiqueta);
        descr_et = (TextView)findViewById(R.id.desc_etiqueta);
        dataIni = (TextView)findViewById(R.id.dataInit);
        dataFi = (TextView)findViewById(R.id.dataFi);

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

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dataFi.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.afegeix:
                ContentValues values = new ContentValues();
                values.put(baseDades.CN_TITOL, String.valueOf(titol.getText())); //content values per passar valor a la BD
                values.put(baseDades.CN_PREU, String.valueOf(preu.getText()));
                values.put(baseDades.CN_DURADA,String.valueOf(durada.getText()));
                values.put(baseDades.CN_DESC,String.valueOf(descr.getText()));
                values.put(baseDades.CN_DATA,String.valueOf(dataIni.getText()));
                baseDades.createObra(values, "Obra");
                Toast.makeText(getApplicationContext(), "Obra afegida", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), llistaObres.class);
                startActivity(intent);
                finish();
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
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), llistaObres.class);
        startActivity(intent);
        finish();
    }
}
