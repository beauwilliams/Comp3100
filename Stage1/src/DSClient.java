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
    public Job getJob() throws IOException {
      try {
        send(READY);
        return new Job(receive());
      } catch (IOException e) {
        System.err.println(e);
        return null;
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
      Job job = dsserver.getJob();
      System.out.println(job.toString());
      dsserver.getCapableServers();
      //TODO: Implement DS-Sim client - LRR
    } catch (Exception e) {
      System.out.println("Server not reachable or client error");
      System.exit(0);
    }
  }
}











public class Job {
    private Integer jobID, submitTime, estRuntime, core, memory, disk;
    private String jobName, status;

    private String[] split(String s) {
      return s.split(" ");
    }

    public Job(Integer submitTime, Integer jobID, Integer estRuntime, Integer core, Integer memory, Integer disk) {
        this.submitTime = submitTime;
        this.estRuntime = estRuntime;
        this.core = core;
        this.memory = memory;
        this.disk = disk;
        this.status = "waiting";
        this.jobName = "unnamed";
    }

    public Job(String[] jobInfo) {
        this.submitTime = Integer.parseInt(jobInfo[1]);
        this.jobID = Integer.parseInt(jobInfo[2]);
        this.estRuntime = Integer.parseInt(jobInfo[3]);
        this.core = Integer.parseInt(jobInfo[4]);
        this.memory = Integer.parseInt(jobInfo[5]);
        this.disk = Integer.parseInt(jobInfo[6]);
        this.status = "waiting";
        this.jobName = "unnamed";
    }

    public Job(String job) {
        String[] jobInfo = split(job);
        this.submitTime = Integer.parseInt(jobInfo[1]);
        this.jobID = Integer.parseInt(jobInfo[2]);
        this.estRuntime = Integer.parseInt(jobInfo[3]);
        this.core = Integer.parseInt(jobInfo[4]);
        this.memory = Integer.parseInt(jobInfo[5]);
        this.disk = Integer.parseInt(jobInfo[6]);
        this.status = "waiting";
        this.jobName = "unnamed";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getJobID() {
        return jobID;
    }

    public Integer getSubmitTime() {
        return submitTime;
    }

    public Integer getEstimatedRuntime() {
        return estRuntime;
    }

    public Integer getCore() {
        return core;
    }

    public Integer getMemory() {
        return memory;
    }

    public Integer getDisk() {
        return disk;
    }

    public String getStatus() {
        return status;
    }
    public String getJobName() {
        return jobName;
    }

    public String toString() {
        return "Job ID: " + jobID + "\n" +
                "Submit Time: " + submitTime + "\n" +
                "Estimated Runtime: " + estRuntime + "\n" +
                "Core: " + core + "\n" +
                "Memory: " + memory + "\n" +
                "Disk: " + disk + "\n" +
                "Status: " + status + "\n" +
                "Job Name: " + jobName + "\n";
    }
}
