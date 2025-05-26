package view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ResourceBundle;

/**
 * Vista principal moderna con diseño dark theme y efectos visuales avanzados
 * Red Social de Aprendizaje Colaborativo - Versión Moderna
 */
public class MainView extends JFrame {

    // Paleta de colores moderna (Dark Theme)
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

    // Componentes principales
    private JPanel mainPanel;
    private ModernButton btnRegistro;
    private ModernButton btnLogin;
    private ModernButton btnSalir;
    private JLabel iconoApp;
    private Timer animationTimer;
    private int animationFrame = 0;

    // Soporte para internacionalización
    private ResourceBundle messages;

    public MainView() {
        loadLocalizedResources("es");
        initModernUI();
        setupAnimations();
        setupEventHandlers();

        // Configuración de la ventana
        setTitle("Red Social de Aprendizaje Colaborativo");
        setMinimumSize(new Dimension(600, 700));
        setPreferredSize(new Dimension(800, 900));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        pack();
    }

    private void loadLocalizedResources(String language) {
        messages = new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {
                switch (key) {
                    case "app.title": return "Red Social de Aprendizaje";
                    case "app.subtitle": return "Conecta, aprende y colabora";
                    case "app.description": return "Únete a una comunidad global de aprendizaje colaborativo";
                    case "button.register": return "Crear Cuenta";
                    case "button.login": return "Iniciar Sesión";
                    case "button.exit": return "Salir";
                    case "footer.version": return "Versión 2.0 - Desarrollado con ❤️";
                    case "exit.confirm.title": return "Confirmar Salida";
                    case "exit.confirm.message": return "¿Estás seguro que deseas cerrar la aplicación?";
                    default: return key;
                }
            }

            @Override
            public java.util.Enumeration<String> getKeys() {
                return null;
            }
        };
    }

    private void initModernUI() {
        // Panel principal con gradiente
        mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout());

        // Header con logo y título
        JPanel headerPanel = createHeaderPanel();

        // Panel central con botones
        JPanel centerPanel = createCenterPanel();

        // Footer moderno
        JPanel footerPanel = createFooterPanel();

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(BorderFactory.createEmptyBorder(40, 30, 30, 30));

        // Icono de la aplicación (simulado con formas geométricas)
        iconoApp = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int size = 80;
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;

                // Crear gradiente para el icono
                GradientPaint gradient = new GradientPaint(
                        x, y, COLOR_PRIMARIO,
                        x + size, y + size, COLOR_SECUNDARIO
                );
                g2d.setPaint(gradient);

                // Dibujar círculo con efecto de rotación suave
                double rotation = Math.toRadians(animationFrame * 2);
                g2d.rotate(rotation, x + size/2, y + size/2);

                // Círculo principal
                g2d.fillOval(x, y, size, size);

                // Elementos decorativos
                g2d.setColor(COLOR_ACENTO);
                g2d.fillOval(x + 15, y + 15, 20, 20);
                g2d.fillOval(x + 45, y + 25, 15, 15);
                g2d.fillOval(x + 25, y + 45, 12, 12);

                g2d.dispose();
            }
        };
        iconoApp.setPreferredSize(new Dimension(100, 100));
        iconoApp.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Título principal con efecto de brillo
        JLabel titulo = new JLabel(messages.getString("app.title"));
        titulo.setFont(FUENTE_TITULO);
        titulo.setForeground(COLOR_TEXTO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0));

        // Subtítulo
        JLabel subtitulo = new JLabel(messages.getString("app.subtitle"));
        subtitulo.setFont(FUENTE_SUBTITULO);
        subtitulo.setForeground(COLOR_TEXTO_SECUNDARIO);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Descripción
        JLabel descripcion = new JLabel(messages.getString("app.description"));
        descripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descripcion.setForeground(COLOR_TEXTO_SECUNDARIO);
        descripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        descripcion.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        header.add(iconoApp);
        header.add(titulo);
        header.add(subtitulo);
        header.add(descripcion);

        return header;
    }

    private JPanel createCenterPanel() {
        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Crear botones modernos
        btnRegistro = new ModernButton(messages.getString("button.register"), COLOR_PRIMARIO, COLOR_SECUNDARIO);
        btnLogin = new ModernButton(messages.getString("button.login"), COLOR_SECUNDARIO, COLOR_PRIMARIO);
        btnSalir = new ModernButton(messages.getString("button.exit"), COLOR_TARJETA, COLOR_FONDO_CLARO);

        // Configurar botones
        Dimension buttonSize = new Dimension(300, 55);

        btnRegistro.setMaximumSize(buttonSize);
        btnRegistro.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnLogin.setMaximumSize(buttonSize);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnSalir.setMaximumSize(buttonSize);
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setForeground(COLOR_TEXTO_SECUNDARIO);

        // Espaciado entre botones
        center.add(Box.createVerticalStrut(20));
        center.add(btnRegistro);
        center.add(Box.createVerticalStrut(15));
        center.add(btnLogin);
        center.add(Box.createVerticalStrut(15));
        center.add(btnSalir);
        center.add(Box.createVerticalStrut(20));

        return center;
    }

    private JPanel createFooterPanel() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setOpaque(false);
        footer.setBorder(BorderFactory.createEmptyBorder(20, 20, 30, 20));

        JLabel footerText = new JLabel(messages.getString("footer.version"));
        footerText.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerText.setForeground(COLOR_TEXTO_SECUNDARIO);

        footer.add(footerText);
        return footer;
    }

    private void setupAnimations() {
        animationTimer = new Timer(50, e -> {
            animationFrame++;
            if (animationFrame >= 180) animationFrame = 0;
            iconoApp.repaint();
        });
        animationTimer.start();
    }

    private void setupEventHandlers() {
        // Eventos de botones
        btnRegistro.addActionListener(e -> openRegistroView());
        btnLogin.addActionListener(e -> openLoginView());
        btnSalir.addActionListener(e -> confirmExit());

        // Manejador de cierre de ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmExit();
            }
        });

        // Atajos de teclado
        setupKeyboardShortcuts();
    }

    private void setupKeyboardShortcuts() {
        // Configurar atajos
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK), "registro");
        getRootPane().getActionMap().put("registro", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegistroView();
            }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK), "login");
        getRootPane().getActionMap().put("login", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginView();
            }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "salir");
        getRootPane().getActionMap().put("salir", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmExit();
            }
        });
    }

    private void openRegistroView() {
        new RegistroView().setVisible(true);
        dispose();
    }



    private void openLoginView() {
        JOptionPane.showMessageDialog(this,
                "Abriendo vista de login...",
                "Navegación",
                JOptionPane.INFORMATION_MESSAGE);
        new LoginView().setVisible(true);
        dispose();
    }

    private void confirmExit() {
        // Crear diálogo personalizado
        JDialog dialog = new JDialog(this, messages.getString("exit.confirm.title"), true);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(COLOR_FONDO_CLARO);

        // Panel de mensaje
        JPanel messagePanel = new JPanel(new FlowLayout());
        messagePanel.setOpaque(false);
        messagePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel messageLabel = new JLabel(messages.getString("exit.confirm.message"));
        messageLabel.setForeground(COLOR_TEXTO);
        messageLabel.setFont(FUENTE_ETIQUETA);
        messagePanel.add(messageLabel);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        ModernButton btnSi = new ModernButton("Sí", COLOR_ERROR, COLOR_ERROR.brighter());
        ModernButton btnNo = new ModernButton("No", COLOR_TARJETA, COLOR_FONDO_CLARO);

        btnSi.setPreferredSize(new Dimension(80, 35));
        btnNo.setPreferredSize(new Dimension(80, 35));

        btnSi.addActionListener(e -> {
            dialog.dispose();
            animationTimer.stop();
            System.exit(0);
        });

        btnNo.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnNo);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(btnSi);

        dialog.add(messagePanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setSize(350, 150);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // Clase para panel con gradiente de fondo
    private class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Gradiente principal
            GradientPaint gradient = new GradientPaint(
                    0, 0, COLOR_FONDO,
                    0, getHeight(), COLOR_FONDO_CLARO
            );
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // Efectos de luz suaves
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));

            // Círculos decorativos con efecto de pulso
            int pulse = (int)(Math.sin(animationFrame * 0.1) * 20 + 50);
            g2d.setColor(COLOR_PRIMARIO);
            g2d.fillOval(getWidth() - 200, -100, pulse + 150, pulse + 150);

            g2d.setColor(COLOR_SECUNDARIO);
            g2d.fillOval(-100, getHeight() - 200, pulse + 100, pulse + 100);

            g2d.setColor(COLOR_ACENTO);
            g2d.fillOval(getWidth() - 100, getHeight() - 150, pulse + 80, pulse + 80);

            g2d.dispose();
        }
    }

    // Clase para botones modernos con efectos
    private class ModernButton extends JButton {
        private Color baseColor;
        private Color hoverColor;
        private boolean isHovered = false;
        private Timer hoverTimer;
        private float hoverAlpha = 0.0f;

        public ModernButton(String text, Color baseColor, Color hoverColor) {
            super(text);
            this.baseColor = baseColor;
            this.hoverColor = hoverColor;

            setForeground(COLOR_TEXTO);
            setFont(FUENTE_BOTON);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Configurar animaciones de hover
            setupHoverAnimation();
        }

        private void setupHoverAnimation() {
            hoverTimer = new Timer(20, e -> {
                if (isHovered && hoverAlpha < 1.0f) {
                    hoverAlpha += 0.1f;
                } else if (!isHovered && hoverAlpha > 0.0f) {
                    hoverAlpha -= 0.1f;
                }

                if (hoverAlpha < 0) hoverAlpha = 0;
                if (hoverAlpha > 1) hoverAlpha = 1;

                repaint();

                if ((isHovered && hoverAlpha >= 1.0f) || (!isHovered && hoverAlpha <= 0.0f)) {
                    hoverTimer.stop();
                }
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    hoverTimer.start();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    hoverTimer.start();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Interpolación de colores para transición suave
            Color currentColor = interpolateColor(baseColor, hoverColor, hoverAlpha);

            // Gradiente del botón
            GradientPaint gradient = new GradientPaint(
                    0, 0, currentColor,
                    0, getHeight(), currentColor.darker()
            );
            g2d.setPaint(gradient);

            // Crear forma redondeada
            RoundRectangle2D roundRect = new RoundRectangle2D.Float(
                    0, 0, getWidth(), getHeight(), 12, 12
            );
            g2d.fill(roundRect);

            // Efecto de sombra
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            g2d.setColor(Color.BLACK);
            g2d.fill(new RoundRectangle2D.Float(2, 2, getWidth(), getHeight(), 12, 12));

            g2d.dispose();
            super.paintComponent(g);
        }

        private Color interpolateColor(Color c1, Color c2, float fraction) {
            int red = (int)(c1.getRed() + fraction * (c2.getRed() - c1.getRed()));
            int green = (int)(c1.getGreen() + fraction * (c2.getGreen() - c1.getGreen()));
            int blue = (int)(c1.getBlue() + fraction * (c2.getBlue() - c1.getBlue()));
            return new Color(red, green, blue);
        }
    }

}