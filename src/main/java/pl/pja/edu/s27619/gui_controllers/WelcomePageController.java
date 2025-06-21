package pl.pja.edu.s27619.gui_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import pl.pja.edu.s27619.Main;

public class WelcomePageController {
    @FXML
    private Button logInButton;

    public WelcomePageController() {}

    /**
     * Method handle enter pressed on the keyboard.
     *
     * @param keyEvent contains which key is pressed
     * @throws Exception if system could not load scene
     */
    public void enterPressed(KeyEvent keyEvent) throws Exception{
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Main.changeScene("/loading_page/loading.fxml");
        }
    }

    /**
     * Method handle mouse button when it was clicked.
     *
     * @param mouseEvent contains information about which mouse button was pressed
     * @throws Exception if system could not load the scene
     */
    public void mouseClicked(MouseEvent mouseEvent) throws Exception {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            Main.changeScene("/loading_page/loading.fxml");
        }
    }
}
