import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientGUI implements StringConsumer, StringProducer {
    private ConnectionProxy proxy;
    private Socket socket;

    private JFrame frame = null;
    private JButton button = null;
    private JPanel panel1, panel2, panel3 = null;
    private JTextField textField = null;
    private JLabel label = null;
    private JTextArea textArea = null;
    private JScrollPane scroll = null;
    private ImageIcon img = null;
    private JLabel iconLabel = null;
    private String nickname = null;

    public ClientGUI() throws IOException {
        try {
            this.socket = new Socket("127.0.0.1", 1300);
            this.proxy = new ConnectionProxy(socket);
        } catch (IOException ex) {
//            System.out.println();
        }
    }

    @Override
    public void consume(String str) throws IOException {
        textArea.append(str);
    }

    public void newUser() {
        this.frame = new JFrame("Awesome Chat");
        this.label = new JLabel("Enter nickname:");
        this.panel1 = new JPanel();
        this.panel1.setLayout(new FlowLayout());
        this.textField = new JTextField(20);
        this.button = new JButton("Send");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nickname = textField.getText();
                frame.dispose();
                try {
                    startChat(nickname);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        this.frame.setSize(600, 150);
        panel1.add(label);
        panel1.add(textField);
        panel1.add(button);
        frame.add(panel1);
        frame.addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed.
             * The close operation can be overridden at this point.
             *
             * @param e
             */
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                shutDown(false);
            }
        });

        frame.setVisible(true);
    }

    public void shutDown(boolean loged) {
        try {
            if (loged)
                proxy.consume("disconnect");

            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void startChat(String nickname) throws IOException {
        frame = null;
        panel1 = null;
        button = null;
        textField = null;

        iconLabel = new JLabel(img);
        iconLabel.setSize(10, 10);

        frame = new JFrame("awesome chat box");
        frame.setSize(700, 700);

        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel1.setLayout(new FlowLayout());
        panel2.setLayout(new FlowLayout());
        panel3.setLayout(new FlowLayout());

        button = new JButton("send");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String message = textField.getText();
                    message.concat("\n");
                    proxy.consume(message);
                    textField.setText("");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        textField = new JTextField(50);
        textArea = new JTextArea(35, 54);
        textArea.setLineWrap(true);
        textArea.setEditable(false);

        scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panel1.add(textField);
        panel1.add(button);
        panel2.add(scroll);
        panel3.add(iconLabel);

        frame.add(panel1, BorderLayout.SOUTH);
        frame.add(panel3, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.CENTER);

        frame.addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed.
             * The close operation can be overridden at this point.
             *
             * @param e
             */
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                shutDown(true);
            }
        });

        frame.setVisible(true);
        proxy.addConsumer(this);
        proxy.start();
        proxy.consume(nickname);
    }

    @Override
    public void addConsumer(StringConsumer sc) {
        this.proxy = (ConnectionProxy) sc;
    }

    @Override
    public void removeConsumer(StringConsumer sc) {
        this.proxy = null;
    }
}
