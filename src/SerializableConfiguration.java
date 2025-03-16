import java.io.*;

public class SerializableConfiguration implements Serializable { //1
    public String databaseIp;
    public int databasePort;
    public String databaseUser;
    public String databasePsw;
    public int classificaMaxLines;
    public int totalAliens;
    public int totalLives;
}

//1: classe Serializable per il file di configurazione locale