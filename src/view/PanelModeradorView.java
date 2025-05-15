package view;

import controller.UsuarioController;
import controller.GrafoController;
import controller.ContenidoController;
import app.AppContext;
import model.Usuario;
import model.Contenido;
import view.GrafoView;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PanelModeradorView extends JFrame {
    // Definimos la misma paleta de colores que las otras vistas
    private static final Color COLOR_FONDO = new Color(240, 248, 255); // Azul muy claro
    private static final Color COLOR_HEADER = new Color(173, 216, 230); // Azul claro
    private static final Color COLOR_BOTON = new Color(100, 149, 237); // Azul cornflower
    private static final Color COLOR_TEXTO = new Color(25, 25, 112); // Azul marino oscuro
    private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FUENTE_ETIQUETA = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FUENTE_CAMPO = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 14);

    private UsuarioController usuarioController;
    private GrafoController grafoController;
    private ContenidoController contenidoController;

    public PanelModeradorView() {
        this.usuarioController = AppContext.usuarioController;
        this.grafoController = AppContext.grafoController;
        this.contenidoController = AppContext.contenidoController;

        setTitle("Panel del Moderador");
        setSize(550, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(COLOR_FONDO);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 25, 25));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(COLOR_HEADER);
        titlePanel.setBorder(new EmptyBorder(15, 0, 15, 0));
        JLabel lblTitulo = new JLabel("Panel de Administración");
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_TEXTO);
        titlePanel.add(lblTitulo);

        JPanel buttonPanel = new JPanel(new GridLayout(7, 1, 0, 15));
        buttonPanel.setBackground(COLOR_FONDO);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton btnVerUsuarios = createStyledButton("Ver Todos los Usuarios", COLOR_BOTON);
        JButton btnTopContenidos = createStyledButton("Contenidos Más Valorados", COLOR_BOTON);
        JButton btnTopConectados = createStyledButton("Usuarios Más Conectados", COLOR_BOTON);
        JButton btnVerGrafo = createStyledButton("Ver Grafo de Afinidad (Texto)", COLOR_BOTON);
        JButton btnVerGrafoVisual = createStyledButton("Ver Grafo de Afinidad (Gráfico)", COLOR_BOTON);
        JButton btnValorarContenido = createStyledButton("Valorar Contenido", COLOR_BOTON);
        JButton btnSalir = createStyledButton("Salir", new Color(200, 200, 200));

        btnVerUsuarios.addActionListener(e -> mostrarUsuarios());
        btnTopContenidos.addActionListener(e -> mostrarContenidosMasValorados());
        btnTopConectados.addActionListener(e -> mostrarUsuariosMasConectados());
        btnVerGrafo.addActionListener(e -> mostrarGrafoTexto());
        btnVerGrafoVisual.addActionListener(e -> new GrafoView().setVisible(true));
        btnValorarContenido.addActionListener(e -> valorarContenido()); // será corregido después
        btnSalir.addActionListener(e -> {
            new MainView().setVisible(true);
            dispose();
        });

        buttonPanel.add(btnVerUsuarios);
        buttonPanel.add(btnTopContenidos);
        buttonPanel.add(btnTopConectados);
        buttonPanel.add(btnVerGrafo);
        buttonPanel.add(btnVerGrafoVisual);
        buttonPanel.add(btnValorarContenido);
        buttonPanel.add(btnSalir);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

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

    /**
     * Crea un panel para un campo del formulario con etiqueta y campo de texto
     */
    private JPanel createFieldPanel(String labelText) {
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

    private void mostrarUsuarios() {
        model.ListaEnlazada<Usuario> usuarios = usuarioController.getTodos();

        if (usuarios.estaVacia()) {
            JOptionPane.showMessageDialog(this,
                    "No hay usuarios registrados en el sistema",
                    "Sin Usuarios",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_FONDO);

        JLabel titulo = new JLabel("Usuarios registrados en el sistema:");
        titulo.setFont(FUENTE_ETIQUETA);
        titulo.setForeground(COLOR_TEXTO);
        panel.add(titulo, BorderLayout.NORTH);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        listPanel.setBackground(COLOR_FONDO);

        for (Usuario u : usuarios) {
            JLabel userLabel = new JLabel("• " + u.getNombre() + " (ID: " + u.getId() + ")");
            userLabel.setFont(FUENTE_CAMPO);
            userLabel.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
            listPanel.add(userLabel);
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setPreferredSize(new Dimension(350, 250));
        panel.add(scrollPane, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(this, panel, "Usuarios Registrados",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void mostrarContenidosMasValorados() {
        model.ListaEnlazada<Usuario> usuarios = usuarioController.getTodos();
        model.ListaEnlazada<Contenido> contenidosValorados = new model.ListaEnlazada<>();

        for (Usuario u : usuarios) {
            for (Contenido c : u.getContenidosPublicados()) {
                if (!c.getValoraciones().estaVacia()) {
                    contenidosValorados.insertarFinal(c);
                }
            }
        }

        // Ordenamiento descendente por promedio (burbuja)
        model.NodoLista<Contenido> i = contenidosValorados.getCabeza();
        while (i != null) {
            model.NodoLista<Contenido> j = i.getSiguiente();
            while (j != null) {
                if (i.getDato().obtenerPromedio() < j.getDato().obtenerPromedio()) {
                    Contenido temp = i.getDato();
                    i.setDato(j.getDato());
                    j.setDato(temp);
                }
                j = j.getSiguiente();
            }
            i = i.getSiguiente();
        }

        if (contenidosValorados.estaVacia()) {
            JOptionPane.showMessageDialog(this,
                    "No hay contenidos valorados en el sistema",
                    "Sin Contenidos Valorados",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_FONDO);

        JLabel titulo = new JLabel("Top 5 contenidos más valorados:");
        titulo.setFont(FUENTE_ETIQUETA);
        titulo.setForeground(COLOR_TEXTO);
        panel.add(titulo, BorderLayout.NORTH);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        listPanel.setBackground(COLOR_FONDO);

        int count = 0;
        for (Contenido c : contenidosValorados) {
            JLabel contentLabel = new JLabel(String.format("• %s (Tema: %s) - Promedio: %.2f",
                    c.getTitulo(), c.getTema(), c.obtenerPromedio()));
            contentLabel.setFont(FUENTE_CAMPO);
            contentLabel.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
            listPanel.add(contentLabel);

            count++;
            if (count >= 5) break;
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        panel.add(scrollPane, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(this, panel, "Contenidos Mejor Valorados",
                JOptionPane.PLAIN_MESSAGE);
    }


    private void mostrarUsuariosMasConectados() {
        model.ListaEnlazada<Usuario> usuarios = usuarioController.getTodos();

        if (usuarios.estaVacia()) {
            JOptionPane.showMessageDialog(this,
                    "No hay usuarios con conexiones en el sistema",
                    "Sin Conexiones",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Ordenamiento descendente por cantidad de conexiones
        model.NodoLista<Usuario> i = usuarios.getCabeza();
        while (i != null) {
            model.NodoLista<Usuario> j = i.getSiguiente();
            while (j != null) {
                if (i.getDato().getConexiones().tamano() < j.getDato().getConexiones().tamano()) {
                    Usuario temp = i.getDato();
                    i.setDato(j.getDato());
                    j.setDato(temp);
                }
                j = j.getSiguiente();
            }
            i = i.getSiguiente();
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_FONDO);

        JLabel titulo = new JLabel("Top 5 usuarios más conectados:");
        titulo.setFont(FUENTE_ETIQUETA);
        titulo.setForeground(COLOR_TEXTO);
        panel.add(titulo, BorderLayout.NORTH);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        listPanel.setBackground(COLOR_FONDO);

        int count = 0;
        for (Usuario u : usuarios) {
            JLabel userLabel = new JLabel(String.format("• %s (ID: %s) - Conexiones: %d",
                    u.getNombre(), u.getId(), u.getConexiones().tamano()));
            userLabel.setFont(FUENTE_CAMPO);
            userLabel.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
            listPanel.add(userLabel);

            count++;
            if (count >= 5) break;
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        panel.add(scrollPane, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(this, panel, "Usuarios Más Conectados",
                JOptionPane.PLAIN_MESSAGE);
    }


    private void mostrarGrafoTexto() {
        model.ListaEnlazada<model.Pair<String, model.Vertice>> vertices = grafoController.getGrafo().getVertices();

        if (vertices.estaVacia()) {
            JOptionPane.showMessageDialog(this,
                    "El grafo de afinidad está vacío",
                    "Grafo Vacío",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_FONDO);

        JLabel titulo = new JLabel("Grafo de afinidad (representación textual):");
        titulo.setFont(FUENTE_ETIQUETA);
        titulo.setForeground(COLOR_TEXTO);
        panel.add(titulo, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setBackground(new Color(250, 250, 250));
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        StringBuilder sb = new StringBuilder();
        for (model.Pair<String, model.Vertice> par : vertices) {
            sb.append(par.getKey()).append(" → ");
            model.ListaEnlazada<model.Vertice> conexiones = par.getValue().getConexiones();

            if (conexiones.estaVacia()) {
                sb.append("(sin conexiones)");
            } else {
                boolean primero = true;
                for (model.Vertice v : conexiones) {
                    if (!primero) sb.append(", ");
                    sb.append(v.getUsuario().getId());
                    primero = false;
                }
            }
            sb.append("\n");
        }

        textArea.setText(sb.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        panel.add(scrollPane, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(this, panel, "Grafo de Afinidad",
                JOptionPane.PLAIN_MESSAGE);
    }


    private void valorarContenido() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel idPanel = createFieldPanel("ID del autor del contenido:");
        panel.add(idPanel, BorderLayout.CENTER);

        JTextField txtId = (JTextField) ((JPanel)idPanel.getComponent(1)).getComponent(0);

        int result = JOptionPane.showConfirmDialog(this, panel, "Buscar Autor",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        String idUsuario = txtId.getText().trim();
        if (idUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese el ID del autor",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario autor = usuarioController.buscarUsuario(idUsuario);
        if (autor == null) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró un usuario con el ID proporcionado",
                    "Usuario no encontrado", JOptionPane.ERROR_MESSAGE);
            return;
        }

        model.ListaEnlazada<Contenido> contenidos = autor.getContenidosPublicados();
        if (contenidos.estaVacia()) {
            JOptionPane.showMessageDialog(this,
                    "Este usuario no ha publicado contenidos",
                    "Sin contenidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Convertir contenidos a array de títulos
        int size = contenidos.tamano();
        String[] titulos = new String[size];
        int index = 0;
        for (Contenido c : contenidos) {
            titulos[index++] = c.getTitulo();
        }

        JPanel selectionPanel = new JPanel(new BorderLayout(0, 10));
        selectionPanel.setBackground(COLOR_FONDO);
        selectionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblSeleccion = new JLabel("Seleccione un contenido para valorar:");
        lblSeleccion.setFont(FUENTE_ETIQUETA);
        lblSeleccion.setForeground(COLOR_TEXTO);
        selectionPanel.add(lblSeleccion, BorderLayout.NORTH);

        JComboBox<String> comboContenidos = new JComboBox<>(titulos);
        comboContenidos.setFont(FUENTE_CAMPO);
        comboContenidos.setBackground(Color.WHITE);
        selectionPanel.add(comboContenidos, BorderLayout.CENTER);

        result = JOptionPane.showConfirmDialog(this, selectionPanel, "Seleccionar Contenido",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        String seleccion = (String) comboContenidos.getSelectedItem();
        Contenido seleccionado = null;
        for (Contenido c : contenidos) {
            if (c.getTitulo().equals(seleccion)) {
                seleccionado = c;
                break;
            }
        }
        if (seleccionado == null) return;

        JPanel ratingPanel = new JPanel(new BorderLayout(0, 10));
        ratingPanel.setBackground(COLOR_FONDO);
        ratingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel valorPanel = createFieldPanel("Calificación (1-5):");
        ratingPanel.add(valorPanel, BorderLayout.CENTER);

        JTextField txtValor = (JTextField) ((JPanel)valorPanel.getComponent(1)).getComponent(0);

        result = JOptionPane.showConfirmDialog(this, ratingPanel, "Valorar Contenido",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        String valor = txtValor.getText().trim();
        try {
            int calif = Integer.parseInt(valor);
            if (calif < 1 || calif > 5) {
                JOptionPane.showMessageDialog(this,
                        "La valoración debe estar entre 1 y 5",
                        "Valor inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            seleccionado.agregarValoracion(calif);
            JOptionPane.showMessageDialog(this,
                    "Valoración registrada exitosamente",
                    "Valoración Registrada", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese un número válido para la valoración",
                    "Formato inválido", JOptionPane.ERROR_MESSAGE);
        }
    }
}