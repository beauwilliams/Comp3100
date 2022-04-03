
package DSClient;

public class Data {
  private int numServers, numJobs;

  public Data(int numServers, int numJobs) {
    this.numServers = numServers;
    this.numJobs = numJobs;
  }

  public Data() {
    this.numServers = 0;
    this.numJobs = 0;
  }

  public int getNumServers() {
    return numServers;
  }

  public int getNumJobs() {
    return numJobs;
  }

  public String toString() {
    return "numServers: " + numServers + " numJobs: " + numJobs;
  }
}
