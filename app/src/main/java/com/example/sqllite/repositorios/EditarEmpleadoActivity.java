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

public class EditarEmpleadoActivity extends AppCompatActivity {

    private Button btnGuardarCambios, btnCancelarEdicion;
    private EditText etNombre, etEdad, etDireccion, etEmail;
    private Empleado empleado;//Empleado que vamos a estar editando
    private EmpleadoController empleadoController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_empleado);

        // Recuperar datos que enviaron
        Bundle extras = getIntent().getExtras();
        // Si no hay datos (cosa rara) salimos
        if (extras == null) {
            finish();
            return;
        }
        // Instanciar el controlador de los empleados
        empleadoController = new EmpleadoController(EditarEmpleadoActivity.this);

        // Rearmar empledos
        // Nota: igualmente solamente podríamos mandar el id y recuperar empleados de la BD
        long idEmpleado = extras.getLong("idEmpleado");
        String nombreEmpleado = extras.getString("nombreEmpleado");
        int edadEmpleado = extras.getInt("edadEmpleado");
        String direccionEmpleado = extras.getString("direccionEmpleado");
        String emailEmpleado = extras.getString("emailEmpleado");
        empleado = new Empleado( emailEmpleado,direccionEmpleado,nombreEmpleado, edadEmpleado, idEmpleado);


        // Ahora declaramos las vistas
        etEdad = findViewById(R.id.etEditarEdad);
        etNombre = findViewById(R.id.etEditarNombre);

        etDireccion = findViewById(R.id.etdireccion);
        etEmail = findViewById(R.id.etEmail);

        btnCancelarEdicion = findViewById(R.id.btnCancelarEdicionEmpleado);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambiosEmpleado);


        // Rellenar los EditText con los datos de empleados
        etNombre.setText(String.valueOf(empleado.getNombre()));
        etEdad.setText(Integer.toString(empleado.getEdad()));
        etDireccion.setText(String.valueOf(empleado.getDireccion()));
        etEmail.setText(String.valueOf(empleado.getEmail()));
        // Listener del click del botón para salir, simplemente cierra la actividad
        btnCancelarEdicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Listener del click del botón que guarda cambios
        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remover previos errores si existen
                etNombre.setError(null);
                etEdad.setError(null);
                etDireccion.setError(null);
                etEmail.setError(null);
                // Crear empleados con los nuevos cambios pero ponerle
                // el id de la anterior
                String nuevoEmail = etEmail.getText().toString();
                String nuevaDireccion = etDireccion.getText().toString();
                String nuevoNombre = etNombre.getText().toString();
                String posibleNuevaEdad = etEdad.getText().toString();
                if (nuevoNombre.isEmpty()) {
                    etNombre.setError("Escribe el nombre");
                    etNombre.requestFocus();
                    return;
                }
                if (posibleNuevaEdad.isEmpty()) {
                    etEdad.setError("Escribe la edad");
                    etEdad.requestFocus();
                    return;
                }
                if (nuevaDireccion.isEmpty()) {
                    etDireccion.setError("Escriba direccion");
                    etDireccion.requestFocus();
                    return;
                }
                if (nuevoEmail.isEmpty()) {
                    etEmail.setError("Escribe email");
                    etEmail.requestFocus();
                    return;
                }
                // Si no es entero, igualmente marcar error
                int nuevaEdad;
                try {
                    nuevaEdad = Integer.parseInt(posibleNuevaEdad);
                } catch (NumberFormatException e) {
                    etEdad.setError("Escribe un número");
                    etEdad.requestFocus();
                    return;
                }
                // Si llegamos hasta aquí es porque los datos ya están validados
                Empleado empleadoConNuevosCambios = new Empleado(nuevoEmail,nuevaDireccion,nuevoNombre, nuevaEdad, empleado.getId());
                int filasModificadas = empleadoController.guardarCambios(empleadoConNuevosCambios);
                if (filasModificadas != 1) {
                    // De alguna forma ocurrió un error porque se debió modificar únicamente una fila
                    Toast.makeText(EditarEmpleadoActivity.this, "Error guardando cambios. Intente de nuevo.", Toast.LENGTH_SHORT).show();
                } else {
                    // Si las cosas van bien, volvemos a la principal
                    // cerrando esta actividad
                    finish();
                }
            }
        });
    }


}
