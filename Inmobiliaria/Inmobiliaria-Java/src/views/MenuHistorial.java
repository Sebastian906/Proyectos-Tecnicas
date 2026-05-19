package views;

import controllers.ControladorPrincipal;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuHistorial {

	private final ControladorPrincipal controlador;

	public MenuHistorial(ControladorPrincipal controlador) {
		this.controlador = controlador;
	}

	public void mostrar(Stage owner) {
		Stage stage = new Stage();
		stage.initOwner(owner);

		ListView<String> lista = new ListView<>();
		var pila = controlador.getHistorial().obtenerPilaBusquedas();
		if (pila != null && !pila.isEmpty()) {
			lista.setItems(FXCollections.observableArrayList(pila));
		} else {
			lista.setPlaceholder(new Label("No hay búsquedas recientes."));
		}

		VBox layout = new VBox(8, new Label("Historial de Búsquedas"), lista);
		layout.setPadding(new Insets(15));
		Scene scene = new Scene(layout, 400, 300);
		stage.setScene(scene);
		stage.setTitle("Historial de Búsquedas");
		stage.show();
	}
}
