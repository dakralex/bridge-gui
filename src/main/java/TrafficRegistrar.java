/**
 * TrafficRegistrar is the interface that defines abstract methods for capturing the traffic flow.
 */
public interface TrafficRegistrar {

    /**
     * Registers the vehicle from the left-hand side.
     *
     * @param v vehicle entering at the left-hand side
     */
    void registerLeft(Vehicle v);

    /**
     * Registers the vehicle from the right-hand side.
     *
     * @param v vehicle entering at the right-hand side
     */
    void registerRight(Vehicle v);

    /**
     * Unregisters the vehicle from the left-hand side.
     *
     * @param v vehicle leaving at the left-hand side
     */
    void deregisterLeft(Vehicle v);

    /**
     * Unregisters the vehicle from the left-hand side.
     *
     * @param v vehicle leaving at the right-hand side
     */
    void deregisterRight(Vehicle v);
}
