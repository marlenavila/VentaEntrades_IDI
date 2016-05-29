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

    public static String CN_BUTAQUES_DISP = "butaques";


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
            + CN_BUTAQUES_DISP + " INTEGER, "
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
        String[] columns = {CN_TITOL,CN_DATA,CN_PREU,CN_DURADA,CN_DESC,CN_BUTAQUES_DISP};
        Cursor c = db.query(
                OBRA_TABLE,  // The table to query
                columns,                                // The columns to return
                null,                                   // The columns for the WHERE clause
                null,                                   // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                CN_TITOL + " ASC"                     // The sort order és alfabètic
        );
        return c;
    }

    //per poder mostrar tota la info d'una obra
    //mostrarà la obra amb titol igual a "titolObra"
    public Cursor getObra(String titolObra) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITOL,CN_DATA,CN_PREU,CN_DURADA,CN_DESC,CN_BUTAQUES_DISP};
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

    //per poder mostrar totes les files d'una mateixa obra però amb dies diferents
    public Cursor getAllDies(String titolObra) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_TITOL,CN_DATA,CN_PREU,CN_DURADA,CN_DESC,CN_BUTAQUES_DISP};
        String[] where = {titolObra};
        Cursor c = db.query(
                OBRA_TABLE,  // The table to query
                columns,                                // The columns to return
                "titolObra=?",                          // The columns for the WHERE clause
                where,                                  // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                CN_DATA + " ASC"                       // The sort order és alfabètic
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


    //borrar BD
    public void deleteBD (){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(OBRA_TABLE, null, null);    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
