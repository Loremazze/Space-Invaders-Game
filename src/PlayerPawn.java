
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.*;

public class PlayerPawn extends Sprite {

    public static int x;
    public static int y;
    public static ImageView player;

    public void initPlayer(Group root, ImageView player) { //1

        player = new ImageView("file:../../myfiles/Player.jpg");

        int START_X = 600;
        int START_Y = 530;
        
        PlayerPawn.x = START_X;
        PlayerPawn.y = START_Y;
        player.setX(START_X);     
        player.setY(START_Y);
        
        player.setFitWidth(150);
        player.setFitHeight(120);
        PlayerPawn.player = player;
        root.getChildren().add(PlayerPawn.player);
    }

    public void movePawn(KeyCode key,GameInitializer gameInitializer) { //2

        if (key == KeyCode.RIGHT) 
            PlayerPawn.x += 5;
        else
            PlayerPawn.x -= 5;

        if (PlayerPawn.x <= 2) PlayerPawn.x = 2;
     
        if (PlayerPawn.x >= 820 - gameInitializer.borderRight)             
            PlayerPawn.x = 820 - gameInitializer.borderRight;
       
        PlayerPawn.player.setX(PlayerPawn.x);
        
    }
    
    public static void setPlayerX(int quanto) { 
        PlayerPawn.x = quanto;
        PlayerPawn.player.setX(PlayerPawn.x);
    }
    
    public static int getPlayerX() {
        return PlayerPawn.x;
    }
    
}
    
//1: Setta la pedina del giocatore all'inizio della schermata di gioco 
//2: Sposta il giocatore a destra o a sinistra, controllando anche che non
//   superi il bordo di gioco