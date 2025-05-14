package model;

public class Vertice {
    private Usuario usuario;
    private ListaEnlazada<Vertice> conexiones;

    public Vertice(Usuario usuario) {
        this.usuario = usuario;
        this.conexiones = new ListaEnlazada<>();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public ListaEnlazada<Vertice> getConexiones() {
        return conexiones;
    }

    public void conectar(Vertice otro) {
        if (!conexiones.contiene(otro)) {
            conexiones.insertarFinal(otro);
        }
    }
}
