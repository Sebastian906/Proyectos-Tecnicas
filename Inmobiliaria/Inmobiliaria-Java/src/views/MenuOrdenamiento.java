package views;

import java.util.ArrayList;

import controllers.ControladorPrincipal;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Casa;

public class MenuOrdenamiento {

    private final ControladorPrincipal controlador;

    public MenuOrdenamiento(ControladorPrincipal controlador) {
        this.controlador = controlador;
    }

    public void mostrar(Stage owner) {
        Stage stage = new Stage();

        // Controles
        ComboBox<String> cbAlgoritmo = new ComboBox<>();
        cbAlgoritmo.getItems().addAll("merge", "burbuja", "seleccion", "insercion");
        cbAlgoritmo.setValue("merge");

        ComboBox<String> cbCriterio = new ComboBox<>();
        cbCriterio.getItems().addAll("precio", "area", "dormitorios", "ciudad");
        cbCriterio.setValue("precio");

        ToggleGroup grupo = new ToggleGroup();
        RadioButton rbAsc = new RadioButton("Ascendente ↑");
        RadioButton rbDesc = new RadioButton("Descendente ↓");
        rbAsc.setToggleGroup(grupo);
        rbDesc.setToggleGroup(grupo);
        rbAsc.setSelected(true);

        Button btnOrdenar = new Button("Ordenar");
        btnOrdenar.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white; -fx-pref-width: 120;");

        Label lblComplejidad = new Label("Selecciona un algoritmo y presiona Ordenar");
        lblComplejidad.setStyle("-fx-font-size: 11px; -fx-text-fill: #555;");

        // Tabla de resultados
        ListView<String> lista = new ListView<>();
        lista.setPlaceholder(new Label("Presiona Ordenar para ver los resultados."));

        // Acción ordenar
        btnOrdenar.setOnAction(e -> {
            String algoritmo = cbAlgoritmo.getValue();
            String criterio = cbCriterio.getValue();
            boolean asc = rbAsc.isSelected();

            ArrayList<Casa> ordenadas = controlador.getBusqueda().ordenarCasas(algoritmo, criterio, asc);

            lista.setItems(FXCollections.observableArrayList(
                    ordenadas.stream().map(Casa::toString).collect(java.util.stream.Collectors.toList())));

            String complejidad = obtenerComplejidad(algoritmo);
            lblComplejidad.setText("Algoritmo: " + algoritmo.toUpperCase()
                    + " | Criterio: " + criterio + " | " + (asc ? "ASC" : "DESC")
                    + " | " + complejidad + " | " + ordenadas.size() + " casas");
        });

        // Layout
        HBox controles = new HBox(10,
                new Label("Algoritmo:"), cbAlgoritmo,
                new Label("Criterio:"), cbCriterio,
                rbAsc, rbDesc,
                btnOrdenar);
        controles.setPadding(new Insets(0, 0, 5, 0));

        // Tabla de complejidades como referencia académica
        TextArea tablaRef = new TextArea(
                """
                COMPLEJIDADES:
                Burbuja:   O(n\u00b2) prom/peor | O(n) mejor
                Selecci\u00f3n: O(n\u00b2) siempre
                Inserci\u00f3n: O(n\u00b2) peor | O(n) mejor
                Merge:     O(n log n) siempre \u2190 m\u00e1s eficiente""");
        tablaRef.setEditable(false);
        tablaRef.setPrefRowCount(4);
        tablaRef.setStyle("-fx-font-family: monospace; -fx-font-size: 11px;");

        VBox root = new VBox(8,
                new Label("Ordenar Propiedades") {
                    {
                        setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
                    }
                },
                controles, lblComplejidad,
                lista,
                tablaRef);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 720, 500);
        stage.setTitle("Ordenar Propiedades");
        stage.setScene(scene);
        stage.initOwner(owner);
        stage.show();
    }

    private String obtenerComplejidad(String algoritmo) {
        return switch (algoritmo) {
            case "burbuja" -> "O(n²)";
            case "seleccion" -> "O(n²)";
            case "insercion" -> "O(n²) / O(n) mejor";
            case "merge" -> "O(n log n)";
            default -> "";
        };
    }
}