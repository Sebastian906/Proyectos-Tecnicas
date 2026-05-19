package controllers;

import java.io.*;
import java.util.ArrayList;

import models.*;

public class ControladorPersistencia {
    public static void guardarCasas(ArrayList<Casa> casas, String ruta) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            for (Casa casa : casas) {
                bw.write(casa.getId() + "," + casa.getCiudad() + "," + casa.getPrecio());
                bw.newLine();
            }
        }
    }
}
