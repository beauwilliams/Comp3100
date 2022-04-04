package DSClient;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DSClient {
  public static void main(String args[]) throws Exception {
    try {
      final String host = "127.0.0.1";
      final int port = 50000;
      DSInterface dsserver = new DSInterface(host, port);
      Scheduler scheduler = new Scheduler();
      dsserver.connect();
      dsserver.authenticateUser();
      scheduler.lrrScheduler(dsserver);
      dsserver.send(dsserver.QUIT);
      System.out.println("Scheduling successful and sent QUIT with response: " +
                         dsserver.receive());
      dsserver.disconnect();
    } catch (Exception e) {
      System.out.println("Server not reachable or client error");
      System.exit(0);
    }
  }
}
