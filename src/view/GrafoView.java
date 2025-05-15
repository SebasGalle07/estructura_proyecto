package view;

import app.AppContext;
import model.Usuario;
import model.Vertice;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

public class GrafoView extends JFrame {
    // Definimos la misma paleta de colores para mantener consistencia
    private static final Color COLOR_FONDO = new Color(240, 248, 255); // Azul muy claro
    private static final Color COLOR_HEADER = new Color(173, 216, 230); // Azul claro
    private static final Color COLOR_NODO = new Color(100, 149, 237); // Azul cornflower
    private static final Color COLOR_NODO_HOVER = new Color(65, 105, 225); // Azul real
    private static final Color COLOR_TEXTO = new Color(25, 25, 112); // Azul marino oscuro
    private static final Color COLOR_ARISTA = new Color(200, 200, 220); // Gris claro
    private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FUENTE_NODO = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font FUENTE_INFO = new Font("Segoe UI", Font.PLAIN, 14);

    private Map<String, Point> posiciones;
    private String nodoSeleccionado = null;
    private GrafoPanel grafoPanel;
    private JPanel infoPanel;
    private JLabel lblInfo;

    public GrafoView() {
        setTitle("Visualización del Grafo de Afinidad");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        posiciones = new HashMap<>();

        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(COLOR_FONDO);

        // Panel de título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(COLOR_HEADER);
        titlePanel.setBorder(new EmptyBorder(15, 0, 15, 0));
        JLabel lblTitulo = new JLabel("Visualización del Grafo de Afinidad");
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_TEXTO);
        titlePanel.add(lblTitulo);

        // Panel de controles (Parte superior)
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        controlPanel.setBackground(COLOR_FONDO);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JButton btnRefresh = createStyledButton("Actualizar Vista", new Color(100, 149, 237));
        JButton btnZoomIn = createStyledButton("Zoom +", new Color(100, 149, 237));
        JButton btnZoomOut = createStyledButton("Zoom -", new Color(100, 149, 237));
        JButton btnCerrar = createStyledButton("Cerrar", new Color(200, 200, 200));

        controlPanel.add(btnRefresh);
        controlPanel.add(btnZoomIn);
        controlPanel.add(btnZoomOut);
        controlPanel.add(btnCerrar);

        // Panel para la visualización del grafo
        grafoPanel = new GrafoPanel();
        grafoPanel.setBackground(COLOR_FONDO);

        // Panel para mostrar información del nodo seleccionado
        infoPanel = new JPanel();
        infoPanel.setBackground(new Color(235, 245, 250));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        infoPanel.setLayout(new BorderLayout());

        lblInfo = new JLabel("Seleccione un usuario para ver detalles");
        lblInfo.setFont(FUENTE_INFO);
        lblInfo.setForeground(COLOR_TEXTO);
        infoPanel.add(lblInfo, BorderLayout.CENTER);

        // Añadir listeners a botones
        btnRefresh.addActionListener(e -> {
            grafoPanel.actualizarPosiciones();
            grafoPanel.repaint();
        });

        btnZoomIn.addActionListener(e -> {
            grafoPanel.zoom(1.1);
            grafoPanel.repaint();
        });

        btnZoomOut.addActionListener(e -> {
            grafoPanel.zoom(0.9);
            grafoPanel.repaint();
        });

        btnCerrar.addActionListener(e -> {
            dispose();
        });

