package app;

import controller.UsuarioController;
import controller.ContenidoController;
import controller.GrafoController;
import controller.AyudaController;
import controller.GrupoEstudioController;
import model.Contenido;
import model.ListaEnlazada;
import model.Usuario;

public class AppContext {

 public static final UsuarioController usuarioController = new UsuarioController();
 public static final ContenidoController contenidoController = new ContenidoController();
 public static final GrafoController grafoController = new GrafoController();
 public static final AyudaController ayudaController = new AyudaController();
 public static final GrupoEstudioController grupoEstudioController = new GrupoEstudioController();

 static {
  // Cambia a false si quieres usar solo conexiones reales (por afinidad)
  cargarDatosPrueba(true);
 }

 public static void cargarDatosPrueba(boolean conectarTodos) {
  // Usuarios con intereses variados
  registrar("1", "Kevin", intereses("Matemáticas", "Física"));
  registrar("2", "Luis", intereses("Física", "Programación"));
  registrar("3", "Ana", intereses("Programación", "Diseño"));
  registrar("4", "Carlos", intereses("Historia", "Diseño"));
  registrar("5", "Santiago", intereses("Historia", "Matemáticas"));
  registrar("moderador", "Moderador", intereses());

  // Publicación de algunos contenidos
  contenidoController.publicarContenido("Guía de Física", "1", "Física", "PDF", "https://link1.com", usuarioController.buscarUsuario("1"));
  contenidoController.publicarContenido("Curso de Programación", "2", "Programación", "Video", "https://link2.com", usuarioController.buscarUsuario("2"));
  contenidoController.publicarContenido("Infografía de Historia", "4", "Historia", "Imagen", "", usuarioController.buscarUsuario("4"));

  // Valoraciones cruzadas (opcional)
  contenidoController.valorarContenido("1", 5, usuarioController.buscarUsuario("2"));
  contenidoController.valorarContenido("2", 4, usuarioController.buscarUsuario("3"));
  contenidoController.valorarContenido("3", 3, usuarioController.buscarUsuario("5"));

  // Conexiones: por intereses comunes o por todos
  if (conectarTodos) {
   conectarTodosConTodos();
  } else {
   conectarPorIntereses();
  }
 }

 private static void registrar(String id, String nombre, ListaEnlazada<String> intereses) {
  usuarioController.registrarUsuario(id, nombre, intereses);
 }

 private static ListaEnlazada<String> intereses(String... temas) {
  ListaEnlazada<String> lista = new ListaEnlazada<>();
  for (String t : temas) {
   lista.insertarFinal(t);
  }
  return lista;
 }

 private static void conectarPorIntereses() {
  ListaEnlazada<Usuario> usuarios = usuarioController.getTodos();
  for (Usuario u1 : usuarios) {
   for (Usuario u2 : usuarios) {
    if (!u1.getId().equals(u2.getId())) {
     for (String interes : u1.getIntereses()) {
      if (u2.getIntereses().contiene(interes)) {
       grafoController.agregarUsuario(u1);
       grafoController.agregarUsuario(u2);
       grafoController.conectarUsuarios(u1, u2);
       break; // Ya hay afinidad
      }
     }
    }
   }
  }
 }

 private static void conectarTodosConTodos() {
  ListaEnlazada<Usuario> usuarios = usuarioController.getTodos();
  for (Usuario u1 : usuarios) {
   for (Usuario u2 : usuarios) {
    if (!u1.getId().equals(u2.getId())) {
     grafoController.agregarUsuario(u1);
     grafoController.agregarUsuario(u2);
     grafoController.conectarUsuarios(u1, u2);
    }
   }
  }
 }
}
