package model;

public class NodoABB<T> {
    public T dato;
    public NodoABB<T> izq, der;

    public NodoABB(T dato) {
        this.dato = dato;
        this.izq = null;
        this.der = null;
    }
}
