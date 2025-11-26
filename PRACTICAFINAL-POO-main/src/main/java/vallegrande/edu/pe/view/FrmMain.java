// FrmMain.java con diseño mejorado
package vallegrande.edu.pe.view;

import vallegrande.edu.pe.model.Usuario;
import javax.swing.*;
import java.awt.*;

public class FrmMain extends JFrame {
    private JButton btnProductos;
    private JButton btnCategorias;
    private JButton btnUsuarios;
    private Usuario usuarioLogueado;

    // Colores pasteles elegantes
    private final Color COLOR_PRIMARIO = new Color(143, 188, 143); // Verde pastel
    private final Color COLOR_SECUNDARIO = new Color(240, 248, 255); // Azul muy claro
    private final Color COLOR_TERCIARIO = new Color(245, 255, 250); // Verde menta muy claro
    private final Color COLOR_TEXTO = new Color(50, 50, 50); // Gris oscuro elegante

    public FrmMain(Usuario usuario) {
        this.usuarioLogueado = usuario;
        initComponents();
        mostrarBienvenida();
    }

    public FrmMain() {
        this.usuarioLogueado = new Usuario(0, "Invitado", "invitado@email.com", "", "Invitado", "", true);
        initComponents();
    }

    private void initComponents() {
        setTitle("Sistema de Gestión - Fertilizantes Agrícolas - Usuario: " + usuarioLogueado.getNombre());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_SECUNDARIO);

        // Panel principal con diseño elegante
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(COLOR_SECUNDARIO);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título de bienvenida
        JLabel lblTitulo = new JLabel("Panel Principal", JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(COLOR_PRIMARIO.darker());
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Panel de botones con diseño de tarjetas
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 15, 15));
        panelBotones.setBackground(COLOR_SECUNDARIO);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        btnProductos = crearBotonTarjeta("Gestionar Productos", "📦", new Color(106, 168, 79));
        btnCategorias = crearBotonTarjeta("Gestionar Categorías", "📋", new Color(70, 130, 180));
        btnUsuarios = crearBotonTarjeta("Gestionar Usuarios", "👥", new Color(100, 149, 237));

        panelBotones.add(btnProductos);
        panelBotones.add(btnCategorias);
        panelBotones.add(btnUsuarios);

        panelPrincipal.add(panelBotones, BorderLayout.CENTER);

        // Información del usuario
        JPanel panelUsuario = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelUsuario.setBackground(COLOR_SECUNDARIO);
        JLabel lblUsuario = new JLabel("Conectado como: " + usuarioLogueado.getNombre() + " (" + usuarioLogueado.getRol() + ")");
        lblUsuario.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblUsuario.setForeground(COLOR_TEXTO);
        panelUsuario.add(lblUsuario);
        panelPrincipal.add(panelUsuario, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);

        // Eventos (se mantienen igual)
        btnProductos.addActionListener(e -> {
            FrmProducto frm = new FrmProducto();
            frm.setVisible(true);
        });

        btnCategorias.addActionListener(e -> {
            FrmCategoria frm = new FrmCategoria();
            frm.setVisible(true);
        });

        btnUsuarios.addActionListener(e -> {
            FrmUsuario frm = new FrmUsuario();
            frm.setVisible(true);
        });
    }

    private JButton crearBotonTarjeta(String texto, String icono, Color color) {
        JButton boton = new JButton("<html><center><font size='5'>" + icono + "</font><br><b>" + texto + "</b></center></html>") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(color.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(color.brighter());
                } else {
                    g2.setColor(color);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.setColor(color.darker());
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 25, 25);
                g2.dispose();

                super.paintComponent(g);
            }
        };

        boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton.setForeground(Color.WHITE);
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setVerticalTextPosition(SwingConstants.BOTTOM);
        boton.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return boton;
    }

    private void mostrarBienvenida() {
        System.out.println("Usuario logueado: " + usuarioLogueado.getNombre() + " - Rol: " + usuarioLogueado.getRol());
    }
}