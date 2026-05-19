package views;

import controllers.ControladorPrincipal;
import models.SistemaInmobiliario;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuPrincipal extends Application {

    private ControladorPrincipal controlador;

    @Override
    public void start(Stage primaryStage) {
        SistemaInmobiliario sistema = new SistemaInmobiliario();
        controlador = new ControladorPrincipal(sistema);

        // Botones principales
        Button btnBuscar = new Button("Buscar Propiedades");
        btnBuscar.setOnAction(e -> new MenuBusqueda(controlador).mostrar());

        Button btnReservar = new Button("Reservar Propiedad");
        btnReservar.setOnAction(e -> new MenuReserva(controlador.getControladorReserva()).mostrar());

        Button btnHistorial = new Button("Ver Historial");
        btnHistorial.setOnAction(e -> new MenuHistorial(controlador.getControladorHistorial()).mostrar());

        VBox layout = new VBox(15, btnBuscar, btnReservar, btnHistorial);
        Scene scene = new Scene(layout, 400, 300);

        primaryStage.setTitle("Sistema Inmobiliario");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // arranca JavaFX
    }
}