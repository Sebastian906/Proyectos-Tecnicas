package views;

import java.time.LocalDate;
import controllers.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.*;

public class MenuReserva {
    private ControladorReserva controlador;

    public MenuReserva(ControladorReserva controlador) {
        this.controlador = controlador;
    }

    public void mostrar() {
        Stage stage = new Stage();

        TextField idUsuarioField = new TextField();
        idUsuarioField.setPromptText("ID Usuario");

        TextField idCasaField = new TextField();
        idCasaField.setPromptText("ID Casa");

        DatePicker fechaInicioPicker = new DatePicker();
        fechaInicioPicker.setPromptText("Fecha inicio");

        TextField duracionField = new TextField();
        duracionField.setPromptText("Duración (meses)");

        Button btnReservar = new Button("Confirmar Reserva");
        Label resultado = new Label();

        btnReservar.setOnAction(e -> {
            try {
                int idUsuario = Integer.parseInt(idUsuarioField.getText());
                int idCasa = Integer.parseInt(idCasaField.getText());
                LocalDate fechaInicio = fechaInicioPicker.getValue();
                int duracion = Integer.parseInt(duracionField.getText());

                Reserva reserva = new Reserva(1, idUsuario, idCasa, fechaInicio, duracion);
                controlador.encolarReserva(reserva);
                resultado.setText("Reserva encolada correctamente.");
            } catch (Exception ex) {
                resultado.setText("Error: " + ex.getMessage());
            }
        });

        VBox layout = new VBox(10, idUsuarioField, idCasaField, fechaInicioPicker, duracionField, btnReservar, resultado);
        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Reservar Propiedad");
        stage.show();
    }
}
