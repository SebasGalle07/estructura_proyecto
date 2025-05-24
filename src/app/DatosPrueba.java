package app;

import controller.*;
import model.*;

public class     DatosPrueba {

    public static void cargar(UsuarioController usuarioCtrl, ContenidoController contenidoCtrl,
                              AyudaController ayudaCtrl, GrafoController grafoCtrl,
                              GrupoEstudioController grupoCtrl) {

        // Crear intereses manualmente con estructuras propias
        ListaEnlazada<String> interesesKevin = new ListaEnlazada<>();
        interesesKevin.insertarFinal("estructura");
        interesesKevin.insertarFinal("java");

        ListaEnlazada<String> interesesLuis = new ListaEnlazada<>();
        interesesLuis.insertarFinal("estructura");
        interesesLuis.insertarFinal("pilas");

        ListaEnlazada<String> interesesAna = new ListaEnlazada<>();
        interesesAna.insertarFinal("grafo");
        interesesAna.insertarFinal("java");

        ListaEnlazada<String> interesesCarlos = new ListaEnlazada<>();
        interesesCarlos.insertarFinal("estructura");
        interesesCarlos.insertarFinal("abb");

        ListaEnlazada<String> interesesSantiago = new ListaEnlazada<>();
        interesesCarlos.insertarFinal("estructura");
        interesesCarlos.insertarFinal("recorridos arboles");

        // Registrar usuarios con intereses
        usuarioCtrl.registrarUsuario("kevin", "Kevin Betancourt", interesesKevin);
        usuarioCtrl.registrarUsuario("luis", "Luis Gómez", interesesLuis);
        usuarioCtrl.registrarUsuario("ana", "Ana Torres", interesesAna);
        usuarioCtrl.registrarUsuario("carlos", "Carlos Ruiz", interesesCarlos);
        usuarioCtrl.registrarUsuario("santiago", "Santiago munoz", interesesSantiago);

        // Obtener objetos de Usuario ya registrados
        Usuario kevin = usuarioCtrl.buscarUsuario("kevin");
        Usuario luis = usuarioCtrl.buscarUsuario("luis");
        Usuario ana = usuarioCtrl.buscarUsuario("ana");
        Usuario carlos = usuarioCtrl.buscarUsuario("carlos");
        Usuario santiago = usuarioCtrl.buscarUsuario("santiago");

        // Publicar contenido desde el controlador
        contenidoCtrl.publicarContenido("Estructuras de Datos en Java", "kevin", "estructura", "documento", "https://ejemplo.com/estructura", kevin);
        contenidoCtrl.publicarContenido("Grafos Explicados", "ana", "grafo", "video", "https://ejemplo.com/grafo", ana);
        contenidoCtrl.publicarContenido("Colas y Pilas", "luis", "estructura", "enlace", "https://ejemplo.com/colas", luis);
        contenidoCtrl.publicarContenido("ABB - Árboles Binarios", "carlos", "estructura", "documento", "https://ejemplo.com/abb", carlos);
        contenidoCtrl.publicarContenido("recorridos arboles", "santiago", "estructura", "documento", "https://ejemplo.com/recorridos", santiago);

        // Conectar usuarios en el grafo
        grafoCtrl.agregarUsuario(kevin);
        grafoCtrl.agregarUsuario(luis);
        grafoCtrl.agregarUsuario(ana);
        grafoCtrl.agregarUsuario(carlos);
        grafoCtrl.agregarUsuario(santiago);

        grafoCtrl.conectarUsuarios(kevin, luis);
        grafoCtrl.conectarUsuarios(luis, ana);
        grafoCtrl.conectarUsuarios(ana, carlos);

        // Solicitudes de ayuda
        ayudaCtrl.solicitarAyuda(kevin, "Grafos", 3);
        ayudaCtrl.solicitarAyuda(luis, "ABB", 5);
        ayudaCtrl.solicitarAyuda(ana, "Listas enlazadas", 2);
        ayudaCtrl.solicitarAyuda(santiago, "recorridos", 9);
    }
}
