// src/main/java/vallegrande/edu/pe/view/FrmCategoria.java
package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.CategoriaController;
import vallegrande.edu.pe.model.Categoria;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmCategoria extends JFrame {
    private JTable tablaCategorias;
    private DefaultTableModel modeloTabla;
    private JButton btnActualizar, btnNuevo, btnEditar, btnEliminar, btnBuscar;
    private JTextField txtBuscar;
    private CategoriaController controller;

    // Colores pasteles elegantes
    private final Color COLOR_PRIMARIO = new Color(143, 188, 143); // Verde pastel
    private final Color COLOR_SECUNDARIO = new Color(240, 248, 255); // Azul muy claro
    private final Color COLOR_TERCIARIO = new Color(245, 255, 250); // Verde menta muy claro
    private final Color COLOR_TEXTO = new Color(50, 50, 50); // Gris oscuro elegante
    private final Color COLOR_BORDE = new Color(200, 230, 200); // Verde claro para bordes

    public FrmCategoria() {
        initComponents();
        controller = new CategoriaController(this);
        controller.cargarCategorias();
    }

    private void initComponents() {
        setTitle("Gestión de Categorías - Fertilizantes Agrícolas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_SECUNDARIO);

        // Panel principal con diseño elegante
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(COLOR_SECUNDARIO);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Modelo de tabla con diseño mejorado
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Descripción");
        modeloTabla.addColumn("Tipo Fertilizante");

        tablaCategorias = new JTable(modeloTabla);
        tablaCategorias.setSelectionBackground(COLOR_PRIMARIO);
        tablaCategorias.setSelectionForeground(Color.WHITE);
        tablaCategorias.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaCategorias.setRowHeight(25);
        tablaCategorias.setShowGrid(true);
        tablaCategorias.setGridColor(new Color(220, 220, 220));

        // Header de la tabla
        tablaCategorias.getTableHeader().setBackground(COLOR_PRIMARIO);
        tablaCategorias.getTableHeader().setForeground(Color.black);
        tablaCategorias.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(tablaCategorias);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDE));

        // Panel de búsqueda con diseño mejorado
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBusqueda.setBackground(COLOR_TERCIARIO);
        panelBusqueda.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblBuscar.setForeground(COLOR_TEXTO);
        panelBusqueda.add(lblBuscar);

        txtBuscar = new JTextField(20);
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        panelBusqueda.add(txtBuscar);

        btnBuscar = crearBotonElegante("Buscar", COLOR_PRIMARIO);
        panelBusqueda.add(btnBuscar);

        // Botones CRUD con diseño mejorado
        btnNuevo = crearBotonElegante("Nueva Categoría", new Color(106, 168, 79)); // Verde más vibrante
        btnEditar = crearBotonElegante("Editar", new Color(70, 130, 180)); // Azul steel
        btnEliminar = crearBotonElegante("Eliminar", new Color(205, 92, 92)); // Rojo pastel
        btnActualizar = crearBotonElegante("Actualizar Tabla", new Color(100, 149, 237)); // Azul cornflower

        // Panel de botones con diseño
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(COLOR_TERCIARIO);
        panelBotones.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, COLOR_BORDE),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);

        // Layout principal
        panelPrincipal.add(panelBusqueda, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);

        // Eventos (se mantienen igual)
        btnNuevo.addActionListener(e -> abrirFormularioNuevo());
        btnEditar.addActionListener(e -> editarCategoria());
        btnEliminar.addActionListener(e -> eliminarCategoria());
        btnActualizar.addActionListener(e -> controller.actualizarTabla());
        btnBuscar.addActionListener(e -> buscarCategorias());
        txtBuscar.addActionListener(e -> buscarCategorias());
    }

    private JButton crearBotonElegante(String texto, Color colorFondo) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fondo con gradiente
                GradientPaint gradiente = new GradientPaint(
                        0, 0, colorFondo.brighter(),
                        0, getHeight(), colorFondo.darker()
                );
                g2.setPaint(gradiente);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Borde
                g2.setColor(colorFondo.darker().darker());
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);

                g2.dispose();

                super.paintComponent(g);
            }
        };

        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return boton;
    }

    // Los métodos restantes se mantienen EXACTAMENTE IGUALES
    private void abrirFormularioNuevo() {
        FrmRegistroCategoria dialog = new FrmRegistroCategoria(this, "Registrar Nueva Categoría");
        dialog.setVisible(true);

        Categoria nuevaCategoria = dialog.getCategoria();
        if (nuevaCategoria != null) {
            controller.registrarCategoria(nuevaCategoria);
        }
    }

    private void editarCategoria() {
        int filaSeleccionada = tablaCategorias.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione una categoría para editar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        Categoria categoria = controller.buscarCategoriaPorId(id);

        if (categoria != null) {
            FrmRegistroCategoria dialog = new FrmRegistroCategoria(this, "Editar Categoría", categoria);
            dialog.setVisible(true);

            Categoria categoriaEditada = dialog.getCategoria();
            if (categoriaEditada != null) {
                controller.modificarCategoria(categoriaEditada);
            }
        }
    }

    private void eliminarCategoria() {
        int filaSeleccionada = tablaCategorias.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione una categoría para eliminar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar la categoría: " + nombre + "?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            controller.eliminarCategoria(id);
        }
    }

    private void buscarCategorias() {
        String criterio = txtBuscar.getText().trim();
        if (criterio.isEmpty()) {
            controller.cargarCategorias();
        } else {
            controller.buscarCategorias(criterio);
        }
    }

    public void mostrarCategorias(List<Categoria> categorias) {
        modeloTabla.setRowCount(0);

        for (Categoria categoria : categorias) {
            Object[] fila = {
                    categoria.getId(),
                    categoria.getNombre(),
                    categoria.getDescripcion(),
                    categoria.getTipoFertilizante()
            };
            modeloTabla.addRow(fila);
        }

        if (categorias.isEmpty() && !txtBuscar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No se encontraron categorías con el criterio: " + txtBuscar.getText(),
                    "Búsqueda sin resultados",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}