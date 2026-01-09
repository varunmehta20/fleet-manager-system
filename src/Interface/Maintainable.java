package Interface;
import Exceptions.InsufficientFuelException;
import Exceptions.InvalidOperationException;
import Exceptions.OverloadException;
public interface Maintainable {
    public void scheduleMaintenance();
    public boolean needsMaintenance();
    public void performMaintenance();
}
