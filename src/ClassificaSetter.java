
import javafx.geometry.*;
import javafx.scene.layout.*;

public class ClassificaSetter {
    private VBox vbox;
    
    public ClassificaSetter() {
        vbox = new VBox();
    }
        
    public void loadTabellaDB(RegisterPlayerScore registerPlayerScore) {
        registerPlayerScore.caricaGiocatoriDB();
        registerPlayerScore.setTabellaDB();
    }
     
    public void setVbox(RegisterPlayerScore registerPlayerScore) { //1
        vbox.getChildren().addAll(registerPlayerScore.getTabellaDB());
        vbox.setAlignment(Pos.CENTER);
        vbox.setLayoutX(1030);   
        vbox.setLayoutY(220); 
        vbox.setPrefHeight(265);
        vbox.setStyle("-fx-text-fill: black;");
    }
    
    public VBox getVbox() {
        return this.vbox;
    }
}

//1: Setta la classfica aggiungendo i campi per il nome del giocatore e il suo punteggio