// src/main/java/vallegrande/edu/pe/view/FrmUsuario.java
package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.UsuarioController;
import vallegrande.edu.pe.model.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmUsuario extends JFrame {
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JButton btnActualizar, btnNuevo, btnEditar, btnEliminar, btnBuscar;
    private JTextField txtBuscar;
    private UsuarioController controller;

    public FrmUsuario() {
        initComponents();
        controller = new UsuarioController(this);
        controller.cargarUsuarios();
    }

    private void initComponents() {
        setTitle("Gestión de Usuarios - Fertilizantes Agrícolas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);

        // Modelo de tabla
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Email");
        modeloTabla.addColumn("Rol");
        modeloTabla.addColumn("Teléfono");
        modeloTabla.addColumn("Activo");

        tablaUsuarios = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("Buscar:"));
        txtBuscar = new JTextField(20);
        panelBusqueda.add(txtBuscar);
        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(btnBuscar);

        // Botones CRUD
        btnNuevo = new JButton("Nuevo Usuario");
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
        btnEditar.addActionListener(e -> editarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuario());
        btnActualizar.addActionListener(e -> controller.actualizarTabla());
        btnBuscar.addActionListener(e -> buscarUsuarios());

        // Buscar al presionar Enter en el campo de búsqueda
        txtBuscar.addActionListener(e -> buscarUsuarios());
    }

    private void abrirFormularioNuevo() {
        FrmRegistroUsuario dialog = new FrmRegistroUsuario(this, "Registrar Nuevo Usuario");
        dialog.setVisible(true);

        Usuario nuevoUsuario = dialog.getUsuario();
        if (nuevoUsuario != null) {
            controller.registrarUsuario(nuevoUsuario);
        }
    }

    private void editarUsuario() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un usuario para editar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        Usuario usuario = controller.buscarUsuarioPorId(id);

        if (usuario != null) {
            FrmRegistroUsuario dialog = new FrmRegistroUsuario(this, "Editar Usuario", usuario);
            dialog.setVisible(true);

            Usuario usuarioEditado = dialog.getUsuario();
            if (usuarioEditado != null) {
                controller.modificarUsuario(usuarioEditado);
            }
        }
    }

    private void eliminarUsuario() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un usuario para eliminar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar el usuario: " + nombre + "?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            controller.eliminarUsuario(id);
        }
    }

    private void buscarUsuarios() {
        String criterio = txtBuscar.getText().trim();
        if (criterio.isEmpty()) {
            controller.cargarUsuarios(); // Mostrar todos si está vacío
        } else {
            controller.buscarUsuarios(criterio);
        }
    }

    public void mostrarUsuarios(List<Usuario> usuarios) {
        modeloTabla.setRowCount(0); // Limpiar tabla

        for (Usuario usuario : usuarios) {
            Object[] fila = {
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getEmail(),
                    usuario.getRol(),
                    usuario.getTelefono(),
                    usuario.isActivo() ? "Sí" : "No"
            };
            modeloTabla.addRow(fila);
        }

        // Mostrar mensaje si no hay resultados
        if (usuarios.isEmpty() && !txtBuscar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No se encontraron usuarios con el criterio: " + txtBuscar.getText(),
                    "Búsqueda sin resultados",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
