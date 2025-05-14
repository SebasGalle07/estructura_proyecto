package view;

import app.AppContext;
import model.Usuario;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class LoginView extends JFrame {
    private static final Color COLOR_FONDO = new Color(240, 248, 255);
    private static final Color COLOR_HEADER = new Color(173, 216, 230);
    private static final Color COLOR_BOTON = new Color(100, 149, 237);
    private static final Color COLOR_TEXTO = new Color(25, 25, 112);
    private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FUENTE_ETIQUETA = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FUENTE_CAMPO = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 14);

    public LoginView() {
        setTitle("Inicio de Sesión");
        setSize(400, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(COLOR_FONDO);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 25, 25));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(COLOR_HEADER);
        titlePanel.setBorder(new EmptyBorder(15, 0, 15, 0));
        JLabel lblTitulo = new JLabel("Inicio de Sesión");
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_TEXTO);
        titlePanel.add(lblTitulo);

        JPanel formPanel = new JPanel(new BorderLayout(0, 15));
        formPanel.setBackground(COLOR_FONDO);
        formPanel.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));

        JPanel panelId = createFieldPanel("ID de usuario:", true);
        JTextField txtId = (JTextField) ((JPanel)panelId.getComponent(1)).getComponent(0);

        JCheckBox chkModerador = new JCheckBox("Ingresar como moderador");
        chkModerador.setFont(FUENTE_ETIQUETA);
        chkModerador.setForeground(COLOR_TEXTO);
        chkModerador.setBackground(COLOR_FONDO);
        chkModerador.setFocusPainted(false);
        chkModerador.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel checkboxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        checkboxPanel.setBackground(COLOR_FONDO);
        checkboxPanel.add(chkModerador);

        formPanel.add(panelId, BorderLayout.NORTH);
        formPanel.add(checkboxPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonPanel.setBackground(COLOR_FONDO);

        JButton btnIngresar = createStyledButton("Ingresar", COLOR_BOTON);
        JButton btnVolver = createStyledButton("Volver", new Color(200, 200, 200));
        JButton btnRegistrar = createStyledButton("Registrarse", new Color(144, 238, 144));

        btnIngresar.addActionListener(e -> {
            String id = txtId.getText().trim();

            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor ingrese un ID de usuario", "Campo requerido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (chkModerador.isSelected()) {
                if (id.equalsIgnoreCase("admin")) {
                    new PanelModeradorView().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "ID de moderador incorrecto.", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                Usuario usuario = AppContext.usuarioController.buscarUsuario(id);
                if (usuario != null) {
                    new PanelEstudianteView(usuario).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Usuario no encontrado. Verifique el ID o regístrese.", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnVolver.addActionListener(e -> {
            new MainView().setVisible(true);
            dispose();
        });

        btnRegistrar.addActionListener(e -> {
            new RegistroView().setVisible(true);
            dispose();
        });

        buttonPanel.add(btnIngresar);
        buttonPanel.add(btnRegistrar);
        buttonPanel.add(btnVolver);

        JLabel lblInfo = new JLabel("¿No tienes cuenta? Usa 'Registrarse' para crear una.");
        lblInfo.setFont(FUENTE_ETIQUETA);
        lblInfo.setForeground(COLOR_TEXTO);
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(lblInfo, BorderLayout.PAGE_END);

        add(mainPanel);
    }

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
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        fieldPanel.add(textField, BorderLayout.CENTER);

        panel.add(label, BorderLayout.NORTH);
        panel.add(fieldPanel, BorderLayout.CENTER);

        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(FUENTE_BOTON);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

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