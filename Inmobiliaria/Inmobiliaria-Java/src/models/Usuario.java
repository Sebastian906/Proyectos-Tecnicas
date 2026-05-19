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

    // Constructor completo con validaciones
    public Usuario(int id, String nombre, String email, String telefono, double presupuesto) {
        if (id <= 0) throw new IllegalArgumentException("El ID debe ser mayor a 0");
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El nombre no puede estar vacío");
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("El email debe contener '@'");
        if (presupuesto < 0) throw new IllegalArgumentException("El presupuesto no puede ser negativo");

        this.id = id;
        this.nombre = nombre.trim();
        this.email = email.trim();
        this.telefono = telefono == null ? "" : telefono.trim();
        this.presupuesto = presupuesto;
        this.preferencias = new ArrayList<>();
        this.activo = true;
        this.fechaRegistro = LocalDateTime.now();
    }

    //  Getters 
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public ArrayList<String> getPreferencias() {
        return preferencias;
    }

    public boolean isActivo() {
        return activo;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    // @param presupuesto Nuevo presupuesto (>= 0)
    public void setPresupuesto(double presupuesto) {
        if (presupuesto < 0) throw new IllegalArgumentException("El presupuesto no puede ser negativo");
        this.presupuesto = presupuesto;
    }

    //Estado de actividad del usuario 
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // Agrega una preferencia a la lista del usuario (si no existe ya).
    public void agregarPreferencia(String preferencia) {
        if (preferencia != null && !preferencia.isBlank()) {
            String p = preferencia.trim();
            if (!preferencias.contains(p)) {
                preferencias.add(p);
            }
        }
    }

    // Remueve una preferencia de la lista del usuario.
    public void removerPreferencia(String preferencia) {
        preferencias.remove(preferencia);
    }

    // Serializa el usuario a CSV.
    public String toCsv() {
        return id + "," + nombre + "," + email + "," + telefono + ","
             + presupuesto + "," + activo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Usuario)) return false;
        Usuario otro = (Usuario) obj;
        return this.id == otro.id;
    }

    @Override
    public int hashCode() { return Integer.hashCode(id); }

    @Override
    public String toString() {
        return String.format("[%d] %s | %s | Tel: %s | Presupuesto: $%,.0f | %s",
            id, nombre, email, telefono, presupuesto, activo ? "Activo" : "Inactivo");
    }
}