package app.main;

import java.util.ArrayList;
import java.util.Scanner;

import app.database.Database;

public class Input {
    private final String HELP = "cs help";

    private final String ADD_CONTACT = "cs add";
    private final String REMOVE_CONTACT = "cs rm";
    private final String REMOVE_ALL_CONTACTS = "cs rm -all";
    private final String FIND_CONTACT = "cs fn";
    private final String SHOW_ALL_CONTATCS = "cs sw";

    private final ArrayList<String> COMMANDS = new ArrayList<>();

    private final Database DATABASE = new Database();
    private final Scanner INPUT = new Scanner(System.in);

    public Input() {
        addingComandsToList();
        input();
    }

    private void addingComandsToList() {
        COMMANDS.add("Add contact: " + ADD_CONTACT);
        COMMANDS.add("Remove contact: " + REMOVE_CONTACT);
        COMMANDS.add("Remove all contacts: " + REMOVE_ALL_CONTACTS);
        COMMANDS.add("Find contact: " + FIND_CONTACT);
        COMMANDS.add("Show all contacts: " + SHOW_ALL_CONTATCS);
        COMMANDS.add("Help: " + HELP);
    }

    private void input() {
        System.out.print(": ");
        String command = INPUT.nextLine();

        switch (command) {
            case ADD_CONTACT:
                DATABASE.addContact();
                break;
            case SHOW_ALL_CONTATCS:
                DATABASE.showAllContacts();
                break;
            case FIND_CONTACT:
                DATABASE.find();
                break;
            case REMOVE_CONTACT:
                DATABASE.removeContacts();
                break;
            case REMOVE_ALL_CONTACTS:
                DATABASE.removeAllContacts();
                break;
            case HELP:
                help();
                break;
            default:
                System.out.println("If you need a help write: " + HELP);
                break;
        }

        input();
    }

    private void help() {
        System.out.println("-HELP-\ncommands:");

        for (String command : COMMANDS)
            System.out.println("   " + command);
    }
}
