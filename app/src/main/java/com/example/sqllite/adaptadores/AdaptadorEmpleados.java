package com.example.sqllite.adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sqllite.R;
import com.example.sqllite.models.Empleado;

import java.util.List;

public class AdaptadorEmpleados extends RecyclerView.Adapter <AdaptadorEmpleados.ViewHolder>  {

    private List<Empleado> listaDeEmpleado;

    public void setListaDeMascotas(List<Empleado> listaDeEmpleado) {
        this.listaDeEmpleado = listaDeEmpleado;
    }

    public AdaptadorEmpleados(List<Empleado> empleados) {
        this.listaDeEmpleado = empleados;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View filaEmpleado = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fila_empleado, viewGroup, false);
        return new ViewHolder(filaEmpleado);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        // Obtener empleados de nuestra lista gracias al índice i
        Empleado empleado = listaDeEmpleado.get(i);

        // Obtener los datos de la lista
        String emailEmpleado = empleado.getEmail();
        String direccionEmpleado = empleado.getDireccion();
        String nombreEmpleado = empleado.getNombre();
        int edadEmpleado = empleado.getEdad();
        // Y poner a los TextView los datos con setText
        viewHolder.email.setText(emailEmpleado);
        viewHolder.direccion.setText(direccionEmpleado);
        viewHolder.nombre.setText(nombreEmpleado);
        viewHolder.edad.setText(String.valueOf(edadEmpleado));
    }

    @Override
    public int getItemCount() {
        return listaDeEmpleado.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, edad, direccion, email;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.email = itemView.findViewById(R.id.tvEmail);
            this.direccion = itemView.findViewById(R.id.tvDireccion);
            this.nombre = itemView.findViewById(R.id.tvNombre);
            this.edad = itemView.findViewById(R.id.tvEdad);

        }
    }
}
