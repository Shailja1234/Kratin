import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class App {
    public static void main (String[] args) throws IOException, SQLException, ClassNotFoundException {
        System.out.println("Welcome to Medicine Routine Notifier App");
        System.out.println("The notifier is running in the background");
        System.out.println("It will notify whenever its time to take the medicine");

        Properties prop = new Properties();

        FileInputStream fis = new FileInputStream(args[0]);
        prop.load(fis);

        DatabaseDriver dbDriver = new DatabaseDriver();
        String dbHost = prop.getProperty("db.host");
        String dbUser = prop.getProperty("db.username");
        String dbPassword = prop.getProperty("db.password");
        String dbName = prop.getProperty("db.name");
        String dbPort = prop.getProperty("db.port");

        Connection dbConn = dbDriver.getConnection(dbHost, dbName, dbUser, dbPassword, dbPort);

        AskPatientDetails obj = new AskPatientDetails(dbConn);
        NotifyPatient obj2 = new NotifyPatient(dbConn);
        obj.start();
        obj2.start();
    }
}
