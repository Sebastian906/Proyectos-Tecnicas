package views;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import models.*;

public class MenuVisualizacion {
    public void mostrar(ArrayList<Casa> casas) {
        Stage stage = new Stage();
        TableView<Casa> tabla = new TableView<>();

        TableColumn<Casa, String> ciudadCol = new TableColumn<>("Ciudad");
        ciudadCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getCiudad()));

        TableColumn<Casa, Number> precioCol = new TableColumn<>("Precio");
        precioCol.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getPrecio()));

        tabla.getColumns().addAll(ciudadCol, precioCol);
        tabla.getItems().setAll(casas);

        VBox layout = new VBox(tabla);
        Scene scene = new Scene(layout, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Visualización de Propiedades");
        stage.show();
    }
}
