
import java.io.*;
import java.net.*;

public class Server {

  public static void main(String[] args) throws IOException {
     try {
        ServerSocket server = new ServerSocket(8080);
        System.out.println("Waiting for connection...");
        File file = new File("../log.txt");
        if (!file.exists())
          file.createNewFile();
        while (true) {
          Socket client = server.accept();
          System.out.println("Client has connected");
          new Thread(new Runnable(){
            @Override
            public void run (){
              try {InputStream in = client.getInputStream();
              OutputStream out = client.getOutputStream();
              String line;
              while (true){
                byte[] buf = new byte[32*1024];
                int readBytes = in.read(buf);
                line  = "--> "+new String(buf,0,readBytes);
                System.out.println("Server side"+line);
                out.write(line.getBytes());
                out.flush();
                PrintWriter pw = new PrintWriter(file);
                pw.println(line);
                if (line.equalsIgnoreCase("--> exit")){
                pw.close();
                break;
                }
              }
          }catch (IOException e) {
            System.out.println("ERROR");
          }
            }
          }).start();
        }
          }catch (IOException e) {
       System.out.println("Server ERR0R");
     }
  }
}