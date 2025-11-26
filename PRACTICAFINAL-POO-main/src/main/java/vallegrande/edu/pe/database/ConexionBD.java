// src/main/java/vallegrande/edu/pe/database/ConexionBD.java
package vallegrande.edu.pe.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    // Configuración para AWS RDS MySQL
    private static final String URL = "jdbc:mysql://database-2.c9kaeqiwud9r.us-east-1.rds.amazonaws.com:3306/sistema_fertilizantes";
    private static final String USER = "admin";  // Usuario de RDS
    private static final String PASSWORD = "12345678";  // Contraseña de RDS

    // Parámetros importantes para RDS
    private static final String PARAMETERS = "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ Driver MySQL cargado correctamente");

            String urlCompleta = URL + PARAMETERS;
            System.out.println("🔗 Intentando conectar a: " + urlCompleta);

            Connection conn = DriverManager.getConnection(urlCompleta, USER, PASSWORD);
            System.out.println("✅ Conexión a RDS establecida exitosamente");
            return conn;

        } catch (ClassNotFoundException e) {
            System.err.println("❌ Error: Driver MySQL no encontrado");
            throw new SQLException("Driver MySQL no encontrado", e);
        } catch (SQLException e) {
            System.err.println("❌ Error de conexión a RDS: " + e.getMessage());
            System.err.println("🔧 Verifica:");
            System.err.println("   - El endpoint de RDS es correcto");
            System.err.println("   - Las credenciales son correctas");
            System.err.println("   - El grupo de seguridad permite tu IP");
            System.err.println("   - La base de datos existe en RDS");
            throw e;
        }
    }

    // Método para probar la conexión
    public static boolean probarConexion() {
        try (Connection conn = getConnection()) {
            System.out.println("✅ Prueba de conexión a RDS exitosa");
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Falló la prueba de conexión a RDS: " + e.getMessage());
            return false;
        }
    }
}