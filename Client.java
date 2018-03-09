import java.io.*;
import java.net.*;
import java.util.Scanner;

class Client {
  public static void main(String[] args) throws IOException {
    try {
      Socket socket = new Socket("localhost",8080);
      try (InputStream in = socket.getInputStream();
          OutputStream out = socket.getOutputStream()){
        Scanner scanner = new Scanner(System.in);
        while (true){
          String line = scanner.nextLine();
          out.write(line.getBytes());
          out.flush();
          byte[] data = new byte[32*1024];
          int bytes = in.read(data);
          System.out.println(new String(data,0,bytes));
          if (line.equalsIgnoreCase("exit")) break;
        }
      }
    }catch (IOException e) {
      System.out.println("Can't connect to server");
    }
  }
}