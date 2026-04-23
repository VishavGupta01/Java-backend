package com.Vishav;

import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

// Session -> Begin Transaction -> Do several CRUD operations -> Commit Transaction -> Close Session.

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student(1, "Vishav", 21);

        // 1. Initialize the Configuration object
        // This is the starting point that gathers all the settings needed to connect to the database.
        Configuration config = new Configuration();

        // 2. Register the Entity Class
        // Tells Hibernate to look for JPA annotations (like @Entity, @Id) inside the Student class
        // so it knows how to map this specific Java class to a database table.
        config.addAnnotatedClass(com.Vishav.Student.class);

        // 3. Load the Configuration File
        // Reads your "hibernate.cfg.xml" file from the classpath (usually src/main/resources)
        // to get the database URL, username, password, and schema instructions.
        config.configure("hibernate.cfg.xml");

        // 4. Build the SessionFactory
        // This is a heavy, thread-safe object usually created only once during application startup.
        // It acts as a master factory that produces Session objects based on the configuration above.
        SessionFactory sf = config.buildSessionFactory();

        // 5. Open a Session.
        // A Session is a lightweight, short-lived object representing a single, active
        // conversation/connection with the database. You use this to perform your CRUD operations.
        Session session = sf.openSession();

        // 6. Begin a Transaction
        // Any operation that modifies the database (Insert, Update, Delete) must happen
        // inside a transaction to ensure data integrity. If anything fails, the whole transaction
        // can be rolled back safely.
        Transaction transaction = session.beginTransaction();

        try {
            // INSERT: Saving data into Database
            session.persist(s1);

            // READ: Fetching Data from Database
            // No need of transaction for READING data

            // Eager Fetching
            Student existingStudent = session.find(Student.class, 1);

            // Lazy Fetching
            // Student existingStudent = session.byId(Student.class, 1);

            if(existingStudent != null) {
                // UPDATE: Updating objects into Database
                existingStudent.setAge(22);
                // No need to save it again.
                // Hibernate tracks all the Fetched objects for updates.
            }

            // UPDATE2: Updating non-Fetched Objects
            // session.merge(s1); // If s1 is not in DB, insert it; else update it.

            // DELETE: Removing data from Database
            Student graduatingStudent = session.find(Student.class, 1);
            if(graduatingStudent != null) {
                // session.remove(graduatingStudent);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            try {
                session.close();
                sf.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
