
import java.util.*;

public class DirectionUpdate {
    public int direction;
    public int goDown;
    
    public DirectionUpdate() {
        direction = -10;
        goDown = 5;
    }
    
    public void setDirection(int dir) {
        direction = dir;
    }
    
    public void updateAlienDirection(List<Alien> aliens) { //1
        Iterator<Alien> alienIterator = aliens.iterator();
        while (alienIterator.hasNext()) 
        {
            Alien alien = alienIterator.next();
            alien.y += goDown;
            alien.alien.setY(alien.y);
        }
    }
}

//1: Trasla tutti gli Alieni verso il basso