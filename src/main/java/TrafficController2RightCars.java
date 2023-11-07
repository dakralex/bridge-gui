/**
 * @author Daniel Kral
 * @id 11908284
 */

/**
 * TrafficController2RightCars is the implementation of TrafficController using synchronized, wait, notify and
 * notifyAll on vehicle objects to control the vehicle-bridge access flow, allowing two cars from the right-side and
 * one car from the left-side of the bridge.
 */
public class TrafficController2RightCars implements TrafficController {

    private static final int MAX_VEHICLES_FOR_RIGHT = 2;
    private static final int MAX_VEHICLES_FOR_LEFT = 1;

    private final TrafficRegistrar registrar;

    private int leftVehicleOnBridge;
    private int rightVehicleOnBridge;


    /**
     * Creates an instance of the TrafficControllerSimple.
     *
     * @param registrar the TrafficRegistrar to use
     */
    TrafficController2RightCars(TrafficRegistrar registrar) {
        this.registrar = registrar;
    }

    @Override
    public synchronized void enterLeft(Vehicle v) {
        // Wait if there is the max for the left cars on the bridge
        while (leftVehicleOnBridge >= MAX_VEHICLES_FOR_LEFT ||
                rightVehicleOnBridge >= MAX_VEHICLES_FOR_LEFT) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }

        ++leftVehicleOnBridge;
        registrar.registerLeft(v);
    }

    @Override
    public synchronized void enterRight(Vehicle v) {
        // Wait if there is the max. for the left or right cars on the bridge
        while (leftVehicleOnBridge >= MAX_VEHICLES_FOR_LEFT ||
                rightVehicleOnBridge >= MAX_VEHICLES_FOR_RIGHT) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }

        ++rightVehicleOnBridge;
        registrar.registerRight(v);
    }

    @Override
    public synchronized void leaveLeft(Vehicle v) {
        --rightVehicleOnBridge;
        registrar.deregisterLeft(v);
        notifyAll();
    }

    @Override
    public synchronized void leaveRight(Vehicle v) {
        --leftVehicleOnBridge;
        registrar.deregisterRight(v);
        notifyAll();
    }
}
