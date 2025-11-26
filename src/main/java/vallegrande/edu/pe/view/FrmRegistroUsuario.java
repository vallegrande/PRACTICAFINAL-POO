// src/main/java/vallegrande/edu/pe/view/FrmRegistroUsuario.java
package vallegrande.edu.pe.view;

import vallegrande.edu.pe.model.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmRegistroUsuario extends JDialog {
    private JTextField txtNombre;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRol;
    private JTextField txtTelefono;
    private JCheckBox chkActivo;
    private JButton btnGuardar;
    private JButton btnCancelar;

    private Usuario usuario;
    private boolean guardado = false;
    private boolean esRegistroDesdeLogin = false;

    public FrmRegistroUsuario(JFrame parent, String titulo, boolean esRegistroDesdeLogin) {
        super(parent, titulo, true);
        this.usuario = new Usuario();
        this.esRegistroDesdeLogin = esRegistroDesdeLogin;
        initComponents();

        if (esRegistroDesdeLogin) {
            // Configuraciones por defecto para registro desde login
            cmbRol.setSelectedItem("Vendedor");
            chkActivo.setSelected(true);
        }
    }

    public FrmRegistroUsuario(JFrame parent, String titulo, Usuario usuario) {
        super(parent, titulo, true);
        this.usuario = usuario;
        initComponents();
        cargarDatos();
    }

    public FrmRegistroUsuario(FrmUsuario frmUsuario, String registrarNuevoUsuario) {
    }

    private void initComponents() {
        setSize(400, 350);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        JPanel panelCampos = new JPanel(new GridLayout(6, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Campos del formulario
        panelCampos.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelCampos.add(txtNombre);

        panelCampos.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelCampos.add(txtEmail);

        panelCampos.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panelCampos.add(txtPassword);

        panelCampos.add(new JLabel("Rol:"));
        cmbRol = new JComboBox<>(new String[]{"Administrador", "Vendedor", "Almacen"});
        panelCampos.add(cmbRol);

        panelCampos.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelCampos.add(txtTelefono);

        panelCampos.add(new JLabel("Activo:"));
        chkActivo = new JCheckBox();
        chkActivo.setSelected(true);
        panelCampos.add(chkActivo);

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
                guardarUsuario();
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
        if (usuario.getId() != 0) {
            txtNombre.setText(usuario.getNombre());
            txtEmail.setText(usuario.getEmail());
            // No cargamos la contraseña por seguridad
            cmbRol.setSelectedItem(usuario.getRol());
            txtTelefono.setText(usuario.getTelefono());
            chkActivo.setSelected(usuario.isActivo());
        }
    }

    // En tu FrmRegistroUsuario, mejora el método guardarUsuario:
    private void guardarUsuario() {
        // Validaciones más estrictas
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El nombre es obligatorio",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();
            return;
        }

        if (txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El email es obligatorio",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return;
        }

        // Validar formato de email
        if (!txtEmail.getText().trim().contains("@")) {
            JOptionPane.showMessageDialog(this,
                    "El email debe tener un formato válido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return;
        }

        String password = new String(txtPassword.getPassword());
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "La contraseña es obligatoria",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            txtPassword.requestFocus();
            return;
        }

        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this,
                    "La contraseña debe tener al menos 4 caracteres",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            txtPassword.requestFocus();
            return;
        }

        try {
            usuario.setNombre(txtNombre.getText().trim());
            usuario.setEmail(txtEmail.getText().trim());
            usuario.setPassword(password);
            usuario.setRol(cmbRol.getSelectedItem().toString());
            usuario.setTelefono(txtTelefono.getText().trim());
            usuario.setActivo(chkActivo.isSelected());

            guardado = true;

            if (esRegistroDesdeLogin && guardado) {
                JOptionPane.showMessageDialog(this,
                        "Usuario registrado exitosamente. Ahora puede iniciar sesión.",
                        "Registro Exitoso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (guardado) {
                JOptionPane.showMessageDialog(this,
                        "Usuario registrado exitosamente.",
                        "Registro Exitoso",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar usuario: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public Usuario getUsuario() {
        return guardado ? usuario : null;
    }
}