package controllers;

import java.util.ArrayList;
import java.util.Stack;
import models.*;

public class ControladorHistorial {

    private final SistemaInmobiliario sistema;

    public ControladorHistorial(SistemaInmobiliario sistema) {
        this.sistema = sistema;
    }

    // Registra una acción general en el historial del sistema.
    public void registrar(int idUsuario, Historial.TipoAccion tipo,
                           String descripcion, String resultado) {
        int id = sistema.generarIdHistorial();
        Historial h = new Historial(id, idUsuario, tipo, descripcion, resultado);
        sistema.registrarAccion(h);
    }

    // Retorna el historial completo de acciones del sistema.
    public ArrayList<Historial> obtenerHistorialCompleto() {
        return sistema.obtenerHistorial();
    }

    // Retorna el historial filtrado por un usuario específico.
    public ArrayList<Historial> obtenerHistorialPorUsuario(int idUsuario) {
        ArrayList<Historial> resultado = new ArrayList<>();
        for (Historial h : sistema.obtenerHistorial()) {
            if (h.getIdUsuario() == idUsuario) resultado.add(h);
        }
        return resultado;
    }

    // Retorna una copia de la pila de búsquedas recientes para mostrar.
    // La búsqueda más reciente está en el tope.
    public Stack<String> obtenerPilaBusquedas() {
        return sistema.obtenerPilaBusquedas();
    }

    // Extrae y retorna la búsqueda más reciente de la pila (LIFO).
    // La búsqueda extraída se elimina de la pila.
    public String deshacerUltimaBusqueda() {
        return sistema.deshacerUltimaBusqueda();
    }

    // Consulta la búsqueda más reciente sin extraerla (peek).
    public String verUltimaBusqueda() {
        return sistema.verUltimaBusqueda();
    }

    // Indica si hay búsquedas registradas en la pila.
    public boolean pilaBusquedasVacia() {
        return sistema.pilaVacia();
    }
}