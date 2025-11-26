// src/main/java/vallegrande/edu/pe/service/UsuarioService.java
package vallegrande.edu.pe.service;

import vallegrande.edu.pe.model.Usuario;
import vallegrande.edu.pe.model.UsuarioDAO;
import java.util.List;

public class UsuarioService {
    private UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioDAO.listar();
    }

    public void registrarUsuario(Usuario usuario) {
        usuarioDAO.insertar(usuario);
    }

    public void modificarUsuario(Usuario usuario) {
        usuarioDAO.actualizar(usuario);
    }

    public void eliminarUsuario(int id) {
        usuarioDAO.eliminar(id);
    }

    public Usuario buscarUsuarioPorId(int id) {
        return usuarioDAO.buscarPorId(id);
    }

    public List<Usuario> buscarUsuarios(String criterio) {
        return usuarioDAO.buscarPorCriterio(criterio);
    }


    // Agrega estos métodos a tu UsuarioService existente


    public Usuario autenticarUsuario(String email, String password) {
        return usuarioDAO.autenticar(email, password);
    }

    public boolean emailExiste(String email) {
        return usuarioDAO.existeEmail(email);
    }
}

