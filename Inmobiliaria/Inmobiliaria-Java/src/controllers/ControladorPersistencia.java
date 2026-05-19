package controllers;

import java.io.*;
import java.time.LocalDate;
import models.*;

public class ControladorPersistencia {

    private static final String RUTA_CASAS    = "datos/casas.csv";
    private static final String RUTA_USUARIOS = "datos/usuarios.csv";
    private static final String RUTA_RESERVAS = "datos/reservas.csv";
    private static final String RUTA_HISTORIAL = "datos/historial.csv";

    // Carga todos los datos persistidos en el sistema.
    // Si algún archivo no existe, simplemente no carga esa colección.
    public static void cargarTodo(SistemaInmobiliario sistema) {
        cargarCasas(sistema);
        cargarUsuarios(sistema);
        cargarReservas(sistema);
        cargarHistorial(sistema);
    }

    // Lee casas.csv y reconstruye la lista de casas en el sistema.
    private static void cargarCasas(SistemaInmobiliario sistema) {
        File archivo = new File(RUTA_CASAS);
        if (!archivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int cargadas = 0;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                try {
                    // Dividir con límite 10 para preservar comas en descripcion
                    String[] partes = linea.split(",", 10);
                    if (partes.length < 10) continue;

                    int id            = Integer.parseInt(partes[0].trim());
                    String direccion  = partes[1].trim();
                    String ciudad     = partes[2].trim();
                    double precio     = Double.parseDouble(partes[3].trim());
                    int area          = Integer.parseInt(partes[4].trim());
                    int dormitorios   = Integer.parseInt(partes[5].trim());
                    int banos         = Integer.parseInt(partes[6].trim());
                    boolean disponible = Boolean.parseBoolean(partes[7].trim());
                    String descripcion = partes[8].trim();
                    String propietario = partes[9].trim();

                    Casa casa = new Casa(id, direccion, ciudad, precio,
                                        area, dormitorios, banos, disponible,
                                        descripcion, propietario);
                    sistema.agregarCasa(casa);
                    cargadas++;
                } catch (NumberFormatException e) {
                    System.err.println("  [Persistencia] Línea inválida en casas.csv: " + linea);
                }
            }
            System.out.println("  [Persistencia] Casas cargadas: " + cargadas);
        } catch (IOException e) {
            System.err.println("  [Persistencia] Error al leer casas.csv: " + e.getMessage());
        }
    }

    // Lee usuarios.csv y reconstruye la lista de usuarios en el sistema.
    private static void cargarUsuarios(SistemaInmobiliario sistema) {
        File archivo = new File(RUTA_USUARIOS);
        if (!archivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int cargados = 0;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                try {
                    String[] partes = linea.split(",", 6);
                    if (partes.length < 6) continue;

                    int id            = Integer.parseInt(partes[0].trim());
                    String nombre     = partes[1].trim();
                    String email      = partes[2].trim();
                    String telefono   = partes[3].trim();
                    double presupuesto = Double.parseDouble(partes[4].trim());

                    Usuario usuario = new Usuario(id, nombre, email, telefono, presupuesto);
                    if (!Boolean.parseBoolean(partes[5].trim())) {
                        usuario.setActivo(false);
                    }
                    sistema.agregarUsuario(usuario);
                    cargados++;
                } catch (NumberFormatException e) {
                    System.err.println("  [Persistencia] Línea inválida en usuarios.csv: " + linea);
                }
            }
            System.out.println("  [Persistencia] Usuarios cargados: " + cargados);
        } catch (IOException e) {
            System.err.println("  [Persistencia] Error al leer usuarios.csv: " + e.getMessage());
        }
    }

    // Lee reservas.csv y reconstruye la lista de reservas en el sistema.
    private static void cargarReservas(SistemaInmobiliario sistema) {
        File archivo = new File(RUTA_RESERVAS);
        if (!archivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int cargadas = 0;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                try {
                    String[] partes = linea.split(",", 8);
                    if (partes.length < 7) continue;

                    int id         = Integer.parseInt(partes[0].trim());
                    int idUsuario  = Integer.parseInt(partes[1].trim());
                    int idCasa     = Integer.parseInt(partes[2].trim());
                    // partes[3] = fechaReserva (la generamos al crear el objeto)
                    LocalDate fechaInicio = LocalDate.parse(partes[4].trim());
                    int duracion   = Integer.parseInt(partes[5].trim());
                    Reserva.EstadoReserva estado =
                        Reserva.EstadoReserva.valueOf(partes[6].trim());

                    Reserva reserva = new Reserva(id, idUsuario, idCasa, fechaInicio, duracion);
                    reserva.setEstado(estado);
                    if (partes.length > 7) reserva.setObservaciones(partes[7].trim());

                    sistema.agregarReserva(reserva);
                    cargadas++;
                } catch (NumberFormatException e) {
                    System.err.println("  [Persistencia] Línea inválida en reservas.csv: " + linea);
                }
            }
            System.out.println("  [Persistencia] Reservas cargadas: " + cargadas);
        } catch (IOException e) {
            System.err.println("  [Persistencia] Error al leer reservas.csv: " + e.getMessage());
        }
    }

    // Lee historial.csv y reconstruye el historial de acciones.
    private static void cargarHistorial(SistemaInmobiliario sistema) {
        File archivo = new File(RUTA_HISTORIAL);
        if (!archivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int cargados = 0;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                try {
                    String[] partes = linea.split(",", 6);
                    if (partes.length < 6) continue;

                    int id          = Integer.parseInt(partes[0].trim());
                    int idUsuario   = Integer.parseInt(partes[1].trim());
                    Historial.TipoAccion tipo =
                        Historial.TipoAccion.valueOf(partes[2].trim());
                    String descripcion = partes[3].trim();
                    // partes[4] = fecha (la generamos al crear)
                    String resultado   = partes[5].trim();

                    Historial h = new Historial(id, idUsuario, tipo, descripcion, resultado);
                    sistema.registrarAccion(h);
                    cargados++;
                } catch (NumberFormatException e) {
                    System.err.println("  [Persistencia] Línea inválida en historial.csv: " + linea);
                }
            }
            System.out.println("  [Persistencia] Historial cargado: " + cargados + " registros");
        } catch (IOException e) {
            System.err.println("  [Persistencia] Error al leer historial.csv: " + e.getMessage());
        }
    }

    // Guarda todo el estado del sistema en los archivos CSV.
    // Crea el directorio "datos/" si no existe.
    public static void guardarTodo(SistemaInmobiliario sistema) {
        new File("datos").mkdirs(); // Crear carpeta si no existe
        guardarCasas(sistema);
        guardarUsuarios(sistema);
        guardarReservas(sistema);
        guardarHistorial(sistema);
        System.out.println("  [Persistencia] Datos guardados correctamente.");
    }

    // Escribe todas las casas del sistema en casas.csv.
    private static void guardarCasas(SistemaInmobiliario sistema) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_CASAS))) {
            for (Casa casa : sistema.obtenerCasas()) {
                bw.write(casa.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("  [Persistencia] Error al guardar casas.csv: " + e.getMessage());
        }
    }

    // Escribe todos los usuarios del sistema en usuarios.csv.
    private static void guardarUsuarios(SistemaInmobiliario sistema) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_USUARIOS))) {
            for (Usuario usuario : sistema.obtenerUsuarios()) {
                bw.write(usuario.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("  [Persistencia] Error al guardar usuarios.csv: " + e.getMessage());
        }
    }

    // Escribe todas las reservas del sistema en reservas.csv.
    private static void guardarReservas(SistemaInmobiliario sistema) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_RESERVAS))) {
            for (Reserva reserva : sistema.obtenerReservas()) {
                bw.write(reserva.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("  [Persistencia] Error al guardar reservas.csv: " + e.getMessage());
        }
    }

    // Escribe todo el historial del sistema en historial.csv.
    private static void guardarHistorial(SistemaInmobiliario sistema) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_HISTORIAL))) {
            for (Historial h : sistema.obtenerHistorial()) {
                bw.write(h.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("  [Persistencia] Error al guardar historial.csv: " + e.getMessage());
        }
    }
}
