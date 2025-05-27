package app;

import controller.*;
import model.*;

public class DatosPrueba {

    public static void cargar(UsuarioController usuarioCtrl, ContenidoController contenidoCtrl,
                              AyudaController ayudaCtrl, GrafoController grafoCtrl,
                              GrupoEstudioController grupoCtrl) {

        // Crear intereses manualmente con estructuras propias
        ListaEnlazada<String> interesesSebastian = new ListaEnlazada<>();
        interesesSebastian.insertarFinal("estructura");
        interesesSebastian.insertarFinal("java");

        ListaEnlazada<String> interesesLuis = new ListaEnlazada<>();
        interesesLuis.insertarFinal("estructura");
        interesesLuis.insertarFinal("pilas");

        ListaEnlazada<String> interesesLisa = new ListaEnlazada<>();
        interesesLisa.insertarFinal("grafo");
        interesesLisa.insertarFinal("java");

        ListaEnlazada<String> interesesJuan = new ListaEnlazada<>();
        interesesJuan.insertarFinal("estructura");
        interesesJuan.insertarFinal("abb");

        ListaEnlazada<String> interesesSantiago = new ListaEnlazada<>();
        interesesSantiago.insertarFinal("estructura");
        interesesSantiago.insertarFinal("recorridos arboles");

        // Registrar usuarios con intereses
        usuarioCtrl.registrarUsuario("sebastian", "Sebastian Gallego", interesesSebastian);
        usuarioCtrl.registrarUsuario("luis", "Luis Gómez", interesesLuis);
        usuarioCtrl.registrarUsuario("lisa", "Lisa Maya", interesesLisa);
        usuarioCtrl.registrarUsuario("juan", "Juan Ruiz", interesesJuan);
        usuarioCtrl.registrarUsuario("santiago", "Santiago munoz", interesesSantiago);

        // Obtener objetos de Usuario ya registrados
        Usuario sebastian = usuarioCtrl.buscarUsuario("sebastian");
        Usuario luis = usuarioCtrl.buscarUsuario("luis");
        Usuario lisa = usuarioCtrl.buscarUsuario("lisa");
        Usuario juan = usuarioCtrl.buscarUsuario("juan");
        Usuario santiago = usuarioCtrl.buscarUsuario("santiago");

        // Publicar contenido desde el controlador
        contenidoCtrl.publicarContenido("Estructuras de Datos en Java", "sebastian", "estructura", "documento", "https://ejemplo.com/estructura", sebastian);
        contenidoCtrl.publicarContenido("Grafos Explicados", "lisa", "grafo", "video", "https://ejemplo.com/grafo", lisa);
        contenidoCtrl.publicarContenido("Colas y Pilas", "luis", "estructura", "enlace", "https://ejemplo.com/colas", luis);
        contenidoCtrl.publicarContenido("ABB - Árboles Binarios", "juan", "estructura", "documento", "https://ejemplo.com/abb", juan);
        contenidoCtrl.publicarContenido("recorridos arboles", "santiago", "estructura", "documento", "https://ejemplo.com/recorridos", santiago);

        // Conectar usuarios en el grafo
        grafoCtrl.agregarUsuario(sebastian);
        grafoCtrl.agregarUsuario(luis);
        grafoCtrl.agregarUsuario(lisa);
        grafoCtrl.agregarUsuario(juan);
        grafoCtrl.agregarUsuario(santiago);

        grafoCtrl.conectarUsuarios(sebastian, luis);
        grafoCtrl.conectarUsuarios(luis, lisa);
        grafoCtrl.conectarUsuarios(lisa, juan);
        grafoCtrl.conectarUsuarios(lisa, santiago);

        // Solicitudes de ayuda
        ayudaCtrl.solicitarAyuda(sebastian, "Grafos", 3);
        ayudaCtrl.solicitarAyuda(luis, "ABB", 5);
        ayudaCtrl.solicitarAyuda(lisa, "Listas enlazadas", 2);
        ayudaCtrl.solicitarAyuda(santiago, "recorridos", 9);

        // Crear un moderador
        Usuario moderador = new Usuario("moderador", "admin", new ListaEnlazada<>());
        usuarioCtrl.registrarUsuario(moderador.getId(), moderador.getNombre(), moderador.getIntereses());
    }
}
