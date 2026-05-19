package models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class SistemaInmobiliario {
    private ArrayList<Casa> casas = new ArrayList<>();
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private ArrayList<Reserva> reservas = new ArrayList<>();
    private ArrayList<Historial> historial = new ArrayList<>();
    private Stack<String> pilaSearches = new Stack<>();
    private Queue<Reserva> colaReservas = new LinkedList<>();

    public void agregarCasa(Casa casa) {
        casas.add(casa);
    }

    public ArrayList<Casa> obtenerCasas() {
        return casas;
    }

    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public ArrayList<Usuario> obtenerUsuarios() {
        return usuarios;
    }

    public void encolarReserva(Reserva reserva) {
        colaReservas.offer(reserva);
    }

    public Reserva procesarReserva() {
        return colaReservas.poll();
    }

    public void registrarAccion(Historial h) {
        historial.add(h);
    }

    public ArrayList<Historial> obtenerHistorial() {
        return historial;
    }
}