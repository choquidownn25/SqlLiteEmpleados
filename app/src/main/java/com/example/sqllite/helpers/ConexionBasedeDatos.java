package com.example.sqllite.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ConexionBasedeDatos extends SQLiteOpenHelper {

    private static final String NOMBRE_BASE_DE_DATOS = "empleado",
            NOMBRE_TABLA_EMPLEADO = "empleado";
    private static final int VERSION_BASE_DE_DATOS = 1;

    public ConexionBasedeDatos(Context context) {
        super(context, NOMBRE_BASE_DE_DATOS, null, VERSION_BASE_DE_DATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s(id integer primary key autoincrement, nombre text, edad int, direccion text, email text)", NOMBRE_TABLA_EMPLEADO));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
