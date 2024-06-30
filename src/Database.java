import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Database {
    private final String DATABASE_SAVE_PATH = "C:\\Users\\Taras Sliusar\\IdeaProjects\\Contacts-Saver\\txt\\Saver.txt";

    private final ArrayList<String> CONTACTS = new ArrayList<>();

    private Scanner input = new Scanner(System.in);

    public Database() {
        getContacts();
    }

    public void addContact() {
        System.out.println("Adding contact:");

        System.out.println("    1. Write name and last name:");
        String name = input.nextLine();

        System.out.println("    2. Write phone number:");
        String phoneNum = input.nextLine();

        String allInfo = name + " - " + phoneNum;

        if (!CONTACTS.contains(allInfo)){
            CONTACTS.add(allInfo);
            System.out.println("Contact: " + allInfo + " - added");
        }
        else
            System.out.println("Contact: " + name + " - already exists");
    }

    public void showAllContacts() {
        if (CONTACTS.isEmpty()){
            System.out.println("No contacts...");
            return;
        }

        System.out.println("All contacts:");
        for (String contact : CONTACTS)
            System.out.println("   " + contact);
    }

    public void find() {
        System.out.println("Search:");
        String search = input.nextLine();

        String result = "";

        for (String contact : CONTACTS)
            if (contact.contains(search))
                result += "\n" + contact;

        if (!result.isEmpty())
            System.out.println("   Found:\n" + result);
        else
            System.out.println("   Not found");
    }

    public void removeContacts() {
        System.out.println("Write name or phone number:");
        String info = input.nextLine();

        for (String contact : CONTACTS){
            if (contact.contains(info)){
                CONTACTS.remove(contact);
                System.out.println("Contact: " + info + " - removed");
                return;
            }
        }

        System.out.println("Contact: " + info + " - does not exist");
    }

    public void removeAllContacts() {
        System.out.println("Are you sure you want to delete all the contacts?");

        if (input.nextLine().equals("Yes")){
            CONTACTS.clear();
            System.out.println("All contacts was be cleaned");
        }
        else
            System.out.println("Removing was be canceled");
    }

    public void save() {
        try (FileWriter writer = new FileWriter(DATABASE_SAVE_PATH, false)) {
            for (String contact : CONTACTS)
                writer.write(contact + "\n");

            System.out.println("Contacts have been saved");
        }
        catch (IOException e) {
            System.out.println("Something went wrong");
        }
    }

    public void getContacts() {
        File logoFile = new File(DATABASE_SAVE_PATH);

        if (!logoFile.exists())
            return;

        try {
            Scanner logoReader = new Scanner(logoFile);

            while (logoReader.hasNextLine())
                CONTACTS.add(logoReader.nextLine());

            logoReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Something went wrong");
        }
    }
}
