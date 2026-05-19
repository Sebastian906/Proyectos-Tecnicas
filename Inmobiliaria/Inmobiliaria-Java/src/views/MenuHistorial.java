package views;

import controllers.*;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuHistorial {
    private ControladorHistorial controlador;

    public MenuHistorial(ControladorHistorial controlador) {
        this.controlador = controlador;
    }

    public void mostrar() {
        Stage stage = new Stage();
        ListView<String> lista = new ListView<>();
        lista.getItems().addAll(controlador.getUltimasBusquedas());

        VBox layout = new VBox(lista);
        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Historial de Búsquedas");
        stage.show();
    }
}
