import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DSClient {


  public static class DSInterface {
    private Socket socket;
    private BufferedReader in;
    private DataOutputStream out;
    private String host;
    private int port;
    private boolean connected;


    public final String OK = "OK";
    public final String HELLO = "HELO";
    public final String READY = "REDY";
    public final String AUTHENTTICATE = "AUTH";
    public final String NONE = "NONE";
    public final String QUIT = "QUIT";
    public final String GETALL = "GETS All";
    public final String GETCAPABLE = "GETS Capable";
    public final String SCHEDULE = "SCHD";
    public final String JOBN = "JOBN";
    public final String JOBP = "JOBP";
    public final String JCPL = "JCPL";
    public final String DOT = ".";
    public final String ERROR = "ERR";
    public final String DATA = "DATA";


    public DSInterface(String host, int port) {
      this.host = host;
      this.port = port;
      this.connected = false;
    }

    public void connect() throws IOException {
      socket = new Socket(host, port);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      // out = new PrintWriter(socket.getOutputStream(), true);
      out = new DataOutputStream(socket.getOutputStream());
      connected = true;
    }

    public void disconnect() throws IOException {
      if (connected) {
        socket.close();
        connected = false;
      }
    }

    public void send(String msg) throws IOException {
      out.write((msg+"\n").getBytes());
      out.flush();
    }

    public String receive() throws IOException {
      return in.readLine();
    }

     /**
     * @dev: DS-Sim handhake protocol implementation
     * @param input - The input stream
     * @param output - The output stream
     */
    public void authenticateUser() throws IOException {
      String username = System.getProperties().getProperty("user.name");
      send(HELLO);
      send(AUTHENTTICATE + " " + username+"\n");
      System.out.println(receive());
      System.out.println(receive());


    }

    public void getAllJobs() throws IOException {
      //Gets JOBN
      send(READY);
      System.out.println(receive());
      //Gets DATA
      send(GETALL);
      System.out.println(receive());
    }
  }


  public static void main(String args[]) throws Exception {
    try {
      /* Socket socket = new Socket("127.0.0.1", 50000);
      BufferedReader inputStream =
      new BufferedReader(new InputStreamReader(socket.getInputStream()));
      DataOutputStream outputStream =
          new DataOutputStream(socket.getOutputStream()); */

      //Write a function to send a handshake message to the server

      //Write a function to receive a handshake message from the server




      final String host = "127.0.0.1";
      final int port = 50000;
      DSInterface dsserver = new DSInterface(host,port);
      dsserver.connect();
      dsserver.authenticateUser();
      dsserver.getAllJobs();


    } catch (Exception e) {
      System.out.println("Server not running");
      System.exit(0);
    }
  }
}
