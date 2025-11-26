// src/main/java/vallegrande/edu/pe/model/UsuarioLogin.java
// src/main/java/vallegrande/edu/pe/model/UsuarioLogin.java
package vallegrande.edu.pe.model;

public class UsuarioLogin {
    private String email;
    private String password;

    public UsuarioLogin() {}

    public UsuarioLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters y Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}