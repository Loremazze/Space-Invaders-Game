
import java.util.*;
import javafx.scene.*;
import javafx.scene.paint.*;


public class GameStatusUpdate {
    
    public static int lives;
    
    public static void incrementScore() {      
        SpaceInvaders.score++;
        LabelLayout.getScoreLabel().setText(Integer.toString(SpaceInvaders.score));       
    }

    public static void checkLives(Group root, int shotIndex, Shot shot) { //1
        
        Iterator<Alien> AlienIterator = SpaceInvaders.aliens.iterator();
        Alien alien = AlienIterator.next();
        
        root.getChildren().remove(10); //2
        alien.changeId(0); //3
        shot.changeId(shotIndex); //4
        lives--;
        SpaceInvaders.root_id_counter -= 2; //5
                
    }
    
     public static void update(Group root,DirectionUpdate directionUpdate,CollisionHandler collisionHandler,GameInitializer gameInitializer) { 
        
        if (SpaceInvaders.greenShots.isEmpty() == false) {              
            Iterator<Alien> AlienIterator = SpaceInvaders.aliens.iterator();
            Iterator<Shot> ShotIterator = SpaceInvaders.greenShots.iterator();           
          while (ShotIterator.hasNext()) {                      
            Shot shot = ShotIterator.next();                
            if (shot.color == Color.GREEN) {           
                shot.y -= 20;
                shot.shot.setY(shot.y);          
                while (AlienIterator.hasNext()) {
                    Alien alien = AlienIterator.next();
                    if (collisionHandler.greenShotHandler(shot,alien,root,AlienIterator,ShotIterator)) break;
                }
            }
          }
        }
        int diff = gameInitializer.boardWidth - gameInitializer.borderRight;
        for (Alien alien : SpaceInvaders.aliens) {
            int x = alien.x;
            if (x >= diff && directionUpdate.direction != -10) {
                directionUpdate.setDirection(-10);
                directionUpdate.updateAlienDirection(SpaceInvaders.aliens);
            }
            if (x <= gameInitializer.borderLeft && directionUpdate.direction != 10) {
                directionUpdate.setDirection(10);
                directionUpdate.updateAlienDirection(SpaceInvaders.aliens);
            }            
            alien.moveAlien(directionUpdate.direction);
        }
    }
    
}

//1: metodo chiamato quando il giocatore è colpito, decrementa le vite del giocatore
//2: all'indice 10 c'è la prima vita, quindi eliminando quella scorrono di indice anche le altre
//3: aggiusto l'id degli alieni ora che è stata tolta una vita
//4: aggiusto l'id degli shot con id maggiore di quello che ha colpito il giocatore
//5: il root_id_counter deve essere diminuito di 2 perchè ho eliminato una vita e uno Shot