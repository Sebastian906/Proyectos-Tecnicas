package controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import models.*;

public class ControladorReserva {

    @SuppressWarnings("FieldMayBeFinal")
    private SistemaInmobiliario sistema;

    public ControladorReserva(SistemaInmobiliario sistema) {
        this.sistema = sistema;
    }

    // Crea una nueva reserva y la encola para procesamiento.
    // No confirma la reserva aún; solo la pone en la cola FIFO.
    public String crearReserva(int idUsuario, int idCasa,
                                LocalDate fechaInicio, int duracion) {
        /* Validar usuario */
        Usuario usuario = sistema.obtenerUsuarioPorId(idUsuario);
        if (usuario == null) return "Error: usuario con ID " + idUsuario + " no existe.";
        if (!usuario.isActivo()) return "Error: el usuario está inactivo.";

        /* Validar casa */
        Casa casa = sistema.obtenerCasaPorId(idCasa);
        if (casa == null) return "Error: casa con ID " + idCasa + " no existe.";
        if (!casa.isDisponible()) return "Error: la propiedad ya está reservada.";

        /* Validar presupuesto */
        if (usuario.getPresupuesto() < casa.getPrecio()) {
            return String.format("Error: presupuesto insuficiente. "
                + "Precio: $%,.0f | Tu presupuesto: $%,.0f",
                casa.getPrecio(), usuario.getPresupuesto());
        }

        /* Validar fecha */
        if (fechaInicio == null) return "Error: la fecha de inicio es obligatoria.";
        if (fechaInicio.isBefore(LocalDate.now())) return "Error: la fecha de inicio no puede ser en el pasado.";

        /* Validar duración */
        if (duracion <= 0) return "Error: la duración debe ser al menos 1 mes.";

        /* Crear reserva y encolar (FIFO) */
        int id = sistema.generarIdReserva();
        Reserva reserva = new Reserva(id, idUsuario, idCasa, fechaInicio, duracion);
        sistema.encolarReserva(reserva);
        sistema.agregarReserva(reserva);

        registrarEnHistorial(idUsuario,
            "Reserva creada para casa ID=" + idCasa, "ENCOLADA");

        return "Reserva #" + id + " encolada correctamente. "
             + "Cola actual: " + sistema.tamanoColaReservas() + " reserva(s) pendiente(s).";
    }

    /**
     * Procesa todas las reservas en la cola usando el orden FIFO.
     * Cada reserva es validada: si es válida se confirma, si no se cancela.
     * Cuando se confirma una reserva, la casa queda marcada como no disponible.
     *
     * Implementa recursión de cola (tail recursion):
     * la llamada recursiva es la ÚLTIMA operación del método.
     * Esto es equivalente a un bucle pero demuestra el concepto académico.
     *
     * Para evitar StackOverflowError en colas muy grandes, se usa
     * un límite de profundidad de recursión y se llama iterativamente
     * cuando se supera ese límite.
     */
    public int procesarColaCompleta() {
        if (sistema.colaVacia()) return 0;
        int total = procesarRecursivo(0, 500); // límite seguro de recursión
        System.out.println("  [Cola FIFO] Total procesadas: " + total + " reserva(s)");
        return total;
    }

    /**
     * Recursión de cola para procesar la cola de reservas.
     *
     * Caso base: cola vacía o límite alcanzado → retornar acumulado
     * Caso recursivo: procesar una reserva, avanzar con procesadas+1
     *
     * La llamada recursiva es la ÚLTIMA operación (tail call).
     */
    private int procesarRecursivo(int procesadas, int limite) {
        /* Caso base 1: cola vacía */
        if (sistema.colaVacia()) return procesadas;

        /* Caso base 2: límite de recursión alcanzado → continuar iterativamente */
        if (procesadas >= limite) {
            return procesadas + procesarIterativo();
        }

        /* Desencolar la reserva más antigua (FIFO: poll desde el frente) */
        Reserva reserva = sistema.procesarReserva();

        /* Validar y actualizar estado */
        String resultado = validarYConfirmar(reserva);

        registrarEnHistorial(reserva.getIdUsuario(),
            "Procesamiento de reserva #" + reserva.getId(), resultado);

        /* Caso recursivo: la llamada recursiva ES la última operación (tail call) */
        return procesarRecursivo(procesadas + 1, limite);
    }

    // Procesamiento iterativo como fallback para colas muy largas.
    // Garantiza que no haya stack overflow sin importar el tamaño de la cola.
    private int procesarIterativo() {
        int procesadas = 0;
        while (!sistema.colaVacia()) {
            Reserva reserva = sistema.procesarReserva();
            String resultado = validarYConfirmar(reserva);
            registrarEnHistorial(reserva.getIdUsuario(),
                "Procesamiento de reserva #" + reserva.getId(), resultado);
            procesadas++;
        }
        return procesadas;
    }

    // Valida una reserva y actualiza su estado a CONFIRMADA o CANCELADA.
    // Si se confirma, marca la casa como no disponible.
    private String validarYConfirmar(Reserva reserva) {
        Casa casa = sistema.obtenerCasaPorId(reserva.getIdCasa());
        Usuario usuario = sistema.obtenerUsuarioPorId(reserva.getIdUsuario());

        String motivo = null;
        if (usuario == null || !usuario.isActivo()) motivo = "usuario inactivo";
        else if (casa == null) motivo = "casa no existe";
        else if (!casa.isDisponible()) motivo = "casa ya reservada";
        else if (usuario.getPresupuesto() < casa.getPrecio()) motivo = "presupuesto insuficiente";

        if (motivo != null) {
            reserva.setEstado(Reserva.EstadoReserva.CANCELADA);
            reserva.setObservaciones("Cancelada: " + motivo);
            return "CANCELADA: " + motivo;
        } else {
            reserva.setEstado(Reserva.EstadoReserva.CONFIRMADA);
            casa.setDisponible(false);
            return "CONFIRMADA";
        }
    }

    // Retorna todas las reservas de un usuario específico.
    public ArrayList<Reserva> obtenerReservasUsuario(int idUsuario) {
        return sistema.obtenerReservasPorUsuario(idUsuario);
    }

    // Retorna todas las reservas del sistema.
    public ArrayList<Reserva> obtenerTodasReservas() {
        return sistema.obtenerReservas();
    }

    // Indica cuántas reservas hay pendientes en la cola.
    public int reservasPendientes() {
        return sistema.tamanoColaReservas();
    }

    private void registrarEnHistorial(int idUsuario, String descripcion, String resultado) {
        int id = sistema.generarIdHistorial();
        Historial h = new Historial(id, idUsuario, Historial.TipoAccion.RESERVA,
                                    descripcion, resultado);
        sistema.registrarAccion(h);
    }
}