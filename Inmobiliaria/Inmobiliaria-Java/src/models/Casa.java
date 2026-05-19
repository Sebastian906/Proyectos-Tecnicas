package models;

import java.time.LocalDateTime;

public class Casa {
    private int id;
    private String direccion;
    private String ciudad;
    private double precio;
    private int area;
    private int dormitorios;
    private int banos;
    private boolean disponible;
    private String descripcion;
    private String propietario;
    private LocalDateTime fechaRegistro;

    public Casa(int id, String direccion, String ciudad, double precio,
            int area, int dormitorios, int banos, boolean disponible,
            String descripcion, String propietario) {
        if (id <= 0 || direccion.isBlank() || ciudad.isBlank() || precio <= 0 || area <= 0) {
            throw new IllegalArgumentException("Datos inválidos para crear Casa");
        }
        this.id = id;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.precio = precio;
        this.area = area;
        this.dormitorios = dormitorios;
        this.banos = banos;
        this.disponible = disponible;
        this.descripcion = descripcion;
        this.propietario = propietario;
        this.fechaRegistro = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public double getPrecio() {
        return precio;
    }

    public int getArea() {
        return area;
    }

    public int getDormitorios() {
        return dormitorios;
    }

    public int getBanos() {
        return banos;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPropietario() {
        return propietario;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public void setDormitorios(int dormitorios) {
        this.dormitorios = dormitorios;
    }

    public void setBanos(int banos) {
        this.banos = banos;
    }

    @Override
    public String toString() {
        return "Casa{" + "id=" + id + ", ciudad='" + ciudad + '\'' +
                ", precio=" + precio + ", disponible=" + disponible + '}';
    }
}