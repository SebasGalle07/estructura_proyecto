package view;

import controller.UsuarioController;
import controller.GrafoController;
import controller.ContenidoController;
import app.AppContext;
import model.Usuario;
import model.Contenido;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelModeradorView extends JFrame {
    // Paleta de colores moderna
    private static final Color COLOR_PRIMARIO = new Color(79, 70, 229);
    private static final Color COLOR_SECUNDARIO = new Color(139, 92, 246);
    private static final Color COLOR_ACENTO = new Color(236, 72, 153);
    private static final Color COLOR_SUCCESS = new Color(85, 239, 196);
    private static final Color COLOR_WARNING = new Color(251, 191, 36);
    private static final Color COLOR_DANGER = new Color(255, 118, 117);
    private static final Color COLOR_BACKGROUND = new Color(15, 23, 42);
    private static final Color COLOR_CARD = new Color(30, 41, 59);
    private static final Color COLOR_TEXT_PRIMARY = new Color(248, 250, 252);
    private static final Color COLOR_TEXT_SECONDARY = new Color(148, 163, 184);

    // Fuentes modernas
    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 16);
    private static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_CARD_TITLE = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font FONT_CARD_DESC = new Font("Segoe UI", Font.PLAIN, 13);

    private UsuarioController usuarioController;
    private GrafoController grafoController;
    private ContenidoController contenidoController;

    // Componentes de animación
    private Timer animationTimer;
    private float animationProgress = 0f;

    public PanelModeradorView() {
        this.usuarioController = AppContext.usuarioController;
        this.grafoController = AppContext.grafoController;
        this.contenidoController = AppContext.contenidoController;

        initializeUI();
        startLoadingAnimation();
    }

    private void initializeUI() {
        setTitle("Panel de Administración Avanzado");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        // Panel principal con gradiente
        JPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));

        // Header moderno
        JPanel headerPanel = createModernHeader();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Panel de contenido con cards
        JPanel contentPanel = createContentPanel();
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Footer con información
        JPanel footerPanel = createFooter();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createModernHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBackground(COLOR_PRIMARIO);
        header.setPreferredSize(new Dimension(0, 80));
        header.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Título con icono
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setOpaque(false);

        JLabel iconLabel = new JLabel("⚡");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        iconLabel.setForeground(COLOR_ACENTO);

        JLabel titleLabel = new JLabel("Panel de Administración");
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        titlePanel.add(iconLabel);
        titlePanel.add(titleLabel);

        // Botón de salida moderno
        JButton exitButton = createModernButton("Salir", COLOR_DANGER, "🚪");
        exitButton.addActionListener(e -> {
            new MainView().setVisible(true);
            dispose();
        });

        header.add(titlePanel, BorderLayout.WEST);
        header.add(exitButton, BorderLayout.EAST);

        return header;
    }

    private JPanel createContentPanel() {
        JPanel content = new JPanel(new BorderLayout(20, 20));
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));

        // Panel de estadísticas rápidas
        JPanel statsPanel = createStatsPanel();
        content.add(statsPanel, BorderLayout.NORTH);

        // Panel de acciones principales con cards
        JPanel cardsPanel = createCardsPanel();
        content.add(cardsPanel, BorderLayout.CENTER);

        return content;
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setOpaque(false);
        statsPanel.setPreferredSize(new Dimension(0, 100));

        // Obtener estadísticas reales
        int totalUsuarios = usuarioController.getTodos().tamano();
        int totalContenidos = getTotalContenidos();
        int totalConexiones = getTotalConexiones();
        int contenidosValorados = getContenidosValorados();

        statsPanel.add(createStatCard("👥", "Usuarios", String.valueOf(totalUsuarios), COLOR_ACENTO));
        statsPanel.add(createStatCard("📄", "Contenidos", String.valueOf(totalContenidos), COLOR_SUCCESS));
        statsPanel.add(createStatCard("🔗", "Conexiones", String.valueOf(totalConexiones), COLOR_WARNING));
        statsPanel.add(createStatCard("⭐", "Valorados", String.valueOf(contenidosValorados), new Color(255, 159, 67)));

        return statsPanel;
    }

    private JPanel createStatCard(String icon, String title, String value, Color accentColor) {
        JPanel card = new RoundedPanel(15);
        card.setBackground(COLOR_CARD);
        card.setLayout(new BorderLayout(10, 5));
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Icono
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Panel de texto
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setOpaque(false);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valueLabel.setForeground(accentColor);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(FONT_CARD_DESC);
        titleLabel.setForeground(COLOR_TEXT_SECONDARY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        textPanel.add(valueLabel, BorderLayout.CENTER);
        textPanel.add(titleLabel, BorderLayout.SOUTH);

        card.add(iconLabel, BorderLayout.NORTH);
        card.add(textPanel, BorderLayout.CENTER);

        // Efecto hover
        addHoverEffect(card);

        return card;
    }

    private JPanel createCardsPanel() {
        JPanel cardsPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        cardsPanel.setOpaque(false);

        // Cards de acciones
        cardsPanel.add(createActionCard("👤", "Ver Usuarios", "Gestionar todos los usuarios registrados", COLOR_ACENTO, this::mostrarUsuarios));
        cardsPanel.add(createActionCard("🏆", "Top Contenidos", "Contenidos mejor valorados del sistema", COLOR_SUCCESS, this::mostrarContenidosMasValorados));
        cardsPanel.add(createActionCard("🌟", "Usuarios Populares", "Los usuarios más conectados", new Color(255, 159, 67), this::mostrarUsuariosMasConectados));
        cardsPanel.add(createActionCard("📊", "Grafo Textual", "Visualización textual del grafo", COLOR_SECUNDARIO, this::mostrarGrafoTexto));
        cardsPanel.add(createActionCard("🎨", "Grafo Visual", "Representación gráfica del grafo", new Color(129, 236, 236), () -> new GrafoView().setVisible(true)));
        cardsPanel.add(createActionCard("⭐", "Valorar Contenido", "Asignar calificaciones a contenidos", new Color(255, 107, 129), this::valorarContenido));
        cardsPanel.add(createActionCard("🧬", "Clústeres", "Comunidades de estudiantes conectados", COLOR_WARNING, this::mostrarClusters));
        cardsPanel.add(createActionCard("🧭", "Ruta entre Usuarios", "Encuentra el camino más corto entre dos estudiantes", new Color(108, 92, 231), this::mostrarRutaMasCorta));
        cardsPanel.add(createActionCard("📥", "Cargar Datos", "Carga rápida de usuarios de ejemplo", COLOR_SUCCESS, this::cargarDatosPrueba));
        return cardsPanel;
    }

    private JPanel createActionCard(String icon, String title, String description, Color accentColor, Runnable action) {
        JPanel card = new RoundedPanel(15);
        card.setBackground(COLOR_CARD);
        card.setLayout(new BorderLayout(15, 10));
        card.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Panel superior con icono y título
        JPanel topPanel = new JPanel(new BorderLayout(10, 0));
        topPanel.setOpaque(false);

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        iconLabel.setForeground(accentColor);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(FONT_CARD_TITLE);
        titleLabel.setForeground(COLOR_TEXT_PRIMARY);

        topPanel.add(iconLabel, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        // Descripción
        JLabel descLabel = new JLabel("<html>" + description + "</html>");
        descLabel.setFont(FONT_CARD_DESC);
        descLabel.setForeground(COLOR_TEXT_SECONDARY);

        // Línea de acento
        JPanel accentLine = new JPanel();
        accentLine.setBackground(accentColor);
        accentLine.setPreferredSize(new Dimension(0, 3));

        card.add(topPanel, BorderLayout.NORTH);
        card.add(descLabel, BorderLayout.CENTER);
        card.add(accentLine, BorderLayout.SOUTH);

        // Efectos interactivos
        addCardInteraction(card, accentColor, action);

        return card;
    }

    private void addCardInteraction(JPanel card, Color accentColor, Runnable action) {
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Animación de click
                animateCardClick(card);
                action.run();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(248, 249, 250));
                card.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(COLOR_CARD);
                card.repaint();
            }
        });
    }

    private void animateCardClick(JPanel card) {
        Timer clickTimer = new Timer(50, null);
        final int[] step = {0};

        clickTimer.addActionListener(e -> {
            if (step[0] < 5) {
                card.setBackground(card.getBackground().darker());
                step[0]++;
            } else {
                card.setBackground(COLOR_CARD);
                clickTimer.stop();
            }
            card.repaint();
        });

        clickTimer.start();
    }

    private JButton createModernButton(String text, Color backgroundColor, String icon) {
        JButton button = new JButton(icon + " " + text);
        button.setFont(FONT_BUTTON);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Bordes redondeados
        button.setContentAreaFilled(false);
        button.setOpaque(true);

        // Efectos hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(backgroundColor.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(COLOR_PRIMARIO);
        footer.setPreferredSize(new Dimension(0, 40));
        footer.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JLabel footerLabel = new JLabel("Sistema de Gestión de Contenidos - Panel Administrativo");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLabel.setForeground(COLOR_TEXT_SECONDARY.brighter());

        JLabel timeLabel = new JLabel(java.time.LocalTime.now().toString().substring(0, 8));
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        timeLabel.setForeground(COLOR_ACENTO);

        footer.add(footerLabel, BorderLayout.WEST);
        footer.add(timeLabel, BorderLayout.EAST);

        return footer;
    }

    private void addHoverEffect(JPanel panel) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(248, 249, 250));
                panel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(COLOR_CARD);
                panel.repaint();
            }
        });
    }

    private void startLoadingAnimation() {
        animationTimer = new Timer(50, e -> {
            animationProgress += 0.05f;
            if (animationProgress >= 1.0f) {
                animationProgress = 1.0f;
                animationTimer.stop();
            }
            repaint();
        });
        animationTimer.start();
    }

    // Métodos auxiliares para estadísticas
    private int getTotalContenidos() {
        int total = 0;
        for (Usuario u : usuarioController.getTodos()) {
            total += u.getContenidosPublicados().tamano();
        }
        return total;
    }

    private int getTotalConexiones() {
        return grafoController.getGrafo().getVertices().tamano();
    }

    private int getContenidosValorados() {
        int total = 0;
        for (Usuario u : usuarioController.getTodos()) {
            for (Contenido c : u.getContenidosPublicados()) {
                if (!c.getValoraciones().estaVacia()) {
                    total++;
                }
            }
        }
        return total;
    }

    // Clases auxiliares para efectos visuales
    private class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            GradientPaint gradient = new GradientPaint(
                    0, 0, COLOR_BACKGROUND,
                    0, getHeight(), new Color(240, 242, 245)
            );
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private class RoundedPanel extends JPanel {
        private int radius;

        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Sombra
            g2d.setColor(new Color(0, 0, 0, 10));
            g2d.fillRoundRect(2, 2, getWidth() - 2, getHeight() - 2, radius, radius);

            // Fondo
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 2, radius, radius);

            super.paintComponent(g);
        }
    }

    // Los métodos originales permanecen igual pero con diálogos mejorados
    private void mostrarUsuarios() {
        model.ListaEnlazada<Usuario> usuarios = usuarioController.getTodos();

        if (usuarios.estaVacia()) {
            showModernDialog("No hay usuarios registrados en el sistema", "Sin Usuarios", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Usuarios Registrados");
        titulo.setFont(FONT_CARD_TITLE);
        titulo.setForeground(COLOR_TEXT_PRIMARY);
        panel.add(titulo, BorderLayout.NORTH);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(COLOR_BACKGROUND);

        for (Usuario u : usuarios) {
            JPanel userPanel = new RoundedPanel(8);
            userPanel.setBackground(COLOR_CARD);
            userPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            userPanel.setLayout(new BorderLayout());

            JLabel userLabel = new JLabel("👤 " + u.getNombre() + " (ID: " + u.getId() + ")");
            userLabel.setFont(FONT_CARD_DESC);
            userLabel.setForeground(COLOR_TEXT_PRIMARY);

            userPanel.add(userLabel);
            listPanel.add(userPanel);
            listPanel.add(Box.createVerticalStrut(5));
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setPreferredSize(new Dimension(400, 350));
        scrollPane.setBorder(null);
        panel.add(scrollPane, BorderLayout.CENTER);

        showModernDialog(panel, "Usuarios Registrados");
    }

    private void showModernDialog(Object message, String title) {
        showModernDialog(message, title, JOptionPane.PLAIN_MESSAGE);
    }

    private void showModernDialog(Object message, String title, int messageType) {
        JOptionPane pane = new JOptionPane(message, messageType);
        JDialog dialog = pane.createDialog(this, title);
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    // Resto de métodos originales adaptados con el nuevo estilo...
    // (mostrarContenidosMasValorados, mostrarUsuariosMasConectados, mostrarGrafoTexto, valorarContenido)
    // Los implemento con el mismo estilo visual moderno

    private void mostrarContenidosMasValorados() {
        model.ListaEnlazada<Usuario> usuarios = usuarioController.getTodos();
        model.ListaEnlazada<Contenido> contenidosValorados = new model.ListaEnlazada<>();

        for (Usuario u : usuarios) {
            for (Contenido c : u.getContenidosPublicados()) {
                if (!c.getValoraciones().estaVacia()) {
                    contenidosValorados.insertarFinal(c);
                }
            }
        }

        if (contenidosValorados.estaVacia()) {
            showModernDialog("No hay contenidos valorados en el sistema", "Sin Contenidos Valorados", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Ordenamiento (mismo código original)
        model.NodoLista<Contenido> i = contenidosValorados.getCabeza();
        while (i != null) {
            model.NodoLista<Contenido> j = i.getSiguiente();
            while (j != null) {
                if (i.getDato().obtenerPromedio() < j.getDato().obtenerPromedio()) {
                    Contenido temp = i.getDato();
                    i.setDato(j.getDato());
                    j.setDato(temp);
                }
                j = j.getSiguiente();
            }
            i = i.getSiguiente();
        }

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("🏆 Top 5 Contenidos Mejor Valorados");
        titulo.setFont(FONT_CARD_TITLE);
        titulo.setForeground(COLOR_TEXT_PRIMARY);
        panel.add(titulo, BorderLayout.NORTH);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(COLOR_BACKGROUND);

        int count = 0;
        for (Contenido c : contenidosValorados) {
            double promedio = c.obtenerPromedio();

            JPanel contentPanel = new RoundedPanel(8);
            contentPanel.setBackground(COLOR_CARD);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            contentPanel.setLayout(new BorderLayout());

            JLabel contentLabel = new JLabel(String.format("⭐ %s (Tema: %s)", c.getTitulo(), c.getTema()));
            contentLabel.setFont(FONT_CARD_DESC);
            contentLabel.setForeground(COLOR_TEXT_PRIMARY);

            JLabel ratingLabel = new JLabel(String.format("%.2f", promedio));
            ratingLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            ratingLabel.setForeground(COLOR_SUCCESS);

            contentPanel.add(contentLabel, BorderLayout.CENTER);
            contentPanel.add(ratingLabel, BorderLayout.EAST);

            listPanel.add(contentPanel);
            listPanel.add(Box.createVerticalStrut(8));

            count++;
            if (count >= 5) break;
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        scrollPane.setBorder(null);
        panel.add(scrollPane, BorderLayout.CENTER);

        showModernDialog(panel, "Contenidos Mejor Valorados");
    }

    private void mostrarUsuariosMasConectados() {
        model.ListaEnlazada<String> topUsuarios = grafoController.getGrafo().getTopUsuariosConectados(5);

        if (topUsuarios.estaVacia()) {
            showModernDialog("No hay usuarios con conexiones en el grafo de afinidad", "Sin Conexiones", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("🌟 Top 5 Usuarios Más Conectados");
        titulo.setFont(FONT_CARD_TITLE);
        titulo.setForeground(COLOR_TEXT_PRIMARY);
        panel.add(titulo, BorderLayout.NORTH);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(COLOR_BACKGROUND);

        for (String info : topUsuarios) {
            JPanel userPanel = new RoundedPanel(8);
            userPanel.setBackground(COLOR_CARD);
            userPanel.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

            JLabel userLabel = new JLabel("🔗 " + info);
            userLabel.setFont(FONT_CARD_DESC);
            userLabel.setForeground(COLOR_TEXT_PRIMARY);

            userPanel.add(userLabel);
            listPanel.add(userPanel);
            listPanel.add(Box.createVerticalStrut(6));
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setPreferredSize(new Dimension(450, 250));
        scrollPane.setBorder(null);
        panel.add(scrollPane, BorderLayout.CENTER);

        showModernDialog(panel, "Usuarios Más Conectados");
    }

    private void mostrarGrafoTexto() {
        model.ListaEnlazada<model.Pair<String, model.Vertice>> vertices = grafoController.getGrafo().getVertices();

        if (vertices.estaVacia()) {
            showModernDialog("El grafo de afinidad está vacío", "Grafo Vacío", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("📊 Grafo de Afinidad - Vista Textual");
        titulo.setFont(FONT_CARD_TITLE);
        titulo.setForeground(COLOR_TEXT_PRIMARY);
        panel.add(titulo, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("JetBrains Mono", Font.PLAIN, 13));
        textArea.setEditable(false);
        textArea.setBackground(new Color(248, 249, 250));
        textArea.setForeground(Color.BLACK);
        textArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        StringBuilder sb = new StringBuilder();
        for (model.Pair<String, model.Vertice> par : vertices) {
            sb.append("🔸 ").append(par.getKey()).append(" → ");
            model.ListaEnlazada<model.Vertice> conexiones = par.getValue().getConexiones();

            if (conexiones.estaVacia()) {
                sb.append("(sin conexiones)");
            } else {
                boolean primero = true;
                for (model.Vertice v : conexiones) {
                    if (!primero) sb.append(", ");
                    sb.append(v.getUsuario().getId());
                    primero = false;
                }
            }
            sb.append("\n");
        }

        textArea.setText(sb.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 221, 225)));
        panel.add(scrollPane, BorderLayout.CENTER);

        showModernDialog(panel, "Grafo de Afinidad");
    }

    private void valorarContenido() {
        model.ListaEnlazada<Contenido> contenidos = contenidoController.obtenerTodosLosContenidos();

        if (contenidos.estaVacia()) {
            showModernDialog("No hay contenidos disponibles para valorar", "Sin Contenidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Convertir a arreglo de títulos con autor incluido
        String[] opciones = new String[contenidos.tamano()];
        int index = 0;
        for (Contenido c : contenidos) {
            opciones[index++] = c.getTitulo() + " - por " + c.getAutor();
        }

        JComboBox<String> comboContenidos = new JComboBox<>(opciones);
        comboContenidos.setFont(FONT_CARD_DESC);
        comboContenidos.setBackground(COLOR_CARD);
        comboContenidos.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(COLOR_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new JLabel("Seleccione un contenido para valorar:"), BorderLayout.NORTH);
        panel.add(comboContenidos, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(this, panel, "Seleccionar Contenido", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        Contenido seleccionado = contenidos.get(comboContenidos.getSelectedIndex());
        if (seleccionado == null) return;

        // Panel de valoración
        JPanel ratingPanel = new JPanel(new BorderLayout(0, 15));
        ratingPanel.setBackground(COLOR_BACKGROUND);
        ratingPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel tituloRating = new JLabel("⭐ Asignar Calificación");
        tituloRating.setFont(FONT_CARD_TITLE);
        tituloRating.setForeground(COLOR_TEXT_PRIMARY);
        ratingPanel.add(tituloRating, BorderLayout.NORTH);

        JPanel infoPanel = new RoundedPanel(8);
        infoPanel.setBackground(new Color(248, 249, 250));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        infoPanel.setLayout(new BorderLayout());

        JLabel infoLabel = new JLabel("<html><b>Título:</b> " + seleccionado.getTitulo()
                + "<br><b>Autor:</b> " + seleccionado.getAutor()
                + "<br><b>Tema:</b> " + seleccionado.getTema() + "</html>");
        infoLabel.setFont(FONT_CARD_DESC);
        infoLabel.setForeground(COLOR_TEXT_PRIMARY);
        infoPanel.add(infoLabel, BorderLayout.CENTER);

        ratingPanel.add(infoPanel, BorderLayout.CENTER);

        // Sección de estrellas y campo
        JTextField txtValor = new JTextField(5);
        txtValor.setFont(FONT_CARD_DESC);
        txtValor.setHorizontalAlignment(JTextField.CENTER);
        txtValor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 221, 225)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        JPanel starsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        starsPanel.setBackground(COLOR_BACKGROUND);

        JLabel[] stars = new JLabel[5];
        for (int i = 0; i < 5; i++) {
            stars[i] = new JLabel("☆");
            stars[i].setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
            stars[i].setForeground(new Color(200, 200, 200));
            stars[i].setCursor(new Cursor(Cursor.HAND_CURSOR));

            final int rating = i + 1;
            stars[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    txtValor.setText(String.valueOf(rating));
                    updateStars(stars, rating);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    updateStarsHover(stars, rating);
                }
            });

            starsPanel.add(stars[i]);
        }

        JPanel scorePanel = new JPanel(new BorderLayout(10, 0));
        scorePanel.setBackground(COLOR_BACKGROUND);
        scorePanel.add(starsPanel, BorderLayout.CENTER);
        scorePanel.add(txtValor, BorderLayout.EAST);

        JPanel total = new JPanel(new BorderLayout(0, 10));
        total.setBackground(COLOR_BACKGROUND);
        total.add(new JLabel("Calificación (1-5 estrellas):"), BorderLayout.NORTH);
        total.add(scorePanel, BorderLayout.CENTER);

        ratingPanel.add(total, BorderLayout.SOUTH);

        result = JOptionPane.showConfirmDialog(this, ratingPanel, "Valorar Contenido", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        String valor = txtValor.getText().trim();
        try {
            int calif = Integer.parseInt(valor);
            if (calif < 1 || calif > 5) {
                showModernDialog("La valoración debe estar entre 1 y 5", "Valor Inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            seleccionado.agregarValoracion(calif);

            JPanel successPanel = new JPanel(new BorderLayout(0, 15));
            successPanel.setBackground(COLOR_BACKGROUND);
            successPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JLabel successIcon = new JLabel("✅");
            successIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
            successIcon.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel successText = new JLabel("<html><center><b>¡Valoración Registrada!</b><br>Se asignaron " + calif + " estrella" + (calif > 1 ? "s" : "") + " al contenido</center></html>");
            successText.setFont(FONT_CARD_DESC);
            successText.setForeground(COLOR_TEXT_PRIMARY);
            successText.setHorizontalAlignment(SwingConstants.CENTER);

            successPanel.add(successIcon, BorderLayout.NORTH);
            successPanel.add(successText, BorderLayout.CENTER);

            showModernDialog(successPanel, "Valoración Exitosa");

        } catch (NumberFormatException e) {
            showModernDialog("Ingrese un número válido para la valoración", "Formato Inválido", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Métodos auxiliares para las estrellas interactivas
    private void updateStars(JLabel[] stars, int rating) {
        for (int i = 0; i < stars.length; i++) {
            if (i < rating) {
                stars[i].setText("★");
                stars[i].setForeground(new Color(255, 193, 7)); // Dorado
            } else {
                stars[i].setText("☆");
                stars[i].setForeground(new Color(200, 200, 200));
            }
        }
    }

    private void updateStarsHover(JLabel[] stars, int rating) {
        for (int i = 0; i < stars.length; i++) {
            if (i < rating) {
                stars[i].setText("★");
                stars[i].setForeground(new Color(255, 213, 79)); // Dorado claro
            } else {
                stars[i].setText("☆");
                stars[i].setForeground(new Color(200, 200, 200));
            }
        }
    }
    private void mostrarClusters() {
        model.ListaEnlazada<model.ListaEnlazada<Usuario>> clusters = grafoController.detectarClusters();

        if (clusters.estaVacia()) {
            showModernDialog("No se encontraron comunidades activas en el grafo.", "Sin Clústeres", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("🔍 Comunidades de Estudio Detectadas");
        titulo.setFont(FONT_CARD_TITLE);
        titulo.setForeground(COLOR_TEXT_PRIMARY);
        panel.add(titulo, BorderLayout.NORTH);

        JPanel clustersPanel = new JPanel();
        clustersPanel.setLayout(new BoxLayout(clustersPanel, BoxLayout.Y_AXIS));
        clustersPanel.setBackground(COLOR_BACKGROUND);

        int grupo = 1;
        for (model.ListaEnlazada<Usuario> comunidad : clusters) {
            JPanel grupoPanel = new RoundedPanel(10);
            grupoPanel.setBackground(COLOR_CARD);
            grupoPanel.setLayout(new BoxLayout(grupoPanel, BoxLayout.Y_AXIS));
            grupoPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            JLabel grupoLabel = new JLabel("📘 Clúster #" + grupo);
            grupoLabel.setFont(FONT_CARD_TITLE);
            grupoLabel.setForeground(COLOR_TEXT_PRIMARY);
            grupoPanel.add(grupoLabel);

            for (Usuario u : comunidad) {
                JLabel miembro = new JLabel("👤 " + u.getNombre() + " (ID: " + u.getId() + ")");
                miembro.setFont(FONT_CARD_DESC);
                miembro.setForeground(COLOR_TEXT_SECONDARY);
                grupoPanel.add(miembro);
            }

            clustersPanel.add(grupoPanel);
            clustersPanel.add(Box.createVerticalStrut(15));
            grupo++;
        }

        JScrollPane scroll = new JScrollPane(clustersPanel);
        scroll.setPreferredSize(new Dimension(500, 400));
        scroll.setBorder(null);

        panel.add(scroll, BorderLayout.CENTER);

        showModernDialog(panel, "Comunidades de Estudio");
    }

    private void mostrarRutaMasCorta() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("🧭 Buscar Ruta Más Corta Entre Estudiantes");
        titulo.setFont(FONT_CARD_TITLE);
        titulo.setForeground(COLOR_TEXT_PRIMARY);
        panel.add(titulo, BorderLayout.NORTH);

        JPanel campos = new JPanel(new GridLayout(2, 2, 10, 10));
        campos.setOpaque(false);

        JTextField campoOrigen = new JTextField();
        JTextField campoDestino = new JTextField();

        campos.add(new JLabel("ID del estudiante origen:", SwingConstants.RIGHT));
        campos.add(campoOrigen);
        campos.add(new JLabel("ID del estudiante destino:", SwingConstants.RIGHT));
        campos.add(campoDestino);

        panel.add(campos, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(this, panel, "Buscar Ruta Corta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        String idOrigen = campoOrigen.getText().trim();
        String idDestino = campoDestino.getText().trim();

        if (idOrigen.isEmpty() || idDestino.isEmpty()) {
            showModernDialog("Debe ingresar ambos ID de usuario", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        model.ListaEnlazada<Usuario> camino = grafoController.caminoMasCorto(idOrigen, idDestino);
        if (camino == null || camino.estaVacia()) {
            showModernDialog("No se encontró un camino entre los estudiantes", "Ruta No Disponible", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Mostrar la ruta
        JPanel rutaPanel = new JPanel();
        rutaPanel.setLayout(new BoxLayout(rutaPanel, BoxLayout.Y_AXIS));
        rutaPanel.setBackground(COLOR_BACKGROUND);
        rutaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        int paso = 1;
        for (Usuario u : camino) {
            JLabel pasoLabel = new JLabel("Paso " + paso + ": " + u.getNombre() + " (ID: " + u.getId() + ")");
            pasoLabel.setFont(FONT_CARD_DESC);
            pasoLabel.setForeground(COLOR_TEXT_PRIMARY);
            rutaPanel.add(pasoLabel);
            paso++;
        }

        JScrollPane scroll = new JScrollPane(rutaPanel);
        scroll.setPreferredSize(new Dimension(400, 250));
        scroll.setBorder(null);

        showModernDialog(scroll, "Camino Más Corto Encontrado");
    }

    private void cargarDatosPrueba() {
        AppContext.cargarDatosPrueba(true); // Puedes cambiar a false si quieres afinidad real
        showModernDialog(
                "Datos de prueba cargados correctamente.\nUsuarios registrados: Kevin, Luis, Ana, Carlos, Santiago, Moderador.\nConexiones: Todos están conectados entre sí.",
                "Carga Exitosa",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


}