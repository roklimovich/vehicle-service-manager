package pl.pja.edu.s27619.exceptions;

public class VehicleNotFoundException extends IllegalArgumentException {
  public VehicleNotFoundException(String message) {
    super(message);
  }
}
