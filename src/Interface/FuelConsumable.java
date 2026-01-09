package Interface;
import Exceptions.InsufficientFuelException;
import Exceptions.InvalidOperationException;
import Exceptions.OverloadException;
public interface FuelConsumable {
    public void refuel(double amount) throws InvalidOperationException;
    public double getFuelLevel();
    public double consumeFuel(double distance) throws InvalidOperationException, InsufficientFuelException;
}
