package DSClient;

public class Server {
  private String type, state;
  private Integer id, hourlyRate, core, memory, disk;

  public Server(String type, Integer id, String state, Integer hourlyRate, Integer core,
                Integer memory, Integer disk) {
    this.type = type;
    this.id = id;
    this.state = state;
    this.hourlyRate = hourlyRate;
    this.core = core;
    this.memory = memory;
    this.disk = disk;
  }

  public Server(String server){
    String[] serverInfo = server.split(" ");
    this.type = serverInfo[0];
    this.id = Integer.parseInt(serverInfo[1]);
    this.state = serverInfo[2];
    this.hourlyRate = Integer.parseInt(serverInfo[3]);
    this.core = Integer.parseInt(serverInfo[4]);
    this.memory = Integer.parseInt(serverInfo[5]);
    this.disk = Integer.parseInt(serverInfo[6]);
  }

  public String getType() { return type; }

  public Integer getID() { return id; }

  public String getState() { return state; }

  public Integer getHourlyRate() { return hourlyRate; }

  public Integer getCore() { return core; }

  public Integer getMemory() { return memory; }

  public Integer getDisk() { return disk; }

  public String toString() {
    return "Server Type: " + type + "\n"
        + "Server ID: " + id + "\n"
        + "Server State: " + state + "\n"
        + "Server Hourly Rate: " + hourlyRate + "\n"
        + "Core: " + core + "\n"
        + "Memory: " + memory + "\n"
        + "Disk: " + disk + "\n";
  }
}
