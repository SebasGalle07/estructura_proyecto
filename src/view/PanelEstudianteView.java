package view;

import app.AppContext;
import controller.ContenidoController;
import controller.GrafoController;
import controller.AyudaController;
import controller.UsuarioController;
import model.Usuario;
import model.Contenido;
import model.Mensaje;
import model.ListaEnlazada;  
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;

public class PanelEstudianteView extends JFrame {
    // Definimos la misma paleta de colores que RegistroView
    private static final Color COLOR_FONDO = new Color(240, 248, 255); // Azul muy claro
    private static final Color COLOR_HEADER = new Color(173, 216, 230); // Azul claro
    private static final Color COLOR_BOTON = new Color(100, 149, 237); // Azul cornflower
    private static final Color COLOR_TEXTO = new Color(25, 25, 112); // Azul marino oscuro
    private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FUENTE_ETIQUETA = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FUENTE_CAMPO = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 14);

    private Usuario usuario;
    private UsuarioController usuarioController;
    private ContenidoController contenidoController;
    private GrafoController grafoController;
    private AyudaController ayudaController;

    public PanelEstudianteView(Usuario usuario) {
        this.usuario = usuario;
        this.usuarioController = AppContext.usuarioController;
        this.contenidoController = AppContext.contenidoController;
        this.grafoController = AppContext.grafoController;
        this.ayudaController = AppContext.ayudaController;

        setTitle("Panel de Estudiante - " + usuario.getNombre());
        setSize(550, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(COLOR_FONDO);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 25, 25));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(COLOR_HEADER);
        titlePanel.setBorder(new EmptyBorder(15, 0, 15, 0));
        JLabel lblTitulo = new JLabel("Bienvenido, " + usuario.getNombre());
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_TEXTO);
        titlePanel.add(lblTitulo);

        JPanel buttonPanel = new JPanel(new GridLayout(12, 1, 0, 15));
        buttonPanel.setBackground(COLOR_FONDO);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton btnPublicar = createStyledButton("Publicar Contenido", COLOR_BOTON);
        JButton btnVerSugerencias = createStyledButton("Ver Sugerencias de Amistad", COLOR_BOTON);
        JButton btnSolicitarAyuda = createStyledButton("Solicitar Ayuda", COLOR_BOTON);
        JButton btnEnviarMensaje = createStyledButton("Enviar Mensaje", COLOR_BOTON);
        JButton btnRutaCorta = createStyledButton("Ver Ruta Corta a Otro Usuario", COLOR_BOTON);
        JButton btnHistorial = createStyledButton("Ver Historial", COLOR_BOTON);
        JButton btnValorarContenido = createStyledButton("Valorar Contenido", COLOR_BOTON);
        JButton btnSalir = createStyledButton("Salir", new Color(200, 200, 200));
        JButton btnVerContenidos = createStyledButton("Ver Contenidos Publicados", COLOR_BOTON);

        JButton btnBuscarPorTema = createStyledButton("Buscar por Tema", COLOR_BOTON);
        btnBuscarPorTema.addActionListener(e -> {
            String tema = JOptionPane.showInputDialog(this, "Ingrese el tema:");
            if (tema != null && !tema.trim().isEmpty()) {
                List<Contenido> resultados = AppContext.contenidoController.buscarPorTema(tema);
                mostrarResultados(resultados);
            }
        });

        JButton btnBuscarPorAutor = createStyledButton("Buscar por Autor", COLOR_BOTON);
        btnBuscarPorAutor.addActionListener(e -> {
            String autor = JOptionPane.showInputDialog(this, "Ingrese el autor:");
            if (autor != null && !autor.trim().isEmpty()) {
                List<Contenido> resultados = AppContext.contenidoController.buscarPorAutor(autor);
                mostrarResultados(resultados);
            }
        });

        JButton btnBuscarPorTipo = createStyledButton("Buscar por Tipo", COLOR_BOTON);
        btnBuscarPorTipo.addActionListener(e -> {
            String tipo = JOptionPane.showInputDialog(this, "Ingrese el tipo:");
            if (tipo != null && !tipo.trim().isEmpty()) {
                List<Contenido> resultados = AppContext.contenidoController.buscarPorTipo(tipo);
                mostrarResultados(resultados);
            }
        });

        btnPublicar.addActionListener(e -> publicarContenido());
        btnVerSugerencias.addActionListener(e -> mostrarSugerencias());
        btnSolicitarAyuda.addActionListener(e -> solicitarAyuda());
        btnEnviarMensaje.addActionListener(e -> enviarMensaje());
        btnRutaCorta.addActionListener(e -> verRutaCorta());
        btnHistorial.addActionListener(e -> mostrarHistorial());
        btnSalir.addActionListener(e -> {
            new MainView().setVisible(true);
            dispose();
        });
        btnVerContenidos.addActionListener(e -> mostrarContenidos());
        btnValorarContenido.addActionListener(e -> valorarContenido());

        buttonPanel.add(btnVerContenidos);
        buttonPanel.add(btnPublicar);
        buttonPanel.add(btnVerSugerencias);
        buttonPanel.add(btnSolicitarAyuda);
        buttonPanel.add(btnEnviarMensaje);
        buttonPanel.add(btnRutaCorta);
        buttonPanel.add(btnHistorial);
        buttonPanel.add(btnValorarContenido);
        buttonPanel.add(btnBuscarPorTema);
        buttonPanel.add(btnBuscarPorAutor);
        buttonPanel.add(btnBuscarPorTipo);
        buttonPanel.add(btnSalir);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    /**
     * Crea un botón estilizado con el color especificado
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(FUENTE_BOTON);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto de hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    /**
     * Crea un panel para un campo del formulario con etiqueta y campo de texto
     */
    private JPanel createFieldPanel(String labelText) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(COLOR_FONDO);

        JLabel label = new JLabel(labelText);
        label.setFont(FUENTE_ETIQUETA);
        label.setForeground(COLOR_TEXTO);

        JPanel fieldPanel = new JPanel(new BorderLayout());
        fieldPanel.setBackground(COLOR_FONDO);

        JTextField textField = new JTextField();
        textField.setFont(FUENTE_CAMPO);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        fieldPanel.add(textField, BorderLayout.CENTER);

        panel.add(label, BorderLayout.NORTH);
        panel.add(fieldPanel, BorderLayout.CENTER);

        return panel;
    }

    private void publicarContenido() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 0, 10));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel tituloPanel = createFieldPanel("Título:");
        JPanel temaPanel = createFieldPanel("Tema:");
        JPanel tipoPanel = createFieldPanel("Tipo:");
        JPanel enlacePanel = createFieldPanel("Enlace:");

        panel.add(tituloPanel);
        panel.add(temaPanel);
        panel.add(tipoPanel);
        panel.add(enlacePanel);

        JTextField txtTitulo = (JTextField) ((JPanel)tituloPanel.getComponent(1)).getComponent(0);
        JTextField txtTema = (JTextField) ((JPanel)temaPanel.getComponent(1)).getComponent(0);
        JTextField txtTipo = (JTextField) ((JPanel)tipoPanel.getComponent(1)).getComponent(0);
        JTextField txtEnlace = (JTextField) ((JPanel)enlacePanel.getComponent(1)).getComponent(0);

        int result = JOptionPane.showConfirmDialog(this, panel, "Nuevo Contenido", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (txtTitulo.getText().trim().isEmpty() || txtTema.getText().trim().isEmpty() ||
                    txtTipo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Por favor complete todos los campos requeridos",
                        "Campos incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Contenido c = contenidoController.publicarContenido(
                    txtTitulo.getText(), usuario.getNombre(), txtTema.getText(),
                    txtTipo.getText(), txtEnlace.getText(), usuario
            );

            JOptionPane.showMessageDialog(this,
                    "Contenido publicado exitosamente: " + c.getTitulo(),
                    "Publicación Exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void mostrarSugerencias() {
        grafoController.agregarUsuario(usuario);  // Esto sigue siendo válido

        model.ListaEnlazada<Usuario> sugerencias = grafoController.sugerirAmigos(usuario.getId());

        if (sugerencias.estaVacia()) {
            JOptionPane.showMessageDialog(this,
                    "No hay sugerencias de amistad disponibles en este momento.",
                    "Sin Sugerencias",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(COLOR_FONDO);

            JLabel titulo = new JLabel("Sugerencias de conexión:");
            titulo.setFont(FUENTE_ETIQUETA);
            titulo.setForeground(COLOR_TEXTO);
            panel.add(titulo, BorderLayout.NORTH);

            JPanel listPanel = new JPanel();
            listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
            listPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            listPanel.setBackground(COLOR_FONDO);

            for (Usuario u : sugerencias) {
                JLabel userLabel = new JLabel("• " + u.getNombre() + " (ID: " + u.getId() + ")");
                userLabel.setFont(FUENTE_CAMPO);
                userLabel.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
                listPanel.add(userLabel);
            }

            JScrollPane scrollPane = new JScrollPane(listPanel);
            scrollPane.setPreferredSize(new Dimension(300, 200));
            panel.add(scrollPane, BorderLayout.CENTER);

            JOptionPane.showMessageDialog(this, panel, "Sugerencias de Amistad",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }


    private void solicitarAyuda() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 10));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel temaPanel = createFieldPanel("Tema de ayuda:");
        JPanel urgenciaPanel = createFieldPanel("Nivel de urgencia (1-10):");

        panel.add(temaPanel);
        panel.add(urgenciaPanel);

        JTextField txtTema = (JTextField) ((JPanel)temaPanel.getComponent(1)).getComponent(0);
        JTextField txtUrgencia = (JTextField) ((JPanel)urgenciaPanel.getComponent(1)).getComponent(0);

        int result = JOptionPane.showConfirmDialog(this, panel, "Solicitar Ayuda",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String tema = txtTema.getText().trim();
            String urgenciaStr = txtUrgencia.getText().trim();

            if (tema.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Por favor indique el tema de ayuda",
                        "Campo requerido",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int nivel = Integer.parseInt(urgenciaStr);
                if (nivel < 1 || nivel > 10) {
                    JOptionPane.showMessageDialog(this,
                            "El nivel de urgencia debe estar entre 1 y 10",
                            "Valor inválido",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                ayudaController.solicitarAyuda(usuario, tema, nivel);
                JOptionPane.showMessageDialog(this,
                        "Solicitud de ayuda registrada con éxito.",
                        "Solicitud Enviada",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "El nivel de urgencia debe ser un número entero",
                        "Formato inválido",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void enviarMensaje() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 10));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel destinoPanel = createFieldPanel("ID del destinatario:");
        JPanel mensajePanel = createFieldPanel("Contenido del mensaje:");

        panel.add(destinoPanel);
        panel.add(mensajePanel);

        JTextField txtDestino = (JTextField) ((JPanel)destinoPanel.getComponent(1)).getComponent(0);
        JTextField txtMensaje = (JTextField) ((JPanel)mensajePanel.getComponent(1)).getComponent(0);

        int result = JOptionPane.showConfirmDialog(this, panel, "Enviar Mensaje",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String idDestino = txtDestino.getText().trim();
            String mensaje = txtMensaje.getText().trim();

            if (idDestino.isEmpty() || mensaje.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Todos los campos son obligatorios",
                        "Campos incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Usuario destino = usuarioController.buscarUsuario(idDestino);
            if (destino == null) {
                JOptionPane.showMessageDialog(this,
                        "No se encontró un usuario con el ID proporcionado",
                        "Usuario no encontrado",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Mensaje nuevo = new Mensaje(usuario, destino, mensaje);
            usuario.enviarMensaje(nuevo);
            destino.recibirMensaje(nuevo);

            JOptionPane.showMessageDialog(this,
            "<html>Mensaje enviado correctamente a <b>" + destino.getNombre() + "</b><br><br>" +
            "<b>Contenido:</b><br>" + mensaje + "</html>",
            "Mensaje Enviado",
            JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void verRutaCorta() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel destinoPanel = createFieldPanel("ID del usuario destino:");
        panel.add(destinoPanel, BorderLayout.CENTER);

        JTextField txtDestino = (JTextField) ((JPanel)destinoPanel.getComponent(1)).getComponent(0);

        int result = JOptionPane.showConfirmDialog(this, panel, "Buscar Ruta",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String idDestino = txtDestino.getText().trim();

            if (idDestino.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Por favor indique el ID del usuario destino",
                        "Campo requerido",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            model.ListaEnlazada<Usuario> camino = grafoController.caminoMasCorto(usuario.getId(), idDestino);

            if (camino == null || camino.estaVacia()) {
                JOptionPane.showMessageDialog(this,
                        "No se encontró un camino hacia el usuario especificado",
                        "Ruta no encontrada",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JPanel resultPanel = new JPanel(new BorderLayout());
                resultPanel.setBackground(COLOR_FONDO);

                JLabel titulo = new JLabel("Camino más corto:");
                titulo.setFont(FUENTE_ETIQUETA);
                titulo.setForeground(COLOR_TEXTO);
                resultPanel.add(titulo, BorderLayout.NORTH);

                JPanel pathPanel = new JPanel();
                pathPanel.setLayout(new BoxLayout(pathPanel, BoxLayout.Y_AXIS));
                pathPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
                pathPanel.setBackground(COLOR_FONDO);

                for (Usuario u : camino) {
                    JLabel userLabel = new JLabel("→ " + u.getNombre() + " (ID: " + u.getId() + ")");
                    userLabel.setFont(FUENTE_CAMPO);
                    userLabel.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
                    pathPanel.add(userLabel);
                }

                JScrollPane scrollPane = new JScrollPane(pathPanel);
                scrollPane.setPreferredSize(new Dimension(350, 200));
                resultPanel.add(scrollPane, BorderLayout.CENTER);

                JOptionPane.showMessageDialog(this, resultPanel, "Ruta Más Corta",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    private void mostrarHistorial() {
        model.NodoLista<String> actual = usuario.getHistorialAcciones().getCabeza();

        if (actual == null) {
            JOptionPane.showMessageDialog(this,
                    "No hay actividades registradas en el historial",
                    "Historial Vacío",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_FONDO);

        JLabel titulo = new JLabel("Historial de Actividades:");
        titulo.setFont(FUENTE_ETIQUETA);
        titulo.setForeground(COLOR_TEXTO);
        panel.add(titulo, BorderLayout.NORTH);

        JPanel historialPanel = new JPanel();
        historialPanel.setLayout(new BoxLayout(historialPanel, BoxLayout.Y_AXIS));
        historialPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        historialPanel.setBackground(COLOR_FONDO);

        while (actual != null) {
            JLabel accionLabel = new JLabel("• " + actual.getDato());
            accionLabel.setFont(FUENTE_CAMPO);
            accionLabel.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
            historialPanel.add(accionLabel);
            actual = actual.getSiguiente();
        }

        JScrollPane scrollPane = new JScrollPane(historialPanel);
        scrollPane.setPreferredSize(new Dimension(400, 250));
        panel.add(scrollPane, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(this, panel, "Historial de Actividades",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void mostrarContenidos() {
        // Obtener todos los contenidos publicados
        ListaEnlazada<Contenido> contenidos = AppContext.contenidoController.obtenerTodosLosContenidos();

        if (contenidos.estaVacia()) {
            JOptionPane.showMessageDialog(this, "No hay contenidos publicados.",
                    "Contenidos Vacíos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Crear un panel para mostrar los contenidos
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        StringBuilder sb = new StringBuilder();
        for (Contenido contenido : contenidos) {
            sb.append("ID: ").append(contenido.getId()).append("\n");
            sb.append("Título: ").append(contenido.getTitulo()).append("\n");
            sb.append("Autor: ").append(contenido.getAutor()).append("\n");
            sb.append("Tema: ").append(contenido.getTema()).append("\n");
            sb.append("Tipo: ").append(contenido.getTipo()).append("\n");
            sb.append("Enlace: ").append(contenido.getEnlace()).append("\n");
            sb.append("Promedio de Valoraciones: ").append(contenido.obtenerPromedio()).append("\n");
            sb.append("--------------------------------------------------\n");
        }

        textArea.setText(sb.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        panel.add(scrollPane, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(this, panel, "Contenidos Publicados",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void valorarContenido() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Campo para ingresar el ID del contenido
        JPanel idPanel = createFieldPanel("ID del contenido a valorar:");
        panel.add(idPanel, BorderLayout.NORTH);

        JTextField txtIdContenido = (JTextField) ((JPanel) idPanel.getComponent(1)).getComponent(0);

        // Mostrar cuadro de diálogo para ingresar el ID del contenido
        int result = JOptionPane.showConfirmDialog(this, panel, "Buscar Contenido",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        String idContenido = txtIdContenido.getText().trim();
        if (idContenido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese el ID del contenido",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Buscar el contenido por ID
            Contenido contenido = AppContext.contenidoController.buscarContenidoPorId(Integer.parseInt(idContenido));
            if (contenido == null) {
                JOptionPane.showMessageDialog(this,
                        "No se encontró un contenido con el ID proporcionado",
                        "Contenido no encontrado", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear un panel para ingresar la valoración
            JPanel valoracionPanel = new JPanel(new BorderLayout(0, 10));
            valoracionPanel.setBackground(COLOR_FONDO);
            valoracionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel lblValoracion = new JLabel("Ingrese una valoración (1-5):");
            lblValoracion.setFont(FUENTE_ETIQUETA);
            lblValoracion.setForeground(COLOR_TEXTO);
            valoracionPanel.add(lblValoracion, BorderLayout.NORTH);

            JTextField txtValoracion = new JTextField();
            valoracionPanel.add(txtValoracion, BorderLayout.CENTER);

            // Mostrar cuadro de diálogo para ingresar la valoración
            result = JOptionPane.showConfirmDialog(this, valoracionPanel, "Valorar Contenido",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION) return;

            String valoracionStr = txtValoracion.getText().trim();
            if (valoracionStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor ingrese una valoración",
                        "Campo requerido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int valoracion = Integer.parseInt(valoracionStr);
            if (valoracion < 1 || valoracion > 5) {
                JOptionPane.showMessageDialog(this, "La valoración debe estar entre 1 y 5",
                        "Valoración inválida", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Registrar la valoración en el contenido
            AppContext.contenidoController.valorarContenido(idContenido, valoracion, usuario);
            JOptionPane.showMessageDialog(this, "Contenido valorado con éxito.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La valoración debe ser un número entre 1 y 5",
                    "Formato inválido", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarResultados(List<Contenido> resultados) {
        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron resultados.",
                    "Búsqueda Vacía", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        StringBuilder sb = new StringBuilder();
        for (Contenido contenido : resultados) {
            sb.append("ID: ").append(contenido.getId()).append("\n");
            sb.append("Título: ").append(contenido.getTitulo()).append("\n");
            sb.append("Autor: ").append(contenido.getAutor()).append("\n");
            sb.append("Tema: ").append(contenido.getTema()).append("\n");
            sb.append("Tipo: ").append(contenido.getTipo()).append("\n");
            sb.append("Enlace: ").append(contenido.getEnlace()).append("\n");
            sb.append("Promedio de Valoraciones: ").append(contenido.obtenerPromedio()).append("\n");
            sb.append("--------------------------------------------------\n");
        }

        textArea.setText(sb.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        panel.add(scrollPane, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(this, panel, "Resultados de Búsqueda",
                JOptionPane.PLAIN_MESSAGE);
    }
}