package views;

import java.util.ArrayList;

import controllers.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.*;

public class MenuBusqueda {

    private final ControladorPrincipal controlador;

    public MenuBusqueda(ControladorPrincipal controlador) {
        this.controlador = controlador;
    }

    public void mostrar(Stage owner) {
        Stage stage = new Stage();

        // Tabs para cada tipo de búsqueda
        TabPane tabs = new TabPane();
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabs.getTabs().addAll(
            tabPorCiudad(),
            tabPorRango(),
            tabAvanzada(),
            tabBinaria()
        );

        Scene scene = new Scene(tabs, 700, 480);
        stage.setTitle("Buscar Propiedades");
        stage.setScene(scene);
        stage.initOwner(owner);
        stage.show();
    }

    // Tab 1: Búsqueda por ciudad (lineal)
    private Tab tabPorCiudad() {
        Tab tab = new Tab("Por Ciudad");

        Label lbl = new Label("Ciudad:");
        TextField txtCiudad = new TextField();
        txtCiudad.setPromptText("Ej: Bogota");
        Button btnBuscar = new Button("Buscar");
        btnBuscar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        ListView<String> lista = new ListView<>();
        Label lblInfo = new Label("Búsqueda lineal O(n)");
        lblInfo.setStyle("-fx-font-size: 11px; -fx-text-fill: #888;");

        btnBuscar.setOnAction(e -> {
            String ciudad = txtCiudad.getText().trim();
            if (ciudad.isEmpty()) {
                MenuPrincipal.mostrarError("Ingresa una ciudad para buscar.");
                return;
            }
            ArrayList<Casa> resultados = controlador.getBusqueda().buscarPorCiudad(ciudad);
            lista.setItems(FXCollections.observableArrayList(
                resultados.stream().map(Casa::toString).collect(java.util.stream.Collectors.toList())));
            lblInfo.setText("Búsqueda lineal O(n) | " + resultados.size() + " resultado(s)");
        });

        VBox layout = new VBox(8, new HBox(8, lbl, txtCiudad, btnBuscar), lblInfo, lista);
        layout.setPadding(new Insets(15));
        tab.setContent(layout);
        return tab;
    }

    // Tab 2: Búsqueda por rango de precio
    private Tab tabPorRango() {
        Tab tab = new Tab("Por Rango de Precio");

        TextField txtMin = new TextField();
        txtMin.setPromptText("Precio mínimo");
        TextField txtMax = new TextField();
        txtMax.setPromptText("Precio máximo");
        Button btnBuscar = new Button("Buscar");
        btnBuscar.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");

        ListView<String> lista = new ListView<>();
        Label lblInfo = new Label("Búsqueda lineal con filtro O(n)");
        lblInfo.setStyle("-fx-font-size: 11px; -fx-text-fill: #888;");

        btnBuscar.setOnAction(e -> {
            try {
                double min = Double.parseDouble(txtMin.getText().trim());
                double max = Double.parseDouble(txtMax.getText().trim());
                if (min > max) { MenuPrincipal.mostrarError("El mínimo no puede ser mayor al máximo."); return; }
                ArrayList<Casa> resultados = controlador.getBusqueda().buscarPorRangoPrecio(min, max);
                lista.setItems(FXCollections.observableArrayList(
                    resultados.stream().map(Casa::toString).collect(java.util.stream.Collectors.toList())));
                lblInfo.setText("Búsqueda lineal O(n) | " + resultados.size() + " resultado(s)");
            } catch (NumberFormatException ex) {
                MenuPrincipal.mostrarError("Ingresa valores numéricos válidos para el precio.");
            }
        });

        HBox campos = new HBox(8, new Label("Min $"), txtMin, new Label("Max $"), txtMax, btnBuscar);
        VBox layout = new VBox(8, campos, lblInfo, lista);
        layout.setPadding(new Insets(15));
        tab.setContent(layout);
        return tab;
    }

