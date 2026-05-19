package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reserva {
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

    public Reserva(int id, int idUsuario, int idCasa, LocalDate fechaInicio, int duracion) {
        if (idUsuario <= 0 || idCasa <= 0 || duracion <= 0) {
            throw new IllegalArgumentException("Datos inválidos para crear Reserva");
        }
        this.id = id;
        this.idUsuario = idUsuario;
        this.idCasa = idCasa;
        this.fechaReserva = LocalDateTime.now();
        this.fechaInicio = fechaInicio;
        this.duracion = duracion;
        this.estado = EstadoReserva.PENDIENTE;
    }

    public int getId() {
        return id;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Reserva{" + "id=" + id + ", idUsuario=" + idUsuario +
                ", idCasa=" + idCasa + ", estado=" + estado + '}';
    }
}