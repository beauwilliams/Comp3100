package DSClient;

public class Scheduler {

  /*
   * @param dsserver
   * @dev schedules based on first available server
   */
  public void facScheduler(DSInterface dsserver) throws Exception {
    String response = "";
    String serverType = "";
    System.out.println("Scheduling via FAC");
    Boolean firstRun = true;
    Data data;
    Servers<Server> availableServers = null;
    Servers<Server> capableServers = null;
    Servers<Server> largestServers = null;
    int i = 0;
    while (true) {
      Job job = dsserver.getJob();
      if (job.getName().equals("NONE")) {
        break;
      }

      if (job.getName().equals("JOBN")) {
        if (firstRun) {
          firstRun = false;
          data = dsserver.getCapable(job.getSpec());
          capableServers = dsserver.getServers(data.getNumServers());
          serverType = dsserver.getFirstLargestServerType(capableServers);
          largestServers =
              dsserver.getServersByType(capableServers, serverType);
          availableServers = capableServers;
        } else {
          data = dsserver.getAvailable(job.getSpec());
          availableServers = dsserver.getServers(data.getNumServers());
        }

        if (availableServers.size() != 0) {
          dsserver.send(dsserver.OK);
          response = dsserver.receive();
          // NOTE:Schedules jobs to the first available
          response = dsserver.scheduleJob(job, availableServers.getServer(0));
        } else {
          if (i >= largestServers.size()) {
            i = 0;
          }
          response = dsserver.receive();
          response = dsserver.scheduleJob(job, largestServers.getServer(i));
          i++;
        }

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
            dsserver.getServersByType(servers, serverType);

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
}
