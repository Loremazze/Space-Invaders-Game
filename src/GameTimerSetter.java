
import java.util.*;
import javafx.application.*;
import javafx.scene.*;

public class GameTimerSetter {
    public Timer timer;
    public TimerTask gameCostantUpdate;
    public TimerTask redBombs;
    public TimerTask gameWin; 
    
    public GameTimerSetter() {
        timer = new Timer();
    }
        
    public void setCostantUpdate(Group r,DirectionUpdate d,CollisionHandler c,GameInitializer g) { //1
        gameCostantUpdate = new TimerTask() {            
            @Override
            public void run() {
                Platform.runLater(() -> {
                    GameStatusUpdate.update(r,d,c,g);
                });
            }    
        };
    }      
       
    public void cancelCostantUpdate() {
        gameCostantUpdate.cancel();
    }
}

//1: Setta il timer periodico per il metodo update