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
      DSInterface dsserver = new DSInterface(host, port);
      dsserver.connect();
      dsserver.authenticateUser();

      int i = 0;
      while (true) {

        System.out.println("hi");
        Job job = dsserver.getJob();
        System.out.println("ye");
        if (job != null) {
        Data data = dsserver.getCapable(job.getSpec());
        Servers<Server> servers = dsserver.getServers(data.getNumServers());
        System.out.println(servers.size());
        // System.out.println(dsserver.getHighestCore(servers));
        Servers<Server> largestServers = dsserver.getLargestServer(servers);
        System.out.println(largestServers.size());
        // dsserver.send(dsserver.OK);
        dsserver.send(dsserver.OK);
        // TODO: Implement DS-Sim client - LRR
        // for i > numJobs
        // schedule server(index)
        //
        String response = dsserver.receive();
        System.out.println(response);
        if (response.equals(dsserver.NONE)) {
          System.out.println("No servers available");
          break;
        }
        System.out.println("wh");
        dsserver.scheduleJob(job, largestServers.getServer(i));

        /* response = dsserver.receive();
        System.out.println(response); */

        // System.out.println("yo");
        // dsserver.send(dsserver.OK);

        if (i == largestServers.size()) {
          i = 0;
        } else {
          i++;
        }
        }
      }
      // if i = largestServers.size() -> i = 0
      // else i++
    } catch (Exception e) {
      System.out.println("Server not reachable or client error");
      System.exit(0);
    }
  }
}
