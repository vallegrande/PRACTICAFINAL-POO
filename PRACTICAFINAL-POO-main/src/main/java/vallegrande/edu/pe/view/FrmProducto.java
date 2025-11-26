// src/main/java/vallegrande/edu/pe/view/FrmProducto.java
package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.ProductoController;
import vallegrande.edu.pe.model.Producto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmProducto extends JFrame {
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JButton btnActualizar, btnNuevo, btnEditar, btnEliminar, btnBuscar;
    private JTextField txtBuscar;
    private ProductoController controller;

    // Colores pasteles elegantes
    private final Color COLOR_PRIMARIO = new Color(143, 188, 143); // Verde pastel
    private final Color COLOR_SECUNDARIO = new Color(240, 248, 255); // Azul muy claro
    private final Color COLOR_TERCIARIO = new Color(245, 255, 250); // Verde menta muy claro
    private final Color COLOR_TEXTO = new Color(50, 50, 50); // Gris oscuro elegante
    private final Color COLOR_BORDE = new Color(200, 230, 200); // Verde claro para bordes

    public FrmProducto() {
        initComponents();
        controller = new ProductoController(this);
        controller.cargarProductos();
    }

    private void initComponents() {
        setTitle("Gestión de Productos - Fertilizantes Agrícolas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
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
                return false;
            }
        };
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Stock");
        modeloTabla.addColumn("Estado");

        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setSelectionBackground(COLOR_PRIMARIO);
        tablaProductos.setSelectionForeground(Color.black);
        tablaProductos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaProductos.setRowHeight(25);
        tablaProductos.setShowGrid(true);
        tablaProductos.setGridColor(new Color(220, 220, 220));

        // Header de la tabla
        tablaProductos.getTableHeader().setBackground(COLOR_PRIMARIO);
        tablaProductos.getTableHeader().setForeground(Color.black);
        tablaProductos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(tablaProductos);
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
        btnNuevo = crearBotonElegante("Nuevo Producto", new Color(106, 168, 79));
        btnEditar = crearBotonElegante("Editar", new Color(70, 130, 180));
        btnEliminar = crearBotonElegante("Eliminar", new Color(205, 92, 92));
        btnActualizar = crearBotonElegante("Actualizar Tabla", new Color(100, 149, 237));

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
        btnEditar.addActionListener(e -> editarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnActualizar.addActionListener(e -> controller.cargarProductos());
        btnBuscar.addActionListener(e -> buscarProductos());
        txtBuscar.addActionListener(e -> buscarProductos());
    }

    private JButton crearBotonElegante(String texto, Color colorFondo) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gradiente = new GradientPaint(
                        0, 0, colorFondo.brighter(),
                        0, getHeight(), colorFondo.darker()
                );
                g2.setPaint(gradiente);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

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

    // === MÉTODOS QUE SE MANTIENEN EXACTAMENTE IGUALES ===

    private void abrirFormularioNuevo() {
        FrmRegistroProducto dialog = new FrmRegistroProducto(this, "Registrar Nuevo Producto");
        dialog.setVisible(true);

        Producto nuevoProducto = dialog.getProducto();
        if (nuevoProducto != null) {
            controller.registrarProducto(nuevoProducto);
        }
    }

    private void editarProducto() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un producto para editar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        Producto producto = controller.buscarProductoPorId(id);

        if (producto != null) {
            FrmRegistroProducto dialog = new FrmRegistroProducto(this, "Editar Producto", producto);
            dialog.setVisible(true);

            Producto productoEditado = dialog.getProducto();
            if (productoEditado != null) {
                controller.modificarProducto(productoEditado);
            }
        }
    }

    private void eliminarProducto() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un producto para eliminar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar el producto: " + nombre + "?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            controller.eliminarProducto(id);
        }
    }

    private void buscarProductos() {
        String criterio = txtBuscar.getText().trim();
        if (criterio.isEmpty()) {
            controller.cargarProductos();
        } else {
            controller.buscarProductos(criterio);
        }
    }

    public void mostrarProductos(List<Producto> productos) {
        modeloTabla.setRowCount(0);

        for (Producto producto : productos) {
            Object[] fila = {
                    producto.getId(),
                    producto.getNombre(),
                    String.format("S/. %.2f", producto.getPrecio()),
                    producto.getStock(),
                    producto.getEstado()
            };
            modeloTabla.addRow(fila);
        }

        if (productos.isEmpty() && !txtBuscar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No se encontraron productos con el criterio: " + txtBuscar.getText(),
                    "Búsqueda sin resultados",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}