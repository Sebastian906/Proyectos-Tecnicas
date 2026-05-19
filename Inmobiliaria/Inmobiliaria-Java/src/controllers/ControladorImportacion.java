package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import models.Casa;
import models.SistemaInmobiliario;
import models.Usuario;

public class ControladorImportacion {

    public static int importarArchivo(File file, SistemaInmobiliario sistema) throws IOException {
        String name = file.getName().toLowerCase();
        if (name.endsWith(".csv")) return importarCsv(file, sistema);
        if (name.endsWith(".json")) return importarJson(file, sistema);
        throw new IllegalArgumentException("Formato no soportado: " + file.getName());
    }

    private static int importarCsv(File file, SistemaInmobiliario sistema) throws IOException {
        int importados = 0;
        try (BufferedReader br = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                String[] partes = linea.split(",", 10);
                if (partes.length < 9) continue; // requiere al menos 9 campos (sin id)
                int id;
                int start;
                if (partes.length == 10) {
                    // primer campo puede ser ID
                    try {
                        id = Integer.parseInt(partes[0].trim());
                    } catch (NumberFormatException e) {
                        id = sistema.generarIdCasa();
                    }
                    start = 1;
                } else {
                    id = sistema.generarIdCasa();
                    start = 0;
                }

                try {
                    String direccion = partes[start + 0].trim();
                    String ciudad = partes[start + 1].trim();
                    double precio = Double.parseDouble(partes[start + 2].trim());
                    int area = Integer.parseInt(partes[start + 3].trim());
                    int dormitorios = Integer.parseInt(partes[start + 4].trim());
                    int banos = Integer.parseInt(partes[start + 5].trim());
                    boolean disponible = Boolean.parseBoolean(partes[start + 6].trim());
                    String descripcion = partes[start + 7].trim();
                    String propietario = partes[start + 8].trim();

                    Casa casa = new Casa(id, direccion, ciudad, precio, area, dormitorios, banos, disponible, descripcion, propietario);
                    sistema.agregarCasa(casa);
                    importados++;
                } catch (NumberFormatException ex) {
                    // línea inválida — se omite
                }
            }
        }
        return importados;
    }

    private static int importarJson(File file, SistemaInmobiliario sistema) throws IOException {
        String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        String normalized = content.replaceAll("\r?\n", " ");

        Pattern objPattern = Pattern.compile("\\{([^}]*)\\}");
        Matcher m = objPattern.matcher(normalized);
        int importados = 0;

        while (m.find()) {
            String obj = m.group(1);

            String direccion = extractString(obj, "direccion");
            String ciudad = extractString(obj, "ciudad");
            Double precio = extractDouble(obj, "precio");
            Integer area = extractInt(obj, "area");
            Integer dormitorios = extractInt(obj, "dormitorios");
            Integer banos = extractInt(obj, "banos");
            Boolean disponible = extractBoolean(obj, "disponible");
            String descripcion = extractString(obj, "descripcion");
            String propietario = extractString(obj, "propietario");

            if (direccion == null || ciudad == null || precio == null || area == null || dormitorios == null || banos == null || disponible == null) {
                continue; // faltan campos requeridos
            }
            Integer idFromJson = extractInt(obj, "id");
            int id = (idFromJson != null && idFromJson > 0) ? idFromJson : sistema.generarIdCasa();
            Casa casa = new Casa(id, direccion, ciudad, precio, area, dormitorios, banos, disponible, descripcion == null ? "" : descripcion, propietario == null ? "" : propietario);
            sistema.agregarCasa(casa);
            importados++;
        }

        return importados;
    }

    private static String extractString(String obj, String key) {
        Pattern p = Pattern.compile("\"" + key + "\"\\s*:\\s*\"([^\"]*)\"", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(obj);
        return m.find() ? m.group(1) : null;
    }

    private static Integer extractInt(String obj, String key) {
        Pattern p = Pattern.compile("\"" + key + "\"\\s*:\\s*([0-9]+)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(obj);
        return m.find() ? Integer.valueOf(m.group(1)) : null;
    }

    private static Double extractDouble(String obj, String key) {
        Pattern p = Pattern.compile("\"" + key + "\"\\s*:\\s*([0-9]+(?:\\.[0-9]+)?)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(obj);
        return m.find() ? Double.valueOf(m.group(1)) : null;
    }

    private static Boolean extractBoolean(String obj, String key) {
        Pattern p = Pattern.compile("\"" + key + "\"\\s*:\\s*(true|false)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(obj);
        return m.find() ? Boolean.valueOf(m.group(1)) : null;
    }

    public static int importarUsuarios(File file, SistemaInmobiliario sistema) throws IOException {
        String name = file.getName().toLowerCase();
        if (name.endsWith(".csv")) return importarUsuariosCsv(file, sistema);
        if (name.endsWith(".json")) return importarUsuariosJson(file, sistema);
        throw new IllegalArgumentException("Formato no soportado: " + file.getName());
    }

    private static int importarUsuariosCsv(File file, SistemaInmobiliario sistema) throws IOException {
        int importados = 0;
        try (BufferedReader br = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                String[] partes = linea.split(",", -1);
                if (partes.length < 4) continue; // requiere al menos nombre,email,telefono,presupuesto

                int id = -1;
                int start = 0;
                // Detectar si la primera columna es un ID numérico
                if (partes.length >= 5) {
                    try {
                        id = Integer.parseInt(partes[0].trim());
                        start = 1;
                    } catch (NumberFormatException ex) {
                        id = -1;
                        start = 0;
                    }
                }

                try {
                    String nombre = partes[start + 0].trim();
                    String email = partes[start + 1].trim();
                    String telefono = (partes.length > start + 2) ? partes[start + 2].trim() : "";
                    double presupuesto = 0.0;
                    if (partes.length > start + 3 && !partes[start + 3].trim().isEmpty()) {
                        presupuesto = Double.parseDouble(partes[start + 3].trim());
                    }
                    boolean activo = true;
                    if (partes.length > start + 4 && !partes[start + 4].trim().isEmpty()) {
                        activo = Boolean.parseBoolean(partes[start + 4].trim());
                    }

                    if (id <= 0) id = sistema.generarIdUsuario();
                    Usuario usuario = new Usuario(id, nombre, email, telefono, presupuesto);
                    if (!activo) usuario.setActivo(false);
                    sistema.agregarUsuario(usuario);
                    importados++;
                } catch (NumberFormatException ex) {
                    // omitir línea inválida
                }
            }
        }
        return importados;
    }

    private static int importarUsuariosJson(File file, SistemaInmobiliario sistema) throws IOException {
        String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        String normalized = content.replaceAll("\r?\n", " ");

        Pattern objPattern = Pattern.compile("\\{([^}]*)\\}");
        Matcher m = objPattern.matcher(normalized);
        int importados = 0;

        while (m.find()) {
            String obj = m.group(1);
            String nombre = extractString(obj, "nombre");
            String email = extractString(obj, "email");
            String telefono = extractString(obj, "telefono");
            Double presupuesto = extractDouble(obj, "presupuesto");
            Boolean activo = extractBoolean(obj, "activo");
            if (nombre == null || email == null || presupuesto == null) continue;

            Integer idFromJson = extractInt(obj, "id");
            int id = (idFromJson != null && idFromJson > 0) ? idFromJson : sistema.generarIdUsuario();
            Usuario usuario = new Usuario(id, nombre, email, telephoneOrEmpty(telefono), presupuesto);
            if (activo != null && !activo) usuario.setActivo(false);
            sistema.agregarUsuario(usuario);
            importados++;
        }

        return importados;
    }

    private static String telephoneOrEmpty(String t) {
        return t == null ? "" : t;
    }
}
