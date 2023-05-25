import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.*;

public class ChatSender extends JFrame {

    String ip = "234.235.236.237";

    InetAddress ipaddr = InetAddress.getByName(ip);
    int port = 55554;

    InetSocketAddress group = new InetSocketAddress(ipaddr,port);
    NetworkInterface net = NetworkInterface.getByName("en0");

    String name;

    JPanel panel = new JPanel();
    JButton button = new JButton("Disconnect");

    JTextArea textArea = new JTextArea(20,20);
    JTextField textField = new JTextField(20);

    ChatSender() throws IOException {

        MulticastSocket socket = new MulticastSocket(port);
        socket.joinGroup(group,net);
        name = JOptionPane.showInputDialog(this,"Write your name:");
        add(panel);
        panel.setLayout(new BorderLayout());
        panel.add(button,BorderLayout.NORTH);
        panel.add(textArea,BorderLayout.CENTER);
        panel.add(textField,BorderLayout.SOUTH);
        setTitle(name + " Chat");
        setSize(500,500);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        button.addActionListener(e ->{

                socket.close();
                System.exit(0);


        });

        textField.addActionListener(e -> {
            String newText = textField.getText();

            String dataToSend;
            dataToSend = name + " says: " + newText + "\n";
            byte[] data = dataToSend.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, ipaddr, port);
            try {
                socket.send(packet);
                System.out.println(dataToSend);
            } catch (IOException ex) {
                System.out.println("sending " + data);
                throw new RuntimeException(ex);
            }

        });

        ChatReciever cr = new ChatReciever(socket, textArea);
        Thread t = new Thread(cr);
        t.start();

    }


    public static void main(String[] args) throws IOException {
        ChatSender asd = new ChatSender();




    }
}

