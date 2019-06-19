package com.example.sqllite.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqllite.helpers.ConexionBasedeDatos;
import com.example.sqllite.models.Empleado;

import java.util.ArrayList;

public class EmpleadoController {

    private ConexionBasedeDatos ayudanteBaseDeDatos;
    private String NOMBRE_TABLA = "empleado";

    public EmpleadoController(Context contexto) {
        ayudanteBaseDeDatos = new ConexionBasedeDatos(contexto);
    }


    public int eliminaEmpleado(Empleado empleado) {

        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        String[] argumentos = {String.valueOf(empleado.getId())};
        return baseDeDatos.delete(NOMBRE_TABLA, "id = ?", argumentos);
    }

    public long nuevaEmpleado(Empleado empleado) {
        // writable porque vamos a insertar
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valoresParaInsertar = new ContentValues();
        valoresParaInsertar.put("nombre", empleado.getNombre());
        valoresParaInsertar.put("edad", empleado.getEdad());
        valoresParaInsertar.put("direccion", empleado.getDireccion());
        valoresParaInsertar.put("email", empleado.getEmail());
        return baseDeDatos.insert(NOMBRE_TABLA, null, valoresParaInsertar);
    }

    public int guardarCambios(Empleado empleadoEditada) {
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valoresParaActualizar = new ContentValues();
        valoresParaActualizar.put("nombre", empleadoEditada.getNombre());
        valoresParaActualizar.put("edad", empleadoEditada.getEdad());
        valoresParaActualizar.put("direccion", empleadoEditada.getDireccion());
        valoresParaActualizar.put("email", empleadoEditada.getEmail());
        // where id...
        String campoParaActualizar = "id = ?";
        // ... = idEmpleados
        String[] argumentosParaActualizar = {String.valueOf(empleadoEditada.getId())};
        return baseDeDatos.update(NOMBRE_TABLA, valoresParaActualizar, campoParaActualizar, argumentosParaActualizar);
    }

    public ArrayList<Empleado> obtenerEmpleado() {
        ArrayList<Empleado> empleados = new ArrayList<>();
        // readable porque no vamos a modificar, solamente leer
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getReadableDatabase();
        // SELECT nombre, edad, id
        String[] columnasAConsultar = {"nombre", "edad", "direccion", "email",  "id"};
        Cursor cursor = baseDeDatos.query(
                NOMBRE_TABLA,//from mascotas
                columnasAConsultar,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor == null) {
            /*
                Salimos aquí porque hubo un error, regresar
                lista vacía
             */
            return empleados;

        }
        // Si no hay datos, igualmente regresamos la lista vacía
        if (!cursor.moveToFirst()) return empleados;

        // En caso de que sí haya, iteramos y vamos agregando los
        // datos a la lista de emplaedos
        do {
            // El 0 es el número de la columna, como seleccionamos
            // nombre, edad,id entonces el nombre es 0, edad 1 e id es 2
            String nombreObtenidoDeBD = cursor.getString(0);
            int edadObtenidaDeBD = cursor.getInt(1);
            String direccionObtenidoDeBD = cursor.getString(2);
            String emailObtenidaDeBD = cursor.getString(3);
            long idEmpleado = cursor.getLong(4);
            Empleado mascotaObtenidaDeBD = new Empleado( direccionObtenidoDeBD, emailObtenidaDeBD, nombreObtenidoDeBD, edadObtenidaDeBD, idEmpleado);
            empleados.add(mascotaObtenidaDeBD);
        } while (cursor.moveToNext());

        // Fin del ciclo. Cerramos cursor y regresamos la lista de empleados
        cursor.close();
        return empleados;
    }

}
