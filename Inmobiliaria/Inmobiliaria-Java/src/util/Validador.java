package util;

// Clase utilitaria con métodos de validación de datos de entrada.
// Centraliza todas las validaciones para evitar duplicación en controladores y vistas.
public class Validador {

    // Valida que un email tenga formato básico correcto.
    // Reglas: debe contener '@', tener texto antes y después, y un punto en el dominio.
    public static boolean esEmailValido(String email) {
        if (email == null || email.isBlank()) return false;

        int posArroba = email.indexOf('@');
        if (posArroba <= 0) return false;               // Sin @ o al inicio
        if (posArroba == email.length() - 1) return false; // @ al final

        String dominio = email.substring(posArroba + 1);
        int posPunto = dominio.indexOf('.');
        if (posPunto <= 0) return false;                // Sin punto o al inicio del dominio
        if (posPunto == dominio.length() - 1) return false; // Punto al final del dominio

        return true;
    }

    // Valida que un teléfono tenga solo dígitos y longitud razonable.
    public static boolean esTelefonoValido(String telefono) {
        if (telefono == null || telefono.isBlank()) return false;
        String limpio = telefono.replaceAll("[\\s\\-\\+\\(\\)]", "");
        if (limpio.length() < 7 || limpio.length() > 15) return false;
        for (char c : limpio.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    // Valida que una cadena no sea nula ni esté vacía.
    public static boolean noEsVacio(String texto) {
        return texto != null && !texto.isBlank();
    }

    // Valida que un número double sea positivo (mayor a cero).
    public static boolean esPositivo(double valor) {
        return valor > 0;
    }

    // Valida que un entero esté dentro de un rango inclusivo.
    public static boolean estaEnRango(int valor, int min, int max) {
        return valor >= min && valor <= max;
    }
}