package DSClient;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DSInterface {
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

  public DSInterface(String host, int port) {
    this.host = host;
    this.port = port;
    this.connected = false;
  }

  private String[] split(String s) { return s.split(" "); }

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

  public void send(String msg) throws IOException { out.println(msg); }

  public String receive() throws IOException {
    try {
      return in.readLine();
    } catch (IOException e) {
      return null;
    }
  }

  /**
   * @dev: DS-Sim handhake protocol implementation
   * @param output - The output stream
   */
  public void authenticateUser() throws IOException {
    try {
      String username = System.getProperties().getProperty("user.name");
      send(HELLO);
      receive();
      send(AUTHENTTICATE + " " + username + "\n");
      String response = receive();
      if (response.equals(OK)) {
        System.out.println("Authentication successful");
      } else {
        System.out.println("Authentication failed");
      }
      /* System.out.println(receive());
      System.out.println(receive()); */
    } catch (IOException e) {
      System.err.println(e);
    }
  }

  /**
   * @param output - JOBN
   */
  public Job getJob() throws IOException {
    try {
      send(READY);
      String res = receive();
      System.out.println("res: " + res);
      if (split(res)[0].equals(NONE)) {
        System.out.println("quitting");
        send(QUIT);
        return null;
      }
      if (!split(res)[0].equals(JOBN)) {
        System.out.println("Error not JOBN: " + split(res)[0]);
        return null;
      }
      return new Job(res);
    } catch (IOException e) {
      System.err.println(e);
      return null;
    }
  }

  public void scheduleJob(Job job, Server server) throws IOException {
    try {
      System.out.println(SCHEDULE + " " + job.getId() + " " + server.getType() +
                         " " + server.getId());
      send(SCHEDULE + " " + job.getId() + " " + server.getType() + " " +
           server.getId());
      String res = receive();
      if (res.equals(OK)) {
        System.out.println("Job scheduled");
      } else {
        System.out.println("Job not scheduled");
      }
    } catch (IOException e) {
      System.err.println(e);
    }
  }

  public Data getCapable(String spec) throws IOException {
    try {
      send(GETCAPABLE + " " + spec);
      String res = receive();
      return new Data(Integer.parseInt(split(res)[1]),
                      Integer.parseInt(split(res)[2]));
    } catch (IOException e) {
      System.err.println(e);
      return new Data();
    }
  }

  public Data getAll() throws IOException {
    try {
      send(GETALL);
      String res = receive();
      return new Data(Integer.parseInt(split(res)[1]),
                      Integer.parseInt(split(res)[2]));
    } catch (IOException e) {
      System.err.println(e);
      return new Data();
    }
  }

  public Servers<Server> getServers(int numServers) throws Exception {
    send(OK);
    try {
      Servers<Server> servers = new Servers<Server>(numServers);
      for (int i = 0; i < numServers; i++) {
        servers.addServer(new Server(receive()));
      }
      return servers;
    } catch (Exception e) {
      System.err.println(e);
      return new Servers<Server>();
    }
  }

  public int getHighestCore(Servers<Server> servers) {
    int highestCore = 0;
    for (Server server : servers) {
      if (server.getCore() > highestCore) {
        highestCore = server.getCore();
      }
    }
    return highestCore;
  }

  public String getFirstLargestServer(Servers<Server> servers) {
    int highestCore = 0;
    String largestServer = "";
    for (Server server : servers) {
      if (server.getCore() == getHighestCore(servers)) {
        highestCore = server.getCore();
        largestServer = server.getType();
        break;
      }
    }
    return largestServer;
  }

  public Servers<Server> getLargestServers(Servers<Server> servers) {
    Servers<Server> largestServers = new Servers<Server>();
    int highestCore = getHighestCore(servers);
    String serverType;
    for (Server server : servers) {
      if (server.getCore() == highestCore) {
        largestServers.addServer(server);
      }
    }
    return largestServers;
  }

  public Servers<Server> getLargestServer(Servers<Server> servers) {

    Servers<Server> largestServers = new Servers<Server>();
    int highestCore = getHighestCore(servers);
    String severType = getFirstLargestServer(servers);
    for (Server server : servers) {
      if (server.getCore() == highestCore &&
          server.getType().equals(getFirstLargestServer(servers))) {
        largestServers.addServer(server);
      }
    }
    return largestServers;
  }

  public Servers<Server> getSmallestServers(Servers<Server> servers) {
    Servers<Server> smallestServers = new Servers<Server>();
    int lowestCore = getHighestCore(servers);
    for (Server server : servers) {
      if (server.getCore() == lowestCore) {
        smallestServers.addServer(server);
      }
    }
    return smallestServers;
  }

  // schedule a job to the server
  public void schedule(Job job, Server server) throws IOException {
    send(SCHEDULE + " " + job.getId() + " " + server.getType() + " " +
         server.getId());
  }
}
