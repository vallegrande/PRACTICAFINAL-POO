// src/main/java/vallegrande/edu/pe/view/FrmRegistroCategoria.java
package vallegrande.edu.pe.view;

import vallegrande.edu.pe.model.Categoria;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmRegistroCategoria extends JDialog {
    private JTextField txtNombre;
    private JTextArea txtDescripcion;
    private JComboBox<String> cmbTipoFertilizante;
    private JButton btnGuardar;
    private JButton btnCancelar;

    private Categoria categoria;
    private boolean guardado = false;

    public FrmRegistroCategoria(JFrame parent, String titulo) {
        super(parent, titulo, true);
        this.categoria = new Categoria();
        initComponents();
    }

    public FrmRegistroCategoria(JFrame parent, String titulo, Categoria categoria) {
        super(parent, titulo, true);
        this.categoria = categoria;
        initComponents();
        cargarDatos();
    }

    private void initComponents() {
        setSize(400, 300);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Campos del formulario
        panelCampos.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelCampos.add(txtNombre);

        panelCampos.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextArea(3, 20);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        panelCampos.add(scrollDescripcion);

        panelCampos.add(new JLabel("Tipo Fertilizante:"));
        cmbTipoFertilizante = new JComboBox<>(new String[]{"Organico", "Quimico", "Mineral", "Liquido"});
        panelCampos.add(cmbTipoFertilizante);

        // Botones
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(panelCampos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCategoria();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void cargarDatos() {
        if (categoria.getId() != 0) {
            txtNombre.setText(categoria.getNombre());
            txtDescripcion.setText(categoria.getDescripcion());
            cmbTipoFertilizante.setSelectedItem(categoria.getTipoFertilizante());
        }
    }

    private void guardarCategoria() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El nombre de la categoría es obligatorio",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        categoria.setNombre(txtNombre.getText().trim());
        categoria.setDescripcion(txtDescripcion.getText().trim());
        categoria.setTipoFertilizante(cmbTipoFertilizante.getSelectedItem().toString());

        guardado = true;
        dispose();
    }

    public Categoria getCategoria() {
        return guardado ? categoria : null;
    }
}
