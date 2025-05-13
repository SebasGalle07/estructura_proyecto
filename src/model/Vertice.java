package model;

import java.util.ArrayList;
import java.util.List;

public class Vertice {
    private Usuario usuario;
    private List<Vertice> conexiones;

    public Vertice(Usuario usuario) {
        this.usuario = usuario;
        this.conexiones = new ArrayList<>();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<Vertice> getConexiones() {
        return conexiones;
    }

    public void conectar(Vertice otro) {
        if (!conexiones.contains(otro)) {
            conexiones.add(otro);
        }
    }
}
