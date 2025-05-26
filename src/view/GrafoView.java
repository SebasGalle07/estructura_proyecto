package view;

import app.AppContext;
import model.Usuario;
import model.Vertice;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

public class GrafoView extends JFrame {
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
    private static final Font FUENTE_NODO = new Font("Segoe UI", Font.BOLD, 12);

    private Map<String, Point> posiciones;
    private String nodoSeleccionado = null;
    private GrafoPanel grafoPanel;
    private JPanel infoPanel;
    private JLabel lblInfo;
    private JPanel statsPanel;
    private Timer animationTimer;
    private float animationProgress = 0f;
    private boolean isAnimating = false;

    public GrafoView() {
        setTitle("Red de Afinidades - Visualización Avanzada");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackground(COLOR_FONDO);

        // Configurar Look and Feel moderno (correctamente)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        posiciones = new HashMap<>();
        initializeComponents();
        setupAnimations();
    }


    private void initializeComponents() {
        // Panel principal con diseño moderno
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(COLOR_FONDO);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header con título y gradiente
        JPanel headerPanel = createHeaderPanel();

        // Panel de control lateral izquierdo
        JPanel leftControlPanel = createLeftControlPanel();

        // Panel de información lateral derecho
        JPanel rightInfoPanel = createRightInfoPanel();

        // Panel central para el grafo
        grafoPanel = new GrafoPanel();
        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.setBackground(COLOR_FONDO);
        centerContainer.setBorder(createModernBorder("Visualización del Grafo", COLOR_PRIMARIO));
        centerContainer.add(grafoPanel, BorderLayout.CENTER);

        // Ensamblar la interfaz
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(leftControlPanel, BorderLayout.WEST);
        mainPanel.add(centerContainer, BorderLayout.CENTER);
        mainPanel.add(rightInfoPanel, BorderLayout.EAST);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradiente de fondo
                GradientPaint gradient = new GradientPaint(
                        0, 0, COLOR_PRIMARIO,
                        getWidth(), 0, COLOR_SECUNDARIO
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Efecto de brillo
                g2d.setPaint(new GradientPaint(
                        0, 0, new Color(255, 255, 255, 50),
                        0, getHeight() / 2, new Color(255, 255, 255, 0)
                ));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight() / 2, 20, 20);
            }
        };

        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(-1, 100));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Título principal
        JLabel titleLabel = new JLabel("Red de Afinidades");
        titleLabel.setFont(FUENTE_TITULO);
        titleLabel.setForeground(COLOR_TEXTO);

        // Subtítulo
        JLabel subtitleLabel = new JLabel("Análisis visual de conexiones entre usuarios");
        subtitleLabel.setFont(FUENTE_SUBTITULO);
        subtitleLabel.setForeground(COLOR_TEXTO_SECUNDARIO);

        JPanel titleContainer = new JPanel();
        titleContainer.setOpaque(false);
        titleContainer.setLayout(new BoxLayout(titleContainer, BoxLayout.Y_AXIS));
        titleContainer.add(titleLabel);
        titleContainer.add(Box.createVerticalStrut(5));
        titleContainer.add(subtitleLabel);

        // Botón de cierre moderno
        JButton closeBtn = createModernButton("✕", COLOR_ERROR, 40, 40);
        closeBtn.addActionListener(e -> dispose());

        headerPanel.add(titleContainer, BorderLayout.WEST);
        headerPanel.add(closeBtn, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createLeftControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(COLOR_FONDO);
        controlPanel.setPreferredSize(new Dimension(250, -1));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));

        // Panel de controles de visualización
        JPanel viewControlsPanel = createCardPanel("Controles de Vista");
        viewControlsPanel.setLayout(new GridLayout(5, 1, 10, 10));

        JButton refreshBtn = createModernButton("🔄 Actualizar", COLOR_PRIMARIO);
        JButton zoomInBtn = createModernButton("🔍+ Acercar", COLOR_EXITO);
        JButton zoomOutBtn = createModernButton("🔍- Alejar", COLOR_ADVERTENCIA);
        JButton centerBtn = createModernButton("🎯 Centrar", COLOR_SECUNDARIO);
        JButton animateBtn = createModernButton("✨ Animar", COLOR_ACENTO);

        // Listeners
        refreshBtn.addActionListener(e -> {
            grafoPanel.actualizarPosiciones();
            startAnimation();
        });

        zoomInBtn.addActionListener(e -> {
            grafoPanel.zoom(1.2);
            grafoPanel.repaint();
        });

        zoomOutBtn.addActionListener(e -> {
            grafoPanel.zoom(0.8);
            grafoPanel.repaint();
        });

        centerBtn.addActionListener(e -> {
            grafoPanel.centrarVista();
            grafoPanel.repaint();
        });

        animateBtn.addActionListener(e -> startAnimation());

        viewControlsPanel.add(refreshBtn);
        viewControlsPanel.add(zoomInBtn);
        viewControlsPanel.add(zoomOutBtn);
        viewControlsPanel.add(centerBtn);
        viewControlsPanel.add(animateBtn);

        // Panel de estadísticas
        statsPanel = createCardPanel("Estadísticas de Red");
        updateStatsPanel();

        controlPanel.add(viewControlsPanel);
        controlPanel.add(Box.createVerticalStrut(15));
        controlPanel.add(statsPanel);
        controlPanel.add(Box.createVerticalGlue());

        return controlPanel;
    }

    private JPanel createRightInfoPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(COLOR_FONDO);
        rightPanel.setPreferredSize(new Dimension(300, -1));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        // Panel de información del nodo
        infoPanel = createCardPanel("Información del Usuario");
        infoPanel.setPreferredSize(new Dimension(-1, 300));

        lblInfo = new JLabel("<html><div style='text-align: center; color: #94A3B8;'>" +
                "<br><br>🔍<br><br>Selecciona un usuario<br>para ver sus detalles" +
                "</div></html>");
        lblInfo.setFont(FUENTE_ETIQUETA);
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(lblInfo, BorderLayout.CENTER);

        // Panel de filtros
        JPanel filtersPanel = createCardPanel("Filtros");
        filtersPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton filterAllBtn = createModernButton("👥 Mostrar Todos", COLOR_PRIMARIO);
        JButton filterConnectedBtn = createModernButton("🔗 Solo Conectados", COLOR_EXITO);
        JButton filterIsolatedBtn = createModernButton("🏝️ Aislados", COLOR_ADVERTENCIA);

        filtersPanel.add(filterAllBtn);
        filtersPanel.add(filterConnectedBtn);
        filtersPanel.add(filterIsolatedBtn);

        rightPanel.add(infoPanel);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(filtersPanel);
        rightPanel.add(Box.createVerticalGlue());

        return rightPanel;
    }

    private JPanel createCardPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fondo con bordes redondeados
                g2d.setColor(COLOR_TARJETA);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Borde sutil
                g2d.setColor(new Color(COLOR_PRIMARIO.getRed(), COLOR_PRIMARIO.getGreen(),
                        COLOR_PRIMARIO.getBlue(), 50));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
        };

        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Título del panel
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(COLOR_TEXTO);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        panel.add(titleLabel, BorderLayout.NORTH);

        return panel;
    }

    private JButton createModernButton(String text, Color baseColor) {
        return createModernButton(text, baseColor, -1, 40);
    }

    private JButton createModernButton(String text, Color baseColor, int width, int height) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Determinar color según estado
                Color bgColor = baseColor;
                if (getModel().isPressed()) {
                    bgColor = baseColor.darker();
                } else if (getModel().isRollover()) {
                    bgColor = new Color(
                            Math.min(255, baseColor.getRed() + 30),
                            Math.min(255, baseColor.getGreen() + 30),
                            Math.min(255, baseColor.getBlue() + 30)
                    );
                }

                // Gradiente de fondo
                GradientPaint gradient = new GradientPaint(
                        0, 0, bgColor,
                        0, getHeight(), bgColor.darker()
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                // Borde brillante
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);

                // Texto
                g2d.setColor(COLOR_TEXTO);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), textX, textY);
            }
        };

        button.setFont(FUENTE_BOTON);
        button.setForeground(COLOR_TEXTO);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (width > 0 && height > 0) {
            button.setPreferredSize(new Dimension(width, height));
        } else if (height > 0) {
            button.setPreferredSize(new Dimension(button.getPreferredSize().width, height));
        }

        return button;
    }

    private Border createModernBorder(String title, Color accentColor) {
        return BorderFactory.createCompoundBorder(
                new AbstractBorder() {
                    @Override
                    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                        // Borde con gradiente
                        g2d.setStroke(new BasicStroke(2));
                        g2d.setColor(new Color(accentColor.getRed(), accentColor.getGreen(),
                                accentColor.getBlue(), 150));
                        g2d.drawRoundRect(x, y, width - 1, height - 1, 15, 15);
                    }

                    @Override
                    public Insets getBorderInsets(Component c) {
                        return new Insets(10, 15, 15, 15);
                    }
                },
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        );
    }

    private void updateStatsPanel() {
        if (statsPanel == null) return;

        // Limpiar panel actual
        Component[] components = statsPanel.getComponents();
        for (Component comp : components) {
            if (comp != components[0]) { // Mantener el título
                statsPanel.remove(comp);
            }
        }

        // Crear estadísticas
        model.ListaEnlazada<model.Pair<String, model.Vertice>> vertices =
                AppContext.grafoController.getGrafo().getVertices();

        int totalUsuarios = vertices.tamano();
        int totalConexiones = 0;

        for (model.Pair<String, model.Vertice> par : vertices) {
            totalConexiones += par.getValue().getConexiones().tamano();
        }

        JPanel statsContent = new JPanel(new GridLayout(3, 1, 5, 5));
        statsContent.setOpaque(false);

        statsContent.add(createStatLabel("👥 Usuarios", String.valueOf(totalUsuarios), COLOR_PRIMARIO));
        statsContent.add(createStatLabel("🔗 Conexiones", String.valueOf(totalConexiones), COLOR_EXITO));
        statsContent.add(createStatLabel("📊 Densidad",
                String.format("%.1f%%", (totalConexiones * 100.0) / (totalUsuarios * (totalUsuarios - 1))),
                COLOR_ADVERTENCIA));

        statsPanel.add(statsContent, BorderLayout.CENTER);
        statsPanel.revalidate();
        statsPanel.repaint();
    }

    private JPanel createStatLabel(String icon, String value, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        valueLabel.setForeground(color);
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(iconLabel, BorderLayout.WEST);
        panel.add(valueLabel, BorderLayout.EAST);

        return panel;
    }

    private void setupAnimations() {
        animationTimer = new Timer(16, e -> {
            if (isAnimating) {
                animationProgress += 0.05f;
                if (animationProgress >= 1.0f) {
                    animationProgress = 1.0f;
                    isAnimating = false;
                    animationTimer.stop();
                }
                grafoPanel.repaint();
            }
        });
    }

    private void startAnimation() {
        animationProgress = 0f;
        isAnimating = true;
        animationTimer.start();
    }

    private class GrafoPanel extends JPanel {
        private double escala = 1.0;
        private String nodoHover = null;
        private int radioNodo = 25;
        private Point offset = new Point(0, 0);
        private Random random = new Random();

        public GrafoPanel() {
            setBackground(COLOR_FONDO_CLARO);
            setPreferredSize(new Dimension(600, 500));

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    String anterior = nodoHover;
                    nodoHover = buscarNodoBajoMouse(e.getPoint());

                    if (!Objects.equals(anterior, nodoHover)) {
                        setCursor(nodoHover != null
                                ? new Cursor(Cursor.HAND_CURSOR)
                                : new Cursor(Cursor.DEFAULT_CURSOR));
                        repaint();
                    }
                }
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String nodoClicado = buscarNodoBajoMouse(e.getPoint());
                    if (nodoClicado != null) {
                        nodoSeleccionado = nodoClicado;
                        actualizarInfoPanel(nodoClicado);
                        repaint();
                    }
                }
            });
        }

        public void zoom(double factor) {
            escala *= factor;
            if (escala < 0.3) escala = 0.3;
            if (escala > 3.0) escala = 3.0;
        }

        public void centrarVista() {
            offset.setLocation(0, 0);
            escala = 1.0;
        }

        public void actualizarPosiciones() {
            posiciones.clear();
            model.ListaEnlazada<model.Pair<String, model.Vertice>> vertices =
                    AppContext.grafoController.getGrafo().getVertices();

            int radio = (int)(180 * escala);
            int centroX = getWidth() / 2 + offset.x;
            int centroY = getHeight() / 2 + offset.y;
            int total = vertices.tamano();
            int index = 0;

            for (model.Pair<String, model.Vertice> par : vertices) {
                double angle = 2 * Math.PI * index / total;
                // Añadir algo de variación para hacer el layout más orgánico
                double variacion = random.nextGaussian() * 20;
                int x = centroX + (int) ((radio + variacion) * Math.cos(angle));
                int y = centroY + (int) ((radio + variacion) * Math.sin(angle));
                posiciones.put(par.getKey(), new Point(x, y));
                index++;
            }

            updateStatsPanel();
        }

        private String buscarNodoBajoMouse(Point mousePoint) {
            for (Map.Entry<String, Point> entry : posiciones.entrySet()) {
                Point punto = entry.getValue();
                double distancia = mousePoint.distance(punto);
                if (distancia <= radioNodo) {
                    return entry.getKey();
                }
            }
            return null;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Configuración de renderizado de alta calidad
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            if (posiciones.isEmpty()) {
                actualizarPosiciones();
            }

            model.ListaEnlazada<model.Pair<String, model.Vertice>> vertices =
                    AppContext.grafoController.getGrafo().getVertices();

            // Aplicar transformación de animación
            float alpha = isAnimating ? animationProgress : 1.0f;
            Composite originalComposite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

            // Dibujar aristas con efectos
            dibujarAristas(g2d, vertices);

            // Dibujar nodos con efectos
            dibujarNodos(g2d, vertices);

            g2d.setComposite(originalComposite);
        }

        private void dibujarAristas(Graphics2D g2d, model.ListaEnlazada<model.Pair<String, model.Vertice>> vertices) {
            for (model.Pair<String, model.Vertice> par : vertices) {
                String origenId = par.getKey();
                model.Vertice origenVertice = par.getValue();
                Point origen = posiciones.get(origenId);
                if (origen == null) continue;

                for (model.Vertice destinoVertice : origenVertice.getConexiones()) {
                    String destinoId = destinoVertice.getUsuario().getId();
                    Point destino = posiciones.get(destinoId);
                    if (destino == null) continue;

                    boolean esConexionActiva = origenId.equals(nodoSeleccionado) ||
                            destinoId.equals(nodoSeleccionado);

                    if (esConexionActiva) {
                        // Arista activa con glow
                        g2d.setStroke(new BasicStroke(4));
                        g2d.setColor(new Color(COLOR_ACENTO.getRed(), COLOR_ACENTO.getGreen(),
                                COLOR_ACENTO.getBlue(), 100));
                        g2d.drawLine(origen.x, origen.y, destino.x, destino.y);

                        g2d.setStroke(new BasicStroke(2));
                        g2d.setColor(COLOR_ACENTO);
                        g2d.drawLine(origen.x, origen.y, destino.x, destino.y);
                    } else {
                        // Arista normal
                        g2d.setStroke(new BasicStroke(1.5f));
                        g2d.setColor(new Color(COLOR_TEXTO_SECUNDARIO.getRed(),
                                COLOR_TEXTO_SECUNDARIO.getGreen(),
                                COLOR_TEXTO_SECUNDARIO.getBlue(), 80));
                        g2d.drawLine(origen.x, origen.y, destino.x, destino.y);
                    }
                }
            }
        }

        private void dibujarNodos(Graphics2D g2d, model.ListaEnlazada<model.Pair<String, model.Vertice>> vertices) {
            for (model.Pair<String, model.Vertice> par : vertices) {
                String id = par.getKey();
                Point p = posiciones.get(id);
                if (p == null) continue;

                Color colorNodo;
                int tamanioNodo = radioNodo;

                if (id.equals(nodoSeleccionado)) {
                    colorNodo = COLOR_ACENTO;
                    tamanioNodo = radioNodo + 8;
                } else if (id.equals(nodoHover)) {
                    colorNodo = COLOR_SECUNDARIO;
                    tamanioNodo = radioNodo + 4;
                } else {
                    colorNodo = COLOR_PRIMARIO;
                }

                // Sombra con blur
                for (int i = 8; i > 0; i--) {
                    g2d.setColor(new Color(0, 0, 0, 10));
                    g2d.fillOval(p.x - tamanioNodo + i, p.y - tamanioNodo + i,
                            tamanioNodo * 2, tamanioNodo * 2);
                }

                // Nodo con gradiente radial
                Paint originalPaint = g2d.getPaint();
                RadialGradientPaint gradient = new RadialGradientPaint(
                        new Point2D.Double(p.x - tamanioNodo * 0.3, p.y - tamanioNodo * 0.3),
                        tamanioNodo * 1.2f,
                        new float[]{0.0f, 0.7f, 1.0f},
                        new Color[]{
                                new Color(255, 255, 255, 80),
                                colorNodo,
                                colorNodo.darker()
                        }
                );
                g2d.setPaint(gradient);
                g2d.fillOval(p.x - tamanioNodo, p.y - tamanioNodo, tamanioNodo * 2, tamanioNodo * 2);
                g2d.setPaint(originalPaint);

                // Borde con glow
                g2d.setColor(new Color(colorNodo.getRed(), colorNodo.getGreen(),
                        colorNodo.getBlue(), 150));
                g2d.setStroke(new BasicStroke(3));
                g2d.drawOval(p.x - tamanioNodo - 1, p.y - tamanioNodo - 1,
                        tamanioNodo * 2 + 2, tamanioNodo * 2 + 2);

                g2d.setColor(colorNodo.brighter());
                g2d.setStroke(new BasicStroke(1));
                g2d.drawOval(p.x - tamanioNodo, p.y - tamanioNodo,
                        tamanioNodo * 2, tamanioNodo * 2);

                // Texto del nodo con sombra
                Font font = FUENTE_NODO;
                FontMetrics metrics = g2d.getFontMetrics(font);
                int textWidth = metrics.stringWidth(id);

                g2d.setFont(font);
                // Sombra del texto
                g2d.setColor(new Color(0, 0, 0, 150));
                g2d.drawString(id, p.x - textWidth / 2 + 1, p.y + 6);

                // Texto principal
                g2d.setColor(COLOR_TEXTO);
                g2d.drawString(id, p.x - textWidth / 2, p.y + 5);

                // Indicador de conexiones (pequeño círculo en la esquina)
                model.Vertice vertice = par.getValue();
                int numConexiones = vertice.getConexiones().tamano();
                if (numConexiones > 0) {
                    int indicadorSize = 8;
                    int indicadorX = p.x + tamanioNodo - indicadorSize;
                    int indicadorY = p.y - tamanioNodo;

                    g2d.setColor(COLOR_EXITO);
                    g2d.fillOval(indicadorX, indicadorY, indicadorSize * 2, indicadorSize * 2);

                    g2d.setColor(COLOR_TEXTO);
                    g2d.setFont(new Font("Segoe UI", Font.BOLD, 10));
                    String conexStr = String.valueOf(numConexiones);
                    FontMetrics fmSmall = g2d.getFontMetrics();
                    int conexWidth = fmSmall.stringWidth(conexStr);
                    g2d.drawString(conexStr,
                            indicadorX + indicadorSize - conexWidth / 2,
                            indicadorY + indicadorSize + 4);
                }
            }
        }
    }

    private void actualizarInfoPanel(String id) {
        Usuario usuario = AppContext.usuarioController.buscarUsuario(id);
        if (usuario != null) {
            StringBuilder info = new StringBuilder();
            info.append("<html><body style='font-family: Segoe UI; background: transparent;'>");

            // Header del usuario con estilo
            info.append("<div style='text-align: center; margin-bottom: 15px;'>");
            info.append("<div style='color: #EC4899; font-size: 18px; font-weight: bold; margin-bottom: 5px;'>");
            info.append("👤 ").append(usuario.getNombre()).append("</div>");
            info.append("<div style='color: #94A3B8; font-size: 12px;'>ID: ").append(id).append("</div>");
            info.append("</div>");

            // Sección de intereses
            info.append("<div style='margin-bottom: 15px;'>");
            info.append("<div style='color: #8B5CF6; font-weight: bold; margin-bottom: 8px;'>");
            info.append("🎯 Intereses:</div>");

            model.ListaEnlazada<String> intereses = usuario.getIntereses();
            if (intereses.tamano() > 0) {
                info.append("<div style='color: #F8FAFC;'>");
                int count = 0;
                for (String interes : intereses) {
                    if (count > 0) info.append(" • ");
                    info.append("<span style='background: #4F46E5; padding: 2px 6px; border-radius: 10px; margin: 2px;'>");
                    info.append(interes).append("</span>");
                    count++;
                }
                info.append("</div>");
            } else {
                info.append("<div style='color: #94A3B8; font-style: italic;'>Sin intereses registrados</div>");
            }
            info.append("</div>");

            // Sección de conexiones
            model.ListaEnlazada<model.Pair<String, model.Vertice>> vertices =
                    AppContext.grafoController.getGrafo().getVertices();
            model.Vertice vertice = null;
            for (model.Pair<String, model.Vertice> par : vertices) {
                if (par.getKey().equals(id)) {
                    vertice = par.getValue();
                    break;
                }
            }

            if (vertice != null) {
                int numConexiones = vertice.getConexiones().tamano();
                info.append("<div style='margin-bottom: 15px;'>");
                info.append("<div style='color: #22C55E; font-weight: bold; margin-bottom: 8px;'>");
                info.append("🔗 Conexiones: ").append(numConexiones).append("</div>");

                if (numConexiones > 0) {
                    info.append("<div style='color: #F8FAFC; font-size: 12px;'>");
                    int count = 0;
                    for (model.Vertice conexion : vertice.getConexiones()) {
                        if (count > 0) info.append(", ");
                        info.append(conexion.getUsuario().getId());
                        count++;
                        if (count >= 5) { // Limitar a 5 conexiones mostradas
                            if (numConexiones > 5) {
                                info.append(" y ").append(numConexiones - 5).append(" más...");
                            }
                            break;
                        }
                    }
                    info.append("</div>");
                } else {
                    info.append("<div style='color: #94A3B8; font-style: italic;'>Usuario aislado</div>");
                }
                info.append("</div>");
            }

            // Métricas adicionales
            info.append("<div style='background: rgba(51, 65, 85, 0.3); padding: 10px; border-radius: 8px; margin-top: 10px;'>");
            info.append("<div style='color: #FBBF24; font-weight: bold; margin-bottom: 5px;'>📊 Métricas:</div>");

            if (vertice != null) {
                int totalUsuarios = vertices.tamano();
                double centralidad = (double) vertice.getConexiones().tamano() / (totalUsuarios - 1);
                info.append("<div style='color: #F8FAFC; font-size: 12px;'>");
                info.append("• Centralidad: ").append(String.format("%.1f%%", centralidad * 100)).append("<br>");
                info.append("• Estado: ");
                if (vertice.getConexiones().tamano() == 0) {
                    info.append("<span style='color: #EF4444;'>Aislado</span>");
                } else if (vertice.getConexiones().tamano() >= totalUsuarios * 0.5) {
                    info.append("<span style='color: #22C55E;'>Muy Conectado</span>");
                } else if (vertice.getConexiones().tamano() >= totalUsuarios * 0.25) {
                    info.append("<span style='color: #FBBF24;'>Bien Conectado</span>");
                } else {
                    info.append("<span style='color: #94A3B8;'>Poco Conectado</span>");
                }
                info.append("</div>");
            }
            info.append("</div>");

            info.append("</body></html>");
            lblInfo.setText(info.toString());
        } else {
            lblInfo.setText("<html><div style='text-align: center; color: #EF4444;'>" +
                    "<br>❌<br><br>Usuario no encontrado<br>" +
                    "<span style='font-size: 12px; color: #94A3B8;'>ID: " + id + "</span>" +
                    "</div></html>");
        }
    }
}