// src/main/java/vallegrande/edu/pe/controller/UsuarioController.java
package vallegrande.edu.pe.controller;

import vallegrande.edu.pe.model.Usuario;
import vallegrande.edu.pe.service.UsuarioService;
import vallegrande.edu.pe.view.FrmUsuario;
import java.util.List;

public class UsuarioController {
    private UsuarioService usuarioService;
    private FrmUsuario vista;

    public UsuarioController(FrmUsuario vista) {
        this.vista = vista;
        this.usuarioService = new UsuarioService();
    }

    public void cargarUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        vista.mostrarUsuarios(usuarios);
    }

    public void registrarUsuario(Usuario usuario) {
        usuarioService.registrarUsuario(usuario);
        cargarUsuarios();
    }

    public void modificarUsuario(Usuario usuario) {
        usuarioService.modificarUsuario(usuario);
        cargarUsuarios();
    }

    public void eliminarUsuario(int id) {
        usuarioService.eliminarUsuario(id);
        cargarUsuarios();
    }

    public Usuario buscarUsuarioPorId(int id) {
        return usuarioService.buscarUsuarioPorId(id);
    }

    public void buscarUsuarios(String criterio) {
        List<Usuario> usuarios = usuarioService.buscarUsuarios(criterio);
        vista.mostrarUsuarios(usuarios);
    }

    public void actualizarTabla() {
        cargarUsuarios();
    }
}