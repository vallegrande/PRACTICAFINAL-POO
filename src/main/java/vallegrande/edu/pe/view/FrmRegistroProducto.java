package vallegrande.edu.pe.view;

import vallegrande.edu.pe.model.Producto;
import javax.swing.*;
import java.awt.*;

public class FrmRegistroProducto extends JDialog {

    private JTextField txtNombre, txtPrecio, txtStock;
    private JButton btnGuardar, btnCancelar;
    private Producto producto;

    public FrmRegistroProducto(Frame owner, String titulo) {
        super(owner, titulo, true);
        initComponents();
    }

    public FrmRegistroProducto(Frame owner, String titulo, Producto producto) {
        super(owner, titulo, true);
        this.producto = producto;
        initComponents();
        cargarDatos();
    }

    private void initComponents() {
        setSize(350, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        add(txtPrecio);

        add(new JLabel("Stock:"));
        txtStock = new JTextField();
        add(txtStock);

        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        add(btnGuardar);
        add(btnCancelar);

        btnGuardar.addActionListener(e -> guardar());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void cargarDatos() {
        if (producto != null) {
            txtNombre.setText(producto.getNombre());
            txtPrecio.setText(String.valueOf(producto.getPrecio()));
            txtStock.setText(String.valueOf(producto.getStock()));
        }
    }

    private void guardar() {
        try {
            String nombre = txtNombre.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío");
                return;
            }

            if (precio <= 0 || stock < 0) {
                JOptionPane.showMessageDialog(this, "Datos numéricos inválidos");
                return;
            }

            if (producto == null) producto = new Producto();

            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setStock(stock);

            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Complete correctamente los campos.");
        }
    }

    public Producto getProducto() {
        return producto;
    }
}
