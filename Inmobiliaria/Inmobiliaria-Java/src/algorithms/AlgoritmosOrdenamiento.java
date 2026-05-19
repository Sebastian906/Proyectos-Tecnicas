package algorithms;

import java.util.ArrayList;
import models.Casa;

// Implementación manual de cuatro algoritmos de ordenamiento para listas de casas.
// Ninguno de estos métodos usa Collections.sort() ni Arrays.sort().
public class AlgoritmosOrdenamiento {

    // Ordena la lista de casas usando el algoritmo de burbuja (ascendente).
    public static void burbuja(ArrayList<Casa> casas, String criterio, boolean ascendente) {
        int n = casas.size();
        int intercambios = 0;
        int comparaciones = 0;

        for (int i = 0; i < n - 1; i++) {
            @SuppressWarnings("unused")
            boolean huboIntercambio = false; // Optimización: salida temprana

            for (int j = 0; j < n - i - 1; j++) {
                comparaciones++;
                boolean debeIntercambiar = ascendente
                    ? comparar(casas.get(j), casas.get(j + 1), criterio) > 0
                    : comparar(casas.get(j), casas.get(j + 1), criterio) < 0;

                if (debeIntercambiar) {
                    intercambiar(casas, j, j + 1);
                    intercambios++;
                    huboIntercambio = true;
                }
            }

            /* Si no hubo intercambios en esta pasada, la lista ya está ordenada */
            // if (!huboIntercambiar) break;
        }

        System.out.println("  [Burbuja] Comparaciones: " + comparaciones
            + " | Intercambios: " + intercambios + " | Complejidad: O(n²)");
    }

    // Ordena la lista usando el algoritmo de selección (ascendente).
    //
    // Concepto: en cada pasada, encuentra el elemento mínimo del subarreglo
    // no ordenado y lo coloca en su posición correcta al inicio.
    public static void seleccion(ArrayList<Casa> casas, String criterio, boolean ascendente) {
        int n = casas.size();
        int intercambios = 0;
        int comparaciones = 0;

        for (int i = 0; i < n - 1; i++) {
            int indiceSeleccionado = i; // Asumimos que el mínimo (o máximo) está en i

            /* Buscar el mínimo/máximo en el subarreglo no ordenado [i+1 .. n-1] */
            for (int j = i + 1; j < n; j++) {
                comparaciones++;
                boolean esmenor = ascendente
                    ? comparar(casas.get(j), casas.get(indiceSeleccionado), criterio) < 0
                    : comparar(casas.get(j), casas.get(indiceSeleccionado), criterio) > 0;

                if (esmenor) {
                    indiceSeleccionado = j;
                }
            }

            /* Intercambiar el mínimo/máximo encontrado con la posición i */
            if (indiceSeleccionado != i) {
                intercambiar(casas, i, indiceSeleccionado);
                intercambios++;
            }
        }

        System.out.println("  [Selección] Comparaciones: " + comparaciones
            + " | Intercambios: " + intercambios + " | Complejidad: O(n²)");
    }

    // Ordena la lista usando el algoritmo de inserción.
    public static void insercion(ArrayList<Casa> casas, String criterio, boolean ascendente) {
        int n = casas.size();
        int desplazamientos = 0;
        int comparaciones = 0;

        for (int i = 1; i < n; i++) {
            Casa elementoAInsertar = casas.get(i);
            int j = i - 1;

            /* Correr elementos mayores una posición hacia la derecha */
            while (j >= 0) {
                comparaciones++;
                boolean debeCorrer = ascendente
                    ? comparar(casas.get(j), elementoAInsertar, criterio) > 0
                    : comparar(casas.get(j), elementoAInsertar, criterio) < 0;

                if (debeCorrer) {
                    casas.set(j + 1, casas.get(j));
                    desplazamientos++;
                    j--;
                } else {
                    break;
                }
            }

            /* Insertar el elemento en su posición correcta */
            casas.set(j + 1, elementoAInsertar);
        }

        System.out.println("  [Inserción] Comparaciones: " + comparaciones
            + " | Desplazamientos: " + desplazamientos + " | Complejidad: O(n²) peor caso");
    }

