import javax.swing.*;
import java.io.IOException;
import java.net.*;



public class ChatReciever implements Runnable {


    JTextArea textArea;
    int port = 55554;

    MulticastSocket socket;
    




    public ChatReciever(MulticastSocket socket, JTextArea textArea) throws IOException {

        this.socket = socket;
        this.textArea = textArea;
/*
        String ip = "234.235.236.237";
        InetAddress ipaddr = InetAddress.getByName(ip);
        InetSocketAddress group = new InetSocketAddress(ipaddr,port);
        NetworkInterface net = NetworkInterface.getByName("en08");


        socket.joinGroup(group, net);

 */


    }

    @Override
    public void run() {
        byte[] data = new byte[500];
        DatagramPacket packet = new DatagramPacket(data,data.length);
        while(true){
            try {

                socket.receive(packet);
                String message = new String(packet.getData(),0, packet.getLength());

                textArea.append(message + "\n");
                //socket.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }

    }

}
