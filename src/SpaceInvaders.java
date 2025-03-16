

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;

public class SpaceInvaders extends Application {    
    public static int root_id_counter;
    public static int score;        
    public static List<Alien> aliens;
    public static List<Shot> greenShots;
    public static List<Shot> redShots;   
    public static int howManyAliens;
    public static boolean playerRegistered;   
    public static boolean gameWon;
    
    public void start(Stage stage) {  //1     
        Configuration.load(); 
        RegisterPlayerScore registerPlayerScore = new RegisterPlayerScore();
        registerPlayerScore.initialize();        
        GameInitializer gameInitializer = new GameInitializer();
        gameInitializer.boardInit();              
        ClassificaSetter classifica = new ClassificaSetter();
        classifica.loadTabellaDB( registerPlayerScore);
        classifica.setVbox( registerPlayerScore);   
        PlayerStatusLayout playerLayout = new PlayerStatusLayout();
        DirectionUpdate directionUpdate = new DirectionUpdate();
        CollisionHandler collisionHandler = new CollisionHandler();
        PlayerPawn playerPawn = new PlayerPawn();
        gameInitializer.doPlayerStatusLayout(playerLayout);
        LabelLayout labelLayout = new LabelLayout();
        gameInitializer.doLabelLayout(labelLayout);                
        gameInitializer.lifeInit();        
        InvioLogServer.inviaLog("AvvioApplicazione");        
        Group root = new Group(playerLayout.rec,playerLayout.recScore,playerLayout.recClassifica,playerLayout.recGiocatore,labelLayout.getGiocatoreLabel(),
                PlayerStatusLayout.playerName,LabelLayout.getScoreLabel(),labelLayout.getPunteggioLabel(),labelLayout.getClassificaLabel(),
                PlayerStatusLayout.startGame,gameInitializer.getFirstLife(),gameInitializer.getSecondLife(),gameInitializer.getThirdLife(),classifica.getVbox());  
        
        LocalCache.prelevaPlayerRegistered();
        LocalCache.prelevaPunteggioTxt();    
        GameTimerSetter timer = new GameTimerSetter();
        checkNewGamePlayer(root,playerPawn,timer,gameInitializer, registerPlayerScore);             
        Scene scene = new Scene(root, gameInitializer.boardWidth, gameInitializer.boardHeight, Color.BLACK);        
        initStage(stage,scene,timer,root,playerPawn,collisionHandler,registerPlayerScore,gameInitializer,directionUpdate);    
    }
      
    public void initStage(Stage stage,Scene scene,GameTimerSetter timer,Group root,PlayerPawn playerPawn,CollisionHandler collisionHandler,
                          RegisterPlayerScore registerPlayerScore,GameInitializer gameInitializer,DirectionUpdate directionUpdate) //2
                          {
        stage.setTitle("SpaceInvaders");          
        stage.setScene(scene);
        stage.show();
        timer.setCostantUpdate(root,directionUpdate,collisionHandler, gameInitializer);
        setRedBombs(root,stage,collisionHandler,playerPawn,timer, registerPlayerScore);        
        gameWinCheck(root,stage,timer);
        checkGameKeyPressed(root,scene,playerPawn, gameInitializer);   
        handleApplicationClosure(stage);
        //gameOver(root,stage);
    }
    
    public void checkNewGamePlayer(Group root,PlayerPawn playerPawn,GameTimerSetter timer,GameInitializer gameInitializer,RegisterPlayerScore registerPlayerScore) {
        if (playerRegistered == true) {
            PlayerStatusLayout.playerName.setDisable(true);
            registerPlayerScore.getTabellaDB().setDisable(true);
        }        
        if (playerRegistered == false) {
        PlayerStatusLayout.startGame.setOnAction((ActionEvent ev)-> {          
            gameInitializer.gameInit(root,playerPawn,timer);
            playerRegistered = true;
            PlayerStatusLayout.startGame.setDisable(true);
            PlayerStatusLayout.playerName.setDisable(true);
            registerPlayerScore.getTabellaDB().setDisable(true); 
            InvioLogServer.inviaLog("InizioPartita");          
        });}
        else {
        PlayerStatusLayout.startGame.setOnAction((ActionEvent ev1)-> {
            PlayerStatusLayout.startGame.setDisable(true);
            gameInitializer.gameInit(root,playerPawn,timer);
            if (gameWon == false){System.out.println(gameWon); LocalCache.prelevaPunteggioBin(root);}
            gameWon = false;
        });}
    }
    
    public void handleApplicationClosure(Stage stage) { //3
        stage.setOnCloseRequest((WindowEvent we) -> {InvioLogServer.inviaLog("FinePartita");                                                     
                                                     LocalCache.conservaPunteggioBin();LocalCache.conservaPunteggioTxt();
                                                     LocalCache.inviaPunteggioBin();LocalCache.inviaPlayerRegistered();
                                                     });
    }
    
