package controller;

import app.AppContext;
import model.Usuario;
import model.ListaEnlazada;
import model.Pair;

public class UsuarioController {
    private ListaEnlazada<Pair<String, Usuario>> usuarios;

    public UsuarioController() {
        usuarios = new ListaEnlazada<>();
    }

    public boolean registrarUsuario(String id, String nombre, ListaEnlazada<String> intereses) {
        if (buscarUsuario(id) != null) return false;
        Usuario nuevo = new Usuario(id, nombre, intereses);
        usuarios.insertarFinal(new Pair<>(id, nuevo));
        AppContext.grafoController.agregarUsuario(nuevo);

        return true;
    }


    public Usuario buscarUsuario(String id) {
        for (Pair<String, Usuario> par : usuarios) {
            if (par.getKey().equals(id)) {
                return par.getValue();
            }
        }
        return null;
    }

    public ListaEnlazada<Usuario> getTodos() {
        ListaEnlazada<Usuario> lista = new ListaEnlazada<>();
        for (Pair<String, Usuario> par : usuarios) {
            lista.insertarFinal(par.getValue());
        }
        return lista;
    }

    public boolean existeUsuario(String id) {
        return buscarUsuario(id) != null;
    }
}
