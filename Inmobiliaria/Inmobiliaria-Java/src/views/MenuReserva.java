package views;

import java.time.LocalDate;
import java.util.ArrayList;
import controllers.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.*;

// Vista para crear y gestionar reservas.
// Muestra la cola de reservas pendientes (FIFO) y permite procesarla.
public class MenuReserva {

    private final ControladorPrincipal controlador;

    public MenuReserva(ControladorPrincipal controlador) {
        this.controlador = controlador;
    }

    public void mostrar(Stage owner) {
        Stage stage = new Stage();

        TabPane tabs = new TabPane();
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabs.getTabs().addAll(tabCrearReserva(stage), tabVerReservas(), tabProcesarCola(stage));

        Scene scene = new Scene(tabs, 640, 440);
        stage.setTitle("Gestión de Reservas");
        stage.setScene(scene);
        stage.initOwner(owner);
        stage.show();
    }

    // Tab 1: Crear reserva
    private Tab tabCrearReserva(Stage stage) {
        Tab tab = new Tab("Nueva Reserva");

        TextField txtIdUsuario = new TextField(); txtIdUsuario.setPromptText("ID del usuario");
        TextField txtIdCasa    = new TextField(); txtIdCasa.setPromptText("ID de la casa");
        DatePicker dpFecha     = new DatePicker(LocalDate.now().plusDays(1));
        TextField txtDuracion  = new TextField(); txtDuracion.setPromptText("Duración en meses");
        Button btnReservar     = new Button("Crear Reserva");
        btnReservar.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-pref-width: 180;");

        Label lblResultado = new Label();
        lblResultado.setWrapText(true);

        btnReservar.setOnAction(e -> {
            try {
                int idUsuario = Integer.parseInt(txtIdUsuario.getText().trim());
                int idCasa    = Integer.parseInt(txtIdCasa.getText().trim());
                LocalDate fecha = dpFecha.getValue();
                int duracion  = Integer.parseInt(txtDuracion.getText().trim());

                String resultado = controlador.getReserva().crearReserva(idUsuario, idCasa, fecha, duracion);
                lblResultado.setText(resultado);
                lblResultado.setStyle(resultado.startsWith("Error")
                    ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
            } catch (NumberFormatException ex) {
                lblResultado.setText("Error: ID y duración deben ser números enteros.");
                lblResultado.setStyle("-fx-text-fill: red;");
            }
        });

        GridPane form = new GridPane();
        form.setHgap(10); form.setVgap(8);
        form.add(new Label("ID Usuario:"), 0, 0);  form.add(txtIdUsuario, 1, 0);
        form.add(new Label("ID Casa:"), 0, 1);      form.add(txtIdCasa, 1, 1);
        form.add(new Label("Fecha inicio:"), 0, 2); form.add(dpFecha, 1, 2);
        form.add(new Label("Duración (mes):"), 0, 3); form.add(txtDuracion, 1, 3);
        form.add(btnReservar, 1, 4);

        VBox layout = new VBox(10, form, lblResultado);
        layout.setPadding(new Insets(15));
        tab.setContent(layout);
        return tab;
    }

    // Tab 2: Ver todas las reservas
    private Tab tabVerReservas() {
        Tab tab = new Tab("Ver Reservas");

        ListView<String> lista = new ListView<>();
        Button btnRefresh = new Button("Actualizar");
        btnRefresh.setOnAction(e -> {
            ArrayList<Reserva> reservas = controlador.getReserva().obtenerTodasReservas();
            lista.setItems(FXCollections.observableArrayList(
                reservas.stream().map(Reserva::toString).collect(java.util.stream.Collectors.toList())));
        });
        btnRefresh.fire(); // Carga inicial

        VBox layout = new VBox(8, btnRefresh, lista);
        layout.setPadding(new Insets(15));
        tab.setContent(layout);
        return tab;
    }

    // Tab 3: Procesar cola FIFO
    private Tab tabProcesarCola(Stage stage) {
        Tab tab = new Tab("Procesar Cola (FIFO)");

        Label lblPendientes = new Label();
        Button btnProcesar  = new Button("Procesar toda la cola");
        btnProcesar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-pref-width: 220;");

        TextArea txtResultado = new TextArea();
        txtResultado.setEditable(false);
        txtResultado.setWrapText(true);

        Label lblInfo = new Label(
            """
            La cola procesa las reservas en orden FIFO: la primera
            en llegar es la primera en procesarse.""");
        lblInfo.setStyle("-fx-font-size: 11px; -fx-text-fill: #666;");

        Runnable actualizarPendientes = () -> {
            int n = controlador.getReserva().reservasPendientes();
            lblPendientes.setText("Reservas en cola: " + n);
            lblPendientes.setStyle(n > 0
                ? "-fx-font-size: 13px; -fx-text-fill: #FF6600;"
                : "-fx-font-size: 13px; -fx-text-fill: #555;");
        };
        actualizarPendientes.run();

        btnProcesar.setOnAction(e -> {
            int procesadas = controlador.getReserva().procesarColaCompleta();
            actualizarPendientes.run();
            if (procesadas == 0) {
                txtResultado.setText("La cola está vacía. No hay reservas que procesar.");
            } else {
                txtResultado.setText("Se procesaron " + procesadas + " reserva(s) en orden FIFO.\n\n"
                    + "Consulta la pestaña 'Ver Reservas' para ver los estados actualizados.");
            }
        });

        VBox layout = new VBox(10, lblPendientes, lblInfo, btnProcesar, txtResultado);
        layout.setPadding(new Insets(15));
        tab.setContent(layout);
        return tab;
    }
}