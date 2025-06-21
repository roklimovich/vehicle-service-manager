package pl.pja.edu.s27619.gui_controllers.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import pl.pja.edu.s27619.Main;
import pl.pja.edu.s27619.administration.Admin;
import pl.pja.edu.s27619.clients.BasicClient;
import pl.pja.edu.s27619.clients.Client;
import pl.pja.edu.s27619.gui_controllers.dbconfig.DatabaseConfigSession;
import pl.pja.edu.s27619.gui_controllers.interfaces.DataReceiver;
import pl.pja.edu.s27619.gui_controllers.interfaces.TableGenerator;
import pl.pja.edu.s27619.vehicle.Vehicle;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static javafx.collections.FXCollections.observableArrayList;

public class ClientManageController implements TableGenerator, DataReceiver {
    private Admin admin;
    @FXML
    private Label wrongLabel;
    @FXML
    private Button backButtonFromEdit;
    @FXML
    private ListView<Vehicle> listClientVehicles;
    @FXML
    private TextField clientLoyaltyPoints;
    @FXML
    private TextField clientDiscount;
    @FXML
    private Button saveButton;
    private ObservableList<Client> mainClientList;
    @FXML
    private Button backButton;
    @FXML
    private TextField clientNameField;
    @FXML
    private TextField clientSurnameField;
    @FXML
    private TextField clientPhoneNumber;
    @FXML
    private Button createButton;
    @FXML
    private TableView<Client> clientList;
    @FXML
    private TableColumn<Client, String> clientIdColumn;
    @FXML
    private TableColumn<Client, String> clientNameColumn;
    @FXML
    private TableColumn<Client, String> clientSurnameColumn;
    @FXML
    private TableColumn<Client, String> clientPhoneNumberColumn;
    @FXML
    private TableColumn<Client, String> clientEmailColumn;
    @FXML
    private TableColumn<Client, String> clientTypeColumn;
    @FXML
    private TableColumn<Client, Double> clientDiscountColumn;
    @FXML
    private TableColumn<Client, Integer> clientLoyaltyPointsColumn;
    @FXML
    private TextField findButton;
    @FXML
    private TextField clientEmail;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;

    @FXML
    public void initialize() {
        generateTableView();

        mainClientList = clientsFromDB();
        clientList.setItems(mainClientList);
    }

    /**
     * Method handle left mouse button when it was clicked and change the state based on clicked button.
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
                    case "createButton":
                        createClient();
                        break;
                    case "deleteButton":
                        deleteClient();
                        break;
                    case "editButton":
                        editClient();
                        break;
                    case "backButton":
                        Main.changeScene("/manage_page/manage.fxml", admin);
                        break;
                }
            }
        }
    }

    /**
     * Method which is needed to create the client in the system. Automatically add to the database if everything is
     * set properly.
     */
    private void createClient() {
        String name = clientNameField.getText();
        String surname = clientSurnameField.getText();
        String phoneNumber = clientPhoneNumber.getText();
        String email = clientEmail.getText();

        Session session = null;

        try {
            session = DatabaseConfigSession.getSessionFactory().openSession();

            session.beginTransaction();

            Client client = new BasicClient(name, surname, phoneNumber, email);

            session.persist(client);


            session.getTransaction().commit();

            mainClientList.add(client);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            wrongLabel.setText("Wrong data, try once again");
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        clientNameField.setText("");
        clientSurnameField.setText("");
        clientPhoneNumber.setText("");
        clientEmail.setText("");

        clientList.setItems(mainClientList);
    }

    /**
     * Method which is needed to delete the client in the system. Automatically delete in the database if chosen
     * mechanic exists.
     */
    private void deleteClient() {
        Client client = clientList.getSelectionModel().getSelectedItem();

        if (client != null) {
            Session session = DatabaseConfigSession.getSessionFactory().openSession();

            try {
                session.beginTransaction();

                session.remove(client);

                session.getTransaction().commit();

            } catch (Exception e) {
                System.out.println(e.getMessage());
                if (session != null && session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
            } finally {
                if (session != null) {
                    session.close();
                }
            }

            mainClientList.remove(client);
            clientList.setItems(mainClientList);

        } else {
            wrongLabel.setText("No selected client to delete");
        }

    }

    /**
     * Method handle enter pressed on the keyboard for find button. It iterates all client from the list and find
     * only client which is should be founded by client email.
     *
     * @param keyEvent contains which key is pressed
     */
    public void enterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String clientParamToFind = findButton.getText().toLowerCase();
            System.out.println(clientParamToFind);

            if (clientParamToFind.isEmpty() || clientParamToFind.isBlank()) {
                clientList.setItems(mainClientList);
            } else {
                ObservableList<Client> sortedClients = clientList.getItems()
                        .stream()
                        .filter(client -> client.getEmail().toLowerCase().contains(clientParamToFind))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));

                clientList.setItems(sortedClients);
            }

        }
    }

    /**
     * Method generates table for the scene to provide for user information about client to the user.
     */
    @Override
    public void generateTableView() {
        for (TableColumn<?, ?> column : clientList.getColumns()) {
            column.setResizable(false);
        }

        clientIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        clientNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        clientSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        clientPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        clientEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        clientTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        clientDiscountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
        clientLoyaltyPointsColumn.setCellValueFactory(new PropertyValueFactory<>("loyaltyPoints"));
    }

    /**
     * Method download new scene to edit the chosen client.
     *
     * @throws Exception if system could not load new scene
     */
    private void editClient() throws Exception {
        Client client = clientList.getSelectionModel().getSelectedItem();

        if (client != null) {
            Main.changeScene("/manage_page/client_page/edit_page/edit_client.fxml", client, admin);
        } else {
            wrongLabel.setText("No selected client to edit");
        }
    }


    private void updateData() {
        clientNameField.setText("");
        clientSurnameField.setText("");
        clientPhoneNumber.setText("");
        clientEmail.setText("");
    }

    /**
     * Method get all clients which is registered into the database and set them into list.
     *
     * @return list of clients
     */
    private ObservableList<Client> clientsFromDB() {
        ObservableList<Client> clientList = observableArrayList();

        try {
            Session session = DatabaseConfigSession.getSessionFactory().openSession();

            session.beginTransaction();

            List<Client> clientListFromDB = session.createQuery("FROM Client", Client.class).list();

            clientList.addAll(clientListFromDB);

            session.getTransaction().commit();

            session.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return clientList;
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
            if (obj instanceof Admin) {
                admin = (Admin) obj;
            }
        }
    }
}
