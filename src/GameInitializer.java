

import java.util.*;
import javafx.scene.*;
import javafx.scene.image.*;

public class GameInitializer {
    
    private  ImageView firstLife;
    private  ImageView secondLife;
    private  ImageView thirdLife;
    public  int boardWidth;
    public  int boardHeight;
    public  int borderRight;
    public  int borderLeft;
    public  int alienInitX;
    public  int alienInitY;
    
    public  void boardInit() {
        boardWidth = 850;
        boardHeight = 500;
        borderRight = 30;
        borderLeft = 5;

        alienInitX = 350;
        alienInitY = 30;
    }
    
    public  void lifeInit() {
        
        firstLife =  new ImageView("file:../../myfiles/Life.jpg");
        secondLife = new ImageView("file:../../myfiles/Life.jpg");
        thirdLife =  new ImageView("file:../../myfiles/Life.jpg");
        
        int x1 = 20;
        int x2 = 80;
        int x3 = 140;
        int y = 650;
        int lifeWidth = 50;
        int lifeHeight = 40;
        
        firstLife.setX(x1);
        secondLife.setX(x2);
        thirdLife.setX(x3);
        firstLife.setY(y);
        secondLife.setY(y);
        thirdLife.setY(y);
        
        firstLife.setFitWidth(lifeWidth);
        firstLife.setFitHeight(lifeHeight);
        secondLife.setFitWidth(lifeWidth);
        secondLife.setFitHeight(lifeHeight);
        thirdLife.setFitWidth(lifeWidth);
        thirdLife.setFitHeight(lifeHeight); 
        
        SpaceInvaders.greenShots = new ArrayList<>();
        SpaceInvaders.redShots = new ArrayList<>();
        
    }
    
    public  void gameInit(Group root,PlayerPawn playerPawn,GameTimerSetter timer) { //1

        SpaceInvaders.aliens = new ArrayList<>();
        playerPawn.initPlayer(root,new ImageView("file:../../myfiles/Player.jpg"));

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {

                Alien alien = new Alien(root, alienInitX + 100 * j,
                        alienInitY + 100 * i, SpaceInvaders.root_id_counter++, new ImageView("file:../../myfiles/Alien.jpg"));
                SpaceInvaders.aliens.add(alien);
                
            }
        }   
       
        timer.timer.scheduleAtFixedRate(timer.gameCostantUpdate, 100, 1000);            
        timer.timer.scheduleAtFixedRate(timer.redBombs, 100, 2000);
        timer.timer.scheduleAtFixedRate(timer.gameWin, 100, 5000);
    }
    
     public void doPlayerStatusLayout(PlayerStatusLayout layout) {
        layout.initVariables();
        layout.initPlayerName();
        layout.initStartGame(); 
        layout.initBackgroundForLabels();
    }
    
    public void doLabelLayout(LabelLayout labelLayout) {
        labelLayout.initLa();
        LabelLayout.initScoreLabel();
        labelLayout.initPunteggioLabel();
        labelLayout.initClassificaLabel();
        labelLayout.initGiocatoreLabel();
    }
    
    public  ImageView getFirstLife() {
        return this.firstLife;
    }
    
     public  ImageView getSecondLife() {
        return this.secondLife;
    }
     
      public  ImageView getThirdLife() {
        return this.thirdLife;
    }
    
}

//1: Inizializza il vettore che contiene gli Alieni e 3 dei Timer del gioco