package view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;

/**
 * Vista principal de la aplicación Red Social de Aprendizaje Colaborativo
 * Clase mejorada con soporte para accesibilidad, internacionalización y diseño responsive
 */
public class MainView extends JFrame {
    // Extraemos las constantes de estilo a una clase de utilidad (referencia)
    private static final StyleConstants STYLE = StyleConstants.getInstance();

    // Soporte para internacionalización
    private ResourceBundle messages;

    // Componentes principales
    private JPanel mainPanel;
    private JButton btnRegistro;
    private JButton btnLogin;
    private JButton btnSalir;

    /**
     * Constructor principal de la vista
     */
    public MainView() {
        // Cargar textos localizados (por defecto en español)
        loadLocalizedResources("es");

        // Configuración básica de la ventana
        setTitle(messages.getString("app.title"));
        setMinimumSize(new Dimension(500, 400));
        setPreferredSize(new Dimension(600, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Permitir redimensionamiento para mejor experiencia en diferentes pantallas
        setResizable(true);

        // Inicializar componentes de la interfaz
        initComponents();

        // Configurar manejadores de eventos
        setupEventHandlers();

        // Configurar accesibilidad
        setupAccessibility();

        // Empaquetar y ajustar tamaño automáticamente
        pack();
    }

    /**
     * Carga los recursos de texto localizados según el idioma especificado
     * @param language código de idioma (ej: "es" para español)
     */
    private void loadLocalizedResources(String language) {
        try {
            // En una implementación real, cargaría desde un archivo .properties
            // Por ahora usamos un bundle simulado
            messages = new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    switch (key) {
                        case "app.title": return "Red Social de Aprendizaje Colaborativo";
                        case "main.title": return "Red Social de Aprendizaje";
                        case "main.subtitle": return "Colaboración y conocimiento compartido";
                        case "button.register": return "Registrarse";
                        case "button.login": return "Iniciar Sesión";
                        case "button.exit": return "Salir";
                        case "footer.copyright": return "© 2025 - Sistema de Aprendizaje Colaborativo";
                        case "exit.confirm.title": return "Confirmar salida";
                        case "exit.confirm.message": return "¿Estás seguro que deseas salir de la aplicación?";
                        default: return null;
                    }
                }

                @Override
                public java.util.Enumeration<String> getKeys() {
                    return null; // No implementado para este ejemplo
                }
            };
        } catch (Exception e) {
            System.err.println("Error cargando recursos de idioma: " + e.getMessage());
            // Fallback a textos por defecto
        }
    }

