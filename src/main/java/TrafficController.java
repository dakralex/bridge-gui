/**
 * @author Daniel Kral
 * @id 11908284
 */

/**
 * TrafficController is the interface that defines abstract methods for controlling access to the bridge. Its
 * implementations of this interface act as monitors to ensure that only one vehicle can pass the bridge at a time and
 * all other vehicles have to wait until the bridge is free again.
 */
interface TrafficController {

    /**
     * Makes the vehicle v from the left-hand side of the bridge enter.
     * <p>
     * This method will return iff the bridge is empty and may block otherwise.
     *
     * @param v vehicle to enter from the left-hand side
     */
    void enterLeft(Vehicle v);

    /**
     * Makes the vehicle v from the right-hand side of the bridge enter.
     * <p>
     * This method will return iff the bridge is empty and may block otherwise.
     *
     * @param v vehicle to enter from the right-hand side
     */
    void enterRight(Vehicle v);

    /**
     * Makes the vehicle v from the left-hand side of the bridge leave.
     *
     * @param v vehicle to leave from the left-hand side
     */
    void leaveLeft(Vehicle v);

    /**
     * Makes the vehicle v from the right-hand side of the bridge leave.
     *
     * @param v vehicle to leave from the right-hand side
     */
    void leaveRight(Vehicle v);

}
