
import java.util.*;
import java.io.*;
import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlexaIntegration {

    private static String pathBuild;
    private static String fileName = "AlexaControl.txt";
    private static File file = new File(fileName);
    private static Boolean bool = false;
    private static ArrayList<String> lineArr = new ArrayList<String>();
    private static String command;

    public static void main(String[] args) throws ParseException, FileNotFoundException, IOException {

        AlexaIntegration.pathBuild();
        while (true) {
            if (AlexaIntegration.config()) {
                AlexaIntegration.exec();
            }
        }
    }

    public static void pathBuild() {

        String[] arrOfPath = System.getProperty("user.dir").split("AlexaIntegration");
        for (String a : arrOfPath) {
            pathBuild = a;
        }
    }

    // File Watcher = control looping for editted document. 
    public static boolean config() throws ParseException {
        // Directory of file to watch
        File file = new File(pathBuild + fileName);
        System.out.println(pathBuild + fileName);

        // Get file timestamp information
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");

        String targetFile = sdf.format(file.lastModified());
        String targetFileDate = date.format(file.lastModified());
        String targetFileTime = time.format(file.lastModified());

        // Get current timestamp information
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String compareFile = sdf.format(timestamp);
        String compareFileDate = date.format(timestamp);
        String compareFileTime = time.format(timestamp);

        // condition
        while (true) {
            if (sdf.parse(compareFile).before(sdf.parse(targetFile))) {
                System.out.println("Target file:" + targetFile);
                System.out.println("Compare file:" + compareFile);

                return true;
            } else {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AlexaIntegration.class.getName()).log(Level.SEVERE, null, ex);
                }
                file = new File(pathBuild + fileName);
                targetFile = sdf.format(file.lastModified());

                System.out.println("Target file:" + targetFile);
                System.out.println("Compare file:" + compareFile);
            }
        }
    }

    // Executes command from Amazon Alexa
    public static void exec() throws FileNotFoundException, IOException {

        // Builds local dropbox directory and filename. 
        File file = new File(pathBuild + fileName);

        // To read target file. 
        BufferedReader br = new BufferedReader(new FileReader(file));

        // Collects all commands and rows of text from target file. 
        String st;
        while ((st = br.readLine()) != null) {
            lineArr.add(st);
        }

        // New commands from IFTTT appends to the bottom of file. 
        command = lineArr.get(lineArr.size() - 1);

        if (System.getProperty("os.name").contains("Mac")) {
            // Open terminal for command execution via command line. 
            System.out.println("Mac");
            String[] args = new String[]{"/bin/bash", "-c", command};
            Process mac = new ProcessBuilder(args).start();
        } else if (System.getProperty("os.name").contains("Windows")) {
            System.out.println("Windows3");
            Runtime windows = Runtime.getRuntime();
            windows.exec("cmd /c" + command);
        } else {
            System.out.println("This program does not support your operating system. Please use Mac OS or Windows.");
        }

    }

}
