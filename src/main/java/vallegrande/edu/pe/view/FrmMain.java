// Actualiza tu FrmMain existente con este constructor:
package vallegrande.edu.pe.view;

import vallegrande.edu.pe.model.Usuario;
import javax.swing.*;
import java.awt.*;

public class FrmMain extends JFrame {
    private JButton btnProductos;
    private JButton btnCategorias;
    private JButton btnUsuarios;
    private Usuario usuarioLogueado;

    // Nuevo constructor que recibe el usuario
    public FrmMain(Usuario usuario) {
        this.usuarioLogueado = usuario;
        initComponents();
        mostrarBienvenida();
    }

    // Mantén el constructor vacío por compatibilidad (opcional)
    public FrmMain() {
        this.usuarioLogueado = new Usuario(0, "Invitado", "invitado@email.com", "", "Invitado", "", true);
        initComponents();
    }

    private void initComponents() {
        setTitle("Sistema de Gestión - Fertilizantes Agrícolas - Usuario: " + usuarioLogueado.getNombre());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        btnProductos = new JButton("Gestionar Productos");
        btnCategorias = new JButton("Gestionar Categorías");
        btnUsuarios = new JButton("Gestionar Usuarios");

        // Estilizar botones
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        btnProductos.setFont(buttonFont);
        btnCategorias.setFont(buttonFont);
        btnUsuarios.setFont(buttonFont);

        panel.add(btnProductos);
        panel.add(btnCategorias);
        panel.add(btnUsuarios);

        add(panel);

        // Eventos
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

    private void mostrarBienvenida() {
        // El título ya muestra el nombre del usuario
        System.out.println("Usuario logueado: " + usuarioLogueado.getNombre() + " - Rol: " + usuarioLogueado.getRol());
    }

    // Elimina o comenta el método main original para usar el de FrmLogin
    /*
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            FrmMain frame = new FrmMain();
            frame.setVisible(true);
        });
    }
    */
}