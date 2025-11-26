package vallegrande.edu.pe.controller;

import vallegrande.edu.pe.model.Usuario;
import vallegrande.edu.pe.service.UsuarioService;
import vallegrande.edu.pe.view.FrmLogin;

public class LoginController {
    private UsuarioService usuarioService;
    private FrmLogin vista;

    public LoginController(FrmLogin vista) {
        this.vista = vista;
        this.usuarioService = new UsuarioService();
    }

    public boolean autenticar(String email, String password) {
        System.out.println("🔐 Intentando autenticar: " + email);

        if (email.isEmpty() || password.isEmpty()) {
            vista.mostrarError("Por favor, complete todos los campos");
            return false;
        }

        Usuario usuario = usuarioService.autenticarUsuario(email, password);
        if (usuario != null) {
            System.out.println("✅ Autenticación exitosa: " + usuario.getNombre());
            vista.mostrarMenuPrincipal(usuario);
            return true;
        } else {
            System.out.println("❌ Autenticación fallida para: " + email);
            vista.mostrarError("Credenciales incorrectas o usuario inactivo");
            return false;
        }
    }

    public void abrirRegistro() {
        System.out.println("📝 Abriendo formulario de registro...");
        vista.abrirRegistro();
    }
}