
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import javafx.scene.*;


public class LocalCache {
    
    public static void conservaPunteggioBin() { //1 
        try ( FileOutputStream fout = new FileOutputStream("./myfiles/cache.bin");
              ObjectOutputStream oout = new ObjectOutputStream(fout);) { 
         
          int playerX = PlayerPawn.getPlayerX();
          oout.writeObject(SpaceInvaders.howManyAliens);
          oout.writeObject(playerX);
          oout.writeObject(GameStatusUpdate.lives);
          
          for (Alien alien : SpaceInvaders.aliens) {
            oout.writeObject(alien.getCostantId());
            oout.writeObject(alien.x);
            oout.writeObject(alien.y);
          }
          
        } catch (IOException ex) { 
            System.out.println("errore: impossibile scrivere nella cache!");
        }
    }
    
    public static void prelevaPunteggioBin(Group root) { //2
        int copia;
        try ( FileInputStream fin = new FileInputStream("./myfiles/cache.bin");
              ObjectInputStream oin = new ObjectInputStream(fin); ) { 
          setFirstElements(oin);
          Iterator<Alien> AlienIterator1 = SpaceInvaders.aliens.iterator();
          Alien alien1 = AlienIterator1.next();
          
          for (int i = 3; i > GameStatusUpdate.lives; i--) {
            root.getChildren().remove(10); //leva le vite
            alien1.changeId(0);
          }       
          Iterator<Alien> AlienIterator = SpaceInvaders.aliens.iterator();             
          int alienIndex;
          int buff = SpaceInvaders.howManyAliens;        
          while (buff > 0) {
              Alien alien = AlienIterator.next();
              copia = (int) oin.readObject();
              alienIndex = 0;              
              while (copia != alien.getCostantId()) {            
                if (alienIndex == 0) alienIndex = alien.getId();            
                root.getChildren().remove(alienIndex); 
                alien.disableAlive();
                AlienIterator.remove();
                alien.changeId(alienIndex + 1);
                alien = AlienIterator.next();
              }
              setAlienElements(alien,oin);
              if (buff == 1) {             
                  while(AlienIterator.hasNext()) {                 
                    root.getChildren().remove(SpaceInvaders.root_id_counter);
                    alien = AlienIterator.next();
                    alien.disableAlive();
                    AlienIterator.remove();                   
                  }
              }
              buff--;                        
          }          
        System.out.println(SpaceInvaders.root_id_counter);
        } catch (IOException | ClassNotFoundException ex) {System.out.println("errore nel prelievo della cache");}
        LabelLayout.getScoreLabel().setText(Integer.toString(SpaceInvaders.score));
    }
    
    public static void setFirstElements(ObjectInputStream oin) throws IOException, ClassNotFoundException { 
        SpaceInvaders.howManyAliens = (int) oin.readObject();
          int playerX = (int) oin.readObject();
          PlayerPawn.setPlayerX(playerX); 
          GameStatusUpdate.lives = (int) oin.readObject();
    }
    
    public static void setAlienElements(Alien alien,ObjectInputStream oin) throws IOException, ClassNotFoundException {
        alien.x = (int) oin.readObject();
              alien.alien.setX(alien.x);
              alien.y = (int) oin.readObject();
              alien.alien.setY(alien.y);
              SpaceInvaders.root_id_counter = alien.getId() + 1;

    }
     
    public static void conservaPunteggioTxt() { 
        String x = Integer.toString(SpaceInvaders.score);
        try { 
          Files.write(Paths.get("./myfiles/cache.txt"), x.getBytes());
        } catch (IOException ex) {            
            System.out.println("errore: impossibile conservare il punteggio!");
        }
    }
     
    public static void prelevaPunteggioTxt() { 
        try {  
          String x = new String(Files.readAllBytes(Paths.get("./myfiles/cache.txt")));
          SpaceInvaders.score = Integer.parseInt(x);
          LabelLayout.getScoreLabel().setText(Integer.toString(SpaceInvaders.score));          
        } catch (IOException ex) {           
            System.out.println("errore: impossibile prelevare il punteggio!");
        }
    }
      
    public static void inviaPunteggioBin() { //3
        try ( Socket s = new Socket("localhost", 8080); 
              ObjectOutputStream oout = new ObjectOutputStream (s.getOutputStream());
        ) { oout.writeObject(SpaceInvaders.score);
        } catch (IOException e) { e.printStackTrace(); }
        System.out.println("invia stato");
    }
    
    public static void inviaPlayerRegistered() { //4 
        try ( FileOutputStream fout = new FileOutputStream("./myfiles/playerRegistered.bin");
              ObjectOutputStream oout = new ObjectOutputStream(fout);) { 
          
          oout.writeObject(SpaceInvaders.playerRegistered);
          oout.writeObject(SpaceInvaders.gameWon);
          oout.writeObject(PlayerStatusLayout.playerName.getText());         
         
        } catch (IOException ex) { 
            System.out.println("errore: impossibile conservare lo stato del giocatore!");
        }
    }
     
    public static void prelevaPlayerRegistered() { 
      
        try ( FileInputStream fin = new FileInputStream("./myfiles/playerRegistered.bin");
              ObjectInputStream oin = new ObjectInputStream(fin); ) { 
        
          SpaceInvaders.playerRegistered = (boolean) oin.readObject();
          SpaceInvaders.gameWon = (boolean) oin.readObject();
          String name = oin.readObject().toString();
          PlayerStatusLayout.playerName.setText(name);
                   
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("errore: impossibile prelevare lo stato del giocatore!");
        }
    }
    
}

//1: Scrive nella Cache i dati di gioco
//2: Preleva i dati dalla Cache
//3: Scrive il punteggio del giocatore nella Cache
//4: Scrive nella Cache i dati per capire se c'è un nuovo giocatore o meno