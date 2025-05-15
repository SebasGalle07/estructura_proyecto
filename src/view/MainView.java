package view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class MainView extends JFrame {
    // Definimos la misma paleta de colores para mantener consistencia
    private static final Color COLOR_FONDO = new Color(240, 248, 255); // Azul muy claro
    private static final Color COLOR_HEADER = new Color(173, 216, 230); // Azul claro
    private static final Color COLOR_BOTON_PRIMARIO = new Color(100, 149, 237); // Azul cornflower
    private static final Color COLOR_BOTON_SECUNDARIO = new Color(135, 206, 250); // Azul cielo claro
    private static final Color COLOR_BOTON_SALIR = new Color(200, 200, 200); // Gris claro
    private static final Color COLOR_TEXTO = new Color(25, 25, 112); // Azul marino oscuro
    private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 14);

    public MainView() {
        setTitle("Red Social de Aprendizaje Colaborativo");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(COLOR_FONDO);

        // Panel de título en la parte superior
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(COLOR_HEADER);
        titlePanel.setBorder(new EmptyBorder(25, 20, 25, 20));

        JLabel lblTitulo = new JLabel("Red Social de Aprendizaje", SwingConstants.CENTER);
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_TEXTO);

        JLabel lblSubtitulo = new JLabel("Colaboración y conocimiento compartido", SwingConstants.CENTER);
        lblSubtitulo.setFont(FUENTE_SUBTITULO);
        lblSubtitulo.setForeground(COLOR_TEXTO);

        JPanel titleTextPanel = new JPanel(new BorderLayout(0, 5));
        titleTextPanel.setOpaque(false);
        titleTextPanel.add(lblTitulo, BorderLayout.CENTER);
        titleTextPanel.add(lblSubtitulo, BorderLayout.SOUTH);

        titlePanel.add(titleTextPanel, BorderLayout.CENTER);

        // Panel de botones en el centro
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));
        buttonPanel.setBackground(COLOR_FONDO);

        // Botones principales
        JButton btnRegistro = createStyledButton("Registrarse", COLOR_BOTON_PRIMARIO);
        JButton btnLogin = createStyledButton("Iniciar Sesión", COLOR_BOTON_SECUNDARIO);
        JButton btnSalir = createStyledButton("Salir", COLOR_BOTON_SALIR);

        // Configurar acciones de los botones
        btnRegistro.addActionListener(e -> {
            new RegistroView().setVisible(true);
            dispose();
        });

        btnLogin.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });

        btnSalir.addActionListener(e -> {
            // Preguntar antes de salir
            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro que deseas salir de la aplicación?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (respuesta == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // Añadir botones al panel
        buttonPanel.add(btnRegistro);
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnSalir);

        // Panel de pie de página
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(235, 245, 250));
        footerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblFooter = new JLabel("© 2025 - Sistema de Aprendizaje Colaborativo");
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblFooter.setForeground(new Color(100, 100, 100));

        footerPanel.add(lblFooter);

        // Añadir todos los paneles al panel principal
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Agregar panel principal al frame
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
        button.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto de sombra suave
        button.setBorder(BorderFactory.createCompoundBorder(
                new SoftBevelBorder(SoftBevelBorder.RAISED),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        // Efecto de hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }


}