
import javafx.beans.property.*;

public class PlayerCredentials {  //1
      
      private final SimpleStringProperty nomeGiocatore;
      private final SimpleIntegerProperty punteggioGiocatore;
 
      public PlayerCredentials(String nome, int score) {
        nomeGiocatore = new SimpleStringProperty(nome);
        punteggioGiocatore = new SimpleIntegerProperty(score);
      }
 
      public String getNome() { return nomeGiocatore.get(); }
      public int getPunteggio() { return punteggioGiocatore.get(); }
    }

//1: Attributi del giocatore da inserire nel Database