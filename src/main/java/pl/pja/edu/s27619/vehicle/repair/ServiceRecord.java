package pl.pja.edu.s27619.vehicle.repair;

import pl.pja.edu.s27619.clients.Client;
import pl.pja.edu.s27619.clients.VIPClient;
import pl.pja.edu.s27619.exceptions.CheckDataException;
import pl.pja.edu.s27619.exceptions.ClientNotFoundException;
import pl.pja.edu.s27619.service.ClientManager;
import pl.pja.edu.s27619.vehicle.Vehicle;

import java.io.Serializable;
import java.time.LocalDate;

public class ServiceRecord implements Serializable {
    private Client client;
    private LocalDate serviceDate;
    private String description;
    private double cost;
    private double costWithDiscount;
    private Vehicle vehicle;

    /**
     * Constructor to create a ServiceRecord and associate it directly with a Vehicle. This constructor shows to us
     * that it make composition relationship and including that a service record cannot exist without vehicle.
     *
     * @param client      contains information for which client service record will be added
     * @param serviceDate the date when the service occurred
     * @param description description of the service
     * @param cost        the cost of the service
     * @param vehicle     vehicle to which this service record connected
     */
    public ServiceRecord(Client client, LocalDate serviceDate, String description, double cost, Vehicle vehicle) {
        setClient(client);
        setServiceDate(serviceDate);
        setDescription(description);
        setCost(cost);
        if (vehicle == null) {
            throw new CheckDataException("Vehicle cannot be null for composition");
        }
        setCostWithDiscount(cost);
        this.vehicle = vehicle;
        vehicle.addServiceRecord(this);
    }

    /**
     * Methods set service date to the service record and checks if service date is null or not. If yes throw exception,
     * otherwise set date to service record.
     *
     * @param serviceDate date of the completion of service
     */
    public void setServiceDate(LocalDate serviceDate) {
        if (serviceDate == null) {
            throw new CheckDataException("Service date could not be null");
        }

        this.serviceDate = serviceDate;
    }

    /**
     * Methods set description to the service record and check if description is null or empty throw exception,
     * otherwise set description to service record.
     *
     * @param description contains information about what was done on service for car
     */
    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new CheckDataException("Service description should contains information");
        }

        this.description = description;
    }

    /**
     * Methods set cost to the service record and check if it negative throw exception, otherwise set cost ot service
     * record.
     *
     * @param cost contains information about amount of money which was spent for service
     */
    public void setCost(double cost) {
        if (cost < 0) {
            throw new CheckDataException("Service repair cost could not be negative");
        }

        this.cost = cost;
    }

    /**
     * Links this service record to a given vehicle without duplication checks. This method is called internally when
     * creating the bidirectional association from the vehicle side.
     *
     * @param vehicle vehicle to link the service record
     */
    public void linkVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new CheckDataException("Vehicle can not be null");
        }

        if (this.vehicle != vehicle) {
            this.vehicle = vehicle;
            vehicle.linkServiceRecord(this);
        }
    }

    /**
     * Unlinks this service record from its current vehicle, if any. This method ensures the bidirectional association
     * is broken safely when the service record is being removed or deleted.
     */
    public void unlinkVehicle() {
        if (this.vehicle != null) {
            Vehicle temp = this.vehicle;
            this.vehicle = null;
            temp.removeServiceRecord(this.getServiceDate());
        }
    }


    /**
     * Method sets client to the service record, firstly checking if the client is not null, otherwise throws exception,
     * add +1 point to loyalty client points, also checks if the client, who should be set is already vip or not.
     *
     * @param client variable which contains information about client
     */
    public void setClient(Client client) {
        if (client == null) {
            throw new ClientNotFoundException("Client is null or empty");
        }

        if (ClientManager.getInfoAboutVIPClients(client.getId())) {
            VIPClient vipClient = new VIPClient(client.getName(), client.getSurname(), client.getPhoneNumber(),
                    client.getEmail());
            vipClient.setId(client.getId());
            vipClient.setClientVehicles(client.getClientVehicles());
            client.setLoyaltyPoints(client.getLoyaltyPoints() + 1);
            this.client = vipClient;
        } else {
            this.client = client;
            client.setLoyaltyPoints(client.getLoyaltyPoints() + 1);
        }
    }

    /**
     * Methods sets cost to the current service record based on added client personal discount.
     *
     * @param cost contains information about costs of service
     */
    public void setCostWithDiscount(double cost) {

        this.costWithDiscount = cost - (cost * (client.getDiscount() / 100));
    }

    public Client getClient() {
        return client;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "ServiceRecord{" +
                "client=" + client.getId() + ", clientType=" + client.getClass() +
                ", serviceDate=" + serviceDate +
                ", description='" + description + '\'' +
                ", cost=" + cost + '\'' +
                ", costWithDiscount='" + costWithDiscount + '\'' +
                ", vehicle=" + vehicle.getName() + " " + vehicle.getModel() + " " + " address in memory: " + vehicle +
                '}';
    }
}
