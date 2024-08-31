package app.database;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import app.database.objects.Adress;
import app.database.objects.Contact;
import app.database.objects.ContactName;

public class Database {
    private Scanner input = new Scanner(System.in);

    private SessionFactory getSessionFactory() {
        Configuration con = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Contact.class);
        
        ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
        SessionFactory sf = con.buildSessionFactory(reg);

        return sf;
    }

    public void addContact() {
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        System.out.println("Adding contact:");

        System.out.println("  1. Name:");
        System.out.println("    1. Write name:");
        String name = input.nextLine();
        System.out.println("    2. Write last name:");
        String lastName = input.nextLine();

        System.out.println("  2. Phone number:");
        System.out.println("    1. Write phone number:");
        String phoneNum = input.nextLine();

        System.out.println("  3. Adress:");
        System.out.println("    1. Write country:");
        String country = input.nextLine();
        System.out.println("    2. Write city:");
        String city = input.nextLine();

        Contact contact = new Contact();

        ContactName contactName = new ContactName();
        contactName.setFirstName(name);
        contactName.setLastName(lastName);

        Adress adress = new Adress();
        adress.setCountry(country);
        adress.setCity(city);

        contact.setName(contactName);
        contact.setPhoneNumber(phoneNum);
        contact.setAdress(adress);

        String hql = "FROM Contact c WHERE c.name.firstName = :firstName AND c.name.lastName = :lastName AND c.phoneNumber = :phoneNumber AND c.adress.country = :country AND c.adress.city = :city";
        Query query = session.createQuery(hql);
        query.setParameter("firstName", name);
        query.setParameter("lastName", lastName);
        query.setParameter("phoneNumber", phoneNum);
        query.setParameter("country", country);
        query.setParameter("city", city);

        Contact findContact = (Contact) query.uniqueResult();

        if (findContact == null) {
            session.save(contact);
            tx.commit();

            System.out.println("Contact - " + contact + " - added");
        }
        else
            System.out.println("Contact: " + contact + " - already exists");

        session.close();
    }

    public void showAllContacts() {
        Session session = getSessionFactory().openSession();

        String hql = "FROM Contact";
        Query query = session.createQuery(hql);
        List<Contact> contacts = (List<Contact>) query.list();

        if (contacts.isEmpty()) {
            System.out.println("No contacts...");
            return;
        }

        System.out.println("All contacts:");
        for (Contact contact : contacts)
            System.out.println("   " + contact);

        session.close();
    }

    public void find() {
        Session session = getSessionFactory().openSession();

        String hql = "FROM Contact";
        Query query = session.createQuery(hql);
        List<Contact> contacts = query.list();

        System.out.println("Search:");
        String search = input.nextLine();

        String result = "";

        for (Contact contact : contacts)
            if (contact.toString().contains(search))
                result += "\n" + contact;

        if (!result.isEmpty())
            System.out.println("   Found:\n" + result);
        else
            System.out.println("   Not found");
    }

    public void removeContacts() {
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        System.out.println("Write id of contact:");
        int id = Integer.parseInt(input.nextLine());

        Contact contact = session.get(Contact.class, id);

        if (contact != null) {
            System.out.println("Contact: " + contact + " - was be deleted");
            session.delete(contact);
        }
        else
            System.out.println("Contact with ID " + id + " not found!");

        tx.commit();
        session.close();
    }

    public void removeAllContacts() {
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        System.out.println("Are you sure you want to delete all the contacts?");

        if (input.nextLine().equals("Yes")){
            String hql = "DELETE FROM Contact";
            Query query = session.createQuery(hql);
            int result = query.executeUpdate();

            tx.commit();

            System.out.println(result + " contacts were deleted.");
        }
        else
            System.out.println("Removing was be canceled");

        session.close();
    }
}
