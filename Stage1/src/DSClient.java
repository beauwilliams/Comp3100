import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DSClient {

  public static class DSInterface {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
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

    private String[] job;
    private String[] data;

    private String[] split(String s) {
      return s.split(" ");
    }

    public DSInterface(String host, int port) {
      this.host = host;
      this.port = port;
      this.connected = false;
    }

    public void connect() throws IOException {
      socket = new Socket(host, port);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(socket.getOutputStream(), true);
      // out = new DataOutputStream(socket.getOutputStream());
      connected = true;
    }

    public void disconnect() throws IOException {
      if (connected) {
        socket.close();
        connected = false;
      }
    }

    public void send(String msg) throws IOException {
      out.println(msg);
    }

    public String receive() throws IOException { return in.readLine(); }

    /**
     * @dev: DS-Sim handhake protocol implementation
     * @param output - The output stream
     */
    public void authenticateUser() throws IOException {
      try {
        String username = System.getProperties().getProperty("user.name");
        send(HELLO);
        send(AUTHENTTICATE + " " + username + "\n");
        System.out.println(receive());
        System.out.println(receive());
      } catch (IOException e) {
        System.err.println(e);
      }
    }

    /**
     * @param output - JOBN
     */
    public void getJob() throws IOException {
      try {
        send(READY);
        //TODO: Split jobs into object
        job = split(receive());
      } catch (IOException e) {
        System.err.println(e);
      }
    }

    public void getCapableServers() throws IOException {
      try {
        send(GETALL);
        System.out.println(receive());
      } catch (IOException e) {
        System.err.println(e);
      }
    }
  }
        // Gets DATA

  public static void main(String args[]) throws Exception {
    try {
      final String host = "127.0.0.1";
      final int port = 50000;
      DSInterface dsserver = new DSInterface(host, port);
      dsserver.connect();
      dsserver.authenticateUser();
      dsserver.getJob();
      dsserver.getCapableServers();
      //TODO: Implement DS-Sim client
    } catch (Exception e) {
      System.out.println("Server not running or not reachable");
      System.exit(0);
    }
  }
}
