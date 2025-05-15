package view;

import app.AppContext;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.Arrays;

public class RegistroView extends JFrame {
    // Definimos la paleta de colores
    private static final Color COLOR_FONDO = new Color(240, 248, 255); // Azul muy claro
    private static final Color COLOR_HEADER = new Color(173, 216, 230); // Azul claro
    private static final Color COLOR_BOTON = new Color(100, 149, 237); // Azul cornflower
    private static final Color COLOR_TEXTO = new Color(25, 25, 112); // Azul marino oscuro
    private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FUENTE_ETIQUETA = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FUENTE_CAMPO = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 14);

    public RegistroView() {
        setTitle("Registro de Usuario");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(COLOR_FONDO);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 25, 25));

        // Panel de título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(COLOR_HEADER);
        titlePanel.setBorder(new EmptyBorder(15, 0, 15, 0));
        JLabel lblTitulo = new JLabel("Registro de Nuevo Usuario");
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_TEXTO);
        titlePanel.add(lblTitulo);

        // Panel de formulario
        JPanel formPanel = new JPanel(new GridLayout(3, 1, 0, 15));
        formPanel.setBackground(COLOR_FONDO);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Crear campos con paneles individuales para mejor diseño
        JPanel panelId = createFieldPanel("ID:", true);
        JTextField txtId = (JTextField) ((JPanel)panelId.getComponent(1)).getComponent(0);

        JPanel panelNombre = createFieldPanel("Nombre completo:", true);
        JTextField txtNombre = (JTextField) ((JPanel)panelNombre.getComponent(1)).getComponent(0);

        JPanel panelIntereses = createFieldPanel("Intereses (separados por coma):", true);
        JTextField txtIntereses = (JTextField) ((JPanel)panelIntereses.getComponent(1)).getComponent(0);

        formPanel.add(panelId);
        formPanel.add(panelNombre);
        formPanel.add(panelIntereses);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setBackground(COLOR_FONDO);

        JButton btnRegistrar = createStyledButton("Registrar", COLOR_BOTON);
        JButton btnVolver = createStyledButton("Volver", new Color(200, 200, 200));

        btnRegistrar.addActionListener(e -> {
            String id = txtId.getText().trim();
            String nombre = txtNombre.getText().trim();
            String[] interesesArray = txtIntereses.getText().split(",");

            if (id.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Todos los campos son obligatorios",
                        "Error de validación",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            model.ListaEnlazada<String> intereses = new model.ListaEnlazada<>();
            for (String interes : interesesArray) {
                intereses.insertarFinal(interes.trim());
            }

            if (AppContext.usuarioController.registrarUsuario(id, nombre, intereses)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Usuario registrado exitosamente.",
                        "Registro Exitoso",
                        JOptionPane.INFORMATION_MESSAGE
                );
                new LoginView().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "El ID ya está en uso. Por favor elija otro.",
                        "Error de Registro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });


        btnVolver.addActionListener(e -> {
            new MainView().setVisible(true);
            dispose();
        });

        buttonPanel.add(btnRegistrar);
        buttonPanel.add(btnVolver);

        // Añadir todos los paneles al panel principal
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Agregar panel principal al frame
        add(mainPanel);

        // Hacer que la ventana no sea redimensionable
        setResizable(false);
    }

    /**
     * Crea un panel para un campo del formulario con etiqueta y campo de texto
     */
    private JPanel createFieldPanel(String labelText, boolean isTextField) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(COLOR_FONDO);

        JLabel label = new JLabel(labelText);
        label.setFont(FUENTE_ETIQUETA);
        label.setForeground(COLOR_TEXTO);

        JPanel fieldPanel = new JPanel(new BorderLayout());
        fieldPanel.setBackground(COLOR_FONDO);

        JTextField textField = new JTextField();
        textField.setFont(FUENTE_CAMPO);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        fieldPanel.add(textField, BorderLayout.CENTER);

        panel.add(label, BorderLayout.NORTH);
        panel.add(fieldPanel, BorderLayout.CENTER);

        return panel;
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
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto de hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

}