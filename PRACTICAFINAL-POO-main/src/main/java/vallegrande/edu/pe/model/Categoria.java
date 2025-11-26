// src/main/java/vallegrande/edu/pe/model/Categoria.java
package vallegrande.edu.pe.model;

public class Categoria {
    private int id;
    private String nombre;
    private String descripcion;
    private String tipoFertilizante;

    public Categoria() {}

    public Categoria(int id, String nombre, String descripcion, String tipoFertilizante) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoFertilizante = tipoFertilizante;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTipoFertilizante() { return tipoFertilizante; }
    public void setTipoFertilizante(String tipoFertilizante) { this.tipoFertilizante = tipoFertilizante; }

    @Override
    public String toString() {
        return String.format("Categoria{id=%d, nombre=%s, tipo=%s}", id, nombre, tipoFertilizante);
    }
}