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
import java.util.List;

public class PanelEstudianteView extends JFrame {
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
        JPanel cardsPanel = createCardsPanel();
        mainPanel.add(cardsPanel, BorderLayout.CENTER);

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

        JLabel iconLabel = new JLabel("🎓");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        iconLabel.setForeground(COLOR_ACENTO);

        JLabel titleLabel = new JLabel("Panel de Estudiante");
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

    private JPanel createCardsPanel() {
        JPanel cardsPanel = new JPanel(new GridLayout(3, 4, 20, 20));
        cardsPanel.setOpaque(false);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        cardsPanel.add(createActionCard("📚", "Ver Contenidos", "Ver todos los contenidos publicados", COLOR_SUCCESS, this::mostrarContenidos));
        cardsPanel.add(createActionCard("➕", "Publicar Contenido", "Publica un nuevo contenido", COLOR_PRIMARIO, this::publicarContenido));
        cardsPanel.add(createActionCard("🔍", "Buscar por Tema", "Busca contenidos por tema", COLOR_WARNING, this::buscarPorTema));
        cardsPanel.add(createActionCard("👤", "Buscar por Autor", "Busca contenidos por autor", COLOR_ACENTO, this::buscarPorAutor));
        cardsPanel.add(createActionCard("📄", "Buscar por Tipo", "Busca contenidos por tipo", COLOR_SECUNDARIO, this::buscarPorTipo));
        cardsPanel.add(createActionCard("⭐", "Valorar Contenido", "Valora un contenido publicado", new Color(255, 159, 67), this::valorarContenido));
        cardsPanel.add(createActionCard("🤝", "Sugerencias de Amistad", "Ver sugerencias de amistad", COLOR_SUCCESS, this::mostrarSugerencias));
        cardsPanel.add(createActionCard("🧭", "Ruta Corta", "Ver ruta más corta a otro usuario", COLOR_PRIMARIO, this::verRutaCorta));
        cardsPanel.add(createActionCard("📜", "Historial", "Ver historial de actividades", COLOR_TEXT_SECONDARY, this::mostrarHistorial));
        cardsPanel.add(createActionCard("✉️", "Enviar Mensaje", "Enviar mensaje a otro usuario", COLOR_ACENTO, this::enviarMensaje));
        cardsPanel.add(createActionCard("🆘", "Solicitar Ayuda", "Solicitar ayuda a la comunidad", COLOR_DANGER, this::solicitarAyuda));
        // Puedes agregar más cards según tus necesidades

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
        button.setContentAreaFilled(false);
        button.setOpaque(true);

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

        JLabel footerLabel = new JLabel("Sistema de Gestión de Contenidos - Panel Estudiante");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLabel.setForeground(COLOR_TEXT_SECONDARY.brighter());

        JLabel timeLabel = new JLabel(java.time.LocalTime.now().toString().substring(0, 8));
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        timeLabel.setForeground(COLOR_ACENTO);

        footer.add(footerLabel, BorderLayout.WEST);
        footer.add(timeLabel, BorderLayout.EAST);

        return footer;
    }

