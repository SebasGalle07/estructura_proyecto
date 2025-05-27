package view;

import app.AppContext;
import model.Usuario;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;

/**
 * Vista moderna de inicio de sesión con diseño contemporáneo
 * Implementa Material Design y efectos visuales suaves
 */
public class LoginView extends JFrame {

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
    private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 32);
    private static final Font FUENTE_SUBTITULO = new Font("Segoe UI Light", Font.PLAIN, 16);
    private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FUENTE_ETIQUETA = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FUENTE_CAMPO = new Font("Segoe UI", Font.PLAIN, 15);

    // Componentes de la interfaz
    private ModernTextField txtId;
    private ModernCheckBox chkModerador;
    private ModernButton btnIngresar;
    private ModernButton btnRegistrar;
    private ModernButton btnVolver;
    private JPanel cardPanel;

    // Controladores y recursos
    private Usuario usuario;
    private Timer animationTimer;
    private ResourceBundle messages;

    public LoginView() {
        loadLocalizedResources("es");
        initializeFrame();
        createComponents();
        setupAnimations();
        setupEventHandlers();
        setVisible(true);
    }

    private void loadLocalizedResources(String language) {
        messages = new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {
                switch (key) {
                    case "login.title": return "Acceso al Sistema";
                    case "login.heading": return "Iniciar Sesión";
                    case "login.subtitle": return "Accede a tu cuenta para continuar";
                    case "login.userid": return "ID de Usuario";
                    case "login.userid.placeholder": return "Ingresa tu identificador";
                    case "login.as_moderator": return "Acceso como Moderador";
                    case "login.enter": return "Iniciar Sesión";
                    case "login.register": return "Crear Cuenta";
                    case "login.back": return "Volver";
                    case "login.info": return "¿Primera vez aquí? Crea tu cuenta para empezar";
                    case "login.error.empty_id": return "El campo ID de usuario es obligatorio";
                    case "login.error.empty_id.title": return "Campo Requerido";
                    case "login.error.wrong_moderator": return "Credenciales de moderador incorrectas";
                    case "login.error.wrong_moderator.title": return "Error de Autenticación";
                    case "login.error.user_not_found": return "Usuario no encontrado. Verifica tu ID o regístrate";
                    case "login.error.user_not_found.title": return "Usuario No Encontrado";
                    default: return null;
                }
            }

            @Override
            public java.util.Enumeration<String> getKeys() {
                return null;
            }
        };
    }

    private void initializeFrame() {
        setTitle(messages.getString("login.title"));
        setSize(500, 650);
        setMinimumSize(new Dimension(450, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        // Fondo degradado para toda la ventana
        setContentPane(new GradientPanel());
        getContentPane().setLayout(new BorderLayout());
    }

    private void createComponents() {
        // Panel principal centrado
        JPanel mainContainer = new JPanel(new GridBagLayout());
        mainContainer.setOpaque(false);

        // Card principal con sombra
        cardPanel = createMainCard();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);

        mainContainer.add(cardPanel, gbc);
        add(mainContainer, BorderLayout.CENTER);

        // Pie de página opcional
        add(createFooter(), BorderLayout.SOUTH);
    }

    private JPanel createMainCard() {
        RoundedPanel card = new RoundedPanel(25);
        card.setBackground(COLOR_TARJETA);
        card.setLayout(new BorderLayout(20, 20)); // Espaciado interno vertical y horizontal
        card.setPreferredSize(new Dimension(600, 750)); // Altura ajustada para evitar recortes

        card.setBorder(new ShadowBorder());

        card.add(createHeader(), BorderLayout.NORTH);
        card.add(createForm(), BorderLayout.CENTER);
        card.add(createButtonPanel(), BorderLayout.SOUTH);

        return card;
    }


    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(BorderFactory.createEmptyBorder(40, 30, 30, 30));

        // Icono decorativo
        JLabel iconLabel = new JLabel("🔐");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Título principal
        JLabel titleLabel = new JLabel(messages.getString("login.heading"));
        titleLabel.setFont(FUENTE_TITULO);
        titleLabel.setForeground(COLOR_TEXTO);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtítulo
        JLabel subtitleLabel = new JLabel(messages.getString("login.subtitle"));
        subtitleLabel.setFont(FUENTE_SUBTITULO);
        subtitleLabel.setForeground(COLOR_TEXTO_SECUNDARIO);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(iconLabel);
        header.add(Box.createVerticalStrut(15));
        header.add(titleLabel);
        header.add(Box.createVerticalStrut(8));
        header.add(subtitleLabel);

        return header;
    }

    private JPanel createForm() {
        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));

        // Campo de ID de usuario
        JLabel userIdLabel = new JLabel(messages.getString("login.userid"));
        userIdLabel.setFont(FUENTE_ETIQUETA);
        userIdLabel.setForeground(COLOR_TEXTO);
        userIdLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtId = new ModernTextField(messages.getString("login.userid.placeholder"));
        txtId.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Checkbox de moderador
        chkModerador = new ModernCheckBox(messages.getString("login.as_moderator"));
        chkModerador.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Mensaje informativo (centrado, sin cortar)
        JLabel infoLabel = new JLabel("<html><div style='text-align: center;'>"
                + messages.getString("login.info") + "</div></html>");
        infoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        infoLabel.setForeground(COLOR_TEXTO_SECUNDARIO);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Orden y espaciado
        form.add(userIdLabel);
        form.add(Box.createVerticalStrut(8));
        form.add(txtId);
        form.add(Box.createVerticalStrut(25));
        form.add(chkModerador);
        form.add(Box.createVerticalStrut(30));
        form.add(infoLabel);

        return form;
    }


    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 40, 30));

        // Botón principal
        btnIngresar = new ModernButton(messages.getString("login.enter"), ModernButton.ButtonType.PRIMARY);
        btnIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIngresar.setMaximumSize(new Dimension(300, 45)); // Uniforme

        // Panel de botones secundarios
        JPanel secondaryButtons = new JPanel(new GridLayout(1, 2, 20, 0));
        secondaryButtons.setOpaque(false);
        secondaryButtons.setMaximumSize(new Dimension(300, 45));  // Ancho fijo

        secondaryButtons.setOpaque(false);

        btnRegistrar = new ModernButton(messages.getString("login.register"), ModernButton.ButtonType.SECONDARY);
        btnVolver = new ModernButton(messages.getString("login.back"), ModernButton.ButtonType.OUTLINE);

        Dimension secondarySize = new Dimension(140, 40); // Igual ancho y altura para ambos
        btnRegistrar.setPreferredSize(secondarySize);
        btnRegistrar.setMaximumSize(secondarySize);

        btnVolver.setPreferredSize(secondarySize);
        btnVolver.setMaximumSize(secondarySize);

        secondaryButtons.add(btnRegistrar);
        secondaryButtons.add(btnVolver);

        buttonPanel.add(btnIngresar);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(secondaryButtons);

        return buttonPanel;
    }



    private JPanel createFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setOpaque(false);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));

        JLabel footerLabel = new JLabel("Sistema de Gestión Académica © 2024");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(COLOR_TEXTO_SECUNDARIO);

        footer.add(footerLabel);
        return footer;
    }

    private void setupAnimations() {
        // Animación de entrada suave para el card principal
        Timer slideInTimer = new Timer(10, null);
        slideInTimer.addActionListener(new ActionListener() {
            private int step = 0;
            private final int maxSteps = 30;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (step < maxSteps) {
                    float progress = (float) step / maxSteps;
                    float easeOut = 1 - (1 - progress) * (1 - progress);

                    cardPanel.setLocation(
                            cardPanel.getX(),
                            (int) (50 * (1 - easeOut))
                    );

                    // Efecto de fade in
                    float alpha = easeOut;
                    if (cardPanel instanceof RoundedPanel) {
                        ((RoundedPanel) cardPanel).setAlpha(alpha);
                    }

                    repaint();
                    step++;
                } else {
                    slideInTimer.stop();
                }
            }
        });

        // Iniciar animación después de que la ventana sea visible
        SwingUtilities.invokeLater(() -> slideInTimer.start());
    }

    private void setupEventHandlers() {
        // Enter en el campo de texto
        txtId.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();
                }
            }
        });

        // Botones
        btnIngresar.addActionListener(e -> handleLogin());
        btnRegistrar.addActionListener(e -> navigateToRegister());
        btnVolver.addActionListener(e -> navigateToMain());

        // Atajos de teclado
        setupKeyboardShortcuts();
    }

    private void setupKeyboardShortcuts() {
        // Mapeo de teclas
        JRootPane rootPane = getRootPane();

        // Enter para login
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "login");
        rootPane.getActionMap().put("login", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtId.getText().trim().length() > 0) {
                    handleLogin();
                }
            }
        });

        // Escape para volver
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "back");
        rootPane.getActionMap().put("back", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateToMain();
            }
        });
    }

    private void handleLogin() {
        String id = txtId.getText().trim();

        if (id.isEmpty()) {
            showErrorMessage(
                    messages.getString("login.error.empty_id"),
                    messages.getString("login.error.empty_id.title")
            );
            txtId.requestFocus();
            return;
        }

        // Animación de loading en el botón
        btnIngresar.setLoading(true);

        // Simular delay de autenticación
        Timer delayTimer = new Timer(1000, e -> {
            btnIngresar.setLoading(false);

            if (chkModerador.isSelected()) {
                handleModeratorLogin(id);
            } else {
                handleStudentLogin(id);
            }
        });
        delayTimer.setRepeats(false);
        delayTimer.start();
    }

    private void handleModeratorLogin(String id) {
        if (id.equalsIgnoreCase("admin")) {
            // Animación de éxito
            showSuccessAnimation();
            Timer transition = new Timer(1500, e -> {
                new PanelModeradorView().setVisible(true);
                dispose();
            });
            transition.setRepeats(false);
            transition.start();
        } else {
            showErrorMessage(
                    messages.getString("login.error.wrong_moderator"),
                    messages.getString("login.error.wrong_moderator.title")
            );
            txtId.selectAll();
            txtId.requestFocus();
        }
    }

    private void handleStudentLogin(String id) {
        Usuario usuario = AppContext.usuarioController.buscarUsuario(id);
        if (usuario != null) {
            showSuccessAnimation();
            Timer transition = new Timer(1500, e -> {
                new PanelEstudianteView(usuario).setVisible(true);
                dispose();
            });
            transition.setRepeats(false);
            transition.start();
        } else {
            showErrorMessage(
                    messages.getString("login.error.user_not_found"),
                    messages.getString("login.error.user_not_found.title")
            );
            txtId.selectAll();
            txtId.requestFocus();
        }
    }

    private void navigateToMain() {
        new MainView().setVisible(true);
        dispose();
    }

    private void navigateToRegister() {
        new RegistroView().setVisible(true);
        dispose();
    }

    private void showErrorMessage(String message, String title) {
        // Crear dialog personalizado
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(350, 180);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JPanel content = new JPanel(new BorderLayout(15, 15));
        content.setBackground(COLOR_FONDO_CLARO);
        content.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Icono de error
        JLabel iconLabel = new JLabel("⚠️");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Mensaje
        JLabel messageLabel = new JLabel("<html><center>" + message + "</center></html>");
        messageLabel.setFont(FUENTE_SUBTITULO);
        messageLabel.setForeground(COLOR_TEXTO);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Botón OK
        ModernButton okButton = new ModernButton("Entendido", ModernButton.ButtonType.PRIMARY);
        okButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(okButton);

        content.add(iconLabel, BorderLayout.WEST);
        content.add(messageLabel, BorderLayout.CENTER);
        content.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(content);
        dialog.setVisible(true);
    }

    private void showSuccessAnimation() {
        // Cambiar color del card temporalmente
        Timer successTimer = new Timer(100, null);
        successTimer.addActionListener(new ActionListener() {
            private int pulses = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (pulses < 3) {
                    cardPanel.setBackground(pulses % 2 == 0 ? COLOR_EXITO : COLOR_TARJETA);
                    repaint();
                    pulses++;
                } else {
                    successTimer.stop();
                    cardPanel.setBackground(COLOR_TARJETA);
                    repaint();
                }
            }
        });
        successTimer.start();
    }

    private class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            GradientPaint gradient = new GradientPaint(
                    0, 0, COLOR_FONDO,
                    0, getHeight(), COLOR_FONDO_CLARO
            );

            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private class RoundedPanel extends JPanel {
        private int radius;
        private float alpha = 1.0f;

        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        public void setAlpha(float alpha) {
            this.alpha = alpha;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Aplicar transparencia
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

            super.paintComponent(g);
        }
    }

    private class ShadowBorder implements Border {
        private final int shadowSize = 8;
        private final Color shadowColor = new Color(0, 0, 0, 50);

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Dibujar sombra
            for (int i = 0; i < shadowSize; i++) {
                g2d.setColor(new Color(0, 0, 0, 20 - (i * 2)));
                g2d.drawRoundRect(
                        x + shadowSize - i, y + shadowSize - i,
                        width - shadowSize + i, height - shadowSize + i,
                        25, 25
                );
            }
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(shadowSize, shadowSize, shadowSize, shadowSize);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }

    private class ModernTextField extends JTextField {
        private String placeholder;
        private boolean focused = false;

        public ModernTextField(String placeholder) {
            this.placeholder = placeholder;
            setFont(FUENTE_CAMPO);
            setBackground(COLOR_FONDO_CLARO);
            setForeground(COLOR_TEXTO);
            setCaretColor(COLOR_PRIMARIO);
            setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(12, COLOR_FONDO_CLARO),
                    BorderFactory.createEmptyBorder(12, 15, 12, 15)
            ));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            setPreferredSize(new Dimension(getPreferredSize().width, 45));

            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    focused = true;
                    setBorder(BorderFactory.createCompoundBorder(
                            new RoundedBorder(12, COLOR_PRIMARIO),
                            BorderFactory.createEmptyBorder(12, 15, 12, 15)
                    ));
                    repaint();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    focused = false;
                    setBorder(BorderFactory.createCompoundBorder(
                            new RoundedBorder(12, COLOR_FONDO_CLARO),
                            BorderFactory.createEmptyBorder(12, 15, 12, 15)
                    ));
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (getText().isEmpty() && !focused) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(COLOR_TEXTO_SECUNDARIO);
                g2d.setFont(getFont());

                FontMetrics fm = g2d.getFontMetrics();
                int y = (getHeight() + fm.getAscent()) / 2 - 2;
                g2d.drawString(placeholder, 15, y);
            }
        }
    }

    private class ModernCheckBox extends JCheckBox {
        public ModernCheckBox(String text) {
            super(text);
            setFont(FUENTE_ETIQUETA);
            setForeground(COLOR_TEXTO);
            setOpaque(false);
            setFocusPainted(false);

            setIcon(createCheckboxIcon(false));
            setSelectedIcon(createCheckboxIcon(true));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });
        }

        private Icon createCheckboxIcon(boolean selected) {
            return new Icon() {
                @Override
                public void paintIcon(Component c, Graphics g, int x, int y) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Fondo del checkbox
                    g2d.setColor(selected ? COLOR_PRIMARIO : COLOR_FONDO_CLARO);
                    g2d.fillRoundRect(x, y, 18, 18, 4, 4);

                    // Borde
                    g2d.setColor(selected ? COLOR_PRIMARIO : COLOR_TEXTO_SECUNDARIO);
                    g2d.drawRoundRect(x, y, 18, 18, 4, 4);

                    // Checkmark
                    if (selected) {
                        g2d.setColor(Color.WHITE);
                        g2d.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        g2d.drawLine(x + 4, y + 9, x + 7, y + 12);
                        g2d.drawLine(x + 7, y + 12, x + 14, y + 5);
                    }
                }

                @Override
                public int getIconWidth() { return 18; }

                @Override
                public int getIconHeight() { return 18; }
            };
        }
    }

    private class ModernButton extends JButton {
        public enum ButtonType {
            PRIMARY, SECONDARY, OUTLINE
        }

        private ButtonType type;
        private boolean loading = false;
        private Timer loadingTimer;
        private int loadingStep = 0;

        public ModernButton(String text, ButtonType type) {
            super(text);
            this.type = type;

            setFont(FUENTE_BOTON);
            setFocusPainted(false);
            setBorderPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            setupButtonStyle();
            setupButtonEffects();

            setPreferredSize(new Dimension(getPreferredSize().width + 30, 45));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        }

        private void setupButtonStyle() {
            switch (type) {
                case PRIMARY:
                    setBackground(COLOR_PRIMARIO);
                    setForeground(Color.WHITE);
                    break;
                case SECONDARY:
                    setBackground(COLOR_SECUNDARIO);
                    setForeground(Color.WHITE);
                    break;
                case OUTLINE:
                    setBackground(new Color(0, 0, 0, 0)); // Color completamente transparente
                    setForeground(COLOR_TEXTO);
                    setBorder(new RoundedBorder(12, COLOR_TEXTO_SECUNDARIO));
                    setBorderPainted(true);
                    break;
            }
        }

        private void setupButtonEffects() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!loading) {
                        switch (type) {
                            case PRIMARY:
                                setBackground(COLOR_PRIMARIO.brighter());
                                break;
                            case SECONDARY:
                                setBackground(COLOR_SECUNDARIO.brighter());
                                break;
                            case OUTLINE:
                                setBackground(new Color(COLOR_TEXTO.getRed(), COLOR_TEXTO.getGreen(),
                                        COLOR_TEXTO.getBlue(), 20));
                                break;
                        }
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (!loading) {
                        setupButtonStyle();
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (!loading) {
                        switch (type) {
                            case PRIMARY:
                                setBackground(COLOR_PRIMARIO.darker());
                                break;
                            case SECONDARY:
                                setBackground(COLOR_SECUNDARIO.darker());
                                break;
                            case OUTLINE:
                                setBackground(new Color(COLOR_TEXTO.getRed(), COLOR_TEXTO.getGreen(),
                                        COLOR_TEXTO.getBlue(), 40));
                                break;
                        }
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (!loading) {
                        setupButtonStyle();
                    }
                }
            });
        }

        public void setLoading(boolean loading) {
            this.loading = loading;
            setEnabled(!loading);

            if (loading) {
                setText("Iniciando...");
                startLoadingAnimation();
            } else {
                stopLoadingAnimation();
                // Restaurar texto original basado en el tipo
                switch (type) {
                    case PRIMARY:
                        setText(messages.getString("login.enter"));
                        break;
                    case SECONDARY:
                        setText(messages.getString("login.register"));
                        break;
                    case OUTLINE:
                        setText(messages.getString("login.back"));
                        break;
                }
            }
        }

        private void startLoadingAnimation() {
            if (loadingTimer != null) {
                loadingTimer.stop();
            }

            loadingTimer = new Timer(200, e -> {
                loadingStep = (loadingStep + 1) % 4;
                String dots = "";
                for (int i = 0; i < loadingStep; i++) {
                    dots += "•";
                }
                setText("Iniciando" + dots);
            });
            loadingTimer.start();
        }

        private void stopLoadingAnimation() {
            if (loadingTimer != null) {
                loadingTimer.stop();
                loadingTimer = null;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Pintar fondo redondeado
            if (type != ButtonType.OUTLINE) {
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }

            super.paintComponent(g);

            // Efecto de loading
            if (loading) {
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }
        }
    }

    private class RoundedBorder implements Border {
        private int radius;
        private Color color;

        public RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(2, 2, 2, 2);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }
}