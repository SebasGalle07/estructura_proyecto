package model;

public class SolicitudAyuda {
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
}
