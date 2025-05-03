package pl.pja.edu.s27619.clients;

public class BasicClient extends Client {
    public BasicClient(String name, String surname, String phoneNumber, String email) {
        super(name, surname, phoneNumber, email);
    }

    /**
     * Method which return discount for Basic client and has 0% as discount.
     *
     * @return value which contains discount in percentage
     */
    @Override
    public double getDiscount() {
        return 0;
    }
}