    // Tab 3: Búsqueda avanzada (fuerza bruta) 
    private Tab tabAvanzada() {
        Tab tab = new Tab("Avanzada (Fuerza Bruta)");

        TextField txtCiudad  = new TextField(); txtCiudad.setPromptText("Ciudad (opcional)");
        TextField txtPrecioMax = new TextField(); txtPrecioMax.setPromptText("Precio máx (0=ignorar)");
        TextField txtDormMin = new TextField(); txtDormMin.setPromptText("Dorm. mín (0=ignorar)");
        CheckBox chkSoloDisp = new CheckBox("Solo disponibles");
        Button btnBuscar = new Button("Buscar con filtros");
        btnBuscar.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white;");

        ListView<String> lista = new ListView<>();
        Label lblInfo = new Label("Fuerza bruta O(n×m)");
        lblInfo.setStyle("-fx-font-size: 11px; -fx-text-fill: #888;");

        btnBuscar.setOnAction(e -> {
            try {
                String ciudad = txtCiudad.getText().trim().isEmpty() ? null : txtCiudad.getText().trim();
                double precioMax = txtPrecioMax.getText().trim().isEmpty() ? 0 : Double.parseDouble(txtPrecioMax.getText().trim());
                int dormMin = txtDormMin.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtDormMin.getText().trim());
                ArrayList<Casa> resultados = controlador.getBusqueda().buscarAvanzado(
                    ciudad, precioMax, dormMin, chkSoloDisp.isSelected());
                lista.setItems(FXCollections.observableArrayList(
                    resultados.stream().map(Casa::toString).collect(java.util.stream.Collectors.toList())));
                lblInfo.setText("Fuerza bruta O(n×m) | " + resultados.size() + " resultado(s)");
            } catch (NumberFormatException ex) {
                MenuPrincipal.mostrarError("Verifica que los valores numéricos sean correctos.");
            }
        });

        GridPane campos = new GridPane();
        campos.setHgap(8); campos.setVgap(6);
        campos.add(new Label("Ciudad:"), 0, 0);   campos.add(txtCiudad, 1, 0);
        campos.add(new Label("Precio máx:"), 0, 1); campos.add(txtPrecioMax, 1, 1);
        campos.add(new Label("Dorm. mín:"), 0, 2); campos.add(txtDormMin, 1, 2);
        campos.add(chkSoloDisp, 1, 3);
        campos.add(btnBuscar, 1, 4);

        VBox layout = new VBox(8, campos, lblInfo, lista);
        layout.setPadding(new Insets(15));
        tab.setContent(layout);
        return tab;
    }

    // Tab 4: Búsqueda binaria por precio exacto
    private Tab tabBinaria() {
        Tab tab = new Tab("Binaria por Precio");

        Label lbl = new Label("Precio exacto:");
        TextField txtPrecio = new TextField();
        txtPrecio.setPromptText("Ej: 1500000");
        Button btnBuscar = new Button("Buscar");
        btnBuscar.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");

        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setWrapText(true);
        resultado.setPrefRowCount(6);
        Label lblInfo = new Label("Búsqueda binaria O(log n) — requiere ordenar primero con Merge Sort");
        lblInfo.setStyle("-fx-font-size: 11px; -fx-text-fill: #888;");
        lblInfo.setWrapText(true);

        btnBuscar.setOnAction(e -> {
            try {
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                Casa casa = controlador.getBusqueda().buscarPorPrecioExacto(precio);
                if (casa != null) {
                    resultado.setText("Propiedad encontrada:\n\n" + formatearCasa(casa));
                } else {
                    resultado.setText("No se encontró ninguna propiedad con precio $"
                        + String.format("%,.0f", precio));
                }
            } catch (NumberFormatException ex) {
                MenuPrincipal.mostrarError("Ingresa un precio numérico válido.");
            }
        });

        VBox layout = new VBox(8, new HBox(8, lbl, txtPrecio, btnBuscar), lblInfo, resultado);
        layout.setPadding(new Insets(15));
        tab.setContent(layout);
        return tab;
    }

    private String formatearCasa(Casa c) {
        return "ID: " + c.getId() + "\n"
             + "Dirección: " + c.getDireccion() + "\n"
             + "Ciudad: " + c.getCiudad() + "\n"
             + "Precio: $" + String.format("%,.0f", c.getPrecio()) + "\n"
             + "Área: " + c.getArea() + " m²\n"
             + "Dormitorios: " + c.getDormitorios() + "\n"
             + "Estado: " + (c.isDisponible() ? "Disponible" : "Reservada");
    }
}