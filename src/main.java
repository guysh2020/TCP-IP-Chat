import javax.swing.*;
import java.io.IOException;

public class main {
    public static void main(String args[]) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ClientGUI gui = null;
                try {
                    gui = new ClientGUI();
                    gui.newUser();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        SwingUtilities.invokeLater(runnable);
    }
}
