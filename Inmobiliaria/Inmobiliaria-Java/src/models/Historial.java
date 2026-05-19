package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Historial {

    // Tipos de acciones que se pueden registrar en el historial.
    public enum TipoAccion {
        BUSQUEDA,
        VISUALIZACION,
        RESERVA,
        CANCELACION,
        REGISTRO_USUARIO,
        REGISTRO_CASA,
        ACCESO_SISTEMA
    }

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private int id;
    private int idUsuario;
    private TipoAccion tipo;
    private String descripcion;
    private LocalDateTime fecha;
    private String resultado;

    // Constructor completo con validaciones
    public Historial(int id, int idUsuario, TipoAccion tipo, String descripcion, String resultado) {
        if (id <= 0) throw new IllegalArgumentException("El ID debe ser mayor a 0");
        this.id = id;
        this.idUsuario = idUsuario;
        this.tipo = tipo;
        this.descripcion = descripcion == null ? "" : descripcion.trim();
        this.resultado = resultado == null ? "" : resultado.trim();
        this.fecha = LocalDateTime.now();
    }

    // Getters
    // ID del registro
    public int getId() {
        return id;
    }

    // ID del usuario que realizó la acción
    public int getIdUsuario() {
        return idUsuario;
    }

    // Tipo de acción registrada
    public TipoAccion getTipo() {
        return tipo;
    }

    // Descripción de la acción
    public String getDescripcion() {
        return descripcion;
    }

    // Fecha y hora en que ocurrió la acción
    public LocalDateTime getFecha() {
        return fecha;
    }

    // Resultado de la acción
    public String getResultado() {
        return resultado;
    }

    // Convierte el registro a formato CSV para almacenamiento o exportación
    public String toCsv() {
        return id + "," + idUsuario + "," + tipo + "," + descripcion + ","
             + fecha.format(FORMATO) + "," + resultado;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s | Usuario:%d | %s | %s | %s",
            id, fecha.format(FORMATO), idUsuario, tipo, descripcion, resultado);
    }
}
