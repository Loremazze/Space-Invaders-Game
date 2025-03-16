
import com.thoughtworks.xstream.*; 
import java.io.*;
import java.net.*;
import java.util.*;
import java.nio.file.*;

public class InvioLogServer {
     
  public static void serializzaBin(GameLog g) { //1
    try ( ObjectOutputStream ooutf = 
           new ObjectOutputStream( new FileOutputStream("GameLog.bin") );
          ObjectOutputStream oouts =
           new ObjectOutputStream( (new Socket("localhost",8080) ).getOutputStream())
    ) { ooutf.writeObject(g); oouts.writeObject(g); } catch (Exception e) {}
    try ( ObjectInputStream oinf = 
           new ObjectInputStream( new FileInputStream("GameLog.bin") ); 
    ) { g = (GameLog)oinf.readObject(); } catch (Exception e) {e.printStackTrace();}
  }
  
  public static void serializzaXML(GameLog g) { //2
    XStream xs = new XStream(); 
    String x = xs.toXML(g); 
    try { Files.write(Paths.get("GameLog.txt"), x.getBytes(), StandardOpenOption.APPEND); //leva il commento con l'append per avere il file txt completo di tutti i log
          x = new String(Files.readAllBytes(Paths.get("GameLog.txt")));
    } catch (Exception e) {}
    g = (GameLog)xs.fromXML(x);  
    try ( DataOutputStream dout = 
          new DataOutputStream( (new Socket("localhost",8080) ).getOutputStream())
    ) { dout.writeUTF(x);} catch (Exception e) {e.printStackTrace();}
  }
  
  public static void inviaLog(String s) { //3
    GameLog g = new GameLog("SpaceInvaders",Configuration.databaseIp,
                    new Date(),s);
        
    serializzaBin(g);
    serializzaXML(g);
  }
}

//1: Serializza i dati dei Log del gioco
//2: Serializzazione per il formato XML dei Log del gioco
//3: metodo chiamato quando si vuole inviare un Log