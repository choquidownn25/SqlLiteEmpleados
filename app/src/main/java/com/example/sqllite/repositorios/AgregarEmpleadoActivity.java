package com.example.sqllite.repositorios;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqllite.R;
import com.example.sqllite.controllers.EmpleadoController;
import com.example.sqllite.models.Empleado;

public class AgregarEmpleadoActivity extends AppCompatActivity {

    private Button btnAgregarEmpleado, btnCancelarNuevaEmpleado;
    private EditText etNombre, etEdad, etDireccion, etEmail;
    private EmpleadoController empleadoController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_empleado);

        // Instanciar vistas
        etNombre = findViewById(R.id.etNombre);
        etEdad = findViewById(R.id.etEdad);
        etDireccion = findViewById(R.id.etdireccion);
        etEmail = findViewById(R.id.etEmail);
        btnAgregarEmpleado = findViewById(R.id.btnAgregarEmpleado);
        btnCancelarNuevaEmpleado = findViewById(R.id.btnCancelarNuevaEmpleado);
        // Crear el controlador
        empleadoController = new EmpleadoController(AgregarEmpleadoActivity.this);

        // Agregar listener del botón de guardar
        btnAgregarEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Resetear errores a ambos
                etNombre.setError(null);
                etEdad.setError(null);
                String nombre = etNombre.getText().toString(),
                        edadComoCadena = etEdad.getText().toString();
                if ("".equals(nombre)) {
                    etNombre.setError("Escribe el nombre de la empleado");
                    etNombre.requestFocus();
                    return;
                }
                if ("".equals(edadComoCadena)) {
                    etEdad.setError("Escribe la edad del empleado");
                    etEdad.requestFocus();
                    return;
                }

                String direccion = etDireccion.getText().toString();
                if ("".equals(direccion)) {
                    etDireccion.setError("Escribe la direccion");
                    etDireccion.requestFocus();
                    return;
                }


                String email = etEmail.getText().toString();
                if ("".equals(email)) {
                    etEmail.setError("Escribe el nombre de la email");
                    etEmail.requestFocus();
                    return;
                }


                // Ver si es un entero
                int edad;
                try {
                    edad = Integer.parseInt(etEdad.getText().toString());
                } catch (NumberFormatException e) {
                    etEdad.setError("Escribe un número");
                    etEdad.requestFocus();
                    return;
                }
                // Ya pasó la validación
                Empleado nuevaEmpleado = new Empleado(nombre, edad, direccion, email);
                long id = empleadoController.nuevaEmpleado(nuevaEmpleado);
                if (id == -1) {
                    // De alguna manera ocurrió un error
                    Toast.makeText(AgregarEmpleadoActivity.this, "Error al guardar. Intenta de nuevo", Toast.LENGTH_SHORT).show();
                } else {
                    // Terminar
                    finish();
                }
            }
        });

        // El de cancelar simplemente cierra la actividad
        btnCancelarNuevaEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
