
import javafx.scene.control.*;

public class LabelLayout { //1   
    private Label la;
    private static Label scoreLabel;
    private Label PunteggioLabel;
    private Label ClassificaLabel;
    private Label GiocatoreLabel;
    
    public void initLa() {
        this.la = new Label("SpaceInvaders");          
        this.la.setLayoutX(20);  
        this.la.setStyle("-fx-font-size: 40px; -fx-text-fill: green; -fx-font-weight: bold;");       
    }
    
    public static void initScoreLabel() {
        scoreLabel = new Label("0");
        scoreLabel.setLayoutX(1130);
        scoreLabel.setLayoutY(65);
        scoreLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: black; -fx-font-weight: bold;");
    }
     
    public void initPunteggioLabel() {
        this.PunteggioLabel = new Label("PUNTEGGIO"); 
        this.PunteggioLabel.setLayoutX(1030);  
        this.PunteggioLabel.setLayoutY(25);
        this.PunteggioLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: white; -fx-font-weight: bold;");
    } 
    
    public void initClassificaLabel() {
        this.ClassificaLabel = new Label("CLASSIFICA"); 
        this.ClassificaLabel.setLayoutX(1030);  
        this.ClassificaLabel.setLayoutY(160);
        this.ClassificaLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: white; -fx-font-weight: bold;");
    }
       
    public void initGiocatoreLabel() {
        this.GiocatoreLabel = new Label("GIOCATORE"); 
        this.GiocatoreLabel.setLayoutX(970);  
        this.GiocatoreLabel.setLayoutY(600);
        this.GiocatoreLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: white; -fx-font-weight: bold;");       
    }
    
    public Label getLa() {
        return this.la;
    }
    
    public static Label getScoreLabel() {
        return LabelLayout.scoreLabel;
    }
    
    public Label getPunteggioLabel() {
        return this.PunteggioLabel;
    }
    
    public Label getClassificaLabel() {
        return this.ClassificaLabel;
    }
    
    public Label getGiocatoreLabel() {
        return this.GiocatoreLabel;
    }
    
}

//1: Ogni metodo di questa classe inizializza una delle Label che compongono 
//   la schermata di gioco
