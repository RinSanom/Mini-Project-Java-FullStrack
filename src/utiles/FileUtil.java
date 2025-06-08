package utiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    public static void writeToFile(String part , String uuid ){
        try (FileWriter fileWriter = new FileWriter(part)){
            fileWriter.write(uuid);
        } catch (Exception e) {
            System.out.println("Error during write to file: "+e.getMessage() );
        }
    }

    public static String readFromFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine();
        } catch (IOException e) {
            return null;
        }
    }
}
