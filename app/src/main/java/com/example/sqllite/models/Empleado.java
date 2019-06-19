package com.example.sqllite.models;

public class Empleado {

    private String email;
    private String direccion;
    private String nombre;
    private int edad;
    private long id; // El ID de la BD

    public Empleado(String nombre, int edad, String direccion, String email) {
        this.email  = email;
        this.direccion = direccion;
        this.nombre = nombre;
        this.edad = edad;
    }

    // Constructor para cuando instanciamos desde la BD
    public Empleado(String email, String direccion, String nombre, int edad, long id) {
        this.email = email;
        this.direccion = direccion;
        this.nombre = nombre;
        this.edad = edad;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "nombre='" + nombre + '\'' +
                "edad='" + edad + '\'' +
                "direccion='" + direccion + '\'' +
                ", email=" + email +
                '}';
    }

}
