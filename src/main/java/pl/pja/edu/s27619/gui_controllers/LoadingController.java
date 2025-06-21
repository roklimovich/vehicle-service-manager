package pl.pja.edu.s27619.gui_controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import org.hibernate.cfg.Configuration;
import pl.pja.edu.s27619.Main;

public class LoadingController {
    public LoadingController() {}

    @FXML
    private ProgressIndicator progressIndicator;

    /**
     * Method to initialize database config and make sure that he will load into the system using Thread and thread
     * sleep to update the status of database config.
     */
    @FXML
    public void initialize() {

        System.out.println("Started initialize");
        Task<Void> configTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                while (!isConfigReadyToUse()) {
                    Thread.sleep(500);
                }
                return null;
            }
        };

        configTask.setOnSucceeded(event -> {
            try {
                Main.changeScene("/signup_page/login.fxml");
            } catch (Exception e) {
                System.out.println("ConfigTask could not change the scene of the application");
            }
        });

        Thread thread = new Thread(configTask);
        thread.setDaemon(true);
        thread.start();

    }

    /**
     * Method which check if database is ready to use.
     *
     * @return true if config is ready, no if not
     */
    public boolean isConfigReadyToUse() {
        System.out.println("Checking config...");

        try {
            System.out.println("Trying to configure Hibernate...");
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            configuration.buildSessionFactory().close();
            System.out.println("Configuration loaded successfully.");
            return true;
        } catch (Exception e) {
            System.out.println("Configuration file is not ready: " + e.getMessage());
            return false;
        }

    }
}
