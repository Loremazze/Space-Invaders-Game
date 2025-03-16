
import java.net.*;
import java.io.*;

public class RicezioneLog {  //1
    public static void main(String[] args) {
      
      while (true) {
        try ( ServerSocket servs = new ServerSocket(8080);
          //Socket so = servs.accept();
          //ObjectInputStream oin = new ObjectInputStream(so.getInputStream());
          Socket sd = servs.accept(); 
          DataInputStream din = new DataInputStream(sd.getInputStream()); 
        ) {
          //if (din.available() >= 0) 
          System.out.println(din.readUTF());
        } catch(EOFException e) {System.out.println("end of file");}
          catch (IOException e) {e.printStackTrace();}
      }
    }
}

//1: Classe per la ricezione dei Log
