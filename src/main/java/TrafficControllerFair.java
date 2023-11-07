/**
 * @author Daniel Kral
 * @id 11908284
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TrafficControllerFair is the implementation of TrafficController using explicit locks and condition variables on
 * the vehicle objects to control the vehicle-bridge access flow. To support fairness, the class ReentrantLock is used,
 * so that the selection of the car that can pass the bridge next from the set of waiting cars exhibits some degree of
 * fairness.
 */
public class TrafficControllerFair implements TrafficController {

    private final TrafficRegistrar registrar;

    private final Lock lock = new ReentrantLock(true);
    private final Condition fullBridge = lock.newCondition();

    private boolean vehicleOnBridge;

    /**
     * Creates an instance of the TrafficControllerFair.
     *
     * @param registrar the TrafficRegistrar to use
     */
    TrafficControllerFair(TrafficRegistrar registrar) {
        this.registrar = registrar;
    }

    private void enter(Runnable register) {
        lock.lock();

        try {
            while (vehicleOnBridge) {
                fullBridge.await();
            }

            vehicleOnBridge = true;
            register.run();
        } catch (InterruptedException ignored) {
        } finally {
            lock.unlock();
        }
    }

    private void leave(Runnable unregister) {
        lock.lock();

        try {
            vehicleOnBridge = false;
            unregister.run();
            fullBridge.signalAll();
        } finally {
            lock.unlock();
        }
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
