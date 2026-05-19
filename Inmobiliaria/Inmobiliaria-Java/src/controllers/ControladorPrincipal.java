package controllers;

import models.*;

public class ControladorPrincipal {
    private SistemaInmobiliario sistema;
    private ControladorBusqueda controladorBusqueda;
    private ControladorReserva controladorReserva;
    private ControladorHistorial controladorHistorial;

    public ControladorPrincipal(SistemaInmobiliario sistema) {
        this.sistema = sistema;
        this.controladorBusqueda = new ControladorBusqueda();
        this.controladorReserva = new ControladorReserva(sistema);
        this.controladorHistorial = new ControladorHistorial();
    }

    public SistemaInmobiliario getSistema() {
        return sistema;
    }

    public ControladorBusqueda getControladorBusqueda() {
        return controladorBusqueda;
    }

    public ControladorReserva getControladorReserva() {
        return controladorReserva;
    }

    public ControladorHistorial getControladorHistorial() {
        return controladorHistorial;
    }
}
