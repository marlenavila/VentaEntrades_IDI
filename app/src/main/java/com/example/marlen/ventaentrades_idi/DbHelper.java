package com.example.marlen.ventaentrades_idi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DbHelper extends SQLiteOpenHelper {

    public static String CN_TITOL = "titolObra";

    public static String CN_DATA = "data";

    public static String CN_PREU = "preu";

    public static String CN_DURADA = "durada";

    public static String CN_DESC = "descripcio";

    public static String CN_BUTAQUES_DISP = "butaques_disponibles";

    public static String CN_MILIS = "milisegons";

    public static String CN_BUTAQUES = "butaques";

    public static String CN_CORREUS = "correus";

    public static String CN_DIA = "dia";
    
    String auxcorreu = "~";
    Long dataMilisegs;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yy");


    //Declaracion del nombre de la base de datos
    public static final int DATABASE_VERSION = 1;

    //Declaracion global de la version de la base de datos
    public static final String DATABASE_NAME = "obra.sqlite";

    //Declaracion del nombre de la tabla
    public static final String OBRA_TABLE ="Obra";

    //sentencia global de creacion de la base de datos
    public static final String OBRA_TABLE_CREATE = "CREATE TABLE " + OBRA_TABLE + " ("
            + CN_TITOL + " TEXT,"
            + CN_DATA + " TEXT," //si la mateixa funció es fa en dies diferents la data hauria de ser primary key tmbe
            + CN_PREU + " INTEGER,"
            + CN_DURADA + " INTEGER,"
            + CN_DESC + " TEXT,"
            + CN_BUTAQUES_DISP + " INTEGER,"
            + CN_MILIS + " LONG, "
            + CN_BUTAQUES + " LONG, "
            + CN_CORREUS + " STRING, "
            + CN_DIA + " STRING, "
            + "PRIMARY KEY (titolObra, data));"; //clau primària composta titol+data


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(OBRA_TABLE_CREATE);
    }

    //per poder mostrar un llistat de totes les obres
    public Cursor getAllObres() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITOL,CN_DATA,CN_PREU,CN_DURADA,CN_DESC,CN_BUTAQUES_DISP,
                            CN_MILIS, CN_BUTAQUES, CN_CORREUS, CN_DIA};
        Cursor c = db.query(
                true,  //DISTINCT
                OBRA_TABLE,  // The table to query
                columns,                                // The columns to return
                null,                                   // The columns for the WHERE clause
                null,                                   // The values for the WHERE clause
                CN_TITOL,                                   // don't group the rows
                null,                                   // don't filter by row groups
                CN_TITOL + " ASC",                     // The sort order és alfabètic
                null
        );
        return c;
    }

    //m'ho retorna absolutament tot, utilitzat per a calcular les estadístiques
    public Cursor getAllEverything() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITOL,CN_DATA,CN_PREU,CN_DURADA,CN_DESC,CN_BUTAQUES_DISP,
                CN_MILIS, CN_BUTAQUES, CN_CORREUS, CN_DIA};
        Cursor c = db.query(
                OBRA_TABLE,  // The table to query
                columns,                                // The columns to return
                null,                                   // The columns for the WHERE clause
                null,                                   // The values for the WHERE clause
                null,                               // don't group the rows
                null,                                   // don't filter by row groups
                null,                     // The sort order és alfabètic
                null
        );
        return c;
    }

    //per poder mostrar tota la info d'una obra
    //mostrarà la obra amb titol igual a "titolObra"
    public Cursor getObra(String titolObra) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITOL,CN_DATA,CN_PREU,CN_DURADA,CN_DESC,CN_BUTAQUES_DISP,
                            CN_MILIS,CN_BUTAQUES,CN_CORREUS, CN_DIA};
        String[] where = {titolObra};
        Cursor c = db.query(
                OBRA_TABLE,  // The table to query
                columns,                                    // The columns to return
                "titolObra=?",                              // The columns for the WHERE clause
                where,                                      // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                null                                        // The sort order
        );
        return c;
    }

    void generaCorreus(int usuaris){
        for(int i = 0; i < usuaris; i++){
            auxcorreu = auxcorreu+i+"usuari@gmail.com"+"~";
        }
    }
    
    public void initDades(){
        ContentValues values = new ContentValues();
        values.put(this.CN_TITOL, String.valueOf("Grease")); //content values per passar valor a la BD
        values.put(this.CN_PREU, String.valueOf("40"));
        values.put(this.CN_DURADA,120);
        values.put(this.CN_DESC,String.valueOf("El musical, ambientado en 1959 en el Rydell High School, " +
                "sigue las peripecias de un grupo de adolescentes, que se mueven entre las complejidades del amor, " +
                "los coches y las carreras. ¡Y todo aderezado con los sonidos del primerizo rock and roll!"));
        try {
            Date oldDate = dateFormatter.parse("25-08-16");
            dataMilisegs = oldDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        values.put(this.CN_DATA,String.valueOf("25-08-16"));
        values.put(this.CN_BUTAQUES,String.valueOf(1204));
        values.put(this.CN_BUTAQUES_DISP,String.valueOf(35));
        values.put(this.CN_DIA, String.valueOf("Dijous"));
        values.put(this.CN_MILIS, String.valueOf(dataMilisegs));
        generaCorreus(5);
        values.put(this.CN_CORREUS, auxcorreu);
        this.createObra(values, "Obra");
        try {
            Date oldDate = dateFormatter.parse("27-08-16");
            dataMilisegs = oldDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        values.put(this.CN_DATA,String.valueOf("27-08-16"));
        values.put(this.CN_BUTAQUES,String.valueOf(657899));
        values.put(this.CN_DIA, String.valueOf("Dissabte"));
        values.put(this.CN_MILIS, String.valueOf(dataMilisegs));
        values.put(this.CN_BUTAQUES_DISP,String.valueOf(31));
        auxcorreu = "~";
        generaCorreus(9);
        values.put(this.CN_CORREUS, auxcorreu);
        this.createObra(values, "Obra");
        try {
            Date oldDate = dateFormatter.parse("1-09-16");
            dataMilisegs = oldDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        values.put(this.CN_DATA,String.valueOf("1-09-16"));
        values.put(this.CN_BUTAQUES,String.valueOf(1111));
        values.put(this.CN_DIA, String.valueOf("Dijous"));
        values.put(this.CN_MILIS, String.valueOf(dataMilisegs));
        values.put(this.CN_BUTAQUES_DISP,String.valueOf(35));
        auxcorreu = "~";
        generaCorreus(5);
        values.put(this.CN_CORREUS, auxcorreu);
        this.createObra(values, "Obra");


        values = new ContentValues();
        values.put(this.CN_TITOL, String.valueOf("Hairspray")); //content values per passar valor a la BD
        values.put(this.CN_PREU, String.valueOf("30"));
        values.put(this.CN_DURADA,90);
        values.put(this.CN_DESC,String.valueOf("Tracy Turnblad, una chica grande, con un gran " +
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
        values.put(this.CN_DATA,String.valueOf("3-10-16"));
        values.put(this.CN_BUTAQUES,String.valueOf(2));
        values.put(this.CN_BUTAQUES_DISP,String.valueOf(39));
        values.put(this.CN_MILIS, String.valueOf(dataMilisegs));
        values.put(this.CN_DIA,String.valueOf("Dilluns"));
        auxcorreu = "~";
        generaCorreus(1);
        values.put(this.CN_CORREUS, auxcorreu);
        this.createObra(values, "Obra");
        try {
            Date oldDate = dateFormatter.parse("5-10-16");
            dataMilisegs = oldDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        values.put(this.CN_DATA,String.valueOf("5-10-16"));
        values.put(this.CN_BUTAQUES,String.valueOf(22222));
        values.put(this.CN_BUTAQUES_DISP,String.valueOf(31));
        values.put(this.CN_MILIS, String.valueOf(dataMilisegs));
        values.put(this.CN_DIA,String.valueOf("Dimecres"));
        auxcorreu = "~";
        generaCorreus(9);
        values.put(this.CN_CORREUS, auxcorreu);
        this.createObra(values, "Obra");
        try {
            Date oldDate = dateFormatter.parse("11-10-16");
            dataMilisegs = oldDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        values.put(this.CN_DATA,String.valueOf("11-10-16"));
        values.put(this.CN_BUTAQUES,String.valueOf(42));
        values.put(this.CN_MILIS, String.valueOf(dataMilisegs));
        values.put(this.CN_DIA,String.valueOf("Dimarts"));
        values.put(this.CN_BUTAQUES_DISP,String.valueOf(37));
        auxcorreu = "~";
        generaCorreus(3);
        values.put(this.CN_CORREUS, auxcorreu);
        this.createObra(values, "Obra");

        values = new ContentValues();
        values.put(this.CN_TITOL, String.valueOf("Les Miserables")); //content values per passar valor a la BD
        values.put(this.CN_PREU, String.valueOf("60"));
        values.put(this.CN_DURADA,95);
        values.put(this.CN_DESC,String.valueOf("LOS MISERABLES transcurre en la Francia del " +
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
        values.put(this.CN_DATA,String.valueOf("26-07-16"));
        values.put(this.CN_BUTAQUES,String.valueOf(3896));
        values.put(this.CN_BUTAQUES_DISP,String.valueOf(33));
        values.put(this.CN_MILIS,String.valueOf(dataMilisegs));
        values.put(this.CN_DIA, String.valueOf("Dimarts"));
        auxcorreu = "~";
        generaCorreus(7);
        values.put(this.CN_CORREUS, auxcorreu);
        this.createObra(values, "Obra");
        try {
            Date oldDate = dateFormatter.parse("28-07-16");
            dataMilisegs = oldDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        values.put(this.CN_DATA,String.valueOf("28-07-16"));
        values.put(this.CN_BUTAQUES,String.valueOf(5678));
        values.put(this.CN_MILIS,String.valueOf(dataMilisegs));
        values.put(this.CN_DIA, String.valueOf("Dijous"));
        auxcorreu = "~";
        generaCorreus(7);
        values.put(this.CN_CORREUS, auxcorreu);
        this.createObra(values, "Obra");
        try {
            Date oldDate = dateFormatter.parse("31-07-16");
            dataMilisegs = oldDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        values.put(this.CN_DATA,String.valueOf("31-07-16"));
        values.put(this.CN_BUTAQUES,String.valueOf(765));
        values.put(this.CN_MILIS,String.valueOf(dataMilisegs));
        values.put(this.CN_DIA, String.valueOf("Diumenge"));
        auxcorreu = "~";
        generaCorreus(7);
        values.put(this.CN_CORREUS, auxcorreu);
        this.createObra(values, "Obra");
        try {
            Date oldDate = dateFormatter.parse("2-08-16");
            dataMilisegs = oldDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        values.put(this.CN_DATA,String.valueOf("2-08-16"));
        values.put(this.CN_BUTAQUES,String.valueOf(3333));
        values.put(this.CN_BUTAQUES_DISP,String.valueOf(36));
        values.put(this.CN_MILIS,String.valueOf(dataMilisegs));
        values.put(this.CN_DIA, String.valueOf("Dimarts"));
        auxcorreu = "~";
        generaCorreus(4);
        values.put(this.CN_CORREUS, auxcorreu);
        this.createObra(values, "Obra");
    }

    //agafar una funció concreta
    public Cursor getFuncio(String titolObra, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITOL,CN_DATA,CN_PREU,CN_DURADA,CN_DESC,CN_BUTAQUES_DISP,
                CN_MILIS,CN_BUTAQUES,CN_CORREUS, CN_DIA};
        String[] where = {titolObra, data};
        Cursor c = db.query(
                OBRA_TABLE,  // The table to query
                columns,                                    // The columns to return
                "titolObra=?" + " and " + "data=?",         // The columns for the WHERE clause
                where,                                      // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                null                                        // The sort order
        );
        return c;
    }

    //per poder mostrar totes les files d'una mateixa obra però amb dies diferents
    public Cursor getAllDies(String titolObra) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITOL,CN_DATA,CN_PREU,CN_DURADA,CN_DESC,CN_BUTAQUES_DISP,
                            CN_MILIS,CN_BUTAQUES,CN_CORREUS,CN_DIA};
        String[] where = {titolObra};
        Cursor c = db.query(
                OBRA_TABLE,  // The table to query
                columns,                                // The columns to return
                "titolObra=?",                          // The columns for the WHERE clause
                where,                                  // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                CN_MILIS + " ASC"                       // The sort order és alfabètic
        );
        return c;
    }

    public Cursor getCorreus(String titolObra, String data){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_CORREUS};
        String[] where = {titolObra, data};
        Cursor c = db.query(
                OBRA_TABLE,  // The table to query
                columns,                                // The columns to return
                "titolObra=?" + " and " + "data=?",     // The columns for the WHERE clause
                where,                                  // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                null                       // The sort order és alfabètic
        );
        return c;
    }

    //mètode per poder crear una obra nova
    //
    public void createObra (ContentValues values, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(
                tableName,
                null,
                values);
    }

    //esborrar obra
    public void deleteObra(String titolObra){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] where = {titolObra};
        db.delete(
                OBRA_TABLE,
                "titolObra=?",
                where
        );

    }

    //actualitzar ristra de bits que em marquen els seients disponibles d'una funció concreta
    public void updateNum(String titolObra, String data, long num){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CN_BUTAQUES,num);
        db.update(OBRA_TABLE, cv, CN_TITOL + "=?" + " and " + CN_DATA + "=?",
                new String[]{titolObra,data});
    }

    //actualitzar num butaques disponibles per una funció concreta
    public void updateNumButDisp(String titolObra, String data, int num){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CN_BUTAQUES_DISP,num);
        db.update(OBRA_TABLE, cv, CN_TITOL + "=?" + " and " + CN_DATA + "=?",
                new String[]{titolObra,data});
    }

    //actualitzar string de correus d'una funció concreta
    public void updateCorreus(String titolObra, String data, String correu){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = getCorreus(titolObra, data);
        String aux = new String();
        if(c.moveToFirst()){
            aux = c.getString(c.getColumnIndex(CN_CORREUS));
        }
        if(aux != null)
            aux = aux + correu + "~"; //concateno els correus que es van afegint i els separo amb ~
        else aux = correu + "~"; //en el cas del primer correu del string
        ContentValues cv = new ContentValues();
        cv.put(CN_CORREUS,aux);
        db.update(OBRA_TABLE, cv, CN_TITOL + "=?" + " and " + CN_DATA + "=?",
                new String[]{titolObra,data});
    }

    //esborrar BD
    public void deleteBD (){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(OBRA_TABLE, null, null);    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
