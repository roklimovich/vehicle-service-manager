package pl.pja.edu.s27619.administration;

import pl.pja.edu.s27619.administration.interfaces.Accountant;
import pl.pja.edu.s27619.administration.interfaces.SystemAdmin;
import pl.pja.edu.s27619.system.SystemInformation;

import java.text.DecimalFormat;

public class Supervisor extends Admin implements SystemAdmin, Accountant {
    private SystemInformation system = new SystemInformation();

    public Supervisor(String name, String surname, String email) {
        super(name, surname, email);
    }

    /**
     * Method shows information about the admin.
     */
    @Override
    public void displayInfo() {
        System.out.println("Supervisor info:" + '\n' + "Name: " + getName() + " | " + "Surname: " + getSurname()
                + " | Email: " + getEmail() + ";");
    }

    /**
     * Method which implements interface Accountant and calculates the budget per month which takes assigned budget
     * for the year and divide it by 12 month, also save only 2 numbers after the dot, using pattern to format.
     *
     * @return double variable which contains information about budget assigned per month
     */
    @Override
    public double calculateBudgetPerMonth() {
        double budgetForMonth = system.getBUDGET_FOR_THE_YEAR() / 12;
        DecimalFormat df = new DecimalFormat("0.00");
        String formatted = df.format(budgetForMonth);
        budgetForMonth = Double.parseDouble(formatted);

        return budgetForMonth;
    }

    /**
     * Method which implements interface SystemAdmin and throws exception, if system working. By default, system works
     * and flag systemDropped is false, after that the flag systemDropped sets to true.
     */
    @Override
    public void breakTheSystem() {
        if (!system.isSystemDropped()) {
            system.setSystemDropped(true);
            throw new RuntimeException("Forced application shutdown");
        }
    }
}
