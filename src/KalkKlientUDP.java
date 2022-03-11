import java.awt.desktop.SystemSleepEvent;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class KalkKlientUDP {
    DatagramSocket datagramSocket;
    InetAddress ip;
    byte[] buf;



     public void createSocket() throws UnknownHostException, SocketException {
         // Create the socket object for carrying data
         datagramSocket = new DatagramSocket(3000);

         //ip = InetAddress.getLocalHost();

         ip = InetAddress.getByName("FOSNB7208");

     }

     public void communicateWithServer() throws IOException {
         Scanner sc = new Scanner(System.in);

         while (true){
             System.out.print("\nEnter the equation in the format:");
             System.out.println("'x operator y'");

             // Awaiting from entered input
             String inp = sc.nextLine();
             buf = new byte[65535];

             // Converting the String input into the byte array
             buf = inp.getBytes();

             sendData();

             if (inp.equals("exit"))
                 break;

             buf = new byte[buf.length];

             System.out.println("receiving");

             receiveData();

             System.out.println("Received now!");

             // Print and display command
             System.out.println("Answer = " + new String(buf, 0, buf.length));
         }
     }

    public void sendData() throws IOException {
        // Creating the datagramPacket for sending the data.

        //DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, 1234);
        DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, 3000);

        //DatagramPacket DpSend = new DatagramPacket(buf, buf.length);
        //System.out.println(InetAddress.getByName(address.getHostName()));
        // Invoking the send call to actually send the data.
        datagramSocket.send(DpSend);
    }

    public void receiveData() throws IOException {
        // Creating an object of DatagramPacket class
        DatagramPacket DpReceive = new DatagramPacket(buf, buf.length);

        datagramSocket.receive(DpReceive);
    }

    public static void main(String[] args) throws IOException {
        KalkKlientUDP kalkKlientUDP = new KalkKlientUDP();
        kalkKlientUDP.createSocket();
        kalkKlientUDP.communicateWithServer();

        //System.out.println(InetAddress.getLocalHost());
    }
}
