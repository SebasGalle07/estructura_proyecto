package model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String id;
    private String nombre;
    private List<String> intereses;
    private List<Contenido> contenidosPublicados;
    private List<Usuario> conexiones;
    private ListaEnlazada<String> historialAcciones;
    private ListaEnlazada<Mensaje> mensajesEnviados;
    private ListaEnlazada<Mensaje> mensajesRecibidos;

    public Usuario(String id, String nombre, List<String> intereses) {
        this.id = id;
        this.nombre = nombre;
        this.intereses = intereses;
        this.contenidosPublicados = new ArrayList<>();
        this.conexiones = new ArrayList<>();
        this.historialAcciones = new ListaEnlazada<>();
        this.mensajesEnviados = new ListaEnlazada<>();
        this.mensajesRecibidos = new ListaEnlazada<>();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<String> getIntereses() {
        return intereses;
    }

    public void agregarContenido(Contenido contenido) {
        contenidosPublicados.add(contenido);
        historialAcciones.insertarFinal("Publicó: " + contenido.getTitulo());
    }

    public void insertarFinalConexion(Usuario otro) {
        if (!conexiones.contains(otro)) {
            conexiones.add(otro);
            historialAcciones.insertarFinal("Conectado con: " + otro.getNombre());
        }
    }

    public List<Usuario> getConexiones() {
        return conexiones;
    }

    public List<Contenido> getContenidosPublicados() {
        return contenidosPublicados;
    }

    public ListaEnlazada<String> getHistorialAcciones() {
        return historialAcciones;
    }

    public void enviarMensaje(Mensaje mensaje) {
        mensajesEnviados.insertarFinal(mensaje);
        historialAcciones.insertarFinal("Envió mensaje a: " + mensaje.getDestinatario().getNombre());
    }

    public void recibirMensaje(Mensaje mensaje) {
        mensajesRecibidos.insertarFinal(mensaje);
        historialAcciones.insertarFinal("Recibió mensaje de: " + mensaje.getRemitente().getNombre());
    }

    public ListaEnlazada<Mensaje> getMensajesEnviados() {
        return mensajesEnviados;
    }

    public ListaEnlazada<Mensaje> getMensajesRecibidos() {
        return mensajesRecibidos;
    }
}
