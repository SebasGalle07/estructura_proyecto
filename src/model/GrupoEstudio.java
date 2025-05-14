package model;

public class GrupoEstudio {
    private String tema;
    private ListaEnlazada<Usuario> integrantes;

    public GrupoEstudio(String tema) {
        this.tema = tema;
        this.integrantes = new ListaEnlazada<>();
    }

    public void agregarIntegrante(Usuario usuario) {
        if (!integrantes.contiene(usuario)) {
            integrantes.insertarFinal(usuario);
        }
    }

    public String getTema() {
        return tema;
    }

    public ListaEnlazada<Usuario> getIntegrantes() {
        return integrantes;
    }
}
