// src/main/java/vallegrande/edu/pe/view/FrmLogin.java
package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.LoginController;
import vallegrande.edu.pe.model.Usuario;
import vallegrande.edu.pe.service.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmLogin extends JFrame {
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegistrar;
    private LoginController controller;
    private UsuarioService usuarioService;

    public FrmLogin() {
        initComponents();
        controller = new LoginController(this);
        usuarioService = new UsuarioService(); // Inicializar el servicio
    }

    private void initComponents() {
        setTitle("Iniciar Sesión - Sistema de Fertilizantes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel lblTitulo = new JLabel("Sistema de Fertilizantes Agrícolas", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Panel de campos
        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        panelCampos.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelCampos.add(txtEmail);

        panelCampos.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panelCampos.add(txtPassword);

        panelCampos.add(new JLabel("")); // Espacio vacío
        JCheckBox chkMostrar = new JCheckBox("Mostrar contraseña");
        chkMostrar.addActionListener(e -> {
            if (chkMostrar.isSelected()) {
                txtPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar('•');
            }
        });
        panelCampos.add(chkMostrar);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnLogin = new JButton("Iniciar Sesión");
        btnRegistrar = new JButton("Registrarse");

        panelBotones.add(btnLogin);
        panelBotones.add(btnRegistrar);

        panelPrincipal.add(panelCampos, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);

        // Eventos
        btnLogin.addActionListener(e -> {
            String email = txtEmail.getText().trim();
            String password = new String(txtPassword.getPassword());
            controller.autenticar(email, password);
        });

        btnRegistrar.addActionListener(e -> abrirRegistro());

        // Enter para iniciar sesión
        txtPassword.addActionListener(e -> btnLogin.doClick());
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarMenuPrincipal(Usuario usuario) {
        // Cerrar login
        dispose();

        // Abrir menú principal
        FrmMain main = new FrmMain(usuario);
        main.setVisible(true);

        JOptionPane.showMessageDialog(null,
                "Bienvenido: " + usuario.getNombre() + "\nRol: " + usuario.getRol(),
                "Login Exitoso",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void abrirRegistro() {
        FrmRegistroUsuario registro = new FrmRegistroUsuario(this, "Registro de Usuario", true);
        registro.setVisible(true);

        // ✅ ESTA ES LA PARTE QUE FALTA: Guardar el usuario después del registro
        Usuario nuevoUsuario = registro.getUsuario();
        if (nuevoUsuario != null) {
            System.out.println("🔍 Usuario obtenido del registro:");
            System.out.println("   Nombre: " + nuevoUsuario.getNombre());
            System.out.println("   Email: " + nuevoUsuario.getEmail());
            System.out.println("   Rol: " + nuevoUsuario.getRol());

            try {
                usuarioService.registrarUsuario(nuevoUsuario);
                System.out.println("✅ Usuario guardado en la base de datos");

                JOptionPane.showMessageDialog(this,
                        "Usuario registrado exitosamente. Ahora puede iniciar sesión.",
                        "Registro Exitoso",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                System.err.println("❌ Error al guardar usuario: " + e.getMessage());
                JOptionPane.showMessageDialog(this,
                        "Error al registrar usuario: " + e.getMessage(),
                        "Error de Registro",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("❌ Registro cancelado por el usuario");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            FrmLogin login = new FrmLogin();
            login.setVisible(true);
        });
    }
}