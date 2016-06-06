package com.example.marlen.ventaentrades_idi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Help extends AppCompatActivity {

    TextView helpStuff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        helpStuff = (TextView)findViewById(R.id.helpstuff);
        helpStuff.setText("Benvigut/da a La Sala.\n\nEn aquesta aplicació podrem fer la compra " +
                "d'entrades amb la seva reserva de butaques corresponent. A més, també podem afegir " +
                "obres, eliminar-les i veure'n les estadístiques de compra i recaptació.\n\n" +
                "La pantalla principal ens mostra directament les obres disponibles al teatre. Al " +
                "menú superior tenim les possibilitats d'inicialitzar i esborrar les dades per " +
                "comoditat.\nPitjant la obra que ens interessi, en podrem veure la seva informació " +
                "general: sinopsi, duració... Al menú superior disposem de les opcions d'eliminar " +
                "aquesta obra i de mostrar-ne les estadístiques.\n\nA la finestra de les estadístiques, " +
                "adalt de tot tenim el total de compra d'entrades d'aquesta obra i el seu percentatge " +
                "respecte el total d'entrades venudes entre totes les obres, i la seva recaptació total. " +
                "Avall, tenim el total de venta d'entrades d'aquesta obra repartida entre tots els dies, " +
                "informació útil per saber quins són els dies de més activitat d'aquesta.\n\n" +
                "Per a comprar l'entrada, accedim a totes les funcions disponibles. Com ja ens indica, " +
                "si mantenim premuda una funció concreta, ens apareixerà una llista amb tots els correus " +
                "dels compradors.\n\nUn cop haguem triat la funció que ens interessa veure, passem al " +
                "pati de butaques, on podrem triar els seients. Els que estan en gris són els " +
                "que ja estan ocupats.\n\nFinalment, arribem al resum final de compra, on tindrem " +
                "el preu total de la compra. A més, a La Sala disposem de varis descomptes si es " +
                "disposa del Carnet Jove, el carnet d'Aturat/da o el carnet universitari.\n\n" +
                "Esperem que aquesta aplicació sigui del vostre gust.");
    }
}
