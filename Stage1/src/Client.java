import java.io.*;
import java.net.*;

class DssimClient {
  public static void main(String args[]) throws Exception {
    try {
      Socket socket = new Socket("127.0.0.1", 50000);
      BufferedReader inputStream =
          new BufferedReader(new InputStreamReader(socket.getInputStream()));
      DataOutputStream outputStream =
          new DataOutputStream(socket.getOutputStream());
      try {
        // Send HELO
        outputStream.write(("HELO\n").getBytes());
        outputStream.flush();
        // Response
        System.out.println("Ds-sim Response: " + inputStream.readLine());
      } catch (Exception e) {
        System.err.println(e);
      }

      try {
        // EX 2.2
        // Send AUTH
        String username = System.getProperty("user.name");
        outputStream.write(("AUTH " + username + "\n").getBytes());
        // outputStream.write(("AUTH beau\n").getBytes());
        outputStream.flush();
        System.out.println("Authenticated: " + username);
        // Response
        System.out.println("Ds-sim Response: " + inputStream.readLine());
      } catch (Exception e) {
        System.err.println(e);
      }

      // Send REDY
      try {
        outputStream.write(("REDY\n").getBytes());
        outputStream.flush();
        // Response
        System.out.println("Ds-sim Response: " + inputStream.readLine());
      } catch (Exception e) {
        System.err.println(e);
      }

      try {
        // Send QUIT
        outputStream.write(("QUIT\n").getBytes());
        outputStream.flush();
        // Response
        System.out.println("Ds-sim Response: " + inputStream.readLine());
      } catch (Exception e) {
        System.err.println(e);
      }

      // Close the streams and sockets
      outputStream.close();
      inputStream.close();
      socket.close();
    } catch (Exception e) {
      System.out.println("Server not running");
      System.exit(0);
    }
  }

  public DssimClient(String address, int port) {
    try {
    } catch (Exception e) {
      System.err.println(e);
    }
  }
}
