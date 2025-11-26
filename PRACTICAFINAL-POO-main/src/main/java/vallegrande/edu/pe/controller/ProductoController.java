package vallegrande.edu.pe.controller;

import vallegrande.edu.pe.model.Producto;
import vallegrande.edu.pe.service.ProductoService;
import vallegrande.edu.pe.view.FrmProducto;
import java.util.List;

public class ProductoController {
    private ProductoService productoService;
    private FrmProducto vista;

    public ProductoController(FrmProducto vista) {
        this.vista = vista;
        this.productoService = new ProductoService();
    }

    public void cargarProductos() {
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        vista.mostrarProductos(productos);
    }

    public void registrarProducto(Producto producto) {
        productoService.registrarProducto(producto);
        cargarProductos(); // Actualizar la tabla
    }

    public void modificarProducto(Producto producto) {
        productoService.modificarProducto(producto);
        cargarProductos(); // Actualizar la tabla
    }

    public void eliminarProducto(int id) {
        productoService.eliminarProducto(id);
        cargarProductos(); // Actualizar la tabla
    }

    public Producto buscarProductoPorId(int id) {
        return productoService.buscarProductoPorId(id);
    }

    // NUEVO MÉTODO: Buscar productos por criterio
    public void buscarProductos(String criterio) {
        List<Producto> productos = productoService.buscarProductos(criterio);
        vista.mostrarProductos(productos);
    }

public void actualizarFila(Producto p) {
    // Avoid accessing vista.modeloTabla (not visible); reload the full table instead
    cargarProductos();
}


public void agregarFila(Producto p) {
    // Avoid accessing vista.modeloTabla (not visible); reload the full table instead
    cargarProductos();
}

}
