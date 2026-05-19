package controllers;

import models.*;

public class ControladorReserva {
    private SistemaInmobiliario sistema;

    public ControladorReserva(SistemaInmobiliario sistema) {
        this.sistema = sistema;
    }

    public void encolarReserva(Reserva reserva) {
        sistema.encolarReserva(reserva);
    }

    // Recursión de cola para procesar reservas
    public int procesarReservasRecursivo(int procesadas) {
        Reserva r = sistema.procesarReserva();
        if (r == null)
            return procesadas;
        // Validar y actualizar estado
        r.setEstado(Reserva.EstadoReserva.CONFIRMADA);
        return procesarReservasRecursivo(procesadas + 1);
    }
}