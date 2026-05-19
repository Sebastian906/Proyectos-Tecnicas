package views;

import controllers.ControladorPrincipal;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuPrincipal extends Application {

    /** Controlador central compartido con todas las vistas */
    private ControladorPrincipal controlador;

    @Override
    public void start(Stage primaryStage) {
        controlador = new ControladorPrincipal();

        // Título
        Label titulo = new Label("Sistema de Gestión Inmobiliaria");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label subtitulo = new Label("Selecciona una opción para continuar");
        subtitulo.setStyle("-fx-font-size: 13px; -fx-text-fill: #666;");

        // Botones de navegación
        Button btnPropiedades = crearBoton("Ver Propiedades", "#2196F3");
        Button btnBuscar      = crearBoton("Buscar Propiedades", "#4CAF50");
        Button btnOrdenar     = crearBoton("Ordenar Propiedades", "#9C27B0");
        Button btnReservar    = crearBoton("Gestión de Reservas", "#FF9800");
        Button btnHistorial   = crearBoton("Historial y Búsquedas", "#607D8B");
        Button btnRegistrar   = crearBoton("Registrar Propiedad", "#00BCD4");
        Button btnRegistrarUsuario = crearBoton("Registrar Usuario", "#00ACC1");
        Button btnSalir       = crearBoton("Salir", "#F44336");

        // Acciones
        btnPropiedades.setOnAction(e ->
            new MenuVisualizacion(controlador).mostrar(primaryStage));

        btnBuscar.setOnAction(e ->
            new MenuBusqueda(controlador).mostrar(primaryStage));

        btnOrdenar.setOnAction(e ->
            new MenuOrdenamiento(controlador).mostrar(primaryStage));

        btnReservar.setOnAction(e ->
            new MenuReserva(controlador).mostrar(primaryStage));

        btnHistorial.setOnAction(e ->
            new MenuHistorial(controlador).mostrar(primaryStage));

        btnRegistrarUsuario.setOnAction(e ->
            new MenuRegistroUsuario(controlador).mostrar(primaryStage));

        btnRegistrar.setOnAction(e ->
            new MenuRegistro(controlador).mostrar(primaryStage));

        btnSalir.setOnAction(e -> {
            controlador.guardarYCerrar();
            Platform.exit();
        });

        // Guardar al cerrar la ventana con la X 
        primaryStage.setOnCloseRequest(e -> controlador.guardarYCerrar());

        // Layout 
        VBox layout = new VBox(12,
            titulo, subtitulo,
            btnPropiedades, btnBuscar, btnOrdenar,
            btnReservar, btnHistorial, btnRegistrarUsuario, btnRegistrar,
            btnSalir);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: #FAFAFA;");

        Scene scene = new Scene(layout, 420, 540);
        primaryStage.setTitle("Sistema Inmobiliario");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // Crea un botón con estilo uniforme para el menú principal.
    private Button crearBoton(String texto, String color) {
        Button btn = new Button(texto);
        btn.setPrefWidth(320);
        btn.setPrefHeight(42);
        btn.setStyle(
            "-fx-background-color: " + color + ";"
            + "-fx-text-fill: white;"
            + "-fx-font-size: 14px;"
            + "-fx-border-radius: 6;"
            + "-fx-background-radius: 6;"
            + "-fx-cursor: hand;"
        );
        btn.setOnMouseEntered(e ->
            btn.setStyle(btn.getStyle() + "-fx-opacity: 0.85;"));
        btn.setOnMouseExited(e ->
            btn.setStyle(btn.getStyle().replace("-fx-opacity: 0.85;", "")));
        return btn;
    }

    // Muestra un diálogo de alerta informativa.
    public static void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Muestra un diálogo de alerta de error.
    public static void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}