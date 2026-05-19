package models;

import java.time.LocalDateTime;

public class Casa implements Comparable<Casa> {
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

    // Constructor completo con validaciones
    public Casa(int id, String direccion, String ciudad, double precio,
                int area, int dormitorios, int banos, boolean disponible,
            String descripcion, String propietario) {
        if (id <= 0)
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        if (direccion == null || direccion.isBlank())
            throw new IllegalArgumentException("La dirección no puede estar vacía");
        if (ciudad == null || ciudad.isBlank())
            throw new IllegalArgumentException("La ciudad no puede estar vacía");
        if (precio <= 0)
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        if (area <= 0)
            throw new IllegalArgumentException("El área debe ser mayor a 0");
        if (dormitorios < 0)
            throw new IllegalArgumentException("Los dormitorios no pueden ser negativos");
        if (banos < 0)
            throw new IllegalArgumentException("Los baños no pueden ser negativos");

        this.id = id;
        this.direccion = direccion.trim();
        this.ciudad = ciudad.trim();
        this.precio = precio;
        this.area = area;
        this.dormitorios = dormitorios;
        this.banos = banos;
        this.disponible = disponible;
        this.descripcion = descripcion == null ? "" : descripcion.trim();
        this.propietario = propietario == null ? "" : propietario.trim();
        this.fechaRegistro = LocalDateTime.now();
    }

    // Getters
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

    // Setters
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setPrecio(double precio) {
        if (precio <= 0) throw new IllegalArgumentException("El precio debe ser mayor a 0");
        this.precio = precio;
    }

    public void setDireccion(String direccion) {
        if (direccion == null || direccion.isBlank()) throw new IllegalArgumentException("La dirección no puede estar vacía");
        this.direccion = direccion.trim();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion == null ? "" : descripcion.trim();
    }

    // Verificar que el precio esté dentro de un rango
    public boolean isPrecioEnRango(double min, double max) {
        return this.precio >= min && this.precio <= max;
    }

    // Verificar que haya texto en la descripción, dirección o propietario
    public boolean contieneTexto(String texto) {
        if (texto == null || texto.isBlank())
            return false;
        String t = texto.toLowerCase();
        return descripcion.toLowerCase().contains(t)
                || direccion.toLowerCase().contains(t)
                || propietario.toLowerCase().contains(t);
    }

    // Verificar que haya un mínimo de dormitorios disponibles
    public boolean tieneDormitoriosMinimo(int minimo) {
        return this.dormitorios >= minimo;
    }

    // Comparación de casas por precio
    @Override
    public int compareTo(Casa otra) {
        return Double.compare(this.precio, otra.precio);
    }

    // Comparar casas por valor igual de ID
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Casa))
            return false;
        Casa otra = (Casa) obj;
        return this.id == otra.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    // Representación de la casa en formato CSV
    public String toCsv() {
        return id + "," + direccion + "," + ciudad + "," + precio + ","
                + area + "," + dormitorios + "," + banos + "," + disponible + ","
                + descripcion + "," + propietario;
    }

    // Representación de la casa en formato legible
    @Override
    public String toString() {
        return String.format("[%d] %s, %s | $%,.0f | %dm² | %d dorm | %d baños | %s",
            id, direccion, ciudad, precio, area, dormitorios, banos,
            disponible ? "Disponible" : "Reservada");
    }
}