package com.example.marlen.ventaentrades_idi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
                            CN_MILIS, CN_BUTAQUES, CN_CORREUS};
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

    //per poder mostrar tota la info d'una obra
    //mostrarà la obra amb titol igual a "titolObra"
    public Cursor getObra(String titolObra) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITOL,CN_DATA,CN_PREU,CN_DURADA,CN_DESC,CN_BUTAQUES_DISP,
                            CN_MILIS,CN_BUTAQUES,CN_CORREUS};
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

    //agafar una funció concreta
    public Cursor getFuncio(String titolObra, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITOL,CN_DATA,CN_PREU,CN_DURADA,CN_DESC,CN_BUTAQUES_DISP,
                CN_MILIS,CN_BUTAQUES,CN_CORREUS};
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
                            CN_MILIS,CN_BUTAQUES,CN_CORREUS};
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
        String aux;
        aux = CN_CORREUS + correu + "~"; //concateno els correus que es van afegint i els separo amb ~
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
