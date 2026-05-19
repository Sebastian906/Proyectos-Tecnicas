package views;

import controllers.ControladorPrincipal;
import controllers.ControladorImportacion;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.stage.Stage;
import models.Casa;

public class MenuRegistro {

    private final ControladorPrincipal controlador;

    public MenuRegistro(ControladorPrincipal controlador) {
        this.controlador = controlador;
    }

    public void mostrar(Stage owner) {
        Stage stage = new Stage();
        stage.initOwner(owner);

        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(6);
        grid.setPadding(new Insets(15));

        TextField txtDireccion = new TextField();
        TextField txtCiudad = new TextField();
        TextField txtPrecio = new TextField();
        TextField txtArea = new TextField();
        TextField txtDorm = new TextField();
        TextField txtBanos = new TextField();
        TextField txtPropietario = new TextField();
        CheckBox chkDisponible = new CheckBox("Disponible");
        chkDisponible.setSelected(true);

        Button btnRegistrar = new Button("Registrar");
        btnRegistrar.setOnAction(e -> {
            try {
                int id = controlador.getSistema().generarIdCasa();
                String direccion = txtDireccion.getText().trim();
                String ciudad = txtCiudad.getText().trim();
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                int area = Integer.parseInt(txtArea.getText().trim());
                int dorm = Integer.parseInt(txtDorm.getText().trim());
                int banos = Integer.parseInt(txtBanos.getText().trim());
                boolean disponible = chkDisponible.isSelected();
                String propietario = txtPropietario.getText().trim();

                Casa casa = new Casa(id, direccion, ciudad, precio, area, dorm, banos, disponible, "", propietario);
                controlador.getSistema().agregarCasa(casa);
                MenuPrincipal.mostrarAlerta("Registro", "Propiedad registrada con ID " + id);
                stage.close();
            } catch (Exception ex) {
                MenuPrincipal.mostrarError("Error al registrar: " + ex.getMessage());
            }
        });
        Button btnImportar = new Button("Importar desde archivo...");
        btnImportar.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Seleccionar archivo CSV o JSON");
            chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("JSON", "*.json")
            );
            File file = chooser.showOpenDialog(stage);
            if (file == null) return;
            try {
                int importados = ControladorImportacion.importarArchivo(file, controlador.getSistema());
                MenuPrincipal.mostrarAlerta("Importación", "Registros importados: " + importados);
                stage.close();
            } catch (Exception ex) {
                MenuPrincipal.mostrarError("Error al importar: " + ex.getMessage());
            }
        });

        grid.add(new Label("Dirección:"), 0, 0); grid.add(txtDireccion, 1, 0);
        grid.add(new Label("Ciudad:"), 0, 1); grid.add(txtCiudad, 1, 1);
        grid.add(new Label("Precio:"), 0, 2); grid.add(txtPrecio, 1, 2);
        grid.add(new Label("Area:"), 0, 3); grid.add(txtArea, 1, 3);
        grid.add(new Label("Dormitorios:"), 0, 4); grid.add(txtDorm, 1, 4);
        grid.add(new Label("Baños:"), 0, 5); grid.add(txtBanos, 1, 5);
        grid.add(new Label("Propietario:"), 0, 6); grid.add(txtPropietario, 1, 6);
        grid.add(chkDisponible, 1, 7);
        HBox acciones = new HBox(8, btnRegistrar, btnImportar);
        grid.add(acciones, 1, 8);

        Scene scene = new Scene(grid, 420, 400);
        stage.setScene(scene);
        stage.setTitle("Registrar Propiedad");
        stage.show();
    }
}
