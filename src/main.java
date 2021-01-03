import javax.swing.*;
import java.io.IOException;

public class main {
    public static void main(String[] args) {
        Runnable runnable = () -> {
            ClientGUI gui;
            try {
                gui = new ClientGUI();
                gui.newUser();

            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        SwingUtilities.invokeLater(runnable);
    }
}
