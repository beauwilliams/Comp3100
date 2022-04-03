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

      // while(true) {
      Job job = dsserver.getJob();
      System.out.println(job.toString());
      Data data = dsserver.getCapable(job.getSpec());
      System.out.println(data.toString());
      Servers<Server> servers = dsserver.getServers(data.getNumServers());
      // System.out.println(servers.size());
      System.out.println(dsserver.getHighestCore(servers));
      // Servers<Server> largest = dsserver.getLargestServers(servers);

      // System.out.println(largest.size());
      /* if (dsserver.receive() == dsserver.NONE) {
          break;
        }

        if (dsserver.receive() == dsserver.DOT) {
          break;
        } */
      // }
      // TODO: Implement DS-Sim client - LRR
    } catch (Exception e) {
      System.out.println("Server not reachable or client error");
      System.exit(0);
    }
  }
}
