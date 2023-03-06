package resource;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Logger {
    
    private PrintStream logger;

    public Logger(String outputFileName) {
        // Create a print stream for logger
        try {
            Files.createDirectories(Paths.get("logs"));
            logger = new PrintStream(new FileOutputStream("logs/" + outputFileName, false));

        } catch (FileNotFoundException e) {
            System.out.println("Could not create log file");

        } catch (IOException e) {
            System.out.println("Could not create /logs directory");
        }
    }


    /**
     * Takes in a piece of text and log it to output file
     * 
     * @param text message to be logged
     */
    public void write(String text) {
        logger.println(text);
    }

}
