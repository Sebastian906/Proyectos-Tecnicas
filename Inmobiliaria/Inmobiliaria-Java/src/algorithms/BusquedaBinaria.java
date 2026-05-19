package algorithms;

import java.util.ArrayList;
import models.Casa;

public class BusquedaBinaria {

    /**
     * Busca una casa cuyo precio sea exactamente igual al precio objetivo.
     * La lista DEBE estar ordenada por precio ascendente antes de llamar.
     *
     * Algoritmo:
     *   1. Definir ventana de búsqueda: [bajo, alto]
     *   2. Calcular el punto medio
     *   3. Si precio[medio] == objetivo → encontró
     *   4. Si precio[medio] < objetivo → buscar en mitad derecha (bajo = medio+1)
     *   5. Si precio[medio] > objetivo → buscar en mitad izquierda (alto = medio-1)
     *   6. Repetir hasta encontrar o que bajo > alto
     */
    public static Casa porPrecioExacto(ArrayList<Casa> casasOrdenadas, double precioObjetivo) {
        int bajo = 0;
        int alto = casasOrdenadas.size() - 1;
        int iteraciones = 0;

        while (bajo <= alto) {
            iteraciones++;
            int medio = bajo + (alto - bajo) / 2; // Evita overflow vs (bajo+alto)/2

            double precioMedio = casasOrdenadas.get(medio).getPrecio();

            if (precioMedio == precioObjetivo) {
                System.out.println("  [Búsqueda binaria] Encontrado en iteración "
                    + iteraciones + " de máx " + (int)(Math.log(casasOrdenadas.size())/Math.log(2)+1)
                    + " | Complejidad: O(log " + casasOrdenadas.size() + ")");
                return casasOrdenadas.get(medio);
            } else if (precioMedio < precioObjetivo) {
                bajo = medio + 1; // Buscar en la mitad derecha (precios mayores)
            } else {
                alto = medio - 1; // Buscar en la mitad izquierda (precios menores)
            }
        }

        System.out.println("  [Búsqueda binaria] No encontrado. Iteraciones: " + iteraciones);
        return null; // No existe casa con ese precio exacto
    }

    // Busca la casa con el precio más cercano (por debajo) al objetivo.
    // Útil cuando no se conoce un precio exacto pero sí un presupuesto máximo.
    public static Casa porPrecioMaximo(ArrayList<Casa> casasOrdenadas, double precioMax) {
        int bajo = 0;
        int alto = casasOrdenadas.size() - 1;
        Casa mejorCandidato = null;

        while (bajo <= alto) {
            int medio = bajo + (alto - bajo) / 2;
            double precioMedio = casasOrdenadas.get(medio).getPrecio();

            if (precioMedio <= precioMax) {
                mejorCandidato = casasOrdenadas.get(medio); // Este cumple; puede haber uno mejor
                bajo = medio + 1; // Buscar precio más cercano al máximo
            } else {
                alto = medio - 1; // Este excede el presupuesto
            }
        }

        return mejorCandidato;
    }

    // Retorna el índice donde está (o debería estar) una casa con ese precio.
    // Útil para operaciones internas de inserción ordenada.
    public static int encontrarIndice(ArrayList<Casa> casasOrdenadas, double precio) {
        int bajo = 0;
        int alto = casasOrdenadas.size() - 1;

        while (bajo <= alto) {
            int medio = bajo + (alto - bajo) / 2;
            double precioMedio = casasOrdenadas.get(medio).getPrecio();

            if (precioMedio == precio) return medio;
            else if (precioMedio < precio) bajo = medio + 1;
            else alto = medio - 1;
        }

        return -(bajo + 1); // No encontrado; bajo es el punto de inserción
    }
}