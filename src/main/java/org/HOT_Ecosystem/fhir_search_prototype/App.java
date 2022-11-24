package org.HOT_Ecosystem.fhir_search_prototype;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import org.HOT_Ecosystem.fhir_search_prototype.Student;
import org.HOT_Ecosystem.fhir_search_prototype.HibernateUtil;
import org.HOT_Ecosystem.fhir_search_prototype.jpa.entity.TermConcept;
import org.HOT_Ecosystem.fhir_search_prototype.jpa.entity.TermCodeSystemVersion;

public class App {

    private static void printSessionStatus(Session session) {
        System.out.println("connected? " + session.isConnected());
        System.out.println("open? " + session.isOpen());
        System.out.println("stats: " + session.getStatistics());
    }
    public static void main(String[] args) {
        //doStudents();
        //queryConceptsByText();
        queryConceptsByTextJoin("LOINC");
        //queryConceptsByCode();
    }

/***
                           Table "public.trm_concept"
     Column      |            Type             | Collation | Nullable | Default 
-----------------+-----------------------------+-----------+----------+---------
 pid             | bigint                      |           | not null | 
 codeval         | character varying(500)      |           | not null | 
 codesystem_pid  | bigint                      |           |          | 
 display         | character varying(400)      |           |          | 
 index_status    | bigint                      |           |          | 
 parent_pids     | oid                         |           |          | 
 code_sequence   | integer                     |           |          | 
 concept_updated | timestamp without time zone |           |          | 

hapifhir=# select * from  trm_concept where lower(display) like '%blood%' and lower(display) like '%pressure%' and lower(display) like '%systolic%';

***/
    public static void queryConceptsByText() {
        String q1 = "from TermConcept where lower(display) like '%systolic%' and lower(display) like '%blood%' and lower(display) like '%pressure%'";
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // simple single-entity query
            System.out.println("----- query by text -----"); 
            List < TermConcept > concepts = session.createQuery(q1, TermConcept.class).list();
            System.out.println("list " + concepts.size());
            concepts.forEach(c -> System.out.println(c.toString()));
        }
        catch (Exception e) {
        	System.out.println("App Exception putting student objects");
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }
    }

// select tc.codeval, tc.display, tcv.cs_display, tcs.cs_name  from trm_concept tc join trm_codesystem_ver tcv on tc.codesystem_pid = tcv.pid  join trm_codesystem tcs on tcs.pid = tcv.codesystem_pid where lower(tc.display) like '%systolic%' and lower(tc.display) like '%blood%' and lower(tc.display) like '%pressure%' and lower(tc.display) not like '%diastolic%';;

    public static void queryConceptsByTextJoin(String codeSystemName) {
        String q1 = "from TermConcept where lower(display) like '%systolic%' and lower(display) like '%blood%' and lower(display) like '%pressure%'";

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // simple single-entity query
            System.out.println("----- query by text -----"); 
            List < TermConcept > concepts = session.createQuery(q1, TermConcept.class).list();
            System.out.println("list " + concepts.size());
            concepts.forEach(c -> {
                // Application Join - Woo-Hoo!
                TermCodeSystemVersion tcs = c.getCodeSystemVersion();
                // Filter on the client, *awesome*!
                if (tcs.getCodeSystemDisplayName().equals(codeSystemName)) {
                    System.out.println(c.toString() + " -- " + tcs.toString());
                }
            });

        }
        catch (Exception e) {
        	System.out.println("App Exception putting student objects");
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }
    }

    public static void queryConceptsByCode() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("---- query by code ----"); 
            List < TermConcept > concepts = session.createQuery(
                "from TermConcept where codeval = '8480-6'",
                TermConcept.class).list();

            System.out.println("list " + concepts.size());
            concepts.forEach(c -> System.out.println(c.toString()));
            //concepts.forEach(c -> System.out.println(c.getId() + " " + c.getDisplay()));
        }
        catch (Exception e) {
        	System.out.println("App Exception putting student objects");
            if (transaction != null) {
                transaction.rollback();
            }
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static void doStudents() {

        Student student = new Student("Ramesh", "Fadatare", "rameshfadatare@javaguides.com");
        Student student1 = new Student("John", "Cena", "john@javaguides.com");

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            printSessionStatus(session);

            // save the student objects
            System.out.println("put first");
            session.save(student);
            System.out.println("put second");
            session.save(student1);

            // commit transaction
            System.out.println("commit");
            transaction.commit();
        }
         catch (Exception e) {
        	System.out.println("App Exception putting student objects");
            if (transaction != null) {
                transaction.rollback();
            }
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("Querying...");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("query"); 
            List < Student > students = session.createQuery("from Student", Student.class).list();

            System.out.println("list");
            students.forEach(s -> System.out.println(s.getFirstName()));
        } catch (Exception e) {
        	System.out.println("App Exception querying student objects");
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
            //e.printStackTrace();
        }

        System.out.println("BYE");
        HibernateUtil.shutdown();
    }
}
