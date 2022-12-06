import java.net.*;
import java.io.*;
import java.util.Scanner;
public class UDPClient{
    public static void main(String args[]){ 
        DatagramSocket aSocket = null;
        Scanner in = new Scanner(System.in);
        try {
            // get ip address and port for server socket
            System.out.println("Enter the ip address for the socket:");
            InetAddress aHost = InetAddress.getByName(in.nextLine());
            System.out.println("Enter the server port for the socket:");
            int serverPort = in.nextInt();

            aSocket = new DatagramSocket(); 

            String line = "";
            byte buf[] = null;
            while(true){
                // Read message
                line = in.nextLine();

                buf = line.getBytes();
                // turn message into packet
                DatagramPacket request = new DatagramPacket(buf, buf.length, aHost, serverPort);
                aSocket.send(request);
                byte[] buffer = new byte[buf.length];
                // get reply
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(reply);
                // print reply
                System.out.println("Reply: " + new String(reply.getData()));
                // quit command
                if(line.equals("bye"))
                    break;
            }
        }catch (SocketException e){
            System.out.println("Socket: " + e.getMessage());
        }catch (IOException e){
            System.out.println("IO: " + e.getMessage());
        }finally{
            // close socket
            if(aSocket != null) aSocket.close();
        }
    } 
}