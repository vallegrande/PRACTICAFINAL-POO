// src/main/java/vallegrande/edu/pe/service/CategoriaService.java
package vallegrande.edu.pe.service;

import vallegrande.edu.pe.model.Categoria;
import vallegrande.edu.pe.model.CategoriaDAO;
import java.util.List;

public class CategoriaService {
    private CategoriaDAO categoriaDAO;

    public CategoriaService() {
        this.categoriaDAO = new CategoriaDAO();
    }

    public List<Categoria> obtenerTodasLasCategorias() {
        return categoriaDAO.listar();
    }

    public void registrarCategoria(Categoria categoria) {
        categoriaDAO.insertar(categoria);
    }

    public void modificarCategoria(Categoria categoria) {
        categoriaDAO.actualizar(categoria);
    }

    public void eliminarCategoria(int id) {
        categoriaDAO.eliminar(id);
    }

    public Categoria buscarCategoriaPorId(int id) {
        return categoriaDAO.buscarPorId(id);
    }

    public List<Categoria> buscarCategorias(String criterio) {
        return categoriaDAO.buscarPorCriterio(criterio);
    }
}