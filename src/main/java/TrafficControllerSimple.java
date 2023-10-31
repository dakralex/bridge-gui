/**
 * @author Daniel Kral
 * @id 11908284
 */

/**
 * TrafficControllerSimple is the implementation of TrafficController using synchronized, wait, notify and notifyAll on
 * vehicle objects to control the vehicle-bridge access flow.
 */
public class TrafficControllerSimple implements TrafficController {

    private final TrafficRegistrar registrar;

    private boolean vehicleOnBridge;


    /**
     * Creates an instance of the TrafficControllerSimple.
     *
     * @param registrar the TrafficRegistrar to use
     */
    TrafficControllerSimple(TrafficRegistrar registrar) {
        this.registrar = registrar;
    }

    private synchronized void enter(Runnable register) {
        while (vehicleOnBridge) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }

        vehicleOnBridge = true;
        register.run();
        notifyAll();
    }

    private synchronized void leave(Runnable unregister) {
        while (!vehicleOnBridge) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }

        vehicleOnBridge = false;
        unregister.run();
        notifyAll();
    }

    @Override
    public void enterLeft(Vehicle v) {
        enter(() -> registrar.registerLeft(v));
    }

    @Override
    public void enterRight(Vehicle v) {
        enter(() -> registrar.registerRight(v));
    }

    @Override
    public void leaveLeft(Vehicle v) {
        leave(() -> registrar.deregisterLeft(v));
    }

    @Override
    public void leaveRight(Vehicle v) {
        leave(() -> registrar.deregisterRight(v));
    }
}
