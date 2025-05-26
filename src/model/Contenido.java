package model;

public class Contenido implements Comparable<Contenido> {
    private int id;
    private String titulo;
    private String autor;
    private String tema;
    private String tipo;
    private String enlace;
    private ListaEnlazada<Integer> valoraciones;

    public Contenido(int id, String titulo, String autor, String tema, String tipo, String enlace) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.tema = tema;
        this.tipo = tipo;
        this.enlace = enlace;
        this.valoraciones = new ListaEnlazada<>();
    }

    public void agregarValoracion(int valor) {
        valoraciones.insertarFinal(valor);
    }

    public double obtenerPromedio() {
        if (valoraciones.estaVacia()) return 0;

        int suma = 0;
        int cantidad = 0;
        NodoLista<Integer> actual = valoraciones.getCabeza();

        while (actual != null) {
            suma += actual.getDato();
            cantidad++;
            actual = actual.getSiguiente();
        }

        return (double) suma / cantidad;
    }

    

    public String getTitulo() {
        return titulo;
    }

    public String getTema() {
        return tema;
    }

    public String getAutor() {
        return autor;
    }

    public String getTipo() {
        return tipo;
    }

    public String getEnlace() {
        return enlace;
    }

    public int getId() {
        return id;
    }

    public ListaEnlazada<Integer> getValoraciones() {
        return valoraciones;
    }

    @Override
    public int compareTo(Contenido otro) {
        return this.titulo.compareTo(otro.titulo);
    }
}
