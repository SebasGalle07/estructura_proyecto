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

    public Contenido buscarPorTitulo(String titulo) {
        return arbol.buscar(titulo);
    }

    public ListaEnlazada<Contenido> obtenerTodos() {
        return arbol.inOrden();
    }

    public ListaEnlazada<Contenido> buscarPorTema(String tema) {
        ListaEnlazada<Contenido> resultado = new ListaEnlazada<>();
        for (Contenido c : arbol.inOrden()) {
            if (c.getTema().equalsIgnoreCase(tema)) {
                resultado.insertarFinal(c);
            }
        }
        return resultado;
    }

    public ListaEnlazada<Contenido> buscarPorAutor(String autor) {
        ListaEnlazada<Contenido> resultado = new ListaEnlazada<>();
        for (Contenido c : arbol.inOrden()) {
            if (c.getAutor().equalsIgnoreCase(autor)) {
                resultado.insertarFinal(c);
            }
        }
        return resultado;
    }

    public ListaEnlazada<Contenido> buscarPorTipo(String tipo) {
        ListaEnlazada<Contenido> resultado = new ListaEnlazada<>();
        for (Contenido c : arbol.inOrden()) {
            if (c.getTipo().equalsIgnoreCase(tipo)) {
                resultado.insertarFinal(c);
            }
        }
        return resultado;
    }
}
