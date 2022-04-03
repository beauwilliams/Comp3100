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

      Boolean completed = false;
      while (!completed) {

        Job job = dsserver.getJob();
        if (job != null) {
        Data data = dsserver.getCapable(job.getSpec());
        Servers<Server> servers = dsserver.getServers(data.getNumServers());
        System.out.println(servers.size());
        // System.out.println(dsserver.getHighestCore(servers));
        Servers<Server> largestServers = dsserver.getLargestServer(servers);
        System.out.println("ls srvs size = "+largestServers.size());


        dsserver.send(dsserver.OK);
        String response = dsserver.receive();
        /* if (response == null) {
            break;
        } */
        // break;
        // System.out.println(response);
        // System.out.println("i = "+i);
        dsserver.scheduleJob(job, largestServers.getServer(0));

        /* response = dsserver.receive();
        System.out.println(response); */

        // System.out.println("yo");
        // dsserver.send(dsserver.OK);

        /* if (i == largestServers.size()-1) {
          i = 0;
        } else {
          i++;
        } */
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
