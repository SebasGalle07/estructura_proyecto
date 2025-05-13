package model;

import java.util.ArrayList;
import java.util.List;

public class Contenido {
    private int id;
    private String titulo;
    private String autor;
    private String tema;
    private String tipo;
    private String enlace;
    private List<Integer> valoraciones;

    public Contenido(int id, String titulo, String autor, String tema, String tipo, String enlace) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.tema = tema;
        this.tipo = tipo;
        this.enlace = enlace;
        this.valoraciones = new ArrayList<>();
    }

    public void agregarValoracion(int valor) {
        valoraciones.add(valor);
    }

    public double obtenerPromedio() {
        return valoraciones.stream().mapToInt(i -> i).average().orElse(0);
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

    public List<Integer> getValoraciones() {
        return valoraciones;
    }
}
