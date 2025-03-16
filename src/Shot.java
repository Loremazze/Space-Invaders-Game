
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;

 public class Shot extends Sprite { //1

        private boolean alive;
        Color color;
        ImageView shot;
        private int id;

        public Shot(Group root, int x, int y, Color color,ImageView shot, int id) {

            initShot(root, x, y, color, shot, id);
        }

        private void initShot(Group root, int x, int y, Color color, ImageView shot, int id) { //2

            this.alive = true;
            this.x = x;
            this.y = y;
            this.color = color;
            this.id = id;
            shot.setX(x);
            shot.setY(y - 5);
            this.shot = shot;
            root.getChildren().add(this.shot);
            
        }

        public void changeId(int id) { //3

            for (Shot shot : SpaceInvaders.redShots) 
            {
                if (shot.id >= id) shot.id -= 2;
                else shot.id -= 1;
            }
            
            for (Shot shot : SpaceInvaders.greenShots) 
            {
                if (shot.id >= id) shot.id -= 2;
                else shot.id -= 1;
            }
        }
        
        public int getId() {
            return this.id;
        }

        public boolean checkAlive() {        
            return this.alive;           
        }
        
        public void disableAlive() {        
            this.alive = false;          
        }
    }
      
//1: Classe per i missili lanciati dal giocatore e dagli alieni, distinti dal coloreù
//2: Inizializza lo Shot
//3: Cambia l'Id di tutti gli Shot per aggiornare a livello visivo la schermata di gioco

