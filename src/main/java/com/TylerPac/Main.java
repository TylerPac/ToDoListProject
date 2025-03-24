package com.TylerPac;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final SessionFactory factory = new Configuration()
            .configure()
            .addAnnotatedClass(ToDoList.class)
            .buildSessionFactory();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("Welcome to the Hibernate To-Do List!");

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Add a To-Do Item");
            System.out.println("2. Delete a To-Do Item");
            System.out.println("3. View All To-Do Items");
            System.out.println("4. Update a To-Do Item");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number between 1 and 5.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addItem(scanner);
                case 2 -> deleteItem(scanner);
                case 3 -> displayItems();
                case 4 -> updateItem(scanner);
                case 5 -> System.out.println("Exiting... Thank you for using the To-Do List Application!");
                default -> System.out.println("Invalid choice! Please choose a number between 1 and 5.");
            }
        } while (choice != 5);

        factory.close();
        scanner.close();
    }

    private static void addItem(Scanner scanner) {
        System.out.print("Enter the description of the to-do item: ");
        String description = scanner.nextLine();

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        ToDoList newItem = new ToDoList();
        newItem.setTDL_NAME(description);
        newItem.setTDL_DONE(false);

        session.persist(newItem);
        transaction.commit();
        session.close();

        System.out.println("Item added successfully!");
    }

    private static void deleteItem(Scanner scanner) {
        displayItems();
        System.out.print("Enter the ID of the item to delete: ");
        int id = scanner.nextInt();

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        ToDoList item = session.get(ToDoList.class, id);
        if (item != null) {
            session.remove(item);
            transaction.commit();
            System.out.println("Item deleted successfully!");
        } else {
            System.out.println("Invalid ID. No item deleted.");
        }
        session.close();
    }

    private static void displayItems() {
        Session session = factory.openSession();
        List<ToDoList> items = session.createQuery("FROM ToDoList", ToDoList.class).list();
        session.close();

        if (items.isEmpty()) {
            System.out.println("Your to-do list is empty!");
        } else {
            System.out.println("\nTo-Do List:");
            for (ToDoList item : items) {
                String status = item.isTDL_DONE() ? "[X]" : "[ ]";  // Show [X] for done, [ ] for not done
                System.out.println("[" + item.getTDL_ID() + "] " + item.getTDL_NAME() + " " + status);
            }
        }
    }


    private static void updateItem(Scanner scanner) {
        displayItems();
        System.out.print("Enter the ID of the item to mark as done: ");
        int id = scanner.nextInt();

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        ToDoList item = session.get(ToDoList.class, id);
        if (item != null) {
            item.setTDL_DONE(true);
            session.merge(item);
            transaction.commit();
            System.out.println("Item marked as DONE!");
        } else {
            System.out.println("Invalid ID. No changes made.");
        }
        session.close();
    }
}