    /**
     * Inicializa y configura todos los componentes de la interfaz
     */
    private void initComponents() {
        // Panel principal con BorderLayout para mejor organización y adaptabilidad
        mainPanel = new JPanel(new BorderLayout(0, 10));
        mainPanel.setBackground(STYLE.COLOR_FONDO);

        // Añadir los tres paneles principales
        mainPanel.add(createTitlePanel(), BorderLayout.NORTH);
        mainPanel.add(createButtonPanel(), BorderLayout.CENTER);
        mainPanel.add(createFooterPanel(), BorderLayout.SOUTH);

        // Agregar panel principal al frame con margen
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);
    }

    /**
     * Crea el panel de título en la parte superior
     */
    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(STYLE.COLOR_HEADER);
        titlePanel.setBorder(new EmptyBorder(25, 20, 25, 20));

        // Título principal
        JLabel lblTitulo = new JLabel(messages.getString("main.title"), SwingConstants.CENTER);
        lblTitulo.setFont(STYLE.FUENTE_TITULO);
        lblTitulo.setForeground(STYLE.COLOR_TEXTO);

        // Subtítulo
        JLabel lblSubtitulo = new JLabel(messages.getString("main.subtitle"), SwingConstants.CENTER);
        lblSubtitulo.setFont(STYLE.FUENTE_SUBTITULO);
        lblSubtitulo.setForeground(STYLE.COLOR_TEXTO);

        // Panel para agrupar título y subtítulo con espaciado
        JPanel titleTextPanel = new JPanel(new BorderLayout(0, 5));
        titleTextPanel.setOpaque(false);
        titleTextPanel.add(lblTitulo, BorderLayout.CENTER);
        titleTextPanel.add(lblSubtitulo, BorderLayout.SOUTH);

        titlePanel.add(titleTextPanel, BorderLayout.CENTER);

        return titlePanel;
    }

    /**
     * Crea el panel central con los botones
     */
    private JPanel createButtonPanel() {
        // Panel con GridBagLayout para mejor adaptabilidad a diferentes tamaños
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(STYLE.COLOR_FONDO);

        // Crear botones principales con estilos
        btnRegistro = createStyledButton(messages.getString("button.register"), STYLE.COLOR_BOTON_PRIMARIO);
        btnLogin = createStyledButton(messages.getString("button.login"), STYLE.COLOR_BOTON_SECUNDARIO);
        btnSalir = createStyledButton(messages.getString("button.exit"), STYLE.COLOR_BOTON_SALIR);

        // Configurar disposición de los botones con GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 80, 10, 80);

        // Añadir los botones al panel
        buttonPanel.add(btnRegistro, gbc);
        buttonPanel.add(btnLogin, gbc);
        buttonPanel.add(btnSalir, gbc);

        return buttonPanel;
    }

    /**
     * Crea el panel de pie de página
     */
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(235, 245, 250));
        footerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblFooter = new JLabel(messages.getString("footer.copyright"));
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblFooter.setForeground(new Color(100, 100, 100));

        footerPanel.add(lblFooter);

        return footerPanel;
    }

    /**
     * Crea un botón estilizado con el color especificado
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(STYLE.FUENTE_BOTON);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Mejorar el contraste para accesibilidad
        if (isLightColor(bgColor)) {
            button.setForeground(Color.BLACK);
        }

        // Efecto de sombra suave
        button.setBorder(BorderFactory.createCompoundBorder(
                new SoftBevelBorder(SoftBevelBorder.RAISED),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        return button;
    }

    /**
     * Determina si un color es claro (para ajustar contraste del texto)
     */
    private boolean isLightColor(Color color) {
        // Fórmula para calcular la luminosidad percibida
        double luminance = (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue()) / 255;
        return luminance > 0.5;
    }

    /**
     * Configura los manejadores de eventos para los botones
     */
    private void setupEventHandlers() {
        // Utilizar clases de listener definidas en lugar de clases anónimas
        ButtonHoverListener hoverListener = new ButtonHoverListener();

        // Listener para efecto hover en botones
        btnRegistro.addMouseListener(new ButtonHoverListener(btnRegistro, STYLE.COLOR_BOTON_PRIMARIO));
        btnLogin.addMouseListener(new ButtonHoverListener(btnLogin, STYLE.COLOR_BOTON_SECUNDARIO));
        btnSalir.addMouseListener(new ButtonHoverListener(btnSalir, STYLE.COLOR_BOTON_SALIR));

        // Acciones para los botones
        btnRegistro.addActionListener(e -> openRegistroView());
        btnLogin.addActionListener(e -> openLoginView());
        btnSalir.addActionListener(e -> confirmExit());

        // Manejador de ventana para confirmar salida
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmExit();
            }
        });
    }

    /**
     * Configura características de accesibilidad
     */
    private void setupAccessibility() {
        // Configurar descripciones para lectores de pantalla
        btnRegistro.getAccessibleContext().setAccessibleDescription("Botón para registrarse como nuevo usuario");
        btnLogin.getAccessibleContext().setAccessibleDescription("Botón para iniciar sesión con una cuenta existente");
        btnSalir.getAccessibleContext().setAccessibleDescription("Botón para salir de la aplicación");

        // Añadir atajos de teclado
        btnRegistro.setMnemonic(KeyEvent.VK_R); // Alt+R
        btnLogin.setMnemonic(KeyEvent.VK_I);    // Alt+I
        btnSalir.setMnemonic(KeyEvent.VK_S);    // Alt+S

        // Orden de tabulación lógico
        btnRegistro.setFocusable(true);
        btnLogin.setFocusable(true);
        btnSalir.setFocusable(true);

        // Establecer el orden de tabulación
        FocusTraversalPolicy policy = new LayoutFocusTraversalPolicy();
        setFocusTraversalPolicy(policy);
    }

    /**
     * Abre la vista de registro
     */
    private void openRegistroView() {
        new RegistroView().setVisible(true);
        dispose();
    }

    /**
     * Abre la vista de inicio de sesión
     */
    private void openLoginView() {
        new LoginView().setVisible(true);
        dispose();
    }

    /**
     * Muestra diálogo de confirmación para salir
     */
    private void confirmExit() {
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                messages.getString("exit.confirm.message"),
                messages.getString("exit.confirm.title"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Clase interna para manejar efectos hover en botones
     */
    private class ButtonHoverListener extends MouseAdapter {
        private final JButton button;
        private final Color originalColor;

        public ButtonHoverListener() {
            this.button = null;
            this.originalColor = null;
        }

        public ButtonHoverListener(JButton button, Color originalColor) {
            this.button = button;
            this.originalColor = originalColor;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (button != null) {
                button.setBackground(originalColor.brighter());
            } else {
                ((JButton)e.getSource()).setBackground(
                        ((JButton)e.getSource()).getBackground().brighter());
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (button != null) {
                button.setBackground(originalColor);
            } else {
                ((JButton)e.getSource()).setBackground(
                        ((JButton)e.getSource()).getBackground().darker());
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (button != null) {
                button.setBackground(originalColor.darker());
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (button != null) {
                button.setBackground(originalColor);
            }
        }
    }
}

/**
 * Clase utilitaria para constantes de estilo
 * Esta clase debería estar en un archivo separado
 */
class StyleConstants {
    // Colores
    public final Color COLOR_FONDO = new Color(240, 248, 255);           // Azul muy claro
    public final Color COLOR_HEADER = new Color(173, 216, 230);          // Azul claro
    public final Color COLOR_BOTON_PRIMARIO = new Color(70, 130, 230);   // Azul más oscuro para mejor contraste
    public final Color COLOR_BOTON_SECUNDARIO = new Color(100, 180, 230); // Azul cielo medio
    public final Color COLOR_BOTON_SALIR = new Color(180, 180, 180);     // Gris medio para mejor contraste
    public final Color COLOR_TEXTO = new Color(25, 25, 112);             // Azul marino oscuro

    // Fuentes
    public final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 22);
    public final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.PLAIN, 14);
    public final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 14);

    // Singleton pattern
    private static StyleConstants instance;

    private StyleConstants() {
        // Constructor privado para singleton
    }

    public static StyleConstants getInstance() {
        if (instance == null) {
            instance = new StyleConstants();
        }
        return instance;
    }
}