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
  public final String GET_ALL = "GETS All";
  public final String GET_CAPABLE = "GETS Capable";
  public final String GET_AVAILABLE = "GETS Avail";
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
      System.out.println("");
      return null;
    }
  }

  public void sendOK() throws IOException { send(OK); }
  public void sendHello() throws IOException { send(HELLO); }
  public void sendReady() throws IOException { send(READY); }


  /**
   * @dev: DS-Sim handhake protocol implementation
   * @param output - The output stream
   */
  public String authenticateUser() throws IOException {
    try {
      String username = System.getProperties().getProperty("user.name");
      send(HELLO);
      receive();
      send(AUTHENTTICATE + " " + username);
      String response = receive();
      if (response.equals(OK)) {
        System.out.println("Authentication of " + username + " successful");
        return response;
      } else {
        System.out.println("Authentication of " + username + " failed");
        return response;
      }
    } catch (IOException e) {
      System.err.println(e);
      return null;
    }
  }

  /**
   * @param output - JOBN
   */
  public Job getJob() throws IOException {
    try {
      send(READY);
      String res = receive();
      return new Job(res);
      /* if (split(res)[0].equals(NONE)) {
        System.out.println("quitting");
        send(QUIT);
        return null;
      }
      if (split(res)[0].equals(JCPL)) {
        System.out.println("Error not JOBN: " + split(res)[0]);
        return new Job(res);
        // return null;
      } */
    } catch (IOException e) {
      System.err.println(e);
      return null;
    }
  }

  public String scheduleJob(Job job, Server server) throws IOException {
    try {
      send(SCHEDULE + " " + job.getId() + " " + server.getType() + " " +
           server.getId());
      String res = receive();
      if (res.equals(OK)) {
        System.out.println("Job " + job.getId() + " scheduled");
      } else {
        System.out.println("Job " + job.getId() + " not scheduled");
      }
      return res;
    } catch (IOException e) {
      System.err.println(e);
      return null;
    }
  }

  public Data getCapable(String spec) throws IOException {
    try {
      send(GET_CAPABLE + " " + spec);
      String res = receive();
      return new Data(Integer.parseInt(split(res)[1]),
                      Integer.parseInt(split(res)[2]));
    } catch (IOException e) {
      System.err.println(e);
      return new Data();
    }
  }

  public Data getAvailable(String spec) throws IOException {
    try {
      send(GET_AVAILABLE + " " + spec);
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
      send(GET_ALL);
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
    Servers<Server> servers = new Servers<Server>();
    for (int i = 0; i < numServers; i++) {
      String res = receive();
      servers.addServer(new Server(res));
    }
    return servers;
  }

  public int getHighestCore(Servers<Server> servers) {
    int highestCore = 0;
    for (Server server : servers) {
      int core = server.getCore();
      if (server.getCore() > highestCore) {
        highestCore = core;
      }
    }
    return highestCore;
  }

  public int getLowestCore(Servers<Server> servers) {
    int lowestCore = Integer.MAX_VALUE;
    for (Server server : servers) {
      int core = server.getCore();
      if (server.getCore() < lowestCore) {
        lowestCore = core;
      }
    }
    return lowestCore;
  }

  public String getFirstLargestServerType(Servers<Server> servers) {
    String largestServerType = "";
    // System.out.println("hights core is "+getHighestCore(servers));
    for (Server server : servers) {
      if (server.getCore() == getHighestCore(servers)) {
        largestServerType = server.getType();
        break;
      }
    }
    return largestServerType;
  }

  public String getFirstSmallestServerType(Servers<Server> servers) {
    String smallestServerType = "";
    // System.out.println("lowest core is "+getLowestCore(servers));
    for (Server server : servers) {
      if (server.getCore() == getLowestCore(servers)) {
        smallestServerType = server.getType();
        break;
      }
    }
    return smallestServerType;
  }

  public Servers<Server> getServersByType(Servers<Server> servers,
                                                 String serverType) {
    Servers<Server> largestServers = new Servers<Server>();
    for (Server server : servers) {
      if (server.getType().equals(serverType)) {
        largestServers.addServer(server);
      }
    }
    return largestServers;
  }

  public Servers<Server> getLargestServer(Servers<Server> servers) {
    Servers<Server> largestServers = new Servers<Server>();
    // System.out.println("core count is"+getHighestCore(servers));
    int i = 0;
    for (Server server : servers) {
      i++;
      if (server.getCore() > 64) {
        largestServers.addServer(server);
      }
      System.out.println(i);
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
    receive();
  }
}