    public void checkGameKeyPressed(Group root,Scene scene,PlayerPawn playerPawn,GameInitializer gameInitializer) { //4
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.LEFT) 
            {
                playerPawn.movePawn(e.getCode(),gameInitializer);
                if (e.getCode() == KeyCode.RIGHT) InvioLogServer.inviaLog("PressioneTastoDestro");                                 
                if (e.getCode() == KeyCode.LEFT) InvioLogServer.inviaLog("PressioneTastoSinistro");                           
            }            
            if (e.getCode() == KeyCode.SPACE)
            {                
                Shot shot = new Shot(root,playerPawn.x + 70,playerPawn.y - 25,Color.GREEN,new ImageView("file:../../myfiles/greenShot.png"),root_id_counter++);
                greenShots.add(shot);                
                InvioLogServer.inviaLog("PressioneBarraSpaziatrice");      
            }                        
        });       
    }      

    private void gameOver(Group root,Stage stage,GameTimerSetter timer,RegisterPlayerScore registerPlayerScore) { //5
        InvioLogServer.inviaLog("GameOver");
        timer.gameWin.cancel();
        timer.redBombs.cancel();
        timer.gameCostantUpdate.cancel();
        registerPlayerScore.registraGiocatoreDB();
        score = 0;
        LocalCache.conservaPunteggioTxt();
        howManyAliens = 24;
        LocalCache.inviaPunteggioBin();
        root.getChildren().clear();
        aliens.clear();
        greenShots.clear();
        GameStatusUpdate.lives = 3;
        redShots.clear();
        playerRegistered = false;
        LocalCache.inviaPlayerRegistered();
        start(stage);
    }
 
    public void redShot(Group root, Stage stage,CollisionHandler collisionHandler,PlayerPawn playerPawn,GameTimerSetter timer,RegisterPlayerScore registerPlayerScore) {  //6      
        Random generator = new Random();
        int RemainingAliens = howManyAliens - 1;     //aliens.size() - 1;
        int bomb = generator.nextInt(RemainingAliens);       
        while (bomb + 6 <= RemainingAliens) bomb += 6;
        int bombX = aliens.get(bomb).x + 47;
        int bombY = aliens.get(bomb).y + 80;
        
        Shot colpo = new Shot(root,bombX,bombY,Color.RED,new ImageView("file:../../myfiles/redShot.png"),root_id_counter++);
        redShots.add(colpo);
        
        if (redShots.isEmpty() == false) {              
            Iterator<Shot> ShotIterator = redShots.iterator();           
            while (ShotIterator.hasNext()) {                
                Shot shot = ShotIterator.next();                
                if (shot.color == Color.RED) {
                    shot.y += 20;
                    shot.shot.setY(shot.y);               
                    if (shot.x >= (playerPawn.x) && shot.x <= (playerPawn.x + 150)
                    && shot.y >= (playerPawn.y) && shot.y <= (playerPawn.y + 100)) 
                    {
                        collisionHandler.redShotHandler(shot, root, ShotIterator);
                        if (GameStatusUpdate.lives != 0) break;                                            
                        gameOver(root,stage,timer, registerPlayerScore);
                        break;
                    }
                }
            }
        }
    }
    
    public void setRedBombs(Group r,Stage s,CollisionHandler c,PlayerPawn p,GameTimerSetter t,RegisterPlayerScore reg) {
        t.redBombs = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    redShot(r,s,c,p,t,reg);
                });
            }
        };
    }   
    
    public void gameWinCheck(Group r,Stage s,GameTimerSetter t) {
        t.gameWin = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    isGameWon(r,s,t);
                });
            }
        };
    }
    
    public void isGameWon(Group root,Stage stage,GameTimerSetter timer) { //7
        //gameOver(root,stage);
        if (howManyAliens > 10) return;
        timer.gameWin.cancel();
        InvioLogServer.inviaLog("PartitaVinta");
        timer.redBombs.cancel();
        timer.gameCostantUpdate.cancel();        
        LocalCache.conservaPunteggioTxt();
        howManyAliens = 24;
        LocalCache.inviaPunteggioBin();
        root.getChildren().clear();
        aliens.clear();
        greenShots.clear();
        redShots.clear();
        playerRegistered = true;
        gameWon = true;
        GameStatusUpdate.lives = 3;
        LocalCache.inviaPlayerRegistered();
        start(stage);
    }
    
}

//1: il metodo Start usa le classi per inizializzare la schermata di gioco, oltre che
//   controllare con la cache se si è iniziata una partita nuova o meno
//2: setta alcuni dei timer di gioco e setta l'evento per la chiusure dell'applicazione
//3: evento per la chiusura dell'applicazione
//4: metodo che gestisce i tasti premuti dal giocatore
//5: metodo per resettare il gioco in caso di GameOver
//6: crea un nuovo Shot sparato dagli alieni
//7: controlla se la partita è stata vinta