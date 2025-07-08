package utiles;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConfig {
    private static final Properties properties = new Properties();
    public static Properties getDatabaseCredential(){
        try(FileReader fileReader = new FileReader("app.properties")){
            properties.load(fileReader);
//            System.out.println(" successfully " +properties);
        }catch (Exception e){
            System.out.println("[!] Get DatabaseConfig Error :    " +e.getMessage());
        }
        return properties;
    }
    public static Connection getDataConnection(){
        try{
            Connection connection = DriverManager.getConnection(
                    DatabaseConfig.getDatabaseCredential().getProperty("db_url"),
                    DatabaseConfig.getDatabaseCredential().getProperty("db_username"),
                    DatabaseConfig.getDatabaseCredential().getProperty("db_password"));
//            System.out.println("Data Connection Success");
            return connection;
        } catch (Exception e) {
            System.err.println("[!] Get DatabaseConfig Error" +e.getMessage());
        }
        return null;
    }
}
