package controller;

import model.ArbolBinarioBusqueda;
import model.Contenido;
import model.Usuario;
import model.ListaEnlazada;
import java.util.ArrayList;
import java.util.List;

public class ContenidoController {
    private ArbolBinarioBusqueda<Contenido> arbol;
    private int idAuto = 1;

    public ContenidoController() {
        this.arbol = new ArbolBinarioBusqueda<>();
    }

    public Contenido publicarContenido(String titulo, String autor, String tema, String tipo, String enlace, Usuario usuario) {
        Contenido nuevo = new Contenido(idAuto++, titulo, autor, tema, tipo, enlace);
        arbol.insertar(nuevo);
        usuario.agregarContenido(nuevo);
        return nuevo;
    }

    public Contenido buscarPorTitulo(String titulo) {
        return arbol.buscar(new Contenido(0, titulo, "", "", "", "")); // asume que `compareTo` usa título
    }

    public ListaEnlazada<Contenido> obtenerTodos() {
        return arbol.inOrden();
    }

    public ListaEnlazada<Contenido> obtenerTodosLosContenidos() {
        return arbol.inOrden(); 
    }

    public List<Contenido> buscarPorTema(String tema) {
        List<Contenido> resultado = new ArrayList<>();
        ListaEnlazada<Contenido> listaEnlazada = arbol.inOrden(); // Obtener todos los contenidos en orden
        List<Contenido> todos = new ArrayList<>();
        for (Contenido contenido : listaEnlazada) {
            todos.add(contenido);
        }
        for (Contenido contenido : todos) {
            if (contenido.getTema().equalsIgnoreCase(tema)) {
                resultado.add(contenido);
            }
        }
        return resultado;
    }

    public List<Contenido> buscarPorAutor(String autor) {
        List<Contenido> resultado = new ArrayList<>();
        ListaEnlazada<Contenido> listaEnlazada = arbol.inOrden(); // Obtener todos los contenidos en orden
        List<Contenido> todos = new ArrayList<>();
        for (Contenido contenido : listaEnlazada) {
            todos.add(contenido);
        }
        for (Contenido contenido : todos) {
            if (contenido.getAutor().equalsIgnoreCase(autor)) {
                resultado.add(contenido);
            }
        }
        return resultado;
    }

    public List<Contenido> buscarPorTipo(String tipo) {
        List<Contenido> resultado = new ArrayList<>();
        ListaEnlazada<Contenido> listaEnlazada = arbol.inOrden(); // Obtener todos los contenidos en orden
        List<Contenido> todos = new ArrayList<>();
        for (Contenido contenido : listaEnlazada) {
            todos.add(contenido);
        }
        for (Contenido contenido : todos) {
            if (contenido.getTipo().equalsIgnoreCase(tipo)) {
                resultado.add(contenido);
            }
        }
        return resultado;
    }

    public Contenido buscarContenidoPorId(int idContenido) {
        ListaEnlazada<Contenido> todos = arbol.inOrden(); 
        for (Contenido contenido : todos) {
            if (contenido.getId() == idContenido) {
                return contenido;
            }
        }
        return null; 
    }

    public void valorarContenido(String idContenido, int valoracion, Usuario usuario) {
        Contenido contenido = buscarContenidoPorId(Integer.parseInt(idContenido));
        if (contenido != null) {
            contenido.agregarValoracion(valoracion);
        } else {
            throw new IllegalArgumentException("El contenido con ID " + idContenido + " no existe.");
        }
    }
}
