
package view;

import app.AppContext;
import model.Usuario;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;

/**
 * Vista para el inicio de sesión de usuarios
 * Versión mejorada con soporte para accesibilidad, internacionalización y diseño responsive
 */
public class LoginView extends JFrame {
    // Usar la clase de estilos compartida
    private static final StyleConstants STYLE = StyleConstants.getInstance();

    // Componentes principales de la interfaz
    private JTextField txtId;
    private JCheckBox chkModerador;
    private JButton btnIngresar;
    private JButton btnRegistrar;
    private JButton btnVolver;

    // Soporte para internacionalización
    private ResourceBundle messages;

    /**
     * Constructor principal de la vista
     */
    public LoginView() {
        // Cargar textos localizados (por defecto en español)
        loadLocalizedResources("es");

        // Configuración básica de la ventana
        setTitle(messages.getString("login.title"));
        setMinimumSize(new Dimension(400, 380));
        setPreferredSize(new Dimension(450, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Permitir redimensionamiento para mejor experiencia en diferentes pantallas
        setResizable(true);

        // Inicializar componentes de la interfaz
        initComponents();

        // Configurar accesibilidad
        setupAccessibility();

        // Configurar manejadores de eventos
        setupEventHandlers();

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
                        case "login.title": return "Inicio de Sesión";
                        case "login.heading": return "Inicio de Sesión";
                        case "login.userid": return "ID de usuario:";
                        case "login.as_moderator": return "Ingresar como moderador";
                        case "login.enter": return "Ingresar";
                        case "login.register": return "Registrarse";
                        case "login.back": return "Volver";
                        case "login.info": return "¿No tienes cuenta? Usa 'Registrarse' para crear una.";
                        case "login.error.empty_id": return "Por favor ingrese un ID de usuario";
                        case "login.error.empty_id.title": return "Campo requerido";
                        case "login.error.wrong_moderator": return "ID de moderador incorrecto.";
                        case "login.error.wrong_moderator.title": return "Error de autenticación";
                        case "login.error.user_not_found": return "Usuario no encontrado. Verifique el ID o regístrese.";
                        case "login.error.user_not_found.title": return "Error de autenticación";
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
        // Panel principal con BorderLayout para mejor organización
        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(STYLE.COLOR_FONDO);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 25, 25, 25));

        // Añadir los paneles principales a la ventana
        mainPanel.add(createTitlePanel(), BorderLayout.NORTH);
        mainPanel.add(createFormPanel(), BorderLayout.CENTER);
        mainPanel.add(createButtonsPanel(), BorderLayout.SOUTH);

        // Agregar panel principal al frame
        setContentPane(mainPanel);
    }

    /**
     * Crea el panel de título de la vista
     */
    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(STYLE.COLOR_HEADER);
        titlePanel.setBorder(new EmptyBorder(15, 0, 15, 0));

        JLabel lblTitulo = new JLabel(messages.getString("login.heading"));
        lblTitulo.setFont(STYLE.FUENTE_TITULO);
        lblTitulo.setForeground(STYLE.COLOR_TEXTO);

        titlePanel.add(lblTitulo);

