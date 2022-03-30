import java.io.*;
import java.net.*;
import java.util.ArrayList;

class DssimClient {
  public static void main(String args[]) throws Exception {
    try {
      Socket socket = new Socket("127.0.0.1", 50000);
      BufferedReader inputStream =
      new BufferedReader(new InputStreamReader(socket.getInputStream()));
      DataOutputStream outputStream =
          new DataOutputStream(socket.getOutputStream());
      try {
        // Send HELO
        outputStream.write(("HELO\n").getBytes());
        outputStream.flush();
        // Response
        System.out.println("Ds-sim Response: " + inputStream.readLine());
      } catch (Exception e) {
        System.err.println(e);
      }

      try {
        // EX 2.2
        // Send AUTH
        String username = System.getProperty("user.name");
        outputStream.write(("AUTH " + username + "\n").getBytes());
        // outputStream.write(("AUTH beau\n").getBytes());
        outputStream.flush();
        System.out.println("Authenticated: " + username);
        // Response
        System.out.println("Ds-sim Response: " + inputStream.readLine());
      } catch (Exception e) {
        System.err.println(e);
      }

      // Send REDY
      try {
        outputStream.write(("REDY\n").getBytes());
        outputStream.flush();
        // Response
        // System.out.println("Ds-sim Response: " + inputStream.readLine());
        String JOBN = inputStream.readLine();
        System.out.println(JOBN);
        // submit time / job id / core / memory / disk
        String[] JOBN_ARRAY = JOBN.split(" ");
        String spec = JOBN_ARRAY[4] + " " + JOBN_ARRAY[5] + " " + JOBN_ARRAY[6];
        outputStream.write(("GETS Capable " + spec + "\n").getBytes());
        String DATA = inputStream.readLine();
        System.out.println(DATA);
        String[] DATA_ARRAY = DATA.split(" ");
        outputStream.write(("OK\n").getBytes());

        ArrayList<String> SERVER_LIST = new ArrayList<String>();
        for (int i = 0; i < Integer.valueOf(DATA_ARRAY[1]); i++) {
          // System.out.println(inputStream.readLine());
          SERVER_LIST.add(inputStream.readLine());
          System.out.println(SERVER_LIST.get(i));
        }


        // rvc JOBN 37 0 1135 700 3800
        // inputStream.readline() //split me into array
        // submit time / job id / core / memory / disk
        // then send....
        // GETS Capable 3 700 3800
        // rcv DATA 30 24
        // numRecords / length records
        // schedule 1 job SCHD 0 xlarge 0
        // outputStream.write(("OK\n").getBytes());




        // outputStream.write(("SCHD 0 super-silk 0\n").getBytes());






        outputStream.write(("OK\n").getBytes());
        System.out.println("Job scheduled to largest server(s) successfully");



        //WEEK 6 ONWARDS......
        // use for loop based on numRecords
      } catch (Exception e) {
        System.err.println(e);
      }


      /* try {
        // Send QUIT
        outputStream.write(("QUIT\n").getBytes());
        outputStream.flush();
        // Response
        System.out.println("Ds-sim Response: " + inputStream.readLine());
      } catch (Exception e) {
        System.err.println(e);
      } */

      // Close the streams and sockets
      outputStream.close();
      inputStream.close();
      socket.close();
    } catch (Exception e) {
      System.out.println("Server not running");
      System.exit(0);
    }
  }

  public DssimClient(String address, int port) {
    try {
    } catch (Exception e) {
      System.err.println(e);
    }
  }
}
