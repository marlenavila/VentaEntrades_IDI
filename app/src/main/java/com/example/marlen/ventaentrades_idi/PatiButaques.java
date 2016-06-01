package com.example.marlen.ventaentrades_idi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PatiButaques extends AppCompatActivity implements View.OnClickListener{

    TextView esc_et, titolEsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pati_butaques);

        esc_et = (TextView)findViewById(R.id.escenari_et);
        titolEsc = (TextView)findViewById(R.id.esc_titolObra);

        Bundle b = getIntent().getExtras();
        String titolRec = b.getString("titol");
        titolEsc.setText(titolRec);
    }

    @Override
    public void onClick(View v) {

    }
}
