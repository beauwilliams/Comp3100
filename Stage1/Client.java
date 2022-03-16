import java.net.*;
import java.io.*;

class DssimClient {
    public static void main(String args[]) throws Exception {
        Socket socket = new Socket("127.0.0.1", 50000);
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

        //Send HELO
        outputStream.write(("HELO\n").getBytes());
        outputStream.flush();
        //Response
        System.out.println("Ds-sim Response: " + inputStream.readLine());


        //EX 2.2
        //Send AUTH
        String username = System.getProperty("user.name");
		outputStream.write(("AUTH " + username + "\n").getBytes());
        // outputStream.write(("AUTH beau\n").getBytes());
        outputStream.flush();
		System.out.println("Authenticated: " + username);
        //Response
        System.out.println("Ds-sim Response: " + inputStream.readLine());



        //Send REDY
        outputStream.write(("REDY\n").getBytes());
        outputStream.flush();
        //Response
        System.out.println("Ds-sim Response: " + inputStream.readLine());




        //Send QUIT
        outputStream.write(("QUIT\n").getBytes());
        outputStream.flush();
        //Response
        System.out.println("Ds-sim Response: " + inputStream.readLine());


        //Close the streams and sockets
        outputStream.close();
        inputStream.close();
        socket.close();
    }
}
