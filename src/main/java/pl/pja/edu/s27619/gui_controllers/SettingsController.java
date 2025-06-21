package pl.pja.edu.s27619.gui_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import pl.pja.edu.s27619.Main;
import pl.pja.edu.s27619.administration.Admin;
import pl.pja.edu.s27619.administration.User;
import pl.pja.edu.s27619.gui_controllers.dbconfig.DatabaseConfigSession;
import pl.pja.edu.s27619.gui_controllers.interfaces.DataReceiver;

import java.util.Arrays;

public class SettingsController implements DataReceiver {

    private User user;
    private Admin admin;
    @FXML
    private Button backButtonFromSettings;
    @FXML
    private Button saveButton;
    @FXML
    private TextField emailField;
    @FXML
    private Label wrongLabel;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField loginField;
    @FXML
    private TextField nameField;
    @FXML
    private PasswordField passwordField;

    /**
     * Method handle left mouse button when it was clicked.
     *
     * @param mouseEvent contains information about which mouse button was pressed
     * @throws Exception if system could not load the scene
     */
    public void leftMouseClicked(MouseEvent mouseEvent) throws Exception {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            Object source = mouseEvent.getSource();

            if (source instanceof Button clickedButton) {
                String buttonId = clickedButton.getId();
                switch (buttonId) {
                    case "saveButton":
                        save();
                        break;
                    case "backButtonFromSettings":
                        Main.changeScene("/home_page/admin_home.fxml", admin);
                        break;
                }
            }
        }
    }

    /**
     * Method update the data for current user, and merge into the database.
     */
    private void save() {
        admin.setName(nameField.getText());
        admin.setSurname(surnameField.getText());
        admin.setLogin(loginField.getText());
        admin.setPassword(passwordField.getText());
        admin.setEmail(emailField.getText());

        Session session = null;

        try {
            session = DatabaseConfigSession.getSessionFactory().openSession();

            session.beginTransaction();

            session.merge(admin);

            session.getTransaction().commit();

            session.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            wrongLabel.setText("Wrong data, try once more");
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        resetFields();
    }


    /**
     * Method sets admin or user which is logged into the system.
     *
     * @param data combined variable which can contains more than 1 Object
     */
    @Override
    public void setUserDetailsToLabel(Object... data) {
        Object[] objects = Arrays.stream(data).toArray();

        for (Object obj : objects) {
            if (obj instanceof Admin) {
                admin = (Admin) obj;
            }
        }

        setFields();
    }

    /**
     * Method initialize the text fields into the system.
     */
    private void setFields() {
        nameField.setText(admin.getName());
        surnameField.setText(admin.getSurname());
        loginField.setText(admin.getLogin());
        passwordField.setText(admin.getPassword());
        emailField.setText(admin.getEmail());
    }

    /**
     * Method initialize the text for default fields into the system.
     */
    private void resetFields() {
        nameField.setText(admin.getName());
        surnameField.setText(admin.getSurname());
        loginField.setText(admin.getLogin());
        passwordField.setText(admin.getPassword());
        emailField.setText(admin.getEmail());
    }


}
