package algorithms;

import java.util.ArrayList;
import models.Casa;

/**
 * Implementación manual de Búsqueda Lineal.
 * Recorre la lista elemento por elemento hasta encontrar coincidencia.
 * No requiere que la lista esté ordenada.
 */
public class BusquedaLineal {

    // Busca todas las casas que coincidan con una ciudad dada.
    public static ArrayList<Casa> porCiudad(ArrayList<Casa> casas, String ciudad) {
        ArrayList<Casa> resultados = new ArrayList<>();
        int comparaciones = 0;

        // Fuerza bruta: revisamos TODAS las casas sin excepción
        for (int i = 0; i < casas.size(); i++) {
            comparaciones++;
            if (casas.get(i).getCiudad().equalsIgnoreCase(ciudad)) {
                resultados.add(casas.get(i));
            }
        }

        System.out.println("  [Búsqueda lineal por ciudad] Comparaciones: " + comparaciones
            + " | Resultados: " + resultados.size() + " | Complejidad: O(" + casas.size() + ")");
        return resultados;
    }

    // Busca una casa por su ID exacto.
    // Retorna en cuanto encuentra la primera coincidencia (IDs son únicos).
    public static Casa porId(ArrayList<Casa> casas, int id) {
        for (int i = 0; i < casas.size(); i++) {
            if (casas.get(i).getId() == id) {
                return casas.get(i); // Encontró: termina inmediatamente
            }
        }
        return null; // No encontró
    }

    // Busca casas dentro de un rango de precio (inclusive en ambos extremos).
    // Aplica fuerza bruta: compara cada casa contra los dos límites.
    public static ArrayList<Casa> porRangoPrecio(ArrayList<Casa> casas,
                                                  double precioMin, double precioMax) {
        ArrayList<Casa> resultados = new ArrayList<>();
        for (Casa casa : casas) {
            if (casa.isPrecioEnRango(precioMin, precioMax)) {
                resultados.add(casa);
            }
        }
        return resultados;
    }

    // Búsqueda avanzada con múltiples filtros simultáneos (Fuerza Bruta).
    // Evalúa TODOS los criterios para CADA casa de la lista.
    public static ArrayList<Casa> avanzada(ArrayList<Casa> casas,
                                            String ciudad,
                                            double precioMax,
                                            int dormMin,
                                            boolean soloDisponible) {
        ArrayList<Casa> resultados = new ArrayList<>();
        int totalComparaciones = 0;

        for (Casa casa : casas) {
            boolean cumple = true;

            /* Filtro 1: ciudad */
            if (ciudad != null && !ciudad.isBlank()) {
                totalComparaciones++;
                if (!casa.getCiudad().equalsIgnoreCase(ciudad)) cumple = false;
            }

            /* Filtro 2: precio máximo */
            if (cumple && precioMax > 0) {
                totalComparaciones++;
                if (casa.getPrecio() > precioMax) cumple = false;
            }

            /* Filtro 3: dormitorios mínimos */
            if (cumple && dormMin > 0) {
                totalComparaciones++;
                if (!casa.tieneDormitoriosMinimo(dormMin)) cumple = false;
            }

            /* Filtro 4: disponibilidad */
            if (cumple && soloDisponible) {
                totalComparaciones++;
                if (!casa.isDisponible()) cumple = false;
            }

            if (cumple) resultados.add(casa);
        }

        System.out.println("  [Búsqueda avanzada - Fuerza bruta] Comparaciones: "
            + totalComparaciones + " | Resultados: " + resultados.size()
            + " | Complejidad: O(n×m) = O(" + casas.size() + "×" + 4 + ")");
        return resultados;
    }

    /**
     * Búsqueda recursiva de una casa por ID.
     * Demuestra recursión de pila: cada llamada apila un nuevo frame hasta
     * encontrar el caso base.
     *
     * Caso base 1: índice fuera de rango → no encontró (retorna null)
     * Caso base 2: ID coincide → encontró (retorna la casa)
     * Caso recursivo: avanzar al siguiente índice
     */
    public static Casa porIdRecursivo(ArrayList<Casa> casas, int id, int indice) {
        /* Caso base 1: llegamos al final sin encontrar */
        if (indice >= casas.size()) return null;

        /* Caso base 2: encontramos la casa */
        if (casas.get(indice).getId() == id) return casas.get(indice);

        /* Caso recursivo: buscar en el resto de la lista */
        return porIdRecursivo(casas, id, indice + 1);
    }
}
