package util;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Formateador {

    private static final DateTimeFormatter FORMATO_FECHA =
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @SuppressWarnings({ "unused", "deprecation" })
    private static final NumberFormat FORMATO_MONEDA =
        NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    // Formatea un valor numérico como moneda colombiana.
    public static String formatearPrecio(double valor) {
        return String.format("$%,.0f", valor);
    }

    // Formatea una fecha/hora en formato legible dd/MM/yyyy HH:mm.
    public static String formatearFecha(LocalDateTime fecha) {
        return fecha == null ? "N/A" : fecha.format(FORMATO_FECHA);
    }

    // Centra un texto dentro de un ancho determinado rellenando con espacios.
    public static String centrar(String texto, int ancho) {
        if (texto == null) texto = "";
        if (texto.length() >= ancho) return texto.substring(0, ancho);
        int padding = (ancho - texto.length()) / 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < padding; i++) sb.append(' ');
        sb.append(texto);
        while (sb.length() < ancho) sb.append(' ');
        return sb.toString();
    }

    // Genera una línea separadora de caracteres para la consola.
    public static String separador(char caracter, int longitud) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longitud; i++) sb.append(caracter);
        return sb.toString();
    }

    // Genera un encabezado de sección con separadores arriba y abajo.
    public static String encabezado(String titulo) {
        String linea = separador('═', 56);
        return linea + "\n" + centrar(titulo, 56) + "\n" + linea;
    }
}