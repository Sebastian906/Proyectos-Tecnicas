package controllers;

import java.util.ArrayList;
import models.*;

public class AlgoritmosOrdenamiento {
    // Burbuja
    public static void burbujaAscendente(ArrayList<Casa> casas) {
        for (int i = 0; i < casas.size() - 1; i++) {
            for (int j = 0; j < casas.size() - i - 1; j++) {
                if (casas.get(j).getPrecio() > casas.get(j + 1).getPrecio()) {
                    Casa temp = casas.get(j);
                    casas.set(j, casas.get(j + 1));
                    casas.set(j + 1, temp);
                }
            }
        }
    }

    // Selección
    public static void seleccionAscendente(ArrayList<Casa> casas) {
        for (int i = 0; i < casas.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < casas.size(); j++) {
                if (casas.get(j).getPrecio() < casas.get(minIndex).getPrecio()) {
                    minIndex = j;
                }
            }
            Casa temp = casas.get(i);
            casas.set(i, casas.get(minIndex));
            casas.set(minIndex, temp);
        }
    }

    // Inserción
    public static void insercionAscendente(ArrayList<Casa> casas) {
        for (int i = 1; i < casas.size(); i++) {
            Casa key = casas.get(i);
            int j = i - 1;
            while (j >= 0 && casas.get(j).getPrecio() > key.getPrecio()) {
                casas.set(j + 1, casas.get(j));
                j--;
            }
            casas.set(j + 1, key);
        }
    }

    // Merge Sort
    public static void mergeSort(ArrayList<Casa> casas, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(casas, left, mid);
            mergeSort(casas, mid + 1, right);
            merge(casas, left, mid, right);
        }
    }

    private static void merge(ArrayList<Casa> casas, int left, int mid, int right) {
        ArrayList<Casa> temp = new ArrayList<>();
        int i = left, j = mid + 1;
        while (i <= mid && j <= right) {
            if (casas.get(i).getPrecio() <= casas.get(j).getPrecio()) {
                temp.add(casas.get(i++));
            } else {
                temp.add(casas.get(j++));
            }
        }
        while (i <= mid) temp.add(casas.get(i++));
        while (j <= right) temp.add(casas.get(j++));
        for (int k = 0; k < temp.size(); k++) {
            casas.set(left + k, temp.get(k));
        }
    }
}
