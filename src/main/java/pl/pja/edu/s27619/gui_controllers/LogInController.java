package pl.pja.edu.s27619.gui_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import pl.pja.edu.s27619.Main;
import pl.pja.edu.s27619.administration.Admin;
import pl.pja.edu.s27619.administration.User;
import pl.pja.edu.s27619.gui_controllers.dbconfig.DatabaseConfigSession;

//import javax.security.auth.login.Configuration;

public class LogInController {
    public LogInController() {}

    @FXML
    private Button button;

    @FXML
    private Label wrongLogin;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    /**
     * Method handle enter pressed on the keyboard.
     *
     * @param keyEvent contains which key is pressed
     * @throws Exception if system could not load scene
     */
    public void enterPressed(KeyEvent keyEvent) throws Exception {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            checkLogin();
        }
    }

    /**
     * Method handle left mouse button when it was clicked, and checks the data provided on the scene.
     *
     * @param mouseEvent contains information about which mouse button was pressed
     * @throws Exception if system could not load the scene
     */
    public void leftMouseClicked(MouseEvent mouseEvent) throws Exception {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            checkLogin();
        }
    }

    /**
     * Method checks if the user with provided details exists into the system, if not reject the authentication into
     * the system, otherwise show the HOMEPAGE of the system.
     */
    public void checkLogin() {
        try {

            Session session = DatabaseConfigSession.getSessionFactory().openSession();
            session.beginTransaction();

            String login = username.getText();
            String pass = password.getText();
            System.out.println(login +  " " + pass);

            User user = session.createQuery("FROM User WHERE login = :login AND password = :pass", User.class)
                    .setParameter("login", login)
                    .setParameter("pass", pass)
                    .uniqueResult();

            session.getTransaction().commit();
            session.close();

            if (user == null) {
                wrongLogin.setText("Wrong password or email, check credentials.");
            } else if (user instanceof Admin) {
                Main.changeScene("/home_page/admin_home.fxml", user);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method handle back button was clicked using left mouse button.
     *
     * @param mouseEvent contains information about which mouse button was pressed
     * @throws Exception if system could not load the scene
     */
    public void leftMouseClickedForBack(MouseEvent mouseEvent) throws Exception {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            Main.changeScene("/welcome_page/welcome.fxml");
        }
    }
}
