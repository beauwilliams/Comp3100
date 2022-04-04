package DSClient;

public class Job {
  private Integer id, submitTime, endTime, estRuntime, core, memory, disk;
  private String name, status, serverType, serverId;

  private String[] split(String s) { return s.split(" "); }

  public Job(String name, Integer submitTime, Integer id, Integer estRuntime,
             Integer core, Integer memory, Integer disk) {
    this.name = name;
    this.submitTime = submitTime;
    this.estRuntime = estRuntime;
    this.core = core;
    this.memory = memory;
    this.disk = disk;
    this.status = "waiting";
    this.name = "unnamed";
  }

  public Job(String[] jobInfo) {
    this.name = jobInfo[0];
    this.submitTime = Integer.parseInt(jobInfo[1]);
    this.id = Integer.parseInt(jobInfo[2]);
    this.estRuntime = Integer.parseInt(jobInfo[3]);
    this.core = Integer.parseInt(jobInfo[4]);
    this.memory = Integer.parseInt(jobInfo[5]);
    this.disk = Integer.parseInt(jobInfo[6]);
    this.status = "waiting";
  }

  public Job(String job) {
    String[] jobInfo = split(job);
    this.name = jobInfo[0];
    if (this.name.equals("JOBN")) {
      this.submitTime = Integer.parseInt(jobInfo[1]);
      this.id = Integer.parseInt(jobInfo[2]);
      this.estRuntime = Integer.parseInt(jobInfo[3]);
      this.core = Integer.parseInt(jobInfo[4]);
      this.memory = Integer.parseInt(jobInfo[5]);
      this.disk = Integer.parseInt(jobInfo[6]);
      this.status = "waiting";
    }

    if (this.name.equals("JCPL")) {
      this.endTime = Integer.parseInt(jobInfo[1]);
      this.id = Integer.parseInt(jobInfo[2]);
      this.serverType = jobInfo[3];
      this.serverId = jobInfo[4];
      this.status = "completed";
    }
  }

  public void setStatus(String status) { this.status = status; }

  public void setName(String name) { this.name = name; }

  public Integer getId() { return id; }

  public Integer getSubmitTime() { return submitTime; }

  public Integer getEstimatedRuntime() { return estRuntime; }

  public Integer getCore() { return core; }

  public Integer getMemory() { return memory; }

  public Integer getDisk() { return disk; }

  public String getStatus() { return status; }

  public String getName() { return name; }

  public String getSpec() { return core + " " + memory + " " + disk; }

  public String toString() {
    return "Job ID: " + id + "\n"
        + "Job Name: " + name + "\n"
        + "Submit Time: " + submitTime + "\n"
        + "Estimated Runtime: " + estRuntime + "\n"
        + "Core: " + core + "\n"
        + "Memory: " + memory + "\n"
        + "Disk: " + disk + "\n"
        + "Status: " + status + "\n"
        + "Job Name: " + name + "\n";
  }
}
