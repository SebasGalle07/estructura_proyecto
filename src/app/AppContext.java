package app;

import controller.UsuarioController;
import controller.ContenidoController;
import controller.GrafoController;
import controller.AyudaController;
import controller.GrupoEstudioController;

public class AppContext {
 public static final UsuarioController usuarioController = new UsuarioController();
 public static final ContenidoController contenidoController = new ContenidoController();
 public static final GrafoController grafoController = new GrafoController();
 public static final AyudaController ayudaController = new AyudaController();
 public static final GrupoEstudioController grupoEstudioController = new GrupoEstudioController(); // ✅ nuevo campo
}
