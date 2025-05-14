package controller;

import model.ColaPrioridad;
import model.SolicitudAyuda;
import model.Usuario;

public class AyudaController {
    private ColaPrioridad<SolicitudAyuda> cola;

    public AyudaController() {
        this.cola = new ColaPrioridad<>();
    }

    public void solicitarAyuda(Usuario usuario, String tema, int urgencia) {
        SolicitudAyuda solicitud = new SolicitudAyuda(usuario, tema, urgencia);
        cola.insertar(solicitud);
    }

    public SolicitudAyuda atenderSiguiente() {
        return cola.extraer();
    }

    public boolean haySolicitudes() {
        return !cola.estaVacia();
    }

    public int totalSolicitudesPendientes() {
        return cola.tamano();
    }
}
