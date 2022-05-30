package DSClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Servers<Server> implements Iterable<Server> {
  private List<Server> servers;

  public Servers() { this.servers = new ArrayList<Server>(); }

  public Servers(int numServers) {
    this.servers = new ArrayList<Server>(numServers);
  }

  public void addServer(Server server) { this.servers.add(server); }

  public void removeServer(Server server) { this.servers.remove(server); }

  public Server getServer(int index) { return this.servers.get(index); }

  public int size() { return this.servers.size(); }

  public Iterator<Server> iterator() { return this.servers.iterator(); }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Server server : this.servers) {
      sb.append(server.toString());
      sb.append("\n");
    }
    return sb.toString();
  }
}
