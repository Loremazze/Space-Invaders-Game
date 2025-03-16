
import javafx.scene.*;
import javafx.scene.image.*;

 public class Alien extends Sprite { 

    private int id;
    private int costantId;
    ImageView alien;
    private boolean alive;

    public Alien(Group root, int x, int y, int id, ImageView alien) {

        this.alive = true;
        this.x = x;
        this.y = y;
        this.id = id;
        this.costantId = id;
        
        alien.setX(x);
        alien.setY(y);
        alien.setFitWidth(100);
        alien.setFitHeight(100);
        this.alien = alien;
        root.getChildren().add(this.alien);
       
    }
    
    public int getId() {

        return this.id;        
    }
    
    public int getCostantId() {

        return this.costantId;        
    }

    public void moveAlien(int direction) { //1

        this.x += direction;
        this.alien.setX(this.x);
        
    }
    
    public void changeId(int id) { //2

        for (Alien alien : SpaceInvaders.aliens) 
        {
            if (alien.id >= id) alien.id -= 1;
        }
    }
    
    public boolean checkAlive() {
        
        return this.alive;
    }
    
    public void disableAlive() {
        
        this.alive = false;
            
    }

}

//1: trasla l'alieno orizzontalmente
//2: cambia l'id di ogni alieno per mantenere a video gli alieni ancora in vita