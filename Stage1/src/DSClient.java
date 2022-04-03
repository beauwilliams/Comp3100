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
      Job job = dsserver.getJob();
      System.out.println(job.toString());
      dsserver.getCapableServers(job.getSpec());
      // TODO: Implement DS-Sim client - LRR
    } catch (Exception e) {
      System.out.println("Server not reachable or client error");
      System.exit(0);
    }
  }
}










