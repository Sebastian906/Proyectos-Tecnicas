package views;

import java.util.ArrayList;
import controllers.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import models.*;

public class MenuVisualizacion {

    private final ControladorPrincipal controlador;

    public MenuVisualizacion(ControladorPrincipal controlador) {
        this.controlador = controlador;
    }

    // Abre la ventana de visualización de propiedades.
    public void mostrar(Stage owner) {
        Stage stage = new Stage();

        // Tabla de propiedades 
        TableView<Casa> tabla = construirTabla();
        ArrayList<Casa> casas = controlador.getSistema().obtenerCasas();
        tabla.setItems(FXCollections.observableArrayList(casas));
        tabla.setPlaceholder(new Label("No hay propiedades registradas."));

        // Panel de detalles
        TextArea detalle = new TextArea();
        detalle.setEditable(false);
        detalle.setPrefWidth(280);
        detalle.setStyle("-fx-font-family: monospace; -fx-font-size: 12px;");
        detalle.setWrapText(true);

        tabla.getSelectionModel().selectedItemProperty().addListener(
            (obs, anterior, seleccionada) -> {
                if (seleccionada != null) detalle.setText(formatearDetalle(seleccionada));
            });

        // Etiqueta conteo
        Label conteo = new Label("Total: " + casas.size() + " propiedad(es)");
        conteo.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");

        // Layout
        HBox contenido = new HBox(10, tabla, detalle);
        contenido.setPadding(new Insets(10));
        HBox.setHgrow(tabla, Priority.ALWAYS);

        VBox root = new VBox(8,
            new Label("Propiedades Registradas") {{
                setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10 10 0 10;");
            }},
            conteo,
            contenido);

        Scene scene = new Scene(root, 860, 480);
        stage.setTitle("Ver Propiedades");
        stage.setScene(scene);
        stage.initOwner(owner);
        stage.show();
    }

    // Construye el TableView con las columnas de la tabla de propiedades.
    @SuppressWarnings({ "unchecked", "deprecation" })
    private TableView<Casa> construirTabla() {
        TableView<Casa> tabla = new TableView<>();
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Casa, Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getId()));
        colId.setMaxWidth(50);

        TableColumn<Casa, String> colCiudad = new TableColumn<>("Ciudad");
        colCiudad.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCiudad()));

        TableColumn<Casa, String> colDireccion = new TableColumn<>("Dirección");
        colDireccion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDireccion()));

        TableColumn<Casa, Number> colPrecio = new TableColumn<>("Precio ($)");
        colPrecio.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getPrecio()));
        colPrecio.setCellFactory(col -> new TableCell<Casa, Number>() {
            @Override protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : String.format("$%,.0f", item.doubleValue()));
            }
        });

        TableColumn<Casa, Number> colArea = new TableColumn<>("m²");
        colArea.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getArea()));
        colArea.setMaxWidth(60);

        TableColumn<Casa, Number> colDorm = new TableColumn<>("Dorm.");
        colDorm.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getDormitorios()));
        colDorm.setMaxWidth(60);

        TableColumn<Casa, String> colDisp = new TableColumn<>("Estado");
        colDisp.setCellValueFactory(c -> new SimpleStringProperty(
            c.getValue().isDisponible() ? "Disponible" : "Reservada"));

        tabla.getColumns().addAll(colId, colCiudad, colDireccion, colPrecio, colArea, colDorm, colDisp);
        return tabla;
    }

    // Formatea los detalles completos de una casa para el panel lateral.
    private String formatearDetalle(Casa casa) {
        return """
                 DETALLE DE PROPIEDAD
               ID:           """ + casa.getId() + "\n"
             + "Dirección:    " + casa.getDireccion() + "\n"
             + "Ciudad:       " + casa.getCiudad() + "\n"
             + "Precio:       $" + String.format("%,.0f", casa.getPrecio()) + "\n"
             + "Área:         " + casa.getArea() + " m²\n"
             + "Dormitorios:  " + casa.getDormitorios() + "\n"
             + "Baños:        " + casa.getBanos() + "\n"
             + "Estado:       " + (casa.isDisponible() ? "Disponible" : "Reservada") + "\n"
             + "Propietario:  " + casa.getPropietario() + "\n"
             + "Descripción:  " + casa.getDescripcion() + "\n"
             + "Registrada:   " + casa.getFechaRegistro().toLocalDate();
    }
}