package pl.pja.edu.s27619;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import pl.pja.edu.s27619.clients.BasicClient;
import pl.pja.edu.s27619.clients.Client;
import pl.pja.edu.s27619.clients.VIPClient;
import pl.pja.edu.s27619.vehicle.Vehicle;
import pl.pja.edu.s27619.vehicle.VehicleType;
import pl.pja.edu.s27619.vehicle.component.EmissionLevel;
import pl.pja.edu.s27619.vehicle.component.Engine;
import pl.pja.edu.s27619.vehicle.component.EngineCategory;
import pl.pja.edu.s27619.vehicle.component.EngineType;
import pl.pja.edu.s27619.vehicle.condition.VehicleCertificate;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StandardServiceRegistry registry = null;
        SessionFactory sessionFactory = null;

        try {
            registry = new StandardServiceRegistryBuilder()
                    .configure() // configures settings from hibernate.cfg.xml
                    .build();
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

            Session session = sessionFactory.openSession();
            session.beginTransaction();

            Engine engineBmw = new Engine(EngineType.DIESEL, EmissionLevel.EURO_6,
                    EngineCategory.SPORT, 567);
            Engine engineNissan = new Engine(EngineType.PETROL, EmissionLevel.EURO_6,
                    EngineCategory.SPORT, 666);

            Client basicClient = new BasicClient("Ksenia", "Klimovich", "+3750000",
                    "ks.klimovich@gmail.com");
            Client vipClient = new VIPClient("Roman", "Klimovich", "+48000",
                    "ro.klimovich@gmail.com");


            Vehicle bmw = new Vehicle(VehicleType.CAR, "BMW", "M550i", engineBmw);
            Vehicle nissan = new Vehicle(VehicleType.CAR, "Nissan", "GT-R", engineNissan);
            bmw.setOwner(vipClient);
            nissan.setOwner(vipClient);

            vipClient.getClientVehicles().add(bmw);
            vipClient.getClientVehicles().add(nissan);

            session.persist(basicClient);
            session.persist(vipClient);

            session.getTransaction().commit();
            session.close();


            // RM - inheritance
            System.out.println("\nClients from db:");
            session = sessionFactory.openSession();
            session.beginTransaction();

            List<Client> clients = session.createQuery("from Client").list();
            for (var client : clients) {
                System.out.println(client);
            }

            System.out.println("\n");
            session.getTransaction().commit();
            session.close();

            // RM - classes
            System.out.println("Engines from db:");
            session = sessionFactory.openSession();
            session.beginTransaction();

            List<Engine> vehicleEngines = session.createQuery("from Engine").list();
            for (var engine : vehicleEngines) {
                System.out.println(engine);
            }

            session.getTransaction().commit();
            session.close();

            // RM - connections
            System.out.println("Vehicle Certificates from db:");
            session = sessionFactory.openSession();
            session.beginTransaction();

            VehicleCertificate bmwCertificate = new VehicleCertificate(LocalDate.now(),
                    LocalDate.of(2027, 6, 22), bmw);
            VehicleCertificate nissanCertificate = new VehicleCertificate(LocalDate.now(),
                    LocalDate.of(2026, 8, 21), nissan);

            bmw.getVehicleCertificates().add(bmwCertificate);
            nissan.getVehicleCertificates().add(nissanCertificate);

            session.merge(bmw);
            session.merge(nissan);

            session.getTransaction().commit();
            session.close();

            session = sessionFactory.openSession();
            session.beginTransaction();

            List<Vehicle> vehicleList = session.createQuery("from Vehicle").list();
            for (var vehicle : vehicleList) {
                System.out.println("Vehicle: " + vehicle.getFullName());
                for (var certificate : vehicle.getVehicleCertificates()) {
                    System.out.println("Certificate " + certificate.getCertificateId());
                }
            }

            session.getTransaction().commit();
            session.close();


        } catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        } finally {
            if (sessionFactory != null) {
                sessionFactory.close();
                sessionFactory = null;
            }
        }
    }
}
