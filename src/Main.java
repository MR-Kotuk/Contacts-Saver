import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        writeLogo();
        new Input();
    }

    private static void writeLogo() {
        String logoPath = "C:\\Users\\Taras Sliusar\\IdeaProjects\\Contacts-Saver\\txt\\ContactsSaver.txt";
        File logoFile = new File(logoPath);

        if (!logoFile.exists()){
            System.out.println("Cannot find the logo with path: " + logoPath);
            return;
        }

        try {
            Scanner logoReader = new Scanner(logoFile);

            while (logoReader.hasNextLine())
                System.out.println(logoReader.nextLine());

            logoReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot find the logo with path: " + logoPath);
        }
    }
}