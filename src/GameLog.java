
import java.io.*;
import java.util.*;

class GameLog implements Serializable { //1
  public String nomeApplicazione;
  public String ipServer;
  public Date data;
  public String etichetta;
  public GameLog(String a, String s, Date d, String e) 
  { nomeApplicazione = a; ipServer = s; data = d; etichetta = e;}
}

//1: Classe che rappresenta i Log del gioco 
