package algorithms;

import java.util.ArrayList;
import models.Casa;

// Implementación de Backtracking para encontrar combinaciones de propiedades
// que encajen dentro de un presupuesto dado.
public class Backtracking {

    /** Límite de combinaciones a encontrar antes de detener la búsqueda */
    private static final int MAX_COMBINACIONES = 50;

    // Encuentra todas las combinaciones de casas que encajan en el presupuesto.
    // Punto de entrada público; inicializa la recursión.
    public static ArrayList<ArrayList<Casa>> encontrarCombinaciones(
            ArrayList<Casa> casas, double presupuestoTotal) {

        ArrayList<ArrayList<Casa>> todasCombinaciones = new ArrayList<>();
        ArrayList<Casa> combinacionActual = new ArrayList<>();

        System.out.println("  [Backtracking] Explorando combinaciones con presupuesto $"
            + String.format("%,.0f", presupuestoTotal) + " sobre " + casas.size() + " casas...");

        explorar(casas, presupuestoTotal, 0, combinacionActual, todasCombinaciones);

        System.out.println("  [Backtracking] Combinaciones encontradas: " + todasCombinaciones.size()
            + " | Complejidad: O(2^" + casas.size() + ") con poda");
        return todasCombinaciones;
    }

    // Función recursiva de backtracking.
    private static void explorar(ArrayList<Casa> casas, double presupuestoRestante,
                                   int indice, ArrayList<Casa> combinacionActual,
                                   ArrayList<ArrayList<Casa>> todasCombinaciones) {

        /* Poda: no seguir si ya tenemos suficientes combinaciones */
        if (todasCombinaciones.size() >= MAX_COMBINACIONES) return;

        /* Caso base: revisamos todas las casas */
        if (indice >= casas.size()) {
            if (!combinacionActual.isEmpty()) {
                /* Guardar copia inmutable de la combinación actual */
                todasCombinaciones.add(new ArrayList<>(combinacionActual));
            }
            return;
        }

        Casa casaActual = casas.get(indice);

        /* OPCIÓN A: INCLUIR esta casa */
        if (presupuestoRestante >= casaActual.getPrecio() && casaActual.isDisponible()) {
            combinacionActual.add(casaActual); // Agregar a la combinación

            explorar(casas,
                     presupuestoRestante - casaActual.getPrecio(),
                     indice + 1,
                     combinacionActual,
                     todasCombinaciones);

            /* BACKTRACK: quitar la casa para explorar la rama sin ella */
            combinacionActual.remove(combinacionActual.size() - 1);
        }

        /* OPCIÓN B: NO INCLUIR esta casa */
        explorar(casas, presupuestoRestante, indice + 1, combinacionActual, todasCombinaciones);
    }
}