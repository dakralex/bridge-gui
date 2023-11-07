import java.util.logging.Logger;

/**
 * TrafficRegistrarLogger is the implementation of the TrafficRegistrar interface, that just logs who goes in and out.
 */
public class TrafficRegistrarLogger implements TrafficRegistrar {

    private static final Logger Log = Logger.getLogger(TrafficRegistrarLogger.class.getSimpleName());

    TrafficRegistrarLogger() {
    }

    public void registerLeft(Vehicle v) {
        Log.info(String.format("Car %d registered from the left side.", v.getId()));
    }

    public void registerRight(Vehicle v) {
        Log.info(String.format("Car %d registered from the right side.", v.getId()));
    }

    public void deregisterLeft(Vehicle v) {
        Log.info(String.format("Car %d unregistered from the left side.", v.getId()));
    }

    public void deregisterRight(Vehicle v) {
        Log.info(String.format("Car %d unregistered from the right side.", v.getId()));
    }

}
