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

    private void enter(Runnable register) throws InterruptedException {
        lock.lock();

        try {
            while (vehicleOnBridge) {
                fullBridge.await();
            }

            vehicleOnBridge = true;
            register.run();
        } finally {
            lock.unlock();
        }
    }

    private void leave(Runnable unregister) throws InterruptedException {
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
        try {
            enter(() -> registrar.registerLeft(v));
        } catch (InterruptedException ignored) {
        }
    }

    @Override
    public void enterRight(Vehicle v) {
        try {
            enter(() -> registrar.registerRight(v));
        } catch (InterruptedException ignored) {
        }
    }

    @Override
    public void leaveLeft(Vehicle v) {
        try {
            leave(() -> registrar.deregisterLeft(v));
        } catch (InterruptedException ignored) {
        }
    }

    @Override
    public void leaveRight(Vehicle v) {
        try {
            leave(() -> registrar.deregisterRight(v));
        } catch (InterruptedException ignored) {
        }
    }
}
