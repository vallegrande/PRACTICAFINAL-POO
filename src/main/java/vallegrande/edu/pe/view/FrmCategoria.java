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

        // Modelo de tabla
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Descripción");
        modeloTabla.addColumn("Tipo Fertilizante");

        tablaCategorias = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaCategorias);

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("Buscar:"));
        txtBuscar = new JTextField(20);
        panelBusqueda.add(txtBuscar);
        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(btnBuscar);

        // Botones CRUD
        btnNuevo = new JButton("Nueva Categoría");
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
        btnEditar.addActionListener(e -> editarCategoria());
        btnEliminar.addActionListener(e -> eliminarCategoria());
        btnActualizar.addActionListener(e -> controller.actualizarTabla());
        btnBuscar.addActionListener(e -> buscarCategorias());

        txtBuscar.addActionListener(e -> buscarCategorias());
    }

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