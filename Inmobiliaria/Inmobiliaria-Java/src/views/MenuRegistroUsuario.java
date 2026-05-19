package views;

import controllers.ControladorImportacion;
import controllers.ControladorPrincipal;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Usuario;

import java.io.File;

public class MenuRegistroUsuario {

    private final ControladorPrincipal controlador;

    public MenuRegistroUsuario(ControladorPrincipal controlador) {
        this.controlador = controlador;
    }

    public void mostrar(Stage owner) {
        Stage stage = new Stage();
        stage.initOwner(owner);

        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(6);
        grid.setPadding(new Insets(15));

        TextField txtNombre = new TextField();
        TextField txtEmail = new TextField();
        TextField txtTelefono = new TextField();
        TextField txtPresupuesto = new TextField();
        CheckBox chkActivo = new CheckBox("Activo");
        chkActivo.setSelected(true);

        Button btnRegistrar = new Button("Registrar Usuario");
        btnRegistrar.setOnAction(e -> {
            try {
                int id = controlador.getSistema().generarIdUsuario();
                String nombre = txtNombre.getText().trim();
                String email = txtEmail.getText().trim();
                String telefono = txtTelefono.getText().trim();
                double presupuesto = Double.parseDouble(txtPresupuesto.getText().trim());
                boolean activo = chkActivo.isSelected();

                Usuario usuario = new Usuario(id, nombre, email, telefono, presupuesto);
                if (!activo) usuario.setActivo(false);
                controlador.getSistema().agregarUsuario(usuario);
                MenuPrincipal.mostrarAlerta("Registro", "Usuario registrado con ID " + id);
                stage.close();
            } catch (Exception ex) {
                MenuPrincipal.mostrarError("Error al registrar usuario: " + ex.getMessage());
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
                int importados = ControladorImportacion.importarUsuarios(file, controlador.getSistema());
                MenuPrincipal.mostrarAlerta("Importación", "Usuarios importados: " + importados);
                stage.close();
            } catch (Exception ex) {
                MenuPrincipal.mostrarError("Error al importar usuarios: " + ex.getMessage());
            }
        });

        grid.add(new Label("Nombre:"), 0, 0); grid.add(txtNombre, 1, 0);
        grid.add(new Label("Email:"), 0, 1); grid.add(txtEmail, 1, 1);
        grid.add(new Label("Teléfono:"), 0, 2); grid.add(txtTelefono, 1, 2);
        grid.add(new Label("Presupuesto:"), 0, 3); grid.add(txtPresupuesto, 1, 3);
        grid.add(chkActivo, 1, 4);
        HBox acciones = new HBox(8, btnRegistrar, btnImportar);
        grid.add(acciones, 1, 5);

        Scene scene = new Scene(grid, 420, 300);
        stage.setScene(scene);
        stage.setTitle("Registrar Usuario");
        stage.show();
    }
}
