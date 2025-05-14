package model;

public class SolicitudAyuda implements Comparable<SolicitudAyuda> {
    private Usuario solicitante;
    private String tema;
    private int nivelUrgencia;

    public SolicitudAyuda(Usuario solicitante, String tema, int nivelUrgencia) {
        this.solicitante = solicitante;
        this.tema = tema;
        this.nivelUrgencia = nivelUrgencia;
    }

    public Usuario getSolicitante() {
        return solicitante;
    }

    public String getTema() {
        return tema;
    }

    public int getNivelUrgencia() {
        return nivelUrgencia;
    }

    @Override
    public int compareTo(SolicitudAyuda otra) {
        return Integer.compare(this.nivelUrgencia, otra.nivelUrgencia);
    }
}
