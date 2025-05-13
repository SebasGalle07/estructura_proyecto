package model;

import java.util.PriorityQueue;
import java.util.Comparator;

public class ColaPrioridad {
    private PriorityQueue<SolicitudAyuda> cola;

    public ColaPrioridad() {
        cola = new PriorityQueue<>(Comparator.comparingInt(SolicitudAyuda::getNivelUrgencia).reversed());
    }

    public void insertar(SolicitudAyuda solicitud) {
        cola.offer(solicitud);
    }

    public SolicitudAyuda extraer() {
        return cola.poll();
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }

    public int tamano() {
        return cola.size();
    }
}