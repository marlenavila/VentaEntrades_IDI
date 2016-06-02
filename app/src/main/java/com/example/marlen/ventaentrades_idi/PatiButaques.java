package com.example.marlen.ventaentrades_idi;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PatiButaques extends AppCompatActivity implements View.OnClickListener{

    TextView esc_et, titolEsc;
    Button[] butaques = new Button[41];
    Butaques but;
    long num;
    int num_entrades = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pati_butaques);

        esc_et = (TextView)findViewById(R.id.escenari_et);
        titolEsc = (TextView)findViewById(R.id.esc_titolObra);

        Bundle b = getIntent().getExtras();
        String titolRec = b.getString("titol");
        titolEsc.setText(titolRec);

        num = b.getLong("numero");
        but = new Butaques(num);
        Resources resources = getResources(); //per poder utilitzar R
        String nom = getPackageName(); //per poder accedir a l'id

        for(int i = 1; i <=40; i++){
            butaques[i] = (Button)findViewById(resources.getIdentifier("butaca" + i,"id", nom));
            butaques[i].setOnClickListener(this);
        }
        Toast.makeText(getApplicationContext(), String.valueOf(but.mytoString2()), Toast.LENGTH_LONG).show();
        for(int i = 1; i <= 40; i++){
            if(but.consultarbutaca(i)){ //si l'estat de la butaca és 1
                butaques[i].setBackgroundColor(0xFF9E9E9E);
                butaques[i].setOnClickListener(null); //perquè el botó no es pugui clickar
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next:
                break;
            default:
                for(int i = 0; i <=40; i++){
                    if(findViewById(v.getId()).equals(butaques[i])){
                        if(but.consultarbutaca(i)){ //si ja està clickada (estat 1)
                            but.canviarbutaca(i); //la poso a 0
                            butaques[i].setBackgroundColor(0xFF8BC34A); //i en color verd (desmarcar)
                            num_entrades++;
                        }
                        else{
                            but.canviarbutaca(i);
                            butaques[i].setBackgroundColor(0xFFF44336);
                            num_entrades--;
                        }
                    }
                }
        }

    }
}
