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
      String serverType = "4xlarge";
      DSInterface dsserver = new DSInterface(host, port);
      dsserver.connect();
      response = dsserver.authenticateUser();

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
          Servers<Server> largestServers =
              dsserver.getLargestServersByType(servers, serverType);

          dsserver.send(dsserver.OK);
          response = dsserver.receive();
          System.out.println(response);
          if (i >= largestServers.size()) {
            i = 0;
          }
          response = dsserver.scheduleJob(job, largestServers.getServer(i));
          System.out.println(response);
          i++;
        }
      }
      dsserver.send(dsserver.QUIT);
      response = dsserver.receive();
      System.out.println(response);
    } catch (Exception e) {
      System.out.println("Server not reachable or client error");
      System.exit(0);
    }
  }
}
