package model;

public class Usuario {
    private String id;
    private String nombre;
    private ListaEnlazada<String> intereses;
    private ListaEnlazada<Contenido> contenidosPublicados;
    private ListaEnlazada<Usuario> conexiones;
    private ListaEnlazada<String> historialAcciones;
    private ListaEnlazada<Mensaje> mensajesEnviados;
    private ListaEnlazada<Mensaje> mensajesRecibidos;

    public Usuario(String id, String nombre, ListaEnlazada<String> intereses) {
        this.id = id;
        this.nombre = nombre;
        this.intereses = intereses;
        this.contenidosPublicados = new ListaEnlazada<>();
        this.conexiones = new ListaEnlazada<>();
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

    public ListaEnlazada<String> getIntereses() {
        return intereses;
    }

    public void agregarContenido(Contenido contenido) {
        contenidosPublicados.insertarFinal(contenido);
        historialAcciones.insertarFinal("Publicó: " + contenido.getTitulo());
    }

    public void insertarFinalConexion(Usuario otro) {
        if (!conexiones.contiene(otro)) {
            conexiones.insertarFinal(otro);
            historialAcciones.insertarFinal("Conectado con: " + otro.getNombre());
        }
    }

    public ListaEnlazada<Usuario> getConexiones() {
        return conexiones;
    }

    public ListaEnlazada<Contenido> getContenidosPublicados() {
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
