package pl.pja.edu.s27619.administration;

import jakarta.persistence.*;
import pl.pja.edu.s27619.exceptions.CheckDataException;

@Entity
@Table(name = "AppUser")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "email", nullable = false)
    private String email;

    public User() {
    } // for hibernate purpose

    public User(String login, String password, String name, String surname, String email) {
        setLogin(login);
        setPassword(password);
        setName(name);
        setSurname(surname);
        setEmail(email);
    }

    /**
     * Method checks if login is null or login is empty and throws exception, otherwise set login.
     *
     * @param login contains information about the name
     */
    public void setLogin(String login) {
        if (login == null || login.isBlank()) {
            throw new CheckDataException("Login could not be null or empty");
        }

        this.login = login;
    }


    /**
     * Method checks if password is null or password is empty and throws exception, otherwise set password.
     *
     * @param password contains information about the name
     */
    public void setPassword(String password) {
        if (password == null || password.isBlank()) {
            throw new CheckDataException("Password could not be null or empty");
        }

        this.password = password;
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

    public String getFullName() {
        return name + " " + surname;
    }

    public Long getId() {
        return id;
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

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}

