package DSClient;

public class Scheduler {

  public void facScheduler(DSInterface dsserver) throws Exception {
    String response = "";
    String serverType = "";
    System.out.println("Scheduling via LRR");
    Boolean gotLargestServer = false;
    Boolean firstRun = true;
    Data data;
    Servers<Server> servers = null;
    Servers<Server> allServers = null;
    int i = 0;
    while (true) {
      Job job = dsserver.getJob();
      if (job.getName().equals("NONE")) {
        break;
      }

      if (job.getName().equals("JOBN")) {
        // Data data = dsserver.getCapable(job.getSpec());
        if (firstRun) {
          firstRun = false;
          System.out.println("First run..");
          data = dsserver.getCapable(job.getSpec());
          servers = dsserver.getServers(data.getNumServers());
          allServers = servers;
        } else {
          data = dsserver.getAvailable(job.getSpec());
          // data = dsserver.getAll();
          servers = dsserver.getServers(data.getNumServers());
        }

        if (gotLargestServer == false) {
          serverType = dsserver.getFirstLargestServerType(allServers);
          gotLargestServer = true;
        }
        Servers<Server> largestServers =
            dsserver.getLargestServersByType(allServers, serverType);
        if (i >= largestServers.size()) {
          i = 0;
        }


        if (servers.size() != 0) {
          dsserver.send(dsserver.OK);
          response = dsserver.receive();
          response = dsserver.scheduleJob(job, servers.getServer(0));
        } else {
          System.out.println("server size is 0");
          response = dsserver.receive();
          response = dsserver.scheduleJob(job, largestServers.getServer(i));
        }
        i++;
      }
    }
  }

public void lrrScheduler(DSInterface dsserver) throws Exception {
    String response = "";
    String serverType = "";
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
        Servers<Server> largestServers =
            dsserver.getLargestServersByType(servers, serverType);

        dsserver.send(dsserver.OK);
        response = dsserver.receive();
        if (i >= largestServers.size()) {
          i = 0;
        }
        response = dsserver.scheduleJob(job, largestServers.getServer(i));
        i++;
      }
    }
  }

  /* public void fcaScheduler(DSInterface dsserver) throws Exception {
    String response = "";
    String serverType = "";
    System.out.println("Scheduling via FCA");
    while (true) {
      Job job = dsserver.getJob();
      if (job.getName().equals("NONE")) {
        break;
      }

      if (job.getName().equals("JOBN")) {
        // Data data = dsserver.getCapable(job.getSpec());
        Data data = dsserver.getCapable();
        Servers<Server> servers = dsserver.getServers(data.getNumServers());
        if (gotLargestServer == false) {
          serverType = dsserver.getFirstLargestServerType(servers);
          gotLargestServer = true;
        }
        Servers<Server> largestServers =
            dsserver.getLargestServersByType(servers, serverType);

        dsserver.send(dsserver.OK);
        response = dsserver.receive();
        if (i >= largestServers.size()) {
          i = 0;
        }
        response = dsserver.scheduleJob(job, largestServers.getServer(i));
        i++;
      }
    }
  } */
}
