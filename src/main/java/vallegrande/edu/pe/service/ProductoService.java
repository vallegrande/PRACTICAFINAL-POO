package vallegrande.edu.pe.service;

import vallegrande.edu.pe.model.Producto;
import vallegrande.edu.pe.model.ProductoDAO;
import java.util.List;

public class ProductoService {
    private ProductoDAO productoDAO;

    public ProductoService() {
        this.productoDAO = new ProductoDAO();
    }

    public List<Producto> obtenerTodosLosProductos() {
        return productoDAO.listar();
    }

    public void registrarProducto(Producto producto) {
        productoDAO.insertar(producto);
    }

    public void modificarProducto(Producto producto) {
        productoDAO.actualizar(producto);
    }

    public void eliminarProducto(int id) {
        productoDAO.eliminar(id);
    }

    public Producto buscarProductoPorId(int id) {
        return productoDAO.buscarPorId(id);
    }

    // NUEVO MÉTODO: Buscar productos por criterio
    public List<Producto> buscarProductos(String criterio) {
        return productoDAO.buscarPorCriterio(criterio);
    }
}