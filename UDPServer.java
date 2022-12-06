import java.net.*;
import java.io.*;
import java.util.Scanner;
public class UDPServer{
    public static void main(String args[]){ 
        DatagramSocket aSocket = null;
        Scanner in = new Scanner(System.in);
        try{
            // Get port number
            System.out.println("Enter the server port for the socket:");
            int serverPort = in.nextInt();
            // Create socket
            aSocket = new DatagramSocket(serverPort);
            // buffer to receive message
            byte[] buffer = new byte[65535];
            while(true){
                // receive message from socket
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                // convert to string, turn to upper, revert back to byte array
                byte[] msg = new String(request.getData(), request.getOffset(), request.getLength()).toUpperCase().getBytes();
                // create reply packet
                DatagramPacket reply = new DatagramPacket(msg, msg.length, request.getAddress(), request.getPort());
                aSocket.send(reply);
                // quit command
                if(new String(request.getData(), request.getOffset(), request.getLength()).equals("bye")){
                    System.out.println("Client sent bye.....EXITING");
                    break;
                }
                // renew buffer
                buffer = new byte[65535];
            }
        }catch (SocketException e){
            System.out.println("Socket: " + e.getMessage());
        }catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }finally {
            // close socket
            if(aSocket != null) aSocket.close();
        }
    }
}