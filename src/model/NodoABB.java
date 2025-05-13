package model;

public class NodoABB {
    public Contenido contenido;
    public NodoABB izq, der;

    public NodoABB(Contenido contenido) {
        this.contenido = contenido;
        this.izq = this.der = null;
    }
}
