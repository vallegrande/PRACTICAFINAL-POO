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

        // Modelo de tabla
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Stock");
        modeloTabla.addColumn("Estado");

        tablaProductos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaProductos);

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("Buscar:"));
        txtBuscar = new JTextField(20);
        panelBusqueda.add(txtBuscar);
        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(btnBuscar);

        // Botones CRUD
        btnNuevo = new JButton("Nuevo Producto");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnActualizar = new JButton("Actualizar Tabla");

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);

        // Layout principal
        setLayout(new BorderLayout());
        add(panelBusqueda, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnNuevo.addActionListener(e -> abrirFormularioNuevo());
        btnEditar.addActionListener(e -> editarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnActualizar.addActionListener(e -> controller.cargarProductos());
        btnBuscar.addActionListener(e -> buscarProductos());

        txtBuscar.addActionListener(e -> buscarProductos());
    }

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