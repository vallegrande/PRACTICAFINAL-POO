// src/main/java/vallegrande/edu/pe/controller/CategoriaController.java
package vallegrande.edu.pe.controller;

import vallegrande.edu.pe.model.Categoria;
import vallegrande.edu.pe.service.CategoriaService;
import vallegrande.edu.pe.view.FrmCategoria;
import java.util.List;

public class CategoriaController {
    private CategoriaService categoriaService;
    private FrmCategoria vista;

    public CategoriaController(FrmCategoria vista) {
        this.vista = vista;
        this.categoriaService = new CategoriaService();
    }

    public void cargarCategorias() {
        List<Categoria> categorias = categoriaService.obtenerTodasLasCategorias();
        vista.mostrarCategorias(categorias);
    }

    public void registrarCategoria(Categoria categoria) {
        categoriaService.registrarCategoria(categoria);
        cargarCategorias();
    }

    public void modificarCategoria(Categoria categoria) {
        categoriaService.modificarCategoria(categoria);
        cargarCategorias();
    }

    public void eliminarCategoria(int id) {
        categoriaService.eliminarCategoria(id);
        cargarCategorias();
    }

    public Categoria buscarCategoriaPorId(int id) {
        return categoriaService.buscarCategoriaPorId(id);
    }

    public void buscarCategorias(String criterio) {
        List<Categoria> categorias = categoriaService.buscarCategorias(criterio);
        vista.mostrarCategorias(categorias);
    }

    public void actualizarTabla() {
        cargarCategorias();
    }
}