package DSClient;

public class Scheduler {

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

}
