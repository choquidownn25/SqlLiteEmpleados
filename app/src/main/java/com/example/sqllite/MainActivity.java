package com.example.sqllite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sqllite.adaptadores.AdaptadorEmpleados;
import com.example.sqllite.adaptadores.RecyclerTouchListener;
import com.example.sqllite.controllers.EmpleadoController;
import com.example.sqllite.models.Empleado;
import com.example.sqllite.repositorios.AgregarEmpleadoActivity;
import com.example.sqllite.repositorios.EditarEmpleadoActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Empleado> listaDeEmpleado;
    private RecyclerView recyclerView;
    private AdaptadorEmpleados adaptadorEmpleados;
    private EmpleadoController empleadoController;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        // Lo siguiente sí es nuestro ;)
        // Definir nuestro controlador
        empleadoController = new EmpleadoController(MainActivity.this);

        // Instanciar vistas
        recyclerView = findViewById(R.id.recyclerViewEmpleado);
        fab = findViewById(R.id.fabAgregarEmpleado);


        // Por defecto es una lista vacía,
        // se la ponemos al adaptador y configuramos el recyclerView
        listaDeEmpleado = new ArrayList<>();
        adaptadorEmpleados = new AdaptadorEmpleados(listaDeEmpleado);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adaptadorEmpleados);

        // Una vez que ya configuramos el RecyclerView le ponemos los datos de la BD
        refrescarListaDeEmpleados();

        // Listener de los clicks en la lista, o sea el RecyclerView
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override // Un toque sencillo
            public void onClick(View view, int position) {
                // Pasar a la actividad EditarMascotaActivity.java
                Empleado empleadoSeleccionada = listaDeEmpleado.get(position);
                Intent intent = new Intent(MainActivity.this, EditarEmpleadoActivity.class);
                intent.putExtra("idEmpleado", empleadoSeleccionada.getId());
                intent.putExtra("nombreEmpleado", empleadoSeleccionada.getNombre());
                intent.putExtra("edadEmpleado", empleadoSeleccionada.getEdad());
                intent.putExtra("direccionEmpleado", empleadoSeleccionada.getDireccion());
                intent.putExtra("emailEmpleado", empleadoSeleccionada.getEmail());
                startActivity(intent);
            }

            @Override // Un toque largo
            public void onLongClick(View view, int position) {
                final Empleado empleadoParaEliminar = listaDeEmpleado.get(position);
                AlertDialog dialog = new AlertDialog
                        .Builder(MainActivity.this)
                        .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                empleadoController.eliminaEmpleado(empleadoParaEliminar);
                                refrescarListaDeEmpleados();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Confirmar")
                        .setMessage("¿Eliminar a la empleado " + empleadoParaEliminar.getNombre() + "?")
                        .create();
                dialog.show();

            }
        }));

        // Listener del FAB
        //FloatingActionButton fab = findViewById(R.id.fabAgregarEmpleado);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AgregarEmpleadoActivity.class);
                startActivity(intent);
            }
        });

        // Créditos
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Acerca de")
                        .setMessage("CRUD de Android con SQLite")
                        .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogo, int which) {
                                dialogo.dismiss();
                            }
                        })
                        .setPositiveButton("Sitio web", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intentNavegador = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.apoyomederi.com/"));
                                startActivity(intentNavegador);
                            }
                        })
                        .create()
                        .show();
                return false;
            }
        });
    }

    private void refrescarListaDeEmpleados() {
        /*
         * ==========
         * Justo aquí obtenemos la lista de la BD
         * y se la ponemos al RecyclerView
         * ============
         *
         * */
        if (adaptadorEmpleados == null) return;
        listaDeEmpleado = empleadoController.obtenerEmpleado();
        adaptadorEmpleados.setListaDeMascotas(listaDeEmpleado);
        adaptadorEmpleados.notifyDataSetChanged();


    }

    @Override
    protected void onResume() {
        super.onResume();
        refrescarListaDeEmpleados();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
