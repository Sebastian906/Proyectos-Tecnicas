package views;

import java.util.ArrayList;

import controllers.*;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.*;

public class MenuBusqueda {
    private ControladorPrincipal controlador;

    public MenuBusqueda(ControladorPrincipal controlador) {
        this.controlador = controlador;
    }

    public void mostrar() {
        Stage stage = new Stage();
        TextField ciudadField = new TextField();
        ciudadField.setPromptText("Ingrese ciudad");

        Button btnBuscar = new Button("Buscar");
        TableView<Casa> tabla = new TableView<>();

        btnBuscar.setOnAction(e -> {
            ArrayList<Casa> resultados = ControladorBusqueda.busquedaLinealPorCiudad(
                    controlador.getSistema().obtenerCasas(), ciudadField.getText()
            );
            tabla.getItems().setAll(resultados);
        });

        VBox layout = new VBox(10, ciudadField, btnBuscar, tabla);
        Scene scene = new Scene(layout, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Búsqueda de Propiedades");
        stage.show();
    }
}
