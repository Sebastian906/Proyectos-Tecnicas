package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reserva {

    // Estados posibles de una reserva en el sistema.
    public enum EstadoReserva {
        PENDIENTE, CONFIRMADA, CANCELADA
    }

    private int id;
    private int idUsuario;
    private int idCasa;
    private LocalDateTime fechaReserva;
    private LocalDate fechaInicio;
    private int duracion;
    private EstadoReserva estado;
    private String observaciones;

    // Constructor completo con validaciones
    public Reserva(int id, int idUsuario, int idCasa, LocalDate fechaInicio, int duracion) {
        if (id <= 0) throw new IllegalArgumentException("El ID debe ser mayor a 0");
        if (idUsuario <= 0) throw new IllegalArgumentException("El ID de usuario debe ser mayor a 0");
        if (idCasa <= 0) throw new IllegalArgumentException("El ID de casa debe ser mayor a 0");
        if (duracion <= 0) throw new IllegalArgumentException("La duración debe ser al menos 1 mes");
        if (fechaInicio == null) throw new IllegalArgumentException("La fecha de inicio es obligatoria");

        this.id = id;
        this.idUsuario = idUsuario;
        this.idCasa = idCasa;
        this.fechaReserva = LocalDateTime.now();
        this.fechaInicio = fechaInicio;
        this.duracion = duracion;
        this.estado = EstadoReserva.PENDIENTE;
        this.observaciones = "";
    }

    // Getters
    // Identificador único de la reserva 
    public int getId() {
        return id;
    }

    // ID del usuario que realizó la reserva 
    public int getIdUsuario() {
        return idUsuario;
    }

    // ID de la propiedad reservada
    public int getIdCasa() {
        return idCasa;
    }

    // Fecha y hora exacta en que se realizó la reserva 
    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    // Fecha de inicio del contrato de arrendamiento 
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    // Duración del contrato en meses
    public int getDuracion() {
        return duracion;
    }

    // Estado actual de la reserva
    public EstadoReserva getEstado() {
        return estado;
    }

    // Observaciones o notas adicionales de la reserva 
    public String getObservaciones() {
        return observaciones;
    }

    // Setters 
    // Actualiza el estado de la reserva.
    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    // Actualiza las observaciones de la reserva
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones == null ? "" : observaciones.trim();
    }

    // Representación de la reserva en formato CSV
    public String toCsv() {
        return id + "," + idUsuario + "," + idCasa + "," + fechaReserva + ","
             + fechaInicio + "," + duracion + "," + estado + "," + observaciones;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Reserva)) return false;
        Reserva otra = (Reserva) obj;
        return this.id == otra.id;
    }

    @Override
    public int hashCode() { return Integer.hashCode(id); }

    @Override
    public String toString() {
        return String.format("[%d] Usuario:%d | Casa:%d | Inicio:%s | %d meses | %s",
            id, idUsuario, idCasa, fechaInicio, duracion, estado);
    }
}