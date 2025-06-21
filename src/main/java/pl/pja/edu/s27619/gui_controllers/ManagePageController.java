package pl.pja.edu.s27619.gui_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import pl.pja.edu.s27619.Main;
import pl.pja.edu.s27619.administration.Admin;
import pl.pja.edu.s27619.gui_controllers.interfaces.DataReceiver;

import java.util.Arrays;

public class ManagePageController implements DataReceiver {

    private Admin admin;

    @FXML
    private Button backButtonFromManage;
    @FXML
    private Button clientsManageButton;
    @FXML
    private Button vehiclesManageButton;
    @FXML
    private Button servicesManageButton;
    @FXML
    private Button certificatesManageButton;
    @FXML
    private Button mechanicManageButton;
    @FXML
    private Button engineManageButton;

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
                    case "backButtonFromManage":
                        Main.changeScene("/home_page/admin_home.fxml", admin);
                        break;
                    case "clientsManageButton":
                        Main.changeScene("/manage_page/client_page/client.fxml", admin);
                        break;
                    case "vehiclesManageButton":
                        Main.changeScene("/manage_page/vehicle_page/vehicle.fxml", admin);
                        break;
                    case "servicesManageButton":
                        Main.changeScene("/manage_page/service_record_page/service.fxml", admin);
                        break;
                    case "mechanicManageButton":
                        Main.changeScene("/manage_page/mechanic_page/mechanic.fxml", admin);
                        break;
                    case "engineManageButton":
                        Main.changeScene("/manage_page/engine_page/engine.fxml", admin);
                        break;
                }

            }
        }
    }

    /**
     * Method sets user details which is logged into the system using interface.
     *
     * @param data combined variable which can contains more than 1 Object
     */
    @Override
    public void setUserDetailsToLabel(Object... data) {
        Object[] objects = Arrays.stream(data).toArray();

        for (Object obj : objects) {
            admin = (Admin) obj;
        }
    }
}
