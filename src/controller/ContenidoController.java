package controller;

import model.ArbolBinarioBusqueda;
import model.Contenido;
import model.Usuario;
import model.ListaEnlazada; // ✅ Import necesario para usar tu lista propia

public class ContenidoController {
    private ArbolBinarioBusqueda arbol;
    private int idAuto = 1;

    public ContenidoController() {
        this.arbol = new ArbolBinarioBusqueda();
    }

    public Contenido publicarContenido(String titulo, String autor, String tema, String tipo, String enlace, Usuario usuario) {
        Contenido nuevo = new Contenido(idAuto++, titulo, autor, tema, tipo, enlace);
        arbol.insertar(nuevo);
        usuario.agregarContenido(nuevo);
        return nuevo;
    }
}

