package models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String telefono;
    private double presupuesto;
    private ArrayList<String> preferencias;
    private boolean activo;
    private LocalDateTime fechaRegistro;

    public Usuario(int id, String nombre, String email, String telefono, double presupuesto) {
        if (id <= 0 || nombre.isBlank() || !email.contains("@") || presupuesto < 0) {
            throw new IllegalArgumentException("Datos inválidos para crear Usuario");
        }
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.presupuesto = presupuesto;
        this.preferencias = new ArrayList<>();
        this.activo = true;
        this.fechaRegistro = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public ArrayList<String> getPreferencias() {
        return preferencias;
    }

    public void agregarPreferencia(String pref) {
        preferencias.add(pref);
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre='" + nombre + '\'' +
                ", presupuesto=" + presupuesto + ", activo=" + activo + '}';
    }
}