        // Añadir todos los paneles al panel principal
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(controlPanel, BorderLayout.PAGE_START);
        mainPanel.add(grafoPanel, BorderLayout.CENTER);
        mainPanel.add(infoPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    /**
     * Crea un botón estilizado con el color especificado
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
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

    private class GrafoPanel extends JPanel {
        private double escala = 1.0;
        private String nodoHover = null;
        private int radioNodo = 20;

        public GrafoPanel() {
            setPreferredSize(new Dimension(800, 500));

            // Agregar detector de movimiento del mouse para hover
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

            // Agregar detector de clic del mouse para seleccionar nodo
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
            if (escala < 0.5) escala = 0.5;
            if (escala > 2.0) escala = 2.0;
        }

        public void actualizarPosiciones() {
            posiciones.clear();
            model.ListaEnlazada<model.Pair<String, model.Vertice>> vertices =
                    AppContext.grafoController.getGrafo().getVertices();

            int radio = (int)(200 * escala);
            int centroX = getWidth() / 2;
            int centroY = getHeight() / 2;
            int total = vertices.tamano();
            int index = 0;

            // Calcular posiciones en círculo
            for (model.Pair<String, model.Vertice> par : vertices) {
                double angle = 2 * Math.PI * index / total;
                int x = centroX + (int) (radio * Math.cos(angle));
                int y = centroY + (int) (radio * Math.sin(angle));
                posiciones.put(par.getKey(), new Point(x, y));
                index++;
            }
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

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            if (posiciones.isEmpty()) {
                actualizarPosiciones();
            }

            model.ListaEnlazada<model.Pair<String, model.Vertice>> vertices =
                    AppContext.grafoController.getGrafo().getVertices();

            // Dibujar aristas
            g2d.setStroke(new BasicStroke(1.5f));
            for (model.Pair<String, model.Vertice> par : vertices) {
                String origenId = par.getKey();
                model.Vertice origenVertice = par.getValue();
                Point origen = posiciones.get(origenId);
                if (origen == null) continue;

                for (model.Vertice destinoVertice : origenVertice.getConexiones()) {
                    String destinoId = destinoVertice.getUsuario().getId();
                    Point destino = posiciones.get(destinoId);
                    if (destino == null) continue;

                    Color aristaColor = COLOR_ARISTA;

                    if (origenId.equals(nodoSeleccionado) || destinoId.equals(nodoSeleccionado)) {
                        aristaColor = new Color(120, 158, 228);
                        g2d.setStroke(new BasicStroke(2.5f));
                    } else {
                        g2d.setStroke(new BasicStroke(1.5f));
                    }

                    g2d.setColor(aristaColor);
                    g2d.drawLine(origen.x, origen.y, destino.x, destino.y);
                }
            }

            // Dibujar nodos
            for (model.Pair<String, model.Vertice> par : vertices) {
                String id = par.getKey();
                Point p = posiciones.get(id);
                if (p == null) continue;

                Color colorNodo;
                int tamanioNodo = radioNodo;

                if (id.equals(nodoSeleccionado)) {
                    colorNodo = COLOR_NODO_HOVER.darker();
                    tamanioNodo = radioNodo + 5;
                } else if (id.equals(nodoHover)) {
                    colorNodo = COLOR_NODO_HOVER;
                    tamanioNodo = radioNodo + 3;
                } else {
                    colorNodo = COLOR_NODO;
                }

                // Sombra
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillOval(p.x - tamanioNodo + 2, p.y - tamanioNodo + 2, tamanioNodo * 2, tamanioNodo * 2);

                // Relleno con degradado
                Paint originalPaint = g2d.getPaint();
                RadialGradientPaint gradient = new RadialGradientPaint(
                        new Point2D.Double(p.x, p.y),
                        tamanioNodo,
                        new float[]{0.0f, 1.0f},
                        new Color[]{colorNodo.brighter(), colorNodo}
                );
                g2d.setPaint(gradient);
                g2d.fillOval(p.x - tamanioNodo, p.y - tamanioNodo, tamanioNodo * 2, tamanioNodo * 2);
                g2d.setPaint(originalPaint);

                // Borde
                g2d.setColor(colorNodo.darker());
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawOval(p.x - tamanioNodo, p.y - tamanioNodo, tamanioNodo * 2, tamanioNodo * 2);

                // Texto
                Font font = FUENTE_NODO;
                FontMetrics metrics = g2d.getFontMetrics(font);
                int textWidth = metrics.stringWidth(id);

                g2d.setFont(font);
                g2d.setColor(new Color(0, 0, 0, 100));
                g2d.drawString(id, p.x - textWidth / 2 + 1, p.y + 5 + 1);
                g2d.setColor(Color.WHITE);
                g2d.drawString(id, p.x - textWidth / 2, p.y + 5);
            }
        }

    }

    private void actualizarInfoPanel(String id) {
        Usuario usuario = AppContext.usuarioController.buscarUsuario(id);
        if (usuario != null) {
            StringBuilder info = new StringBuilder("<html><body>");
            info.append("<b>Usuario: </b>").append(usuario.getNombre()).append(" (").append(id).append(")<br>");

            // Concatenar intereses manualmente
            model.ListaEnlazada<String> intereses = usuario.getIntereses();
            StringBuilder interesesTexto = new StringBuilder();
            int count = 0;
            for (String interes : intereses) {
                if (count++ > 0) interesesTexto.append(", ");
                interesesTexto.append(interes);
            }
            info.append("<b>Intereses: </b>").append(interesesTexto).append("<br>");

            // Buscar vértice manualmente
            model.ListaEnlazada<model.Pair<String, model.Vertice>> vertices = AppContext.grafoController.getGrafo().getVertices();
            model.Vertice vertice = null;
            for (model.Pair<String, model.Vertice> par : vertices) {
                if (par.getKey().equals(id)) {
                    vertice = par.getValue();
                    break;
                }
            }

            if (vertice != null) {
                info.append("<b>Conexiones: </b>").append(vertice.getConexiones().tamano());
            }

            info.append("</body></html>");
            lblInfo.setText(info.toString());
        }
    }



}