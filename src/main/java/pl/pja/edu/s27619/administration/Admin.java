package pl.pja.edu.s27619.administration;

import jakarta.persistence.*;
import pl.pja.edu.s27619.exceptions.CheckDataException;

@Entity
@Table(name = "Administrator")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "admin_type")
public abstract class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    public Admin() {} // for hibernate purpose

    /**
     * Constructor to initialize Admin object.
     *
     * @param name    String variable which contains information about the admin name
     * @param surname String variable which contains information about the admin surname
     * @param email   String variable which contains information about the admin email
     */
    public Admin(String name, String surname, String email) {
        setName(name);
        setSurname(surname);
        setEmail(email);
    }

    /**
     * Method shows information about admin details.
     */
    public abstract void displayInfo();

    /**
     * Method checks if name is null or name is empty and throws exception, otherwise set name.
     *
     * @param name contains information about the name
     */
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new CheckDataException("Name could not be null or empty");
        }

        this.name = name;
    }

    /**
     * Method checks if the surname is null or empty and throws exception, otherwise set the surname.
     *
     * @param surname contains information about the surname
     */
    public void setSurname(String surname) {
        if (surname == null || surname.isBlank()) {
            throw new CheckDataException("Surname could not be null or empty");
        }

        this.surname = surname;
    }

    /**
     * Method check the email and if it null or empty throws exception, otherwise set the email.
     *
     * @param email variable which contains email of Admin user
     */
    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new CheckDataException("Email could not be null or empty");
        }

        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }
}
