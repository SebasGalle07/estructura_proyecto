package app;

import controller.UsuarioController;
import controller.ContenidoController;
import controller.GrafoController;
import controller.AyudaController;

public class AppContext {
 public static final UsuarioController usuarioController = new UsuarioController();
 public static final ContenidoController contenidoController = new ContenidoController();
 public static final GrafoController grafoController = new GrafoController();
 public static final AyudaController ayudaController = new AyudaController();
}
