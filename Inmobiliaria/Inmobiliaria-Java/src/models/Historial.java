package models;

import java.time.LocalDateTime;

public class Historial {
    public enum TipoAccion {
        BUSQUEDA, VISUALIZACION, RESERVA, CANCELACION
    }

    private int id;
    private int idUsuario;
    private TipoAccion tipo;
    private String descripcion;
    private LocalDateTime fecha;
    private String resultado;

    public Historial(int id, int idUsuario, TipoAccion tipo, String descripcion, String resultado) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.resultado = resultado;
        this.fecha = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Historial{" + "id=" + id + ", tipo=" + tipo +
                ", descripcion='" + descripcion + '\'' + ", resultado='" + resultado + '\'' + '}';
    }
}
