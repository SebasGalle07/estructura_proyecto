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
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

public class PanelEstudianteView extends JFrame {
    // Paleta de colores moderna
    private static final Color COLOR_PRIMARIO = new Color(79, 70, 229); // Índigo
    private static final Color COLOR_SECUNDARIO = new Color(139, 92, 246); // Violeta
    private static final Color COLOR_ACENTO = new Color(236, 72, 153); // Rosa
    private static final Color COLOR_FONDO = new Color(15, 23, 42); // Azul muy oscuro
    private static final Color COLOR_FONDO_CLARO = new Color(30, 41, 59); // Azul oscuro
    private static final Color COLOR_TARJETA = new Color(51, 65, 85); // Gris azulado
    private static final Color COLOR_TEXTO = new Color(248, 250, 252); // Blanco hueso
    private static final Color COLOR_TEXTO_SECUNDARIO = new Color(148, 163, 184); // Gris claro
    private static final Color COLOR_EXITO = new Color(34, 197, 94); // Verde
    private static final Color COLOR_ADVERTENCIA = new Color(251, 191, 36); // Amarillo
    private static final Color COLOR_ERROR = new Color(239, 68, 68); // Rojo

    // Fuentes modernas
    private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.PLAIN, 16);
    private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FUENTE_ETIQUETA = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FUENTE_CAMPO = new Font("Segoe UI", Font.PLAIN, 14);

    private Usuario usuario;
    private UsuarioController usuarioController;
    private ContenidoController contenidoController;
    private GrafoController grafoController;
    private AyudaController ayudaController;
    private Timer animationTimer;

    public PanelEstudianteView(Usuario usuario) {
        this.usuario = usuario;
        this.usuarioController = AppContext.usuarioController;
        this.contenidoController = AppContext.contenidoController;
        this.grafoController = AppContext.grafoController;
        this.ayudaController = AppContext.ayudaController;

        initializeFrame();
        createUI();
        startAnimations();
    }

    private void initializeFrame() {
        setTitle("EduConnect - Panel de Estudiante");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setMinimumSize(new Dimension(900, 600));

        // Fondo con gradiente
        setContentPane(new GradientPanel());
    }

    private void createUI() {
        setLayout(new BorderLayout());

        // Header con información del usuario
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Panel principal con las opciones
        JPanel mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.CENTER);

        // Footer con información adicional
        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 40));

        // Panel izquierdo con saludo
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);

        JLabel welcomeLabel = new JLabel("¡Bienvenido de vuelta!");
        welcomeLabel.setFont(FUENTE_SUBTITULO);
        welcomeLabel.setForeground(COLOR_TEXTO_SECUNDARIO);

        JLabel nameLabel = new JLabel(usuario.getNombre());
        nameLabel.setFont(FUENTE_TITULO);
        nameLabel.setForeground(COLOR_TEXTO);

        leftPanel.add(welcomeLabel, BorderLayout.NORTH);
        leftPanel.add(nameLabel, BorderLayout.CENTER);

        // Panel derecho con avatar y estadísticas
        JPanel rightPanel = createUserStatsPanel();

        header.add(leftPanel, BorderLayout.WEST);
        header.add(rightPanel, BorderLayout.EAST);

        return header;
    }

    private JPanel createUserStatsPanel() {
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        statsPanel.setOpaque(false);

        // Avatar circular
        JPanel avatarPanel = createAvatarPanel();
        statsPanel.add(avatarPanel);

        return statsPanel;
    }

    private JPanel createAvatarPanel() {
        JPanel avatarContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Círculo con gradiente
                GradientPaint gradient = new GradientPaint(0, 0, COLOR_PRIMARIO,
                        getWidth(), getHeight(), COLOR_SECUNDARIO);
                g2d.setPaint(gradient);
                g2d.fillOval(5, 5, getWidth()-10, getHeight()-10);

                // Letra inicial
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 24));
                FontMetrics fm = g2d.getFontMetrics();
                String initial = usuario.getNombre().substring(0, 1).toUpperCase();
                int x = (getWidth() - fm.stringWidth(initial)) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 2;
                g2d.drawString(initial, x, y);

                g2d.dispose();
            }
        };
        avatarContainer.setPreferredSize(new Dimension(60, 60));
        avatarContainer.setOpaque(false);
        return avatarContainer;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Grid de tarjetas de funcionalidades
        JPanel cardsPanel = new JPanel(new GridLayout(3, 3, 20, 20));
        cardsPanel.setOpaque(false);

        // Crear tarjetas para cada funcionalidad
        cardsPanel.add(createFeatureCard("📚", "Ver Contenidos", "Explora todo el contenido disponible",
                COLOR_PRIMARIO, e -> mostrarContenidos()));
        cardsPanel.add(createFeatureCard("✏️", "Publicar", "Comparte tu conocimiento",
                COLOR_SECUNDARIO, e -> publicarContenido()));
        cardsPanel.add(createFeatureCard("👥", "Sugerencias", "Descubre nuevas conexiones",
                COLOR_ACENTO, e -> mostrarSugerencias()));
        cardsPanel.add(createFeatureCard("🆘", "Solicitar Ayuda", "Obtén ayuda de la comunidad",
                COLOR_ADVERTENCIA, e -> solicitarAyuda()));
        cardsPanel.add(createFeatureCard("💬", "Mensajes", "Comunícate con otros usuarios",
                COLOR_EXITO, e -> enviarMensaje()));
        cardsPanel.add(createFeatureCard("🗺️", "Rutas", "Encuentra el camino más corto",
                COLOR_PRIMARIO, e -> verRutaCorta()));
        cardsPanel.add(createFeatureCard("📋", "Historial", "Revisa tu actividad",
                COLOR_SECUNDARIO, e -> mostrarHistorial()));
        cardsPanel.add(createFeatureCard("⭐", "Valorar", "Califica el contenido",
                COLOR_ACENTO, e -> valorarContenido()));
        cardsPanel.add(createFeatureCard("🚪", "Salir", "Cerrar sesión",
                COLOR_ERROR, e -> {
                    new MainView().setVisible(true);
                    dispose();
                }));
        cardsPanel.add(createFeatureCard("🤝", "Grupos de Estudio", "Únete a grupos con intereses comunes",
                COLOR_SECUNDARIO, e -> mostrarGruposDeEstudio()));
        cardsPanel.add(createFeatureCard("🔍", "Buscar Contenidos", "Filtra por tema, autor o tipo", COLOR_PRIMARIO, e -> buscarContenidos()));

        mainPanel.add(cardsPanel, BorderLayout.CENTER);
        return mainPanel;
    }
    private void mostrarGruposDeEstudio() {
        List<Usuario> todos = AppContext.usuarioController.getTodos().toList();
        List<String> temas = usuario.getIntereses().toList();
        List<model.GrupoEstudio> grupos = AppContext.grupoEstudioController.agruparPorInteres(todos, temas);

        if (grupos == null || grupos.isEmpty()) {
            mostrarMensajeModerno("Grupos de Estudio", "No se encontraron grupos disponibles con tus intereses.", COLOR_ADVERTENCIA);
            return;
        }

        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        JLabel titulo = new JLabel("🤝 Grupos Sugeridos");
        titulo.setFont(FUENTE_TITULO);
        titulo.setForeground(COLOR_TEXTO);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titulo, BorderLayout.NORTH);

        JPanel gruposPanel = new JPanel();
        gruposPanel.setLayout(new BoxLayout(gruposPanel, BoxLayout.Y_AXIS));
        gruposPanel.setOpaque(false);

        for (model.GrupoEstudio grupo : grupos) {
            // ✅ Conectar a todos los integrantes entre sí
            List<Usuario> integrantes = grupo.getIntegrantes().toList();
            for (int i = 0; i < integrantes.size(); i++) {
                for (int j = i + 1; j < integrantes.size(); j++) {
                    Usuario a = integrantes.get(i);
                    Usuario b = integrantes.get(j);
                    if (!a.getConexiones().contiene(b)) {
                        AppContext.grafoController.agregarUsuario(a);
                        AppContext.grafoController.agregarUsuario(b);
                        AppContext.grafoController.conectarUsuarios(a, b);
                    }
                }
            }

            JPanel grupoCard = new JPanel(new BorderLayout(0, 10)) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new Color(0, 0, 0, 30));
                    g2d.fillRoundRect(3, 3, getWidth()-3, getHeight()-3, 15, 15);
                    g2d.setColor(COLOR_FONDO_CLARO);
                    g2d.fillRoundRect(0, 0, getWidth()-3, getHeight()-3, 15, 15);
                    g2d.setColor(COLOR_PRIMARIO);
                    g2d.setStroke(new BasicStroke(1f));
                    g2d.drawRoundRect(1, 1, getWidth()-5, getHeight()-5, 15, 15);
                    g2d.dispose();
                }
            };
            grupoCard.setOpaque(false);
            grupoCard.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

            JLabel lblTema = new JLabel("📚 Tema: " + grupo.getTema());
            lblTema.setFont(new Font("Segoe UI", Font.BOLD, 16));
            lblTema.setForeground(COLOR_TEXTO);

            JPanel integrantesPanel = new JPanel();
            integrantesPanel.setLayout(new BoxLayout(integrantesPanel, BoxLayout.Y_AXIS));
            integrantesPanel.setOpaque(false);
            integrantesPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

            for (Usuario integrante : integrantes) {
                JLabel miembro = new JLabel("👤 " + integrante.getNombre() + " (ID: " + integrante.getId() + ")");
                miembro.setFont(FUENTE_CAMPO);
                miembro.setForeground(COLOR_TEXTO_SECUNDARIO);
                integrantesPanel.add(miembro);
            }

            grupoCard.add(lblTema, BorderLayout.NORTH);
            grupoCard.add(integrantesPanel, BorderLayout.CENTER);

            gruposPanel.add(grupoCard);
            gruposPanel.add(Box.createVerticalStrut(15));
        }

        JScrollPane scrollPane = new JScrollPane(gruposPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnCerrar = createModernButton("Cerrar", COLOR_PRIMARIO);
        btnCerrar.addActionListener(e -> SwingUtilities.getWindowAncestor(panel).dispose());
        panel.add(btnCerrar, BorderLayout.SOUTH);

        createModernDialog("Grupos de Estudio", panel).setVisible(true);
    }

    private JPanel createFeatureCard(String icon, String title, String description, Color accentColor, ActionListener action) {
        JPanel card = new JPanel() {
            private boolean hovered = false;
            private float hoverProgress = 0f;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Sombra
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRoundRect(3, 3, getWidth()-3, getHeight()-3, 20, 20);

                // Fondo con gradiente
                Color baseColor = COLOR_TARJETA;
                Color hoverColor = new Color(
                        Math.min(255, baseColor.getRed() + (int)(20 * hoverProgress)),
                        Math.min(255, baseColor.getGreen() + (int)(20 * hoverProgress)),
                        Math.min(255, baseColor.getBlue() + (int)(20 * hoverProgress))
                );

                GradientPaint gradient = new GradientPaint(0, 0, hoverColor,
                        0, getHeight(), baseColor);
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth()-3, getHeight()-3, 20, 20);

                // Borde de acento
                g2d.setColor(accentColor);
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawRoundRect(1, 1, getWidth()-5, getHeight()-5, 20, 20);

                g2d.dispose();
                super.paintComponent(g);
            }
        };

        card.setLayout(new BorderLayout());
        card.setOpaque(false);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Contenido de la tarjeta
        JPanel contentPanel = new JPanel(new BorderLayout(0, 10));
        contentPanel.setOpaque(false);

        // Icono
        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        contentPanel.add(iconLabel, BorderLayout.NORTH);

        // Título
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(FUENTE_BOTON);
        titleLabel.setForeground(COLOR_TEXTO);
        contentPanel.add(titleLabel, BorderLayout.CENTER);

        // Descripción
        JLabel descLabel = new JLabel("<html><center>" + description + "</center></html>", SwingConstants.CENTER);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        descLabel.setForeground(COLOR_TEXTO_SECUNDARIO);
        contentPanel.add(descLabel, BorderLayout.SOUTH);

        card.add(contentPanel, BorderLayout.CENTER);

        // Efectos de hover
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                animateHover(card, true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                animateHover(card, false);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                action.actionPerformed(new ActionEvent(card, ActionEvent.ACTION_PERFORMED, ""));
            }
        });

        return card;
    }

    private void animateHover(JPanel card, boolean entering) {
        Timer hoverTimer = new Timer(10, null);
        hoverTimer.addActionListener(e -> {
            // Esta animación se puede implementar con más detalle si se necesita
            card.repaint();
            hoverTimer.stop();
        });
        hoverTimer.start();
    }

    private JPanel createFooterPanel() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setOpaque(false);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        JLabel footerLabel = new JLabel("EduConnect © 2025 - Plataforma Educativa Inteligente");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLabel.setForeground(COLOR_TEXTO_SECUNDARIO);
        footer.add(footerLabel);

        return footer;
    }

    private void startAnimations() {
        // Timer para animaciones sutiles
        animationTimer = new Timer(50, e -> {
            // Aquí se pueden agregar animaciones adicionales
            repaint();
        });
        animationTimer.start();
    }

    // Clase para el fondo con gradiente
    private class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Gradiente de fondo
            GradientPaint gradient = new GradientPaint(
                    0, 0, COLOR_FONDO,
                    getWidth(), getHeight(), COLOR_FONDO_CLARO
            );
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // Método para crear diálogos modernos
    private JDialog createModernDialog(String title, JPanel content) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setUndecorated(true);
        dialog.setBackground(new Color(0, 0, 0, 0));

        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Sombra
                g2d.setColor(new Color(0, 0, 0, 100));
                g2d.fillRoundRect(5, 5, getWidth()-5, getHeight()-5, 20, 20);

                // Fondo
                g2d.setColor(COLOR_TARJETA);
                g2d.fillRoundRect(0, 0, getWidth()-5, getHeight()-5, 20, 20);

                g2d.dispose();
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(content, BorderLayout.CENTER);

        dialog.add(mainPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        return dialog;
    }

    private JTextField createModernTextField(String placeholder) {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fondo
                g2d.setColor(COLOR_FONDO_CLARO);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                // Borde
                g2d.setColor(COLOR_PRIMARIO);
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 10, 10);

                g2d.dispose();
                super.paintComponent(g);
            }
        };

        field.setOpaque(false);
        field.setForeground(COLOR_TEXTO);
        field.setCaretColor(COLOR_TEXTO);
        field.setFont(FUENTE_CAMPO);
        field.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        return field;
    }

    private JButton createModernButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Sombra
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRoundRect(2, 2, getWidth()-2, getHeight()-2, 12, 12);

                // Fondo con gradiente
                GradientPaint gradient = new GradientPaint(0, 0, bgColor,
                        0, getHeight(), bgColor.darker());
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth()-2, getHeight()-2, 12, 12);

                g2d.dispose();
                super.paintComponent(g);
            }
        };

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setFont(FUENTE_BOTON);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));

        return button;
    }

    // Métodos de funcionalidad (mantener la lógica original pero con diálogos modernos)
    private void publicarContenido() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        JLabel titulo = new JLabel("📄 Publicar Nuevo Contenido");
        titulo.setFont(FUENTE_TITULO);
        titulo.setForeground(COLOR_TEXTO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(20));

        // Campos con etiquetas
        JTextField txtTitulo = createModernTextField("");
        JTextField txtTema = createModernTextField("");
        JTextField txtTipo = createModernTextField("");
        JTextField txtEnlace = createModernTextField("");

        panel.add(crearCampoConEtiqueta("Título del contenido:", txtTitulo));
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearCampoConEtiqueta("Tema principal:", txtTema));
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearCampoConEtiqueta("Tipo de contenido:", txtTipo));
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearCampoConEtiqueta("Enlace (opcional):", txtEnlace));
        panel.add(Box.createVerticalStrut(20));

        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        JButton btnPublicar = createModernButton("Publicar", COLOR_EXITO);
        JButton btnCancelar = createModernButton("Cancelar", COLOR_ERROR);

        btnPublicar.addActionListener(e -> {
            if (txtTitulo.getText().trim().isEmpty() ||
                    txtTema.getText().trim().isEmpty() ||
                    txtTipo.getText().trim().isEmpty()) {
                mostrarMensajeModerno("Error", "Por favor complete todos los campos requeridos", COLOR_ERROR);
                return;
            }

            Contenido c = contenidoController.publicarContenido(
                    txtTitulo.getText(), usuario.getNombre(), txtTema.getText(),
                    txtTipo.getText(), txtEnlace.getText(), usuario
            );

            mostrarMensajeModerno("Éxito", "Contenido publicado: " + c.getTitulo(), COLOR_EXITO);
            SwingUtilities.getWindowAncestor(panel).dispose();
        });

        btnCancelar.addActionListener(e -> SwingUtilities.getWindowAncestor(panel).dispose());

        buttonPanel.add(btnPublicar);
        buttonPanel.add(btnCancelar);
        panel.add(buttonPanel);

        createModernDialog("Publicar Contenido", panel).setVisible(true);
    }
    private JPanel crearCampoConEtiqueta(String texto, JComponent componente) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 5));
        panel.setOpaque(false);

        JLabel label = new JLabel(texto);
        label.setFont(FUENTE_ETIQUETA);
        label.setForeground(COLOR_TEXTO);

        panel.add(label, BorderLayout.NORTH);
        panel.add(componente, BorderLayout.CENTER);

        return panel;
    }

    private void mostrarMensajeModerno(String titulo, String mensaje, Color color) {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(color);

        JLabel lblMensaje = new JLabel("<html><center>" + mensaje + "</center></html>", SwingConstants.CENTER);
        lblMensaje.setFont(FUENTE_ETIQUETA);
        lblMensaje.setForeground(COLOR_TEXTO);

        JButton btnOk = createModernButton("Entendido", COLOR_PRIMARIO);
        btnOk.addActionListener(e -> SwingUtilities.getWindowAncestor(panel).dispose());

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(lblMensaje, BorderLayout.CENTER);
        panel.add(btnOk, BorderLayout.SOUTH);

        createModernDialog(titulo, panel).setVisible(true);
    }

    // Los demás métodos siguen la misma lógica pero con la nueva estética
    // Por brevedad, incluiré solo algunos ejemplos más:

    private void mostrarSugerencias() {
        grafoController.agregarUsuario(usuario);
        model.ListaEnlazada<Usuario> sugerencias = grafoController.sugerirAmigos(usuario.getId());

        if (sugerencias.estaVacia()) {
            mostrarMensajeModerno("Sin Sugerencias",
                    "No hay sugerencias de amistad disponibles en este momento.", COLOR_ADVERTENCIA);
        } else {
            JPanel panel = new JPanel(new BorderLayout(0, 15));
            panel.setOpaque(false);

            JLabel titulo = new JLabel("Sugerencias de Conexión");
            titulo.setFont(FUENTE_TITULO);
            titulo.setForeground(COLOR_TEXTO);
            titulo.setHorizontalAlignment(SwingConstants.CENTER);

            JPanel listPanel = new JPanel();
            listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
            listPanel.setOpaque(false);

            for (Usuario u : sugerencias) {
                JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                userPanel.setOpaque(false);
                userPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                JLabel userLabel = new JLabel("👤 " + u.getNombre() + " (ID: " + u.getId() + ")");
                userLabel.setFont(FUENTE_CAMPO);
                userLabel.setForeground(COLOR_TEXTO);
                userPanel.add(userLabel);

                listPanel.add(userPanel);
            }

            JScrollPane scrollPane = new JScrollPane(listPanel);
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);
            scrollPane.setBorder(null);
            scrollPane.setPreferredSize(new Dimension(400, 200));

            panel.add(titulo, BorderLayout.NORTH);
            panel.add(scrollPane, BorderLayout.CENTER);

            JButton btnCerrar = createModernButton("Cerrar", COLOR_PRIMARIO);
            btnCerrar.addActionListener(e -> SwingUtilities.getWindowAncestor(panel).dispose());
            panel.add(btnCerrar, BorderLayout.SOUTH);

            createModernDialog("Sugerencias de Amistad", panel).setVisible(true);
        }
    }

    private void solicitarAyuda() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        JLabel titulo = new JLabel("🆘 Solicitar Ayuda");
        titulo.setFont(FUENTE_TITULO);
        titulo.setForeground(COLOR_TEXTO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(20));

        JTextField txtTema = createModernTextField("");
        JTextField txtUrgencia = createModernTextField("");

        panel.add(crearCampoConEtiqueta("Tema de ayuda:", txtTema));
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearCampoConEtiqueta("Nivel de urgencia (1-10):", txtUrgencia));
        panel.add(Box.createVerticalStrut(20));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        JButton btnSolicitar = createModernButton("Solicitar", COLOR_ADVERTENCIA);
        JButton btnCancelar = createModernButton("Cancelar", COLOR_ERROR);

        btnSolicitar.addActionListener(e -> {
            String tema = txtTema.getText().trim();
            String urgenciaStr = txtUrgencia.getText().trim();

            if (tema.isEmpty()) {
                mostrarMensajeModerno("Error", "Por favor indique el tema de ayuda", COLOR_ERROR);
                return;
            }

            try {
                int nivel = Integer.parseInt(urgenciaStr);
                if (nivel < 1 || nivel > 10) {
                    mostrarMensajeModerno("Error", "El nivel de urgencia debe estar entre 1 y 10", COLOR_ERROR);
                    return;
                }

                ayudaController.solicitarAyuda(usuario, tema, nivel);
                mostrarMensajeModerno("Éxito", "Solicitud de ayuda registrada con éxito", COLOR_EXITO);
                SwingUtilities.getWindowAncestor(panel).dispose();
            } catch (NumberFormatException ex) {
                mostrarMensajeModerno("Error", "El nivel de urgencia debe ser un número entero", COLOR_ERROR);
            }
        });

        btnCancelar.addActionListener(e -> SwingUtilities.getWindowAncestor(panel).dispose());

        buttonPanel.add(btnSolicitar);
        buttonPanel.add(btnCancelar);
        panel.add(buttonPanel);

        createModernDialog("Solicitar Ayuda", panel).setVisible(true);
    }



    private void enviarMensaje() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        JLabel titulo = new JLabel("💬 Enviar Mensaje");
        titulo.setFont(FUENTE_TITULO);
        titulo.setForeground(COLOR_TEXTO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(20));

        JTextField txtDestino = createModernTextField("");
        JTextField txtMensaje = createModernTextField("");

        panel.add(crearCampoConEtiqueta("ID del destinatario:", txtDestino));
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearCampoConEtiqueta("Contenido del mensaje:", txtMensaje));
        panel.add(Box.createVerticalStrut(20));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        JButton btnEnviar = createModernButton("Enviar", COLOR_EXITO);
        JButton btnCancelar = createModernButton("Cancelar", COLOR_ERROR);

        btnEnviar.addActionListener(e -> {
            String idDestino = txtDestino.getText().trim();
            String mensaje = txtMensaje.getText().trim();

            if (idDestino.isEmpty() || mensaje.isEmpty()) {
                mostrarMensajeModerno("Error", "Todos los campos son obligatorios", COLOR_ERROR);
                return;
            }

            Usuario destino = usuarioController.buscarUsuario(idDestino);
            if (destino == null) {
                mostrarMensajeModerno("Error", "No se encontró un usuario con el ID proporcionado", COLOR_ERROR);
                return;
            }

            Mensaje nuevo = new Mensaje(usuario, destino, mensaje);
            usuario.enviarMensaje(nuevo);
            destino.recibirMensaje(nuevo);

            // Conectar en el grafo si son usuarios distintos
            if (!usuario.getId().equals(destino.getId())) {
                grafoController.agregarUsuario(usuario);
                grafoController.agregarUsuario(destino);
                grafoController.conectarUsuarios(usuario, destino);
            }

            mostrarMensajeModerno("Éxito", "Mensaje enviado correctamente a " + destino.getNombre(), COLOR_EXITO);
            SwingUtilities.getWindowAncestor(panel).dispose();
        });

        btnCancelar.addActionListener(e -> SwingUtilities.getWindowAncestor(panel).dispose());

        buttonPanel.add(btnEnviar);
        buttonPanel.add(btnCancelar);
        panel.add(buttonPanel);

        createModernDialog("Enviar Mensaje", panel).setVisible(true);
    }


    private void verRutaCorta() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        JLabel titulo = new JLabel("🗺️ Buscar Ruta Más Corta");
        titulo.setFont(FUENTE_TITULO);
        titulo.setForeground(COLOR_TEXTO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(20));

        JTextField txtDestino = createModernTextField("");
        panel.add(crearCampoConEtiqueta("ID del usuario destino:", txtDestino));
        panel.add(Box.createVerticalStrut(20));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        JButton btnBuscar = createModernButton("Buscar Ruta", COLOR_PRIMARIO);
        JButton btnCancelar = createModernButton("Cancelar", COLOR_ERROR);

        btnBuscar.addActionListener(e -> {
            String idDestino = txtDestino.getText().trim();

            if (idDestino.isEmpty()) {
                mostrarMensajeModerno("Error", "Por favor indique el ID del usuario destino", COLOR_ERROR);
                return;
            }

            model.ListaEnlazada<Usuario> camino = grafoController.caminoMasCorto(usuario.getId(), idDestino);

            if (camino == null || camino.estaVacia()) {
                mostrarMensajeModerno("Sin Ruta", "No se encontró un camino hacia el usuario especificado", COLOR_ADVERTENCIA);
            } else {
                mostrarRutaEncontrada(camino);
            }
            SwingUtilities.getWindowAncestor(panel).dispose();
        });

        btnCancelar.addActionListener(e -> SwingUtilities.getWindowAncestor(panel).dispose());

        buttonPanel.add(btnBuscar);
        buttonPanel.add(btnCancelar);
        panel.add(buttonPanel);

        createModernDialog("Buscar Ruta", panel).setVisible(true);
    }


    private void mostrarRutaEncontrada(model.ListaEnlazada<Usuario> camino) {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setOpaque(false);

        JLabel titulo = new JLabel("🗺️ Camino Más Corto Encontrado");
        titulo.setFont(FUENTE_TITULO);
        titulo.setForeground(COLOR_EXITO);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel pathPanel = new JPanel();
        pathPanel.setLayout(new BoxLayout(pathPanel, BoxLayout.Y_AXIS));
        pathPanel.setOpaque(false);

        int paso = 1;
        for (Usuario u : camino) {
            JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            userPanel.setOpaque(false);
            userPanel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

            String icon = paso == 1 ? "🏁" : (paso == camino.tamano() ? "🎯" : "📍");
            JLabel userLabel = new JLabel(icon + " " + u.getNombre() + " (ID: " + u.getId() + ")");
            userLabel.setFont(FUENTE_CAMPO);
            userLabel.setForeground(COLOR_TEXTO);

            if (paso < camino.tamano()) {
                JLabel arrow = new JLabel("     ↓");
                arrow.setForeground(COLOR_PRIMARIO);
                arrow.setFont(new Font("Segoe UI", Font.BOLD, 16));
                userPanel.add(userLabel);
                pathPanel.add(userPanel);
                pathPanel.add(arrow);
            } else {
                userPanel.add(userLabel);
                pathPanel.add(userPanel);
            }
            paso++;
        }

        JScrollPane scrollPane = new JScrollPane(pathPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(400, 250));

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnCerrar = createModernButton("Cerrar", COLOR_PRIMARIO);
        btnCerrar.addActionListener(e -> SwingUtilities.getWindowAncestor(panel).dispose());
        panel.add(btnCerrar, BorderLayout.SOUTH);

        createModernDialog("Ruta Encontrada", panel).setVisible(true);
    }

    private void mostrarHistorial() {
        model.NodoLista<String> actual = usuario.getHistorialAcciones().getCabeza();

        if (actual == null) {
            mostrarMensajeModerno("Historial Vacío",
                    "No hay actividades registradas en el historial", COLOR_ADVERTENCIA);
            return;
        }

        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setOpaque(false);

        JLabel titulo = new JLabel("📋 Historial de Actividades");
        titulo.setFont(FUENTE_TITULO);
        titulo.setForeground(COLOR_TEXTO);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel historialPanel = new JPanel();
        historialPanel.setLayout(new BoxLayout(historialPanel, BoxLayout.Y_AXIS));
        historialPanel.setOpaque(false);

        int contador = 1;
        while (actual != null) {
            JPanel activityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            activityPanel.setOpaque(false);
            activityPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            JLabel accionLabel = new JLabel(contador + ". " + actual.getDato());
            accionLabel.setFont(FUENTE_CAMPO);
            accionLabel.setForeground(COLOR_TEXTO);
            activityPanel.add(accionLabel);

            historialPanel.add(activityPanel);
            actual = actual.getSiguiente();
            contador++;
        }

        JScrollPane scrollPane = new JScrollPane(historialPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnCerrar = createModernButton("Cerrar", COLOR_PRIMARIO);
        btnCerrar.addActionListener(e -> SwingUtilities.getWindowAncestor(panel).dispose());
        panel.add(btnCerrar, BorderLayout.SOUTH);

        createModernDialog("Historial de Actividades", panel).setVisible(true);
    }

    private void mostrarContenidos() {
        ListaEnlazada<Contenido> contenidos = AppContext.contenidoController.obtenerTodosLosContenidos();

        if (contenidos.estaVacia()) {
            mostrarMensajeModerno("Sin Contenidos", "No hay contenidos publicados.", COLOR_ADVERTENCIA);
            return;
        }

        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setOpaque(false);

        JLabel titulo = new JLabel("📚 Contenidos Publicados");
        titulo.setFont(FUENTE_TITULO);
        titulo.setForeground(COLOR_TEXTO);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel contenidosPanel = new JPanel();
        contenidosPanel.setLayout(new BoxLayout(contenidosPanel, BoxLayout.Y_AXIS));
        contenidosPanel.setOpaque(false);

        for (Contenido contenido : contenidos) {
            JPanel contentCard = createContentCard(contenido);
            contenidosPanel.add(contentCard);
            contenidosPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(contenidosPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnCerrar = createModernButton("Cerrar", COLOR_PRIMARIO);
        btnCerrar.addActionListener(e -> SwingUtilities.getWindowAncestor(panel).dispose());
        panel.add(btnCerrar, BorderLayout.SOUTH);

        createModernDialog("Contenidos Publicados", panel).setVisible(true);
    }

    private JPanel createContentCard(Contenido contenido) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Sombra
                g2d.setColor(new Color(0, 0, 0, 30));
                g2d.fillRoundRect(3, 3, getWidth()-3, getHeight()-3, 15, 15);

                // Fondo
                g2d.setColor(COLOR_FONDO_CLARO);
                g2d.fillRoundRect(0, 0, getWidth()-3, getHeight()-3, 15, 15);

                // Borde
                g2d.setColor(COLOR_PRIMARIO);
                g2d.setStroke(new BasicStroke(1f));
                g2d.drawRoundRect(1, 1, getWidth()-5, getHeight()-5, 15, 15);

                g2d.dispose();
            }
        };

        card.setLayout(new BorderLayout());
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Información del contenido
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel tituloLabel = new JLabel("📄 " + contenido.getTitulo());
        tituloLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tituloLabel.setForeground(COLOR_TEXTO);

        JLabel autorLabel = new JLabel("👤 Autor: " + contenido.getAutor());
        autorLabel.setFont(FUENTE_CAMPO);
        autorLabel.setForeground(COLOR_TEXTO_SECUNDARIO);

        JLabel temaLabel = new JLabel("🏷️ Tema: " + contenido.getTema());
        temaLabel.setFont(FUENTE_CAMPO);
        temaLabel.setForeground(COLOR_TEXTO_SECUNDARIO);

        JLabel tipoLabel = new JLabel("📋 Tipo: " + contenido.getTipo());
        tipoLabel.setFont(FUENTE_CAMPO);
        tipoLabel.setForeground(COLOR_TEXTO_SECUNDARIO);

        String enlaceTexto = contenido.getEnlace().isEmpty() ? "Sin enlace" : contenido.getEnlace();
        JLabel enlaceLabel = new JLabel("🔗 Enlace: " + enlaceTexto);
        enlaceLabel.setFont(FUENTE_CAMPO);
        enlaceLabel.setForeground(COLOR_TEXTO_SECUNDARIO);

        JLabel valoracionLabel = new JLabel("⭐ Valoración: " + String.format("%.1f", contenido.obtenerPromedio()));
        valoracionLabel.setFont(FUENTE_CAMPO);
        valoracionLabel.setForeground(COLOR_ACENTO);

        infoPanel.add(tituloLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(autorLabel);
        infoPanel.add(temaLabel);
        infoPanel.add(tipoLabel);
        infoPanel.add(enlaceLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(valoracionLabel);

        card.add(infoPanel, BorderLayout.CENTER);
        return card;
    }
    private void valorarContenido() {
        ListaEnlazada<Contenido> contenidos = AppContext.contenidoController.obtenerTodosLosContenidos();

        if (contenidos.estaVacia()) {
            mostrarMensajeModerno("Información", "No hay contenidos disponibles para valorar.", COLOR_ADVERTENCIA);
            return;
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        JLabel titulo = new JLabel("⭐ Valorar Contenido");
        titulo.setFont(FUENTE_TITULO);
        titulo.setForeground(COLOR_TEXTO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(20));

        String[] opciones = new String[contenidos.tamano()];
        int i = 0;
        for (Contenido c : contenidos) {
            opciones[i++] = c.getTitulo() + " - por " + c.getAutor();
        }

        JComboBox<String> comboContenidos = new JComboBox<>(opciones);
        comboContenidos.setFont(FUENTE_CAMPO);
        comboContenidos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        comboContenidos.setBackground(COLOR_FONDO_CLARO);
        comboContenidos.setForeground(COLOR_TEXTO);
        comboContenidos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 221, 225)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        JTextField txtValoracion = createModernTextField("");

        panel.add(crearCampoConEtiqueta("Seleccione el contenido:", comboContenidos));
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearCampoConEtiqueta("Valoración (1-5):", txtValoracion));
        panel.add(Box.createVerticalStrut(20));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        JButton btnValorar = createModernButton("Valorar", COLOR_ACENTO);
        JButton btnCancelar = createModernButton("Cancelar", COLOR_ERROR);

        btnValorar.addActionListener(e -> {
            String valoracionStr = txtValoracion.getText().trim();
            if (valoracionStr.isEmpty()) {
                mostrarMensajeModerno("Error", "Ingrese una valoración del 1 al 5", COLOR_ERROR);
                return;
            }

            try {
                int valoracion = Integer.parseInt(valoracionStr);
                if (valoracion < 1 || valoracion > 5) {
                    mostrarMensajeModerno("Error", "La valoración debe estar entre 1 y 5", COLOR_ERROR);
                    return;
                }

                Contenido seleccionado = contenidos.get(comboContenidos.getSelectedIndex());

                AppContext.contenidoController.valorarContenido(
                        String.valueOf(seleccionado.getId()),
                        valoracion,
                        usuario
                );

                // ✅ Conectar con los demás usuarios que ya valoraron este contenido
                ListaEnlazada<Usuario> todos = AppContext.usuarioController.getTodos();
                for (Usuario otro : todos) {
                    if (!otro.getId().equals(usuario.getId()) &&
                            otro.getContenidosPublicados().contiene(seleccionado)) {
                        AppContext.grafoController.agregarUsuario(usuario);
                        AppContext.grafoController.agregarUsuario(otro);
                        AppContext.grafoController.conectarUsuarios(usuario, otro);
                    }
                }

                mostrarMensajeModerno("Éxito", "Valoración registrada con éxito", COLOR_EXITO);
                SwingUtilities.getWindowAncestor(panel).dispose();

            } catch (NumberFormatException ex) {
                mostrarMensajeModerno("Error", "Por favor ingrese un número válido", COLOR_ERROR);
            } catch (Exception ex) {
                mostrarMensajeModerno("Error", "Error: " + ex.getMessage(), COLOR_ERROR);
            }
        });

        btnCancelar.addActionListener(e -> SwingUtilities.getWindowAncestor(panel).dispose());

        buttonPanel.add(btnValorar);
        buttonPanel.add(btnCancelar);
        panel.add(buttonPanel);

        createModernDialog("Valorar Contenido", panel).setVisible(true);
    }
    private void buscarContenidos() {
        ListaEnlazada<Contenido> contenidos = AppContext.contenidoController.obtenerTodosLosContenidos();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        JLabel titulo = new JLabel("🔍 Buscar Contenidos");
        titulo.setFont(FUENTE_TITULO);
        titulo.setForeground(COLOR_TEXTO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(20));

        JTextField txtBuscar = createModernTextField("");
        String[] opciones = {"Tema", "Autor", "Tipo"};
        JComboBox<String> filtro = new JComboBox<>(opciones);
        filtro.setFont(FUENTE_CAMPO);
        filtro.setMaximumSize(new Dimension(200, 40));
        filtro.setBackground(COLOR_FONDO_CLARO);
        filtro.setForeground(COLOR_TEXTO);

        panel.add(crearCampoConEtiqueta("Texto a buscar:", txtBuscar));
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearCampoConEtiqueta("Filtrar por:", filtro));
        panel.add(Box.createVerticalStrut(20));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        JButton btnBuscar = createModernButton("Buscar", COLOR_PRIMARIO);
        JButton btnCancelar = createModernButton("Cancelar", COLOR_ERROR);

        btnBuscar.addActionListener(e -> {
            String texto = txtBuscar.getText().trim().toLowerCase();
            String campo = filtro.getSelectedItem().toString();

            if (texto.isEmpty()) {
                mostrarMensajeModerno("Error", "Ingrese un texto para buscar", COLOR_ERROR);
                return;
            }

            ListaEnlazada<Contenido> filtrados = new ListaEnlazada<>();
            for (Contenido c : contenidos) {
                if ((campo.equals("Tema") && c.getTema().toLowerCase().contains(texto)) ||
                        (campo.equals("Autor") && c.getAutor().toLowerCase().contains(texto)) ||
                        (campo.equals("Tipo") && c.getTipo().toLowerCase().contains(texto))) {
                    filtrados.insertarFinal(c);
                }
            }

            if (filtrados.estaVacia()) {
                mostrarMensajeModerno("Sin Resultados", "No se encontraron contenidos con esos criterios.", COLOR_ADVERTENCIA);
            } else {
                mostrarContenidosFiltrados(filtrados);
            }
            SwingUtilities.getWindowAncestor(panel).dispose();
        });

        btnCancelar.addActionListener(e -> SwingUtilities.getWindowAncestor(panel).dispose());

        buttonPanel.add(btnBuscar);
        buttonPanel.add(btnCancelar);
        panel.add(buttonPanel);

        createModernDialog("Buscar Contenidos", panel).setVisible(true);
    }
    private void mostrarContenidosFiltrados(ListaEnlazada<Contenido> contenidos) {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setOpaque(false);

        JLabel titulo = new JLabel("📑 Resultados de Búsqueda");
        titulo.setFont(FUENTE_TITULO);
        titulo.setForeground(COLOR_TEXTO);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel resultadosPanel = new JPanel();
        resultadosPanel.setLayout(new BoxLayout(resultadosPanel, BoxLayout.Y_AXIS));
        resultadosPanel.setOpaque(false);

        for (Contenido contenido : contenidos) {
            JPanel card = createContentCard(contenido);
            resultadosPanel.add(card);
            resultadosPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(resultadosPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnCerrar = createModernButton("Cerrar", COLOR_PRIMARIO);
        btnCerrar.addActionListener(e -> SwingUtilities.getWindowAncestor(panel).dispose());
        panel.add(btnCerrar, BorderLayout.SOUTH);

        createModernDialog("Resultados", panel).setVisible(true);
    }


    @Override
    public void dispose() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        super.dispose();
    }
}