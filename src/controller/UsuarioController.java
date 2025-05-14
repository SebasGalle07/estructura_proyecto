package controller;

import model.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioController {
    private Map<String, Usuario> usuarios;

    public UsuarioController() {
        usuarios = new HashMap<>();
    }

    public boolean registrarUsuario(String id, String nombre, List<String> intereses) {
        if (usuarios.containsKey(id)) return false;
        Usuario nuevo = new Usuario(id, nombre, intereses);
        usuarios.put(id, nuevo);
        return true;
    }

    public Usuario buscarUsuario(String id) {
        return usuarios.get(id);
    }

    public List<Usuario> getTodos() {
        return new ArrayList<>(usuarios.values());
    }

    public boolean existeUsuario(String id) {
        return usuarios.containsKey(id);
    }
}
