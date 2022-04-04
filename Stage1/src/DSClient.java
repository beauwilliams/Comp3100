package DSClient;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DSClient {

  // Gets DATA

  public static void main(String args[]) throws Exception {
    try {
      final String host = "127.0.0.1";
      final int port = 50000;
      String response = "";
      String serverType = "";
      DSInterface dsserver = new DSInterface(host, port);
      dsserver.connect();
      response = dsserver.authenticateUser();

      System.out.println("Scheduling via LRR");
      Boolean gotLargestServer = false;
      int i = 0;
      while (true) {
        Job job = dsserver.getJob();
        if (job.getName().equals("NONE")) {
          break;
        }

        if (job.getName().equals("JOBN")) {
          // Data data = dsserver.getCapable(job.getSpec());
          Data data = dsserver.getAll();
          Servers<Server> servers = dsserver.getServers(data.getNumServers());
          if (gotLargestServer == false) {
            serverType = dsserver.getFirstLargestServerType(servers);
            gotLargestServer = true;
          }
          Servers<Server> largestServers = dsserver.getLargestServersByType(servers, serverType);

          dsserver.send(dsserver.OK);
          response = dsserver.receive();
          if (i >= largestServers.size()) {
            i = 0;
          }
          response = dsserver.scheduleJob(job, largestServers.getServer(i));
          i++;
        }
      }
      dsserver.send(dsserver.QUIT);
      response = dsserver.receive();
      dsserver.disconnect();
      System.out.println("Scheduling successful with response: "+response);
    } catch (Exception e) {
      System.out.println("Server not reachable or client error");
      System.exit(0);
    }
  }
}
