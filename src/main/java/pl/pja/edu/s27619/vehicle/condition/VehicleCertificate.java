package pl.pja.edu.s27619.vehicle.condition;

import jakarta.persistence.*;
import pl.pja.edu.s27619.exceptions.CheckDataException;
import pl.pja.edu.s27619.service.VehicleManager;
import pl.pja.edu.s27619.vehicle.Vehicle;

import java.time.LocalDate;

@Entity
@Table(name = "Vehicle_Certificate")
public class VehicleCertificate {
    @Id
    private String certificateId;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issuedDate;

    @Column(name = "valid_until", nullable = false)
    private LocalDate validUntil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    public VehicleCertificate() {}

    public VehicleCertificate(LocalDate issuedDate, LocalDate validUntil, Vehicle vehicle) {
        certificateId = generatesUniqueId();
        setIssuedDate(issuedDate);
        setValidUntil(validUntil);
        setVehicle(vehicle);
        VehicleManager.registerCertificate(this);
    }

    /**
     * Method generates the unique id to the certificate using pattern: "CERT-x", where x - number which generated
     * randomly in range [1, 99999].
     *
     * @return String which contains unique ID for certificate
     */
    public String generatesUniqueId() {
        return "CERT-" + (int) (Math.random() * 99999 + 1);
    }

    /**
     * Method sets the vehicle automatically to the given certificate
     *
     * @param vehicle variable which contains information about the vehicle
     */
    public void setVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new CheckDataException("Vehicle cannot be null for assigning to Certificate");
        }

        if (this.vehicle != vehicle) {
            this.vehicle = vehicle;
            vehicle.addCertificate(this);
        }
    }

    /**
     * Method removes certificate from the vehicle.
     */
    public void removeVehicle() {
        if (this.vehicle != null) {
            Vehicle temp = this.vehicle;
            this.vehicle = null;
            temp.removeCertificate(this);
        }
    }

    /**
     * Method sets the issued date of the certificate.
     *
     * @param issuedDate LocalDate variable which contains information about when certificate was given
     */
    public void setIssuedDate(LocalDate issuedDate) {
        if (issuedDate == null) {
            throw new CheckDataException("Issued date could not be null");
        }

        this.issuedDate = issuedDate;
    }

    /**
     * Method sets the valid date of the certificate.
     *
     * @param validUntil LocalDate variable which contains information about when certificate will invalid
     */
    public void setValidUntil(LocalDate validUntil) {
        if (validUntil == null) {
            throw new CheckDataException("Valid date could not be null");
        }

        this.validUntil = validUntil;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }
}
