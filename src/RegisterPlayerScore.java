
import java.sql.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

public class RegisterPlayerScore {   
    private TableView<PlayerCredentials> tabellaDB;
    private ObservableList<PlayerCredentials> ol;    
    private TableColumn nomeGiocatoreCol;
    private TableColumn punteggioGiocatoreCol;
        
    public void initialize() { 
        tabellaDB = new TableView<>();
        nomeGiocatoreCol = new TableColumn("GIOCATORE");
        punteggioGiocatoreCol = new TableColumn("PUNTEGGIO"); 
        nomeGiocatoreCol.setCellValueFactory(new PropertyValueFactory<>("nome")); 
        punteggioGiocatoreCol.setCellValueFactory(new PropertyValueFactory<>("punteggio"));


	try (

            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/FishFarm","root","Password123#@!");

            String sql = " insert into temperature (first_name, last_name, date_created, is_admin, num_points)"
                    + " values (?, ?, ?, ?, ?)";

	){
                PreparedStatement preparedStmt = conn.prepareStatement(sql);
                preparedStmt.setString(1, "test");

                preparedStmt.setInt(5,2);

                preparedStmt.execute();
                conn.close();}

	  catch (Exception e)
            {
                System.err.println("Got an exception!");
                // printStackTrace method
                // prints line numbers + call stack
                e.printStackTrace();
                // Prints what exception has been thrown
                System.out.println(e);
            }
        }
        
    }
    
    public void setTabellaDB() {
        tabellaDB.setItems(ol); 
        tabellaDB.getColumns().addAll(nomeGiocatoreCol, punteggioGiocatoreCol); 
        
    }
    
    public  TableView<PlayerCredentials> getTabellaDB() {
        return tabellaDB;
    }
    
    public void caricaGiocatoriDB() { //1
      ol = FXCollections.observableArrayList();
      try ( 
            Connection co = DriverManager.getConnection("jdbc:mysql://"+Configuration.databaseIp+":"+Configuration.databasePort+"/spaceinvaders",
                                                        Configuration.databaseUser,Configuration.databasePsw); 
            Statement st = co.createStatement(); 
      ) { 
            ResultSet rs = st.executeQuery("SELECT * FROM classifica ORDER BY punteggioGiocatore DESC limit " + Configuration.classificaMaxLines); 
            while (rs.next())  
              ol.add(new PlayerCredentials(rs.getString("nomeGiocatore"), rs.getInt("punteggioGiocatore")));
      } catch (SQLException e) {System.err.println(e.getMessage());}     
    }
    
    public void registraGiocatoreDB() { //2
      ol = FXCollections.observableArrayList();
      try ( 
            Connection co = DriverManager.getConnection("jdbc:mysql://"+Configuration.databaseIp+":"+Configuration.databasePort+"/spaceinvaders",
                                                        Configuration.databaseUser,Configuration.databasePsw);
            PreparedStatement ps = co.prepareStatement("INSERT INTO classifica VALUES (?, ?)"); 
      ) { 
            ps.setString(1,PlayerStatusLayout.playerName.getText()); ps.setInt(2,SpaceInvaders.score);
            System.out.println("rows affected: " + ps.executeUpdate()); 
      } catch (SQLException e) {System.err.println(e.getMessage());}     
    }
    
}

//1: Popola la classifica nella schermata di gioco con i top 10 giocatori
//2: Inserisce nel Database il nome de giocatore e relativo punteggio