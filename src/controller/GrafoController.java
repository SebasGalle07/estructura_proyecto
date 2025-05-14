package controller;

import model.GrafoAfinidad;
import model.Usuario;
import model.ListaEnlazada;

public class GrafoController {
    private GrafoAfinidad grafo;

    public GrafoController() {
        this.grafo = new GrafoAfinidad();
    }

    public void agregarUsuario(Usuario usuario) {
        grafo.agregarUsuario(usuario);
    }

    public void conectarUsuarios(Usuario u1, Usuario u2) {
        grafo.conectar(u1.getId(), u2.getId());
    }

    public ListaEnlazada<Usuario> sugerirAmigos(String idUsuario) {
        return grafo.sugerenciasDeAmigos(idUsuario);
    }

    public ListaEnlazada<Usuario> caminoMasCorto(String origen, String destino) {
        return grafo.caminoMasCorto(origen, destino);
    }

    public GrafoAfinidad getGrafo() {
        return grafo;
    }
}