    // Ordena la lista usando Merge Sort (divide y conquista).
    public static void mergeSort(ArrayList<Casa> casas, String criterio, boolean ascendente) {
        if (casas.size() <= 1) return;
        mergeSortRecursivo(casas, criterio, ascendente, 0, casas.size() - 1);
        System.out.println("  [Merge Sort] Complejidad: O(n log n) | n=" + casas.size()
            + " → ~" + (int)(casas.size() * (Math.log(casas.size()) / Math.log(2))) + " operaciones");
    }

    // Función recursiva interna de Merge Sort.
    private static void mergeSortRecursivo(ArrayList<Casa> casas, String criterio,
                                            boolean ascendente, int bajo, int alto) {
        /* Caso base: subarreglo de 1 elemento, ya está ordenado */
        if (bajo >= alto) return;

        int medio = bajo + (alto - bajo) / 2;

        /* Dividir: ordenar mitad izquierda recursivamente */
        mergeSortRecursivo(casas, criterio, ascendente, bajo, medio);

        /* Dividir: ordenar mitad derecha recursivamente */
        mergeSortRecursivo(casas, criterio, ascendente, medio + 1, alto);

        /* Conquistar: mezclar las dos mitades ya ordenadas */
        mezclar(casas, criterio, ascendente, bajo, medio, alto);
    }

    // Mezcla dos subarreglos contiguos ya ordenados en uno solo ordenado.
    // Crea copias temporales de ambas mitades y las fusiona en orden.
    private static void mezclar(ArrayList<Casa> casas, String criterio,
                                  boolean ascendente, int bajo, int medio, int alto) {
        /* Crear copias de ambas mitades (espacio O(n) total) */
        ArrayList<Casa> izquierda = new ArrayList<>();
        ArrayList<Casa> derecha = new ArrayList<>();

        for (int i = bajo; i <= medio; i++) izquierda.add(casas.get(i));
        for (int i = medio + 1; i <= alto; i++) derecha.add(casas.get(i));

        int i = 0, j = 0, k = bajo;

        /* Comparar y mezclar tomando el menor de cada mitad */
        while (i < izquierda.size() && j < derecha.size()) {
            boolean tomarIzquierda = ascendente
                ? comparar(izquierda.get(i), derecha.get(j), criterio) <= 0
                : comparar(izquierda.get(i), derecha.get(j), criterio) >= 0;

            if (tomarIzquierda) {
                casas.set(k++, izquierda.get(i++));
            } else {
                casas.set(k++, derecha.get(j++));
            }
        }

        /* Copiar los elementos restantes de cada mitad */
        while (i < izquierda.size()) casas.set(k++, izquierda.get(i++));
        while (j < derecha.size()) casas.set(k++, derecha.get(j++));
    }

    // Compara dos casas según el criterio especificado.
    // Centraliza la lógica de comparación para todos los algoritmos.
    private static int comparar(Casa a, Casa b, String criterio) {
        return switch (criterio.toLowerCase()) {
            case "precio" -> Double.compare(a.getPrecio(), b.getPrecio());
            case "area" -> Integer.compare(a.getArea(), b.getArea());
            case "dormitorios" -> Integer.compare(a.getDormitorios(), b.getDormitorios());
            case "ciudad" -> a.getCiudad().compareToIgnoreCase(b.getCiudad());
            default -> Double.compare(a.getPrecio(), b.getPrecio());
        }; // precio por defecto
    }

    // Intercambia dos elementos en la lista.
    // Método reutilizado por burbuja y selección.
    private static void intercambiar(ArrayList<Casa> casas, int i, int j) {
        Casa temp = casas.get(i);
        casas.set(i, casas.get(j));
        casas.set(j, temp);
    }
}