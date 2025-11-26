package vallegrande.edu.pe.model;

import vallegrande.edu.pe.database.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // LISTAR todos los productos
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT id, nombre, precio, stock FROM productos";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));
                productos.add(producto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    // INSERTAR nuevo producto
    public void insertar(Producto producto) {
        String sql = "INSERT INTO productos (nombre, precio, stock) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, producto.getNombre());
            pstmt.setDouble(2, producto.getPrecio());
            pstmt.setInt(3, producto.getStock());
            pstmt.executeUpdate();

            System.out.println("✅ Producto insertado: " + producto.getNombre());

        } catch (SQLException e) {
            System.out.println("❌ Error al insertar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ACTUALIZAR producto
    public void actualizar(Producto producto) {
        String sql = "UPDATE productos SET nombre = ?, precio = ?, stock = ? WHERE id = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, producto.getNombre());
            pstmt.setDouble(2, producto.getPrecio());
            pstmt.setInt(3, producto.getStock());
            pstmt.setInt(4, producto.getId());
            pstmt.executeUpdate();

            System.out.println("✅ Producto actualizado: " + producto.getNombre());

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ELIMINAR producto
    public void eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            System.out.println("✅ Producto eliminado ID: " + id);

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // BUSCAR por ID
    public Producto buscarPorId(int id) {
        String sql = "SELECT id, nombre, precio, stock FROM productos WHERE id = ?";
        Producto producto = null;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;
    }

    // NUEVO MÉTODO: Buscar por criterio (nombre o ID)
    public List<Producto> buscarPorCriterio(String criterio) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT id, nombre, precio, stock FROM productos " +
                "WHERE nombre LIKE ? OR id = ? OR CAST(precio AS CHAR) LIKE ? OR CAST(stock AS CHAR) LIKE ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String likePattern = "%" + criterio + "%";
            pstmt.setString(1, likePattern);

            // Intentar convertir a ID si es numérico
            try {
                int idCriterio = Integer.parseInt(criterio);
                pstmt.setInt(2, idCriterio);
            } catch (NumberFormatException e) {
                pstmt.setInt(2, -1); // ID inválido si no es número
            }

            pstmt.setString(3, likePattern);
            pstmt.setString(4, likePattern);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));
                productos.add(producto);
            }

            System.out.println("🔍 Búsqueda realizada: " + criterio + " - Encontrados: " + productos.size());

        } catch (SQLException e) {
            System.out.println("❌ Error en búsqueda: " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }
}