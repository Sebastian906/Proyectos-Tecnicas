package controllers;

import java.util.ArrayList;

import models.*;

public class ControladorBusqueda {

    // Búsqueda lineal (adaptada de TheAlgorithms)
    public static Casa busquedaLinealPorCiudad(ArrayList<Casa> casas, String ciudad) {
        for (Casa casa : casas) {
            if (casa.getCiudad().equalsIgnoreCase(ciudad)) {
                return casa; // retorna la primera coincidencia
            }
        }
        return null;
    }

    // Búsqueda binaria (adaptada de TheAlgorithms)
    public static Casa busquedaBinariaPorPrecio(ArrayList<Casa> casasOrdenadas, double precio) {
        int left = 0, right = casasOrdenadas.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            double valor = casasOrdenadas.get(mid).getPrecio();
            if (valor == precio)
                return casasOrdenadas.get(mid);
            else if (valor < precio)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return null;
    }

    // Fuerza bruta con filtros
    public static ArrayList<Casa> busquedaAvanzada(ArrayList<Casa> casas, String ciudad, double precioMax,
            int dormitoriosMin) {
        ArrayList<Casa> resultados = new ArrayList<>();
        for (Casa casa : casas) {
            if (casa.getCiudad().equalsIgnoreCase(ciudad) &&
                    casa.getPrecio() <= precioMax &&
                    casa.getDormitorios() >= dormitoriosMin) {
                resultados.add(casa);
            }
        }
        return resultados;
    }

    // Backtracking (combinaciones de propiedades dentro de presupuesto)
    public static void encontrarCombinaciones(ArrayList<Casa> casas, double presupuesto, int indice,
            ArrayList<Casa> combinacionActual, ArrayList<ArrayList<Casa>> todas) {
        if (indice >= casas.size()) {
            if (!combinacionActual.isEmpty()) {
                todas.add(new ArrayList<>(combinacionActual));
            }
            return;
        }

        Casa casa = casas.get(indice);

        // Incluir
        if (presupuesto >= casa.getPrecio()) {
            combinacionActual.add(casa);
            encontrarCombinaciones(casas, presupuesto - casa.getPrecio(), indice + 1, combinacionActual, todas);
            combinacionActual.remove(combinacionActual.size() - 1); // retroceso
        }

        // No incluir
        encontrarCombinaciones(casas, presupuesto, indice + 1, combinacionActual, todas);
    }
}