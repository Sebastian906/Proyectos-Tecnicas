package controllers;

import algorithms.*;
import java.util.ArrayList;
import java.util.Stack;
import models.*;

// Controlador que orquesta las búsquedas y ordenamientos sobre las casas.
// Delega la lógica algorítmica a los paquetes de algoritmos y
// registra cada búsqueda en la pila de historial del sistema.
public class ControladorBusqueda {

    private final SistemaInmobiliario sistema;

    public ControladorBusqueda(SistemaInmobiliario sistema) {
        this.sistema = sistema;
    }

    // Búsqueda lineal por ciudad. Retorna TODAS las coincidencias.
    // Registra la búsqueda en la pila de búsquedas recientes.
    public ArrayList<Casa> buscarPorCiudad(String ciudad) {
        ArrayList<Casa> casas = sistema.obtenerCasas();
        ArrayList<Casa> resultados = BusquedaLineal.porCiudad(casas, ciudad);
        sistema.agregarBusquedaReciente("Ciudad: " + ciudad);
        registrarEnHistorial("Búsqueda por ciudad: " + ciudad,
                             resultados.size() + " resultado(s)");
        return resultados;
    }

    // Búsqueda por rango de precio usando búsqueda lineal con filtro.
    public ArrayList<Casa> buscarPorRangoPrecio(double precioMin, double precioMax) {
        ArrayList<Casa> casas = sistema.obtenerCasas();
        ArrayList<Casa> resultados = BusquedaLineal.porRangoPrecio(casas, precioMin, precioMax);
        sistema.agregarBusquedaReciente(
            "Precio: $" + String.format("%,.0f", precioMin)
            + " - $" + String.format("%,.0f", precioMax));
        registrarEnHistorial("Búsqueda por rango de precio",
                             resultados.size() + " resultado(s)");
        return resultados;
    }

    // Búsqueda avanzada con múltiples filtros simultáneos (fuerza bruta).
    // Cualquier parámetro puede ser null/0 para ignorarlo.
    public ArrayList<Casa> buscarAvanzado(String ciudad, double precioMax,
                                           int dormMin, boolean soloDisponible) {
        ArrayList<Casa> casas = sistema.obtenerCasas();
        ArrayList<Casa> resultados = BusquedaLineal.avanzada(
            casas, ciudad, precioMax, dormMin, soloDisponible);
        sistema.agregarBusquedaReciente("Búsqueda avanzada");
        registrarEnHistorial("Búsqueda avanzada con filtros",
                             resultados.size() + " resultado(s)");
        return resultados;
    }

    // Búsqueda binaria por precio exacto.
    // REQUIERE que la lista esté ordenada por precio — se ordena automáticamente
    // con Merge Sort antes de aplicar la búsqueda binaria.
    public Casa buscarPorPrecioExacto(double precioObjetivo) {
        /* Paso 1: ordenar con Merge Sort (precondición de búsqueda binaria) */
        ArrayList<Casa> casasOrdenadas = sistema.obtenerCasas();
        AlgoritmosOrdenamiento.mergeSort(casasOrdenadas, "precio", true);

        /* Paso 2: aplicar búsqueda binaria O(log n) */
        Casa resultado = BusquedaBinaria.porPrecioExacto(casasOrdenadas, precioObjetivo);
        sistema.agregarBusquedaReciente(
            "Precio exacto: $" + String.format("%,.0f", precioObjetivo));
        registrarEnHistorial("Búsqueda binaria por precio $"
            + String.format("%,.0f", precioObjetivo),
            resultado != null ? "Encontrada" : "No encontrada");
        return resultado;
    }

    // Búsqueda recursiva de una casa por ID.
    // Demuestra recursión de pila: la función se llama a sí misma avanzando
    // en la lista hasta encontrar el caso base.
    public Casa buscarPorIdRecursivo(int id) {
        ArrayList<Casa> casas = sistema.obtenerCasas();
        /* Llamada inicial con índice 0 */
        return BusquedaLineal.porIdRecursivo(casas, id, 0);
    }

    // Encuentra combinaciones de casas que encajen en un presupuesto dado.
    // Usa backtracking para explorar todas las posibilidades.
    public ArrayList<ArrayList<Casa>> encontrarCombinaciones(double presupuesto) {
        ArrayList<Casa> casas = sistema.obtenerCasas();
        ArrayList<ArrayList<Casa>> combinaciones =
            Backtracking.encontrarCombinaciones(casas, presupuesto);
        registrarEnHistorial("Backtracking: combinaciones con $"
            + String.format("%,.0f", presupuesto),
            combinaciones.size() + " combinación(es)");
        return combinaciones;
    }

    // Ordena las casas usando el algoritmo seleccionado y los parámetros dados.
    // Los algoritmos disponibles: "burbuja", "seleccion", "insercion", "merge"
    public ArrayList<Casa> ordenarCasas(String algoritmo, String criterio, boolean ascendente) {
        ArrayList<Casa> casas = sistema.obtenerCasas(); // Ya es copia

        switch (algoritmo.toLowerCase()) {
            case "burbuja":
                AlgoritmosOrdenamiento.burbuja(casas, criterio, ascendente);
                break;
            case "seleccion":
                AlgoritmosOrdenamiento.seleccion(casas, criterio, ascendente);
                break;
            case "insercion":
                AlgoritmosOrdenamiento.insercion(casas, criterio, ascendente);
                break;
            case "merge":
            default:
                AlgoritmosOrdenamiento.mergeSort(casas, criterio, ascendente);
                break;
        }

        registrarEnHistorial(
            "Ordenamiento " + algoritmo + " por " + criterio
            + (ascendente ? " ASC" : " DESC"),
            casas.size() + " casas ordenadas");
        return casas;
    }

    // Retorna la pila de búsquedas recientes para visualización.
    // La búsqueda más reciente está en el tope.
    public Stack<String> obtenerBusquedasRecientes() {
        return sistema.obtenerPilaBusquedas();
    }

    // Registra una acción de búsqueda en el historial del sistema.
    private void registrarEnHistorial(String descripcion, String resultado) {
        int id = sistema.generarIdHistorial();
        Historial h = new Historial(id, 0, Historial.TipoAccion.BUSQUEDA,
                                    descripcion, resultado);
        sistema.registrarAccion(h);
    }
}