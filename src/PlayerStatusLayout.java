
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

public class PlayerStatusLayout {
    public Rectangle rec;  
    public Rectangle recScore;
    public Rectangle recClassifica;
    public Rectangle recGiocatore;
    public static TextField playerName; 
    public static Button startGame;  
    
    public void initBackgroundForLabels() {
        rec = new Rectangle(950, 0, 700, 1000);
        rec.setFill(Color.WHITE);
        recScore = new Rectangle(1000, 20, 300, 100);
        recScore.setFill(Color.BLUE);
        recClassifica = new Rectangle(1000, 160, 300, 350);
        recClassifica.setFill(Color.BLUE);
        recGiocatore = new Rectangle(960, 550, 250, 100);
        recGiocatore.setFill(Color.BLUE);
    }
    
    public void initVariables() { //1
        SpaceInvaders.root_id_counter = 15; //tiene conto di tutti gli elementi già presenti in root
        SpaceInvaders.score = 0;
        GameStatusUpdate.lives = Configuration.totalLives;
        SpaceInvaders.howManyAliens = Configuration.totalAliens;
        SpaceInvaders.playerRegistered = false;
    }
    
    public void initPlayerName() { //2
        playerName = new TextField("Inserisci il nome");
        playerName.setLayoutX(1000);
        playerName.setLayoutY(560);
        playerName.setStyle("-fx-font-size: 20px; -fx-text-fill: black;");
        playerName.setMaxWidth(170);
    }
     
    public void initStartGame() { //3
        startGame = new Button("Inizia Partita");
        startGame.setLayoutX(1220);
        startGame.setLayoutY(575);
        startGame.setStyle("-fx-font-size: 20px; -fx-background-color: red"); 
    }
       
}

//1: Inizializza le variabili per lo status iniziale del gioco
//2: Inizializza il TextField dove inserire il nome del giocatore
//3: Inizializza il pulsante di InizioPartita