// src/main/java/vallegrande/edu/pe/model/CategoriaDAO.java
package vallegrande.edu.pe.model;

import vallegrande.edu.pe.database.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public List<Categoria> listar() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion, tipo_fertilizante FROM categorias";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("id"));
                categoria.setNombre(rs.getString("nombre"));
                categoria.setDescripcion(rs.getString("descripcion"));
                categoria.setTipoFertilizante(rs.getString("tipo_fertilizante"));
                categorias.add(categoria);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }

    public void insertar(Categoria categoria) {
        String sql = "INSERT INTO categorias (nombre, descripcion, tipo_fertilizante) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, categoria.getNombre());
            pstmt.setString(2, categoria.getDescripcion());
            pstmt.setString(3, categoria.getTipoFertilizante());
            pstmt.executeUpdate();

            System.out.println("✅ Categoría insertada: " + categoria.getNombre());

        } catch (SQLException e) {
            System.out.println("❌ Error al insertar categoría: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void actualizar(Categoria categoria) {
        String sql = "UPDATE categorias SET nombre = ?, descripcion = ?, tipo_fertilizante = ? WHERE id = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, categoria.getNombre());
            pstmt.setString(2, categoria.getDescripcion());
            pstmt.setString(3, categoria.getTipoFertilizante());
            pstmt.setInt(4, categoria.getId());
            pstmt.executeUpdate();

            System.out.println("✅ Categoría actualizada: " + categoria.getNombre());

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar categoría: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM categorias WHERE id = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            System.out.println("✅ Categoría eliminada ID: " + id);

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar categoría: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Categoria buscarPorId(int id) {
        String sql = "SELECT id, nombre, descripcion, tipo_fertilizante FROM categorias WHERE id = ?";
        Categoria categoria = null;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                categoria = new Categoria();
                categoria.setId(rs.getInt("id"));
                categoria.setNombre(rs.getString("nombre"));
                categoria.setDescripcion(rs.getString("descripcion"));
                categoria.setTipoFertilizante(rs.getString("tipo_fertilizante"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoria;
    }

    public List<Categoria> buscarPorCriterio(String criterio) {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion, tipo_fertilizante FROM categorias " +
                "WHERE nombre LIKE ? OR descripcion LIKE ? OR tipo_fertilizante LIKE ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String likePattern = "%" + criterio + "%";
            pstmt.setString(1, likePattern);
            pstmt.setString(2, likePattern);
            pstmt.setString(3, likePattern);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("id"));
                categoria.setNombre(rs.getString("nombre"));
                categoria.setDescripcion(rs.getString("descripcion"));
                categoria.setTipoFertilizante(rs.getString("tipo_fertilizante"));
                categorias.add(categoria);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }
}
