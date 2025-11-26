// src/main/java/vallegrande/edu/pe/AppLauncher.java
package vallegrande.edu.pe;

import vallegrande.edu.pe.view.FrmLogin;
import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Iniciar con la pantalla de Login
            FrmLogin login = new FrmLogin();
            login.setVisible(true);
        });
    }
}