        return titlePanel;
    }

    /**
     * Crea el panel con el formulario de inicio de sesión
     */
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setBackground(STYLE.COLOR_FONDO);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Panel para el campo de ID de usuario
        JPanel userIdPanel = createFieldPanel(messages.getString("login.userid"));
        txtId = (JTextField) ((JPanel)userIdPanel.getComponent(1)).getComponent(0);

        // Checkbox para modo moderador
        chkModerador = new JCheckBox(messages.getString("login.as_moderator"));
        chkModerador.setFont(STYLE.FUENTE_SUBTITULO);
        chkModerador.setForeground(STYLE.COLOR_TEXTO);
        chkModerador.setBackground(STYLE.COLOR_FONDO);
        chkModerador.setFocusPainted(false);
        chkModerador.setAlignmentX(Component.LEFT_ALIGNMENT);
        chkModerador.setBorder(BorderFactory.createEmptyBorder(15, 5, 5, 0));

        // Panel para el checkbox
        JPanel checkboxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        checkboxPanel.setBackground(STYLE.COLOR_FONDO);
        checkboxPanel.add(chkModerador);
        checkboxPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        checkboxPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, checkboxPanel.getPreferredSize().height));

        // Mensaje informativo
        JLabel lblInfo = new JLabel(messages.getString("login.info"));
        lblInfo.setFont(STYLE.FUENTE_SUBTITULO);
        lblInfo.setForeground(STYLE.COLOR_TEXTO);
        lblInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblInfo.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));

        // Añadir componentes al panel del formulario
        formPanel.add(userIdPanel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(checkboxPanel);
        formPanel.add(Box.createVerticalGlue());
        formPanel.add(lblInfo);

        return formPanel;
    }

    /**
     * Crea un panel para un campo de formulario con su etiqueta
     */
    private JPanel createFieldPanel(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(STYLE.COLOR_FONDO);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));

        // Etiqueta del campo
        JLabel label = new JLabel(labelText);
        label.setFont(STYLE.FUENTE_SUBTITULO);
        label.setForeground(STYLE.COLOR_TEXTO);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Panel para el campo de texto
        JPanel fieldPanel = new JPanel(new BorderLayout());
        fieldPanel.setBackground(STYLE.COLOR_FONDO);
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        fieldPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Campo de texto
        JTextField textField = new JTextField();
        textField.setFont(STYLE.FUENTE_SUBTITULO);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        fieldPanel.add(textField, BorderLayout.CENTER);

        // Añadir componentes al panel
        panel.add(label);
        panel.add(fieldPanel);

        return panel;
    }

    /**
     * Crea el panel para los botones de acción
     */
    private JPanel createButtonsPanel() {
        // Panel con FlowLayout para mejor distribución de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(STYLE.COLOR_FONDO);

        // Crear botones con estilos apropiados
        btnIngresar = createStyledButton(
                messages.getString("login.enter"),
                STYLE.COLOR_BOTON_PRIMARIO
        );

        btnRegistrar = createStyledButton(
                messages.getString("login.register"),
                STYLE.COLOR_BOTON_SECUNDARIO
        );

        btnVolver = createStyledButton(
                messages.getString("login.back"),
                STYLE.COLOR_BOTON_SALIR
        );

        // Añadir botones en el orden correcto
        buttonPanel.add(btnIngresar);
        buttonPanel.add(btnRegistrar);
        buttonPanel.add(btnVolver);

        return buttonPanel;
    }

    /**
     * Configura las características de accesibilidad de la interfaz
     */
    private void setupAccessibility() {
        // Configurar descripciones para lectores de pantalla
        txtId.getAccessibleContext().setAccessibleDescription(
                "Campo para ingresar su identificador de usuario"
        );

        chkModerador.getAccessibleContext().setAccessibleDescription(
                "Marque esta casilla para ingresar como moderador del sistema"
        );

        btnIngresar.getAccessibleContext().setAccessibleDescription(
                "Botón para iniciar sesión con el ID proporcionado"
        );

        btnRegistrar.getAccessibleContext().setAccessibleDescription(
                "Botón para ir a la pantalla de registro de nuevo usuario"
        );

        btnVolver.getAccessibleContext().setAccessibleDescription(
                "Botón para volver a la pantalla principal"
        );

        // Añadir atajos de teclado
        btnIngresar.setMnemonic(KeyEvent.VK_I);  // Alt+I
        btnRegistrar.setMnemonic(KeyEvent.VK_R); // Alt+R
        btnVolver.setMnemonic(KeyEvent.VK_V);    // Alt+V
        chkModerador.setMnemonic(KeyEvent.VK_M); // Alt+M

        // Establecer el orden de tabulación
        txtId.setFocusable(true);
        chkModerador.setFocusable(true);
        btnIngresar.setFocusable(true);
        btnRegistrar.setFocusable(true);
        btnVolver.setFocusable(true);

        // Establecer el orden de foco inicial
        txtId.requestFocusInWindow();
    }

    /**
     * Configura los manejadores de eventos para los componentes
     */
    private void setupEventHandlers() {
        // Acción para el botón ingresar
        btnIngresar.addActionListener(e -> handleLogin());

        // Efecto visual para los botones
        ButtonHoverListener hoverListener = new ButtonHoverListener();
        btnIngresar.addMouseListener(new ButtonHoverListener(btnIngresar, STYLE.COLOR_BOTON_PRIMARIO));
        btnRegistrar.addMouseListener(new ButtonHoverListener(btnRegistrar, STYLE.COLOR_BOTON_SECUNDARIO));
        btnVolver.addMouseListener(new ButtonHoverListener(btnVolver, STYLE.COLOR_BOTON_SALIR));

        // Navegación entre ventanas
        btnVolver.addActionListener(e -> navigateToMain());
        btnRegistrar.addActionListener(e -> navigateToRegister());

        // Acción al presionar Enter en el campo de texto
        txtId.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();
                }
            }
        });
    }

    /**
     * Maneja la lógica de inicio de sesión
     */
    private void handleLogin() {
        String id = txtId.getText().trim();

        // Validar que el ID no esté vacío
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    messages.getString("login.error.empty_id"),
                    messages.getString("login.error.empty_id.title"),
                    JOptionPane.WARNING_MESSAGE
            );
            txtId.requestFocus();
            return;
        }

        // Verificar si es acceso como moderador
        if (chkModerador.isSelected()) {
            handleModeratorLogin(id);
        } else {
            handleStudentLogin(id);
        }
    }

    /**
     * Maneja el inicio de sesión de un moderador
     */
    private void handleModeratorLogin(String id) {
        // Verificar credenciales de moderador
        if (id.equalsIgnoreCase("admin")) {
            new PanelModeradorView().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    messages.getString("login.error.wrong_moderator"),
                    messages.getString("login.error.wrong_moderator.title"),
                    JOptionPane.ERROR_MESSAGE
            );
            txtId.selectAll();
            txtId.requestFocus();
        }
    }

    /**
     * Maneja el inicio de sesión de un estudiante
     */
    private void handleStudentLogin(String id) {
        // Validar existencia del usuario
        Usuario usuario = AppContext.usuarioController.buscarUsuario(id);
        if (usuario != null) {
            new PanelEstudianteView(usuario).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    messages.getString("login.error.user_not_found"),
                    messages.getString("login.error.user_not_found.title"),
                    JOptionPane.ERROR_MESSAGE
            );
            txtId.selectAll();
            txtId.requestFocus();
        }
    }

    /**
     * Navega a la vista principal
     */
    private void navigateToMain() {
        new MainView().setVisible(true);
        dispose();
    }

    /**
     * Navega a la vista de registro
     */
    private void navigateToRegister() {
        new RegistroView().setVisible(true);
        dispose();
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
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
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