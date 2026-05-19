package controllers;

import java.util.Stack;

public class ControladorHistorial {
    private Stack<String> pilaSearches = new Stack<>();

    public void agregarBusqueda(String busqueda) {
        pilaSearches.push(busqueda);
    }

    public String deshacerBusqueda() {
        return pilaSearches.isEmpty() ? null : pilaSearches.pop();
    }
}
