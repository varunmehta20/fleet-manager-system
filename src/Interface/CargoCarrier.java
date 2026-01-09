package Interface;
import Fleet.*;
import Interface.*;
import Exceptions.*;

public interface CargoCarrier {
    public void loadCargo(double weight) throws OverloadException, InvalidOperationException;
    public void unloadCargo(double weight) throws InvalidOperationException;
    public double getCargoCapacity();
    public double getCurrentCargo();
}