    // Paneles visuales
    private class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            GradientPaint gradient = new GradientPaint(
                    0, 0, COLOR_BACKGROUND,
                    0, getHeight(), new Color(30, 41, 59)
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

    // ------------------- MÉTODOS ORIGINALES -------------------

    // Todos los métodos de tu versión anterior, pegados aquí sin cambios:
    // publicarContenido, mostrarSugerencias, solicitarAyuda, enviarMensaje, verRutaCorta,
    // mostrarHistorial, mostrarContenidos, valorarContenido, mostrarResultados, createFieldPanel

    private void publicarContenido() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 0, 10));
        panel.setBackground(COLOR_CARD);
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
        grafoController.agregarUsuario(usuario);

        model.ListaEnlazada<Usuario> sugerencias = grafoController.sugerirAmigos(usuario.getId());

        if (sugerencias.estaVacia()) {
            JOptionPane.showMessageDialog(this,
                    "No hay sugerencias de amistad disponibles en este momento.",
                    "Sin Sugerencias",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(COLOR_CARD);

            JLabel titulo = new JLabel("Sugerencias de conexión:");
            titulo.setFont(FONT_CARD_DESC);
            titulo.setForeground(COLOR_TEXT_PRIMARY);
            panel.add(titulo, BorderLayout.NORTH);

            JPanel listPanel = new JPanel();
            listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
            listPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            listPanel.setBackground(COLOR_CARD);

            for (Usuario u : sugerencias) {
                JLabel userLabel = new JLabel("• " + u.getNombre() + " (ID: " + u.getId() + ")");
                userLabel.setFont(FONT_CARD_DESC);
                userLabel.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
                userLabel.setForeground(COLOR_TEXT_SECONDARY);
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
        panel.setBackground(COLOR_CARD);
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
        panel.setBackground(COLOR_CARD);
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
        panel.setBackground(COLOR_CARD);
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
                resultPanel.setBackground(COLOR_CARD);

                JLabel titulo = new JLabel("Camino más corto:");
                titulo.setFont(FONT_CARD_DESC);
                titulo.setForeground(COLOR_TEXT_PRIMARY);
                resultPanel.add(titulo, BorderLayout.NORTH);

                JPanel pathPanel = new JPanel();
                pathPanel.setLayout(new BoxLayout(pathPanel, BoxLayout.Y_AXIS));
                pathPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
                pathPanel.setBackground(COLOR_CARD);

                for (Usuario u : camino) {
                    JLabel userLabel = new JLabel("→ " + u.getNombre() + " (ID: " + u.getId() + ")");
                    userLabel.setFont(FONT_CARD_DESC);
                    userLabel.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
                    userLabel.setForeground(COLOR_TEXT_SECONDARY);
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
        panel.setBackground(COLOR_CARD);

        JLabel titulo = new JLabel("Historial de Actividades:");
        titulo.setFont(FONT_CARD_DESC);
        titulo.setForeground(COLOR_TEXT_PRIMARY);
        panel.add(titulo, BorderLayout.NORTH);

        JPanel historialPanel = new JPanel();
        historialPanel.setLayout(new BoxLayout(historialPanel, BoxLayout.Y_AXIS));
        historialPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        historialPanel.setBackground(COLOR_CARD);

        while (actual != null) {
            JLabel accionLabel = new JLabel("• " + actual.getDato());
            accionLabel.setFont(FONT_CARD_DESC);
            accionLabel.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
            accionLabel.setForeground(COLOR_TEXT_SECONDARY);
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
        ListaEnlazada<Contenido> contenidos = AppContext.contenidoController.obtenerTodosLosContenidos();

        if (contenidos.estaVacia()) {
            JOptionPane.showMessageDialog(this, "No hay contenidos publicados.",
                    "Contenidos Vacíos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(COLOR_CARD);
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
        panel.setBackground(COLOR_CARD);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel idPanel = createFieldPanel("ID del contenido a valorar:");
        panel.add(idPanel, BorderLayout.NORTH);

        JTextField txtIdContenido = (JTextField) ((JPanel) idPanel.getComponent(1)).getComponent(0);

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
            Contenido contenido = AppContext.contenidoController.buscarContenidoPorId(Integer.parseInt(idContenido));
            if (contenido == null) {
                JOptionPane.showMessageDialog(this,
                        "No se encontró un contenido con el ID proporcionado",
                        "Contenido no encontrado", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JPanel valoracionPanel = new JPanel(new BorderLayout(0, 10));
            valoracionPanel.setBackground(COLOR_CARD);
            valoracionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel lblValoracion = new JLabel("Ingrese una valoración (1-5):");
            lblValoracion.setFont(FONT_CARD_DESC);
            lblValoracion.setForeground(COLOR_TEXT_PRIMARY);
            valoracionPanel.add(lblValoracion, BorderLayout.NORTH);

            JTextField txtValoracion = new JTextField();
            valoracionPanel.add(txtValoracion, BorderLayout.CENTER);

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
        panel.setBackground(COLOR_CARD);
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

    private JPanel createFieldPanel(String labelText) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(COLOR_CARD);

        JLabel label = new JLabel(labelText);
        label.setFont(FONT_CARD_DESC);
        label.setForeground(COLOR_TEXT_PRIMARY);

        JPanel fieldPanel = new JPanel(new BorderLayout());
        fieldPanel.setBackground(COLOR_CARD);

        JTextField textField = new JTextField();
        textField.setFont(FONT_CARD_DESC);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        fieldPanel.add(textField, BorderLayout.CENTER);

        panel.add(label, BorderLayout.NORTH);
        panel.add(fieldPanel, BorderLayout.CENTER);

        return panel;
    }

    // Métodos para los cards modernos que llaman a tus métodos originales
    private void buscarPorTema() {
        String tema = JOptionPane.showInputDialog(this, "Ingrese el tema:");
        if (tema != null && !tema.trim().isEmpty()) {
            List<Contenido> resultados = AppContext.contenidoController.buscarPorTema(tema);
            mostrarResultados(resultados);
        }
    }

    private void buscarPorAutor() {
        String autor = JOptionPane.showInputDialog(this, "Ingrese el autor:");
        if (autor != null && !autor.trim().isEmpty()) {
            List<Contenido> resultados = AppContext.contenidoController.buscarPorAutor(autor);
            mostrarResultados(resultados);
        }
    }

    private void buscarPorTipo() {
        String tipo = JOptionPane.showInputDialog(this, "Ingrese el tipo:");
        if (tipo != null && !tipo.trim().isEmpty()) {
            List<Contenido> resultados = AppContext.contenidoController.buscarPorTipo(tipo);
            mostrarResultados(resultados);
        }
    }
}