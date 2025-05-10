package pl.pja.edu.s27619.service.interfaces;

import pl.pja.edu.s27619.clients.Client;

public interface Manager {
    void getListOfAllServiceRecordsByGivenClient(Client client);
}
