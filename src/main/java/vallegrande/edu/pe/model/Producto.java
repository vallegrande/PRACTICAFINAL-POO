// src/main/java/vallegrande/edu/pe/model/Producto.java
package vallegrande.edu.pe.model;

public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int stock;
    private int categoriaId;
    private String nombreCategoria;

    public Producto() {}

    public Producto(int id, String nombre, double precio, int stock, int categoriaId) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoriaId = categoriaId;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public int getCategoriaId() { return categoriaId; }
    public void setCategoriaId(int categoriaId) { this.categoriaId = categoriaId; }

    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }

    // Método para verificar disponibilidad
    public boolean estaDisponible() {
        return stock > 0;
    }

    // Método para obtener estado como texto
    public String getEstado() {
        return estaDisponible() ? "DISPONIBLE" : "AGOTADO";
    }

    @Override
    public String toString() {
        return String.format("Producto{id=%d, nombre='%s', precio=S/.%.2f, stock=%d, estado=%s}",
                id, nombre, precio, stock, getEstado());
    }
}