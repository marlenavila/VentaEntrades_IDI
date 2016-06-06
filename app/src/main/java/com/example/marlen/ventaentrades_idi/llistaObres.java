package com.example.marlen.ventaentrades_idi;

import android.app.AlertDialog;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class llistaObres extends AppCompatActivity implements RecyclerItemClickListener.OnItemClickListener {

    DbHelper baseDades;
    RecyclerView recView;
    LinearLayoutManager manager;
    MyCustomAdapter myCustomAdapter;
    private SimpleDateFormat dateFormatter;
    long dataMilisegs;
    String auxcorreu = "~";

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

        dateFormatter = new SimpleDateFormat("dd-MM-yy");

        recView.setLayoutManager(manager);
        recView.addOnItemTouchListener(new RecyclerItemClickListener(this,this));

        //accedeixo a la BD per mostrar les dades que m'interessen de les obres
        //i anar emplenant l'arraylist d'obres
        Cursor c = baseDades.getAllObres();
        //comprovo que la BD no estigui buida
        if(c.moveToFirst()){
            do{
                String titolObra = c.getString(c.getColumnIndex(baseDades.CN_TITOL));
                String preu = c.getString(c.getColumnIndex(baseDades.CN_PREU));
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

    void generaCorreus(int usuaris){
        for(int i = 0; i < usuaris; i++){
            auxcorreu = auxcorreu+i+"usuari@gmail.com"+"~";
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //per anar més ràpid pr borrar les dades actuals
        if (id == R.id.borrarBD) {
            baseDades.deleteBD();
            Intent intent = new Intent(getApplicationContext(), llistaObres.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(id == R.id.about){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("About");
            builder.setIcon(R.drawable.businesswoman);
            builder.setMessage(" La Sala\n Autora: Marlen Àvila\n Versió: 1.0\n " +
                    "marlen.avila@est.fib.upc.edu");
            /*builder.setMessage("Autora: Marlen Àvila");
            builder.setMessage("Versió: 1.0");
            builder.setMessage("marlen.avila@est.fib.upc.edu");*/
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }

        //opció per insertar directament les 3 obres inicials per estalviar feina
        if(id == R.id.initData){
            ContentValues values = new ContentValues();
            values.put(baseDades.CN_TITOL, String.valueOf("Grease")); //content values per passar valor a la BD
            values.put(baseDades.CN_PREU, String.valueOf("40"));
            values.put(baseDades.CN_DURADA,120);
            values.put(baseDades.CN_DESC,String.valueOf("El musical, ambientado en 1959 en el Rydell High School, " +
                    "sigue las peripecias de un grupo de adolescentes, que se mueven entre las complejidades del amor, " +
                    "los coches y las carreras. ¡Y todo aderezado con los sonidos del primerizo rock and roll!"));
            try {
                Date oldDate = dateFormatter.parse("25-08-16");
                dataMilisegs = oldDate.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            values.put(baseDades.CN_DATA,String.valueOf("25-08-16"));
            values.put(baseDades.CN_BUTAQUES,String.valueOf(1204));
            values.put(baseDades.CN_BUTAQUES_DISP,String.valueOf(35));
            values.put(baseDades.CN_DIA, String.valueOf("Dijous"));
            values.put(baseDades.CN_MILIS, String.valueOf(dataMilisegs));
            generaCorreus(5);
            values.put(baseDades.CN_CORREUS, auxcorreu);
            baseDades.createObra(values, "Obra");
            try {
                Date oldDate = dateFormatter.parse("27-08-16");
                dataMilisegs = oldDate.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            values.put(baseDades.CN_DATA,String.valueOf("27-08-16"));
            values.put(baseDades.CN_BUTAQUES,String.valueOf(657899));
            values.put(baseDades.CN_DIA, String.valueOf("Dissabte"));
            values.put(baseDades.CN_MILIS, String.valueOf(dataMilisegs));
            values.put(baseDades.CN_BUTAQUES_DISP,String.valueOf(31));
            auxcorreu = "~";
            generaCorreus(9);
            values.put(baseDades.CN_CORREUS, auxcorreu);
            baseDades.createObra(values, "Obra");
            try {
                Date oldDate = dateFormatter.parse("1-09-16");
                dataMilisegs = oldDate.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            values.put(baseDades.CN_DATA,String.valueOf("1-09-16"));
            values.put(baseDades.CN_BUTAQUES,String.valueOf(1111));
            values.put(baseDades.CN_DIA, String.valueOf("Dijous"));
            values.put(baseDades.CN_MILIS, String.valueOf(dataMilisegs));
            values.put(baseDades.CN_BUTAQUES_DISP,String.valueOf(35));
            auxcorreu = "~";
            generaCorreus(5);
            values.put(baseDades.CN_CORREUS, auxcorreu);
            baseDades.createObra(values, "Obra");


            values = new ContentValues();
            values.put(baseDades.CN_TITOL, String.valueOf("Hairspray")); //content values per passar valor a la BD
            values.put(baseDades.CN_PREU, String.valueOf("30"));
            values.put(baseDades.CN_DURADA,90);
            values.put(baseDades.CN_DESC,String.valueOf("Tracy Turnblad, una chica grande, con un gran " +
                    "peinado y un corazón aún mayor, tiene solamente una pasión: bailar. Su sueño es aparecer" +
                    " en 'El Show de Corny Collins', el programa de baile televisado más codiciado de Baltimore. " +
                    "Tracy parece perfecta para el programa, a no ser por un problema no tan pequeño: no cabe. Su " +
                    "figura generosa siempre la ha apartado de los grupos de moda, cosa que le recuerda continuamente " +
                    "su amante pero excesivamente sobre protectora madre y de generosa figura, Edna."));
            try {
                Date oldDate = dateFormatter.parse("3-10-16");
                dataMilisegs = oldDate.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            values.put(baseDades.CN_DATA,String.valueOf("3-10-16"));
            values.put(baseDades.CN_BUTAQUES,String.valueOf(2));
            values.put(baseDades.CN_BUTAQUES_DISP,String.valueOf(39));
            values.put(baseDades.CN_MILIS, String.valueOf(dataMilisegs));
            values.put(baseDades.CN_DIA,String.valueOf("Dilluns"));
            auxcorreu = "~";
            generaCorreus(1);
            values.put(baseDades.CN_CORREUS, auxcorreu);
            baseDades.createObra(values, "Obra");
            try {
                Date oldDate = dateFormatter.parse("5-10-16");
                dataMilisegs = oldDate.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            values.put(baseDades.CN_DATA,String.valueOf("5-10-16"));
            values.put(baseDades.CN_BUTAQUES,String.valueOf(22222));
            values.put(baseDades.CN_BUTAQUES_DISP,String.valueOf(31));
            values.put(baseDades.CN_MILIS, String.valueOf(dataMilisegs));
            values.put(baseDades.CN_DIA,String.valueOf("Dimecres"));
            auxcorreu = "~";
            generaCorreus(9);
            values.put(baseDades.CN_CORREUS, auxcorreu);
            baseDades.createObra(values, "Obra");
            try {
                Date oldDate = dateFormatter.parse("11-10-16");
                dataMilisegs = oldDate.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            values.put(baseDades.CN_DATA,String.valueOf("11-10-16"));
            values.put(baseDades.CN_BUTAQUES,String.valueOf(42));
            values.put(baseDades.CN_MILIS, String.valueOf(dataMilisegs));
            values.put(baseDades.CN_DIA,String.valueOf("Dimarts"));
            values.put(baseDades.CN_BUTAQUES_DISP,String.valueOf(37));
            auxcorreu = "~";
            generaCorreus(3);
            values.put(baseDades.CN_CORREUS, auxcorreu);
            baseDades.createObra(values, "Obra");

            values = new ContentValues();
            values.put(baseDades.CN_TITOL, String.valueOf("Les Miserables")); //content values per passar valor a la BD
            values.put(baseDades.CN_PREU, String.valueOf("60"));
            values.put(baseDades.CN_DURADA,95);
            values.put(baseDades.CN_DESC,String.valueOf("LOS MISERABLES transcurre en la Francia del " +
                    "siglo XIX y cuenta una emotiva historia de sueños rotos, amor no correspondido, " +
                    "pasión, sacrificio y redención: una prueba atemporal de la fuerza del espíritu humano. " +
                    "Jean Valjean es un exconvicto al que persigue durante décadas el despiadado " +
                    "policía Javert después de saltarse la condicional. Cuando Valjean accede a " +
                    "cuidar a Cosette, la joven hija de Fantine, sus vidas cambiarán para siempre."));
            try {
                Date oldDate = dateFormatter.parse("26-07-16");
                dataMilisegs = oldDate.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            values.put(baseDades.CN_DATA,String.valueOf("26-07-16"));
            values.put(baseDades.CN_BUTAQUES,String.valueOf(3896));
            values.put(baseDades.CN_BUTAQUES_DISP,String.valueOf(33));
            values.put(baseDades.CN_MILIS,String.valueOf(dataMilisegs));
            values.put(baseDades.CN_DIA, String.valueOf("Dimarts"));
            auxcorreu = "~";
            generaCorreus(7);
            values.put(baseDades.CN_CORREUS, auxcorreu);
            baseDades.createObra(values, "Obra");
            try {
                Date oldDate = dateFormatter.parse("28-07-16");
                dataMilisegs = oldDate.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            values.put(baseDades.CN_DATA,String.valueOf("28-07-16"));
            values.put(baseDades.CN_BUTAQUES,String.valueOf(5678));
            values.put(baseDades.CN_MILIS,String.valueOf(dataMilisegs));
            values.put(baseDades.CN_DIA, String.valueOf("Dijous"));
            auxcorreu = "~";
            generaCorreus(7);
            values.put(baseDades.CN_CORREUS, auxcorreu);
            baseDades.createObra(values, "Obra");
            try {
                Date oldDate = dateFormatter.parse("31-07-16");
                dataMilisegs = oldDate.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            values.put(baseDades.CN_DATA,String.valueOf("31-07-16"));
            values.put(baseDades.CN_BUTAQUES,String.valueOf(765));
            values.put(baseDades.CN_MILIS,String.valueOf(dataMilisegs));
            values.put(baseDades.CN_DIA, String.valueOf("Diumenge"));
            auxcorreu = "~";
            generaCorreus(7);
            values.put(baseDades.CN_CORREUS, auxcorreu);
            baseDades.createObra(values, "Obra");
            try {
                Date oldDate = dateFormatter.parse("2-08-16");
                dataMilisegs = oldDate.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            values.put(baseDades.CN_DATA,String.valueOf("2-08-16"));
            values.put(baseDades.CN_BUTAQUES,String.valueOf(3333));
            values.put(baseDades.CN_BUTAQUES_DISP,String.valueOf(36));
            values.put(baseDades.CN_MILIS,String.valueOf(dataMilisegs));
            values.put(baseDades.CN_DIA, String.valueOf("Dimarts"));
            auxcorreu = "~";
            generaCorreus(4);
            values.put(baseDades.CN_CORREUS, auxcorreu);
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

    }
}
