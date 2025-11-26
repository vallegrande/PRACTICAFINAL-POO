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

    // Colores pasteles elegantes
    private final Color COLOR_PRIMARIO = new Color(143, 188, 143); // Verde pastel
    private final Color COLOR_SECUNDARIO = new Color(240, 248, 255); // Azul muy claro
    private final Color COLOR_TERCIARIO = new Color(245, 255, 250); // Verde menta muy claro
    private final Color COLOR_TEXTO = new Color(50, 50, 50); // Gris oscuro elegante

    public FrmLogin() {
        initComponents();
        controller = new LoginController(this);
        usuarioService = new UsuarioService();
    }

    private void initComponents() {
        setTitle("Iniciar Sesión - Sistema de Fertilizantes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(COLOR_SECUNDARIO);

        // Panel principal con diseño elegante
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(COLOR_SECUNDARIO);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Título con diseño mejorado
        JLabel lblTitulo = new JLabel("Sistema de Fertilizantes Agrícolas", JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(COLOR_PRIMARIO.darker());
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Panel de campos con diseño elegante
        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBackground(COLOR_TERCIARIO);
        panelCampos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_PRIMARIO, 1, true),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Email
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEmail.setForeground(COLOR_TEXTO);
        panelCampos.add(lblEmail, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_PRIMARIO),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        panelCampos.add(txtEmail, gbc);

        // Contraseña
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPassword.setForeground(COLOR_TEXTO);
        panelCampos.add(lblPassword, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_PRIMARIO),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        panelCampos.add(txtPassword, gbc);

        // Checkbox mostrar contraseña
        gbc.gridx = 1; gbc.gridy = 2;
        JCheckBox chkMostrar = new JCheckBox("Mostrar contraseña");
        chkMostrar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        chkMostrar.setBackground(COLOR_TERCIARIO);
        chkMostrar.setFocusPainted(false);
        chkMostrar.addActionListener(e -> {
            if (chkMostrar.isSelected()) {
                txtPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar('•');
            }
        });
        panelCampos.add(chkMostrar, gbc);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelBotones.setBackground(COLOR_TERCIARIO);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        btnLogin = crearBotonElegante("Iniciar Sesión", COLOR_PRIMARIO);
        btnRegistrar = crearBotonElegante("Registrarse", new Color(100, 149, 237)); // Azul pastel

        panelBotones.add(btnLogin);
        panelBotones.add(btnRegistrar);

        // Agregar componentes al panel principal
        panelPrincipal.add(panelCampos, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);

        // Eventos (se mantienen igual)
        btnLogin.addActionListener(e -> {
            String email = txtEmail.getText().trim();
            String password = new String(txtPassword.getPassword());
            controller.autenticar(email, password);
        });

        btnRegistrar.addActionListener(e -> abrirRegistro());
        txtPassword.addActionListener(e -> btnLogin.doClick());
    }

    private JButton crearBotonElegante(String texto, Color colorFondo) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(colorFondo.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(colorFondo.brighter());
                } else {
                    g2.setColor(colorFondo);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(colorFondo.darker());
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
                g2.dispose();

                super.paintComponent(g);
            }
        };

        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return boton;
    }

    // Los métodos restantes se mantienen EXACTAMENTE IGUALES
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarMenuPrincipal(Usuario usuario) {
        dispose();
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