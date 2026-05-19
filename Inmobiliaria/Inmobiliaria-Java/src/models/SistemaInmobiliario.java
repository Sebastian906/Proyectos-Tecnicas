package models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class SistemaInmobiliario {

    // Lista dinámica de todas las propiedades registradas en el sistema 
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Casa> casas;

    // Lista dinámica de todos los usuarios registrados 
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Usuario> usuarios;

    // Lista dinámica de todas las reservas creadas
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Reserva> reservas;

    // Lista dinámica de todas las acciones registradas en el historial
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Historial> historial;

    // Pila (LIFO) de búsquedas recientes.
    // La búsqueda más reciente siempre está en el tope.
    // Permite al usuario ver y deshacer sus últimas búsquedas.
    @SuppressWarnings("FieldMayBeFinal")
    private Stack<String> pilaSearches;

    // Cola (FIFO) de reservas pendientes de procesamiento.
    // Las reservas se procesan en el orden exacto en que llegaron.
    // Garantiza justicia: quien solicitó primero, se atiende primero.
    @SuppressWarnings("FieldMayBeFinal")
    private Queue<Reserva> colaReservas;

    // Contadores
    private int contadorCasas;
    private int contadorUsuarios;
    private int contadorReservas;
    private int contadorHistorial;

    // Constructor que inicializa todas las colecciones y contadores.
    public SistemaInmobiliario() {
        casas = new ArrayList<>();
        usuarios = new ArrayList<>();
        reservas = new ArrayList<>();
        historial = new ArrayList<>();
        pilaSearches = new Stack<>();
        colaReservas = new LinkedList<>();
        contadorCasas = 0;
        contadorUsuarios = 0;
        contadorReservas = 0;
        contadorHistorial = 0;
    }

    // Genera un nuevo ID único para una casa y lo devuelve.
    // El ID es secuencial e irrepetible.
    public int generarIdCasa() {
        return ++contadorCasas;
    }

    // Agrega una casa al sistema.
    public void agregarCasa(Casa casa) {
        if (casa == null) throw new IllegalArgumentException("La casa no puede ser null");
        casas.add(casa);
        // Asegurar que el contador esté sincronizado al cargar desde archivo
        if (casa.getId() > contadorCasas) {
            contadorCasas = casa.getId();
        }
    }

    // Retorna una copia de la lista completa de casas.
    // Se retorna una copia para evitar modificaciones externas no controladas.
    public ArrayList<Casa> obtenerCasas() {
        return new ArrayList<>(casas);
    }

    // Retorna la referencia directa a la lista de casas.
    // Usar con cuidado: solo para algoritmos que necesiten modificar la lista.
    public ArrayList<Casa> obtenerCasasRef() {
        return casas;
    }

    // Busca una casa por su ID.
    public Casa obtenerCasaPorId(int id) {
        for (Casa casa : casas) {
            if (casa.getId() == id) return casa;
        }
        return null;
    }

    // Elimina una casa del sistema por su ID.
    public boolean eliminarCasa(int id) {
        for (int i = 0; i < casas.size(); i++) {
            if (casas.get(i).getId() == id) {
                casas.remove(i);
                return true;
            }
        }
        return false;
    }

    // Genera un nuevo ID único para un usuario.
    public int generarIdUsuario() {
        return ++contadorUsuarios;
    }

    // Agrega un usuario al sistema.
    public void agregarUsuario(Usuario usuario) {
        if (usuario == null) throw new IllegalArgumentException("El usuario no puede ser null");
        usuarios.add(usuario);
        if (usuario.getId() > contadorUsuarios) {
            contadorUsuarios = usuario.getId();
        }
    }

    // Retorna una copia de la lista completa de usuarios.
    public ArrayList<Usuario> obtenerUsuarios() {
        return new ArrayList<>(usuarios);
    }

    // Busca un usuario por su ID.
    public Usuario obtenerUsuarioPorId(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) return usuario;
        }
        return null;
    }

    // Genera un nuevo ID único para una reserva.
    public int generarIdReserva() {
        return ++contadorReservas;
    }

    // Agrega una reserva a la lista de reservas del sistema.
    public void agregarReserva(Reserva reserva) {
        if (reserva == null) throw new IllegalArgumentException("La reserva no puede ser null");
        reservas.add(reserva);
        if (reserva.getId() > contadorReservas) {
            contadorReservas = reserva.getId();
        }
    }

    // Retorna todas las reservas del sistema.
    public ArrayList<Reserva> obtenerReservas() {
        return new ArrayList<>(reservas);
    }

    // Retorna las reservas de un usuario específico.
    public ArrayList<Reserva> obtenerReservasPorUsuario(int idUsuario) {
        ArrayList<Reserva> resultado = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getIdUsuario() == idUsuario) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    // Agrega una búsqueda al tope de la pila de búsquedas recientes.
    // La búsqueda más reciente siempre queda en el tope (LIFO).
    // Limita la pila a 20 elementos para no consumir memoria indefinidamente.
    public void agregarBusquedaReciente(String busqueda) {
        if (busqueda != null && !busqueda.isBlank()) {
            pilaSearches.push(busqueda.trim());
        }
    }

    // Extrae y retorna la búsqueda más reciente de la pila (LIFO).
    // La búsqueda extraída se elimina de la pila.
    public String deshacerUltimaBusqueda() {
        return pilaSearches.isEmpty() ? null : pilaSearches.pop();
    }

    // Consulta la búsqueda más reciente sin extraerla de la pila.
    public String verUltimaBusqueda() {
        return pilaSearches.isEmpty() ? null : pilaSearches.peek();
    }

    // Retorna una copia de la pila de búsquedas para visualización.
    @SuppressWarnings("unchecked")
    public Stack<String> obtenerPilaBusquedas() {
        return (Stack<String>) pilaSearches.clone();
    }

    // Indica si la pila de búsquedas está vacía.
    public boolean pilaVacia() {
        return pilaSearches.isEmpty();
    }

    // Encola una reserva al final de la cola de procesamiento (FIFO).
    // Las reservas se agregan al final y se procesan desde el inicio.
    public void encolarReserva(Reserva reserva) {
        if (reserva == null) throw new IllegalArgumentException("La reserva no puede ser null");
        colaReservas.offer(reserva);
    }

    // Desencola y retorna la reserva más antigua de la cola (FIFO).
    // La primera reserva en llegar es la primera en procesarse.
    public Reserva procesarReserva() {
        return colaReservas.poll();
    }

    // Consulta la próxima reserva a procesar sin extraerla de la cola.
    public Reserva verProximaReserva() {
        return colaReservas.peek();
    }

    // Retorna cuántas reservas están pendientes de procesamiento en la cola.
    public int tamanoColaReservas() {
        return colaReservas.size();
    }

    // Indica si la cola de reservas está vacía.
    public boolean colaVacia() {
        return colaReservas.isEmpty();
    }

    // Genera un nuevo ID único para un registro de historial.
    public int generarIdHistorial() {
        return ++contadorHistorial;
    }

    // Registra una nueva acción en el historial del sistema.
    public void registrarAccion(Historial h) {
        if (h == null) throw new IllegalArgumentException("El historial no puede ser null");
        historial.add(h);
        if (h.getId() > contadorHistorial) {
            contadorHistorial = h.getId();
        }
    }

    // Retorna todos los registros del historial del sistema.
    public ArrayList<Historial> obtenerHistorial() {
        return new ArrayList<>(historial);
    }
}