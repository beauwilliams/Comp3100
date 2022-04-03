package DSClient;

public class Server {
  private String name, type, state;
  private Integer core, memory, disk;

  public Server(String name, String type, String state, Integer core,
                Integer memory, Integer disk) {
    this.name = name;
    this.type = type;
    this.state = state;
    this.core = core;
    this.memory = memory;
    this.disk = disk;
  }

  public Server(String server){
    String[] serverInfo = server.split(" ");
    this.name = serverInfo[0];
    this.type = serverInfo[1];
    this.state = serverInfo[2];
    this.core = Integer.parseInt(serverInfo[3]);
    this.memory = Integer.parseInt(serverInfo[4]);
    this.disk = Integer.parseInt(serverInfo[5]);
  }

  public String getName() { return name; }

  public String getType() { return type; }

  public String getState() { return state; }

  public Integer getCore() { return core; }

  public Integer getMemory() { return memory; }

  public Integer getDisk() { return disk; }

  public String toString() {
    return "Server Name: " + name + "\n"
        + "Server Type: " + type + "\n"
        + "Server State: " + state + "\n"
        + "Core: " + core + "\n"
        + "Memory: " + memory + "\n"
        + "Disk: " + disk + "\n";
  }
}
