package model;

import java.util.Iterator;

public class ListaEnlazada<T> implements Iterable<T> {
    private NodoLista<T> cabeza;

    public void insertarFinal(T dato) {
        NodoLista<T> nuevo = new NodoLista<>(dato);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoLista<T> actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
    }

    public void insertarInicio(T dato) {
        NodoLista<T> nuevo = new NodoLista<>(dato);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
    }

    public boolean contiene(T dato) {
        NodoLista<T> actual = cabeza;
        while (actual != null) {
            if (actual.dato.equals(dato)) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public void insertarUnico(T dato) {
        if (!contiene(dato)) {
            insertarFinal(dato);
        }
    }

    public NodoLista<T> getCabeza() {
        return cabeza;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public void imprimir() {
        NodoLista<T> actual = cabeza;
        while (actual != null) {
            System.out.println(actual.dato);
            actual = actual.siguiente;
        }
    }

    public int tamano() {
        int contador = 0;
        NodoLista<T> actual = cabeza;
        while (actual != null) {
            contador++;
            actual = actual.siguiente;
        }
        return contador;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private NodoLista<T> actual = cabeza;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public T next() {
                T dato = actual.dato;
                actual = actual.siguiente;
                return dato;
            }
        };
    }
    public int contar() {
        return tamano();
    }

    public T get(int index) {
        NodoLista<T> actual = cabeza;
        int contador = 0;
        while (actual != null) {
            if (contador == index) {
                return actual.dato;
            }
            actual = actual.siguiente;
            contador++;
        }
        return null;
    }

}
