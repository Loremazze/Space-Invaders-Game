
import java.util.*;
import javafx.scene.*;

public class CollisionHandler {
    private int alienIndex;
    private int greenShotIndex;
    private int redShotIndex;

    public boolean greenShotHandler(Shot shot, Alien alien, Group root,
                                 Iterator<Alien> AlienIterator,Iterator<Shot> ShotIterator) { //1
            if (shot.x >= (alien.x)
                            && shot.x <= (alien.x + 100)
                            && shot.y >= (alien.y)
                            && shot.y <= (alien.y + 100)) {
                   
                        alienIndex = alien.getId();
                        greenShotIndex = shot.getId();                          
                        root.getChildren().remove(greenShotIndex);
                        root.getChildren().remove(alienIndex);
                        GameStatusUpdate.incrementScore();
                        SpaceInvaders.howManyAliens--;
                        alien.disableAlive();
                        shot.disableAlive();                        
                        AlienIterator.remove();
                        ShotIterator.remove();
                        alien.changeId(alienIndex + 1);
                        shot.changeId(greenShotIndex + 1);
                        SpaceInvaders.root_id_counter -= 2;
                        return true;
    }
    return false;
  }
    
  public void redShotHandler(Shot shot,Group root,Iterator<Shot> ShotIterator) { //2
                                            
    redShotIndex = shot.getId();                       
    root.getChildren().remove(redShotIndex);
    ShotIterator.remove();                       
    GameStatusUpdate.checkLives(root,redShotIndex + 1,shot);
                     
  }
}

//1: Controlla se un Shot verde ha colpito un alieno e in tal caso rimuove dalla 
//   schermata lo Shot stesso e l'alieno colpito
//2: Se uno Shot di un alieno ha colpito il giocatore, lo Shot viene eliminato 
//   schermata e viene tolta una vita al giocatore