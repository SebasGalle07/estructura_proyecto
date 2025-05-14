package model;

public class ArbolBinarioBusqueda<T extends Comparable<T>> {
    private NodoABB<T> raiz;

    public void insertar(T dato) {
        raiz = insertarRec(raiz, dato);
    }

    private NodoABB<T> insertarRec(NodoABB<T> actual, T dato) {
        if (actual == null) return new NodoABB<>(dato);
        if (dato.compareTo(actual.dato) < 0) {
            actual.izq = insertarRec(actual.izq, dato);
        } else {
            actual.der = insertarRec(actual.der, dato);
        }
        return actual;
    }

    public T buscar(T clave) {
        return buscarRec(raiz, clave);
    }

    private T buscarRec(NodoABB<T> actual, T clave) {
        if (actual == null) return null;
        int cmp = clave.compareTo(actual.dato);
        if (cmp == 0) return actual.dato;
        else if (cmp < 0) return buscarRec(actual.izq, clave);
        else return buscarRec(actual.der, clave);
    }

    public ListaEnlazada<T> inOrden() {
        ListaEnlazada<T> lista = new ListaEnlazada<>();
        inOrdenRec(raiz, lista);
        return lista;
    }

    private void inOrdenRec(NodoABB<T> actual, ListaEnlazada<T> lista) {
        if (actual != null) {
            inOrdenRec(actual.izq, lista);
            lista.insertarFinal(actual.dato);
            inOrdenRec(actual.der, lista);
        }
    }
}
