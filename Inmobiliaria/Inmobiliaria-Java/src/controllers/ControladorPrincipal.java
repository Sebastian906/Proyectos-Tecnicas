package controllers;

import models.SistemaInmobiliario;

public class ControladorPrincipal {

    private final SistemaInmobiliario sistema;
    private final ControladorBusqueda controladorBusqueda;
    private final ControladorReserva controladorReserva;
    private final ControladorHistorial controladorHistorial;

    /**
     * Inicializa el sistema completo:
     *   1. Crea el modelo central
     *   2. Carga datos desde archivos CSV
     *   3. Instancia todos los controladores especializados
     */
    public ControladorPrincipal() {
        sistema = new SistemaInmobiliario();
        System.out.println("Cargando datos persistidos...");
        ControladorPersistencia.cargarTodo(sistema);

        controladorBusqueda  = new ControladorBusqueda(sistema);
        controladorReserva   = new ControladorReserva(sistema);
        controladorHistorial = new ControladorHistorial(sistema);
    }

    // Guarda todos los datos del sistema en archivos CSV.
    // Debe llamarse antes de cerrar la aplicación.
    public void guardarYCerrar() {
        System.out.println("Guardando datos...");
        ControladorPersistencia.guardarTodo(sistema);
    }

    public SistemaInmobiliario getSistema() {
        return sistema;
    }

    public ControladorBusqueda getBusqueda() {
        return controladorBusqueda;
    }

    public ControladorReserva getReserva() {
        return controladorReserva;
    }

    public ControladorHistorial getHistorial() {
        return controladorHistorial;
    }
}
