import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.StringTokenizer;

public class KalkTjenerUDP {

    DatagramSocket datagramSocket;
    InetAddress ip;
    byte[] buf;
    DatagramPacket DpReceive;
    DatagramPacket DpSend;
    String inp;
    String resultAsString;
    int port = 3000;

    public void createSocket() throws SocketException {
        datagramSocket = new DatagramSocket(port);
    }

    public void calculate(){
        int result;

        // Use StringTokenizer to break the equation into operand and operation
        System.out.println("Equation received: " + inp);
        StringTokenizer st = new StringTokenizer(inp);

        int oprnd1 = Integer.parseInt(st.nextToken());
        String operation = st.nextToken();
        int oprnd2 = Integer.parseInt(st.nextToken());

        // Perform the required operation
        if (operation.equals("+"))
            result = oprnd1 + oprnd2;

        else if (operation.equals("-"))
            result = oprnd1 - oprnd2;

        else if (operation.equals("*"))
            result = oprnd1 * oprnd2;

        else
            result = oprnd1 / oprnd2;

        System.out.print("Answer: " + result);
        System.out.println(", sending the result...");
        resultAsString = Integer.toString(result);
    }

    public void sendData() throws IOException {
        // Clearing the buffer after every message
        buf = resultAsString.getBytes();

        // Getting the port of client
        //int port = DpReceive.getPort();

        ip = DpReceive.getAddress();

        DpSend = new DatagramPacket(buf, buf.length, ip, port);
        datagramSocket.send(DpSend);
    }

    public void communicateWithClient() throws IOException {
        while (true){
            buf = new byte[65535];

            // Creating a DatagramPacket to receive the data.
            DpReceive = new DatagramPacket(buf, buf.length);

            // Receiving the data in byte buffer.
            datagramSocket.receive(DpReceive);

            inp = new String(buf, 0, buf.length);

            // Using trim() method to remove extra spaces.
            inp = inp.trim();

            // Exit the server if the client sends "bye"
            if (inp.equals("exit")) {
                System.out.println("Client sent bye.....EXITING");

                // Exit from program here itself without checking further
                break;
            }

            calculate();
            sendData();
        }
    }

    public static void main(String[] args) throws IOException {
        KalkTjenerUDP kalkTjenerUDP = new KalkTjenerUDP();
        kalkTjenerUDP.createSocket();
        kalkTjenerUDP.communicateWithClient();
    }
}
