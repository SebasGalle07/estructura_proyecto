package view;

import app.AppContext;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;

public class RegistroView extends JFrame {

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

    private Timer animationTimer;
    private float animationProgress = 0f;

    public RegistroView() {
        setupFrame();
        initializeComponents();
        startEntryAnimation();
    }

    private void setupFrame() {
        setTitle("Registro de Usuario");
        setSize(600, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Fondo con gradiente
        setContentPane(new GradientPanel());
        getContentPane().setLayout(new BorderLayout());
    }

    private void initializeComponents() {
        // Panel principal con padding y sombra
        JPanel mainContainer = createMainContainer();

        // Panel de encabezado con icono y título
        JPanel headerPanel = createHeaderPanel();

        // Panel de formulario con campos estilizados
        JPanel formPanel = createFormPanel();

        // Panel de botones con efectos
        JPanel buttonPanel = createButtonPanel();

        // Ensamblar la interfaz
        mainContainer.add(headerPanel, BorderLayout.NORTH);
        mainContainer.add(formPanel, BorderLayout.CENTER);
        mainContainer.add(buttonPanel, BorderLayout.SOUTH);

        add(mainContainer, BorderLayout.CENTER);
    }

    private JPanel createMainContainer() {
        JPanel container = new JPanel(new BorderLayout(0, 30)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Sombra del contenedor principal
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRoundRect(25, 25, getWidth() - 50, getHeight() - 50, 20, 20);

                // Contenedor principal con bordes redondeados
                g2d.setColor(COLOR_TARJETA);
                g2d.fillRoundRect(20, 20, getWidth() - 40, getHeight() - 40, 20, 20);

                // Borde sutil
                g2d.setColor(COLOR_PRIMARIO.darker());
                g2d.drawRoundRect(20, 20, getWidth() - 40, getHeight() - 40, 20, 20);

                g2d.dispose();
            }
        };
        container.setOpaque(false);
        container.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        return container;
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Panel del título con icono
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        titlePanel.setOpaque(false);

        // Icono decorativo
        JLabel iconLabel = new JLabel("👤") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Círculo de fondo para el icono
                g2d.setColor(COLOR_PRIMARIO);
                g2d.fillOval(5, 5, 40, 40);

                // Borde del círculo
                g2d.setColor(COLOR_SECUNDARIO);
                g2d.drawOval(5, 5, 40, 40);

                super.paintComponent(g);
                g2d.dispose();
            }
        };
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        iconLabel.setForeground(COLOR_TEXTO);
        iconLabel.setPreferredSize(new Dimension(50, 50));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Título principal
        JLabel titleLabel = new JLabel("Registro de Usuario");
        titleLabel.setFont(FUENTE_TITULO);
        titleLabel.setForeground(COLOR_TEXTO);

        // Subtítulo
        JLabel subtitleLabel = new JLabel("Crea tu cuenta para comenzar");
        subtitleLabel.setFont(FUENTE_SUBTITULO);
        subtitleLabel.setForeground(COLOR_TEXTO_SECUNDARIO);

        titlePanel.add(iconLabel);
        titlePanel.add(titleLabel);

        header.add(titlePanel, BorderLayout.NORTH);
        header.add(subtitleLabel, BorderLayout.CENTER);

        return header;
    }

    private JPanel createFormPanel() {
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setOpaque(false);
        form.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        // Campos del formulario
        ModernTextField txtId = createModernField("ID de Usuario", "Ingresa tu ID único");
        ModernTextField txtNombre = createModernField("Nombre Completo", "Tu nombre completo");
        ModernTextField txtIntereses = createModernField("Intereses", "Separados por coma (ej: música, deportes)");

        form.add(createFieldContainer("📋 ID de Usuario", txtId));
        form.add(Box.createVerticalStrut(20));
        form.add(createFieldContainer("👤 Nombre Completo", txtNombre));
        form.add(Box.createVerticalStrut(20));
        form.add(createFieldContainer("🎯 Intereses", txtIntereses));

        return form;
    }

    private JPanel createFieldContainer(String labelText, ModernTextField field) {
        JPanel container = new JPanel(new BorderLayout(0, 8));
        container.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(FUENTE_ETIQUETA);
        label.setForeground(COLOR_TEXTO);

        container.add(label, BorderLayout.NORTH);
        container.add(field, BorderLayout.CENTER);

        return container;
    }

    private ModernTextField createModernField(String name, String placeholder) {
        return new ModernTextField(name, placeholder);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));

        ModernButton btnRegistrar = new ModernButton("Registrar", COLOR_PRIMARIO, COLOR_SECUNDARIO);
        ModernButton btnVolver = new ModernButton("Volver", COLOR_FONDO_CLARO, COLOR_TARJETA);

        // Lógica de los botones (simplificada para el ejemplo)
        btnRegistrar.addActionListener(e -> {
            // Aquí iría la lógica de registro
            JOptionPane.showMessageDialog(this, "Funcionalidad de registro implementada",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        btnVolver.addActionListener(e -> {
            new MainView().setVisible(true);
            dispose();
        });


        buttonPanel.add(btnVolver);
        buttonPanel.add(btnRegistrar);

        return buttonPanel;
    }

    private void startEntryAnimation() {
        animationTimer = new Timer(16, e -> {
            animationProgress += 0.05f;
            if (animationProgress >= 1.0f) {
                animationProgress = 1.0f;
                animationTimer.stop();
            }
            repaint();
        });
        animationTimer.start();
    }

    // Clase para panel con gradiente de fondo
    private class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Gradiente de fondo
            GradientPaint gradient = new GradientPaint(
                    0, 0, COLOR_FONDO,
                    0, getHeight(), COLOR_FONDO_CLARO
            );
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // Efectos decorativos
            g2d.setColor(new Color(COLOR_PRIMARIO.getRed(), COLOR_PRIMARIO.getGreen(),
                    COLOR_PRIMARIO.getBlue(), 20));
            g2d.fillOval(-100, -100, 300, 300);
            g2d.fillOval(getWidth() - 200, getHeight() - 200, 300, 300);

            g2d.dispose();
        }
    }

    // Clase para campos de texto modernos
    private class ModernTextField extends JTextField {
        private String placeholder;
        private boolean focused = false;

        public ModernTextField(String name, String placeholder) {
            this.placeholder = placeholder;
            setFont(FUENTE_CAMPO);
            setForeground(COLOR_TEXTO);
            setCaretColor(COLOR_PRIMARIO);
            setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
            setOpaque(false);

            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    focused = true;
                    repaint();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    focused = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Fondo del campo
            if (focused) {
                g2d.setColor(COLOR_FONDO_CLARO);
            } else {
                g2d.setColor(COLOR_FONDO);
            }
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

            // Borde
            if (focused) {
                g2d.setColor(COLOR_PRIMARIO);
                g2d.setStroke(new BasicStroke(2));
            } else {
                g2d.setColor(COLOR_TEXTO_SECUNDARIO.darker());
                g2d.setStroke(new BasicStroke(1));
            }
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);

            g2d.dispose();
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            // No pintar borde predeterminado
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            // Placeholder
            if (getText().isEmpty() && !focused) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(COLOR_TEXTO_SECUNDARIO);
                g2d.setFont(getFont());

                FontMetrics fm = g2d.getFontMetrics();
                int x = getInsets().left;
                int y = (getHeight() + fm.getAscent()) / 2 - 2;

                g2d.drawString(placeholder, x, y);
                g2d.dispose();
            }
        }
    }

    // Clase para botones modernos
    private class ModernButton extends JButton {
        private Color primaryColor;
        private Color hoverColor;
        private boolean hovered = false;
        private float hoverProgress = 0f;
        private Timer hoverTimer;

        public ModernButton(String text, Color primaryColor, Color hoverColor) {
            super(text);
            this.primaryColor = primaryColor;
            this.hoverColor = hoverColor;

            setFont(FUENTE_BOTON);
            setForeground(COLOR_TEXTO);
            setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
            setFocusPainted(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    hovered = true;
                    startHoverAnimation();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hovered = false;
                    startHoverAnimation();
                }
            });
        }

        private void startHoverAnimation() {
            if (hoverTimer != null) hoverTimer.stop();

            hoverTimer = new Timer(16, e -> {
                if (hovered) {
                    hoverProgress = Math.min(1f, hoverProgress + 0.1f);
                } else {
                    hoverProgress = Math.max(0f, hoverProgress - 0.1f);
                }

                if ((hovered && hoverProgress >= 1f) || (!hovered && hoverProgress <= 0f)) {
                    hoverTimer.stop();
                }
                repaint();
            });
            hoverTimer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Color interpolado
            Color currentColor = interpolateColor(primaryColor, hoverColor, hoverProgress);

            // Sombra
            g2d.setColor(new Color(0, 0, 0, (int)(30 * (1 + hoverProgress))));
            g2d.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 12, 12);

            // Fondo del botón
            g2d.setColor(currentColor);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

            // Efecto de brillo
            if (hoverProgress > 0) {
                g2d.setColor(new Color(255, 255, 255, (int)(20 * hoverProgress)));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }

            g2d.dispose();
            super.paintComponent(g);
        }

        private Color interpolateColor(Color c1, Color c2, float progress) {
            int r = (int)(c1.getRed() + (c2.getRed() - c1.getRed()) * progress);
            int g = (int)(c1.getGreen() + (c2.getGreen() - c1.getGreen()) * progress);
            int b = (int)(c1.getBlue() + (c2.getBlue() - c1.getBlue()) * progress);
            return new Color(Math.max(0, Math.min(255, r)),
                    Math.max(0, Math.min(255, g)),
                    Math.max(0, Math.min(255, b)));
        }
    }
}