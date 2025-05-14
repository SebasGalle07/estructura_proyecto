package model;

public class ColaPrioridad<T extends Comparable<T>> {
    private Nodo<T> cabeza;
    private int tamano;

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
        }
    }

    public ColaPrioridad() {
        cabeza = null;
        tamano = 0;
    }

    public void insertar(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);

        if (cabeza == null || elemento.compareTo(cabeza.dato) > 0) {
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.siguiente != null && elemento.compareTo(actual.siguiente.dato) <= 0) {
                actual = actual.siguiente;
            }
            nuevo.siguiente = actual.siguiente;
            actual.siguiente = nuevo;
        }
        tamano++;
    }

    public T extraer() {
        if (cabeza == null) return null;
        T dato = cabeza.dato;
        cabeza = cabeza.siguiente;
        tamano--;
        return dato;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public int tamano() {
        return tamano;
    }
}
