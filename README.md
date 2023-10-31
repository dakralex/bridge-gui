BridgeGUI
---

BridgeGUI is an application developed for the university course "Progrmaming Languages and Concepts" to test for basic 
understanding of Java's threading features and how to apply those correctly.

## Specification

### Interface `Vehicle`

`Vehicle` is the interface that defines abstract methods for getting identifiable information of a vehicle.

The method `int getId()` returns the vehicle's identifier.

### Interface `TrafficRegistrar`

`TrafficRegistrar` is the interfaces that defines abstract methods for capturing the traffic flow.

The method `void registerLeft(Vehicle v)` registers the vehicle v from the left-hand side of the bridge.

The method `void registerRight(Vehicle v)` registers the vehicle v from the right-hand side of the bridge.

The method `void deregisterLeft(Vehicle v)` unregisters the vehicle v from the left-hand side of the bridge.

The method `void deregisterRight(Vehicle v)` unregisters the vehicle v from the right-hand side of the bridge.

### Interface `TrafficController`

`TrafficController` is the interface that defines abstract methods for controlling access to the bridge.

The implementations of this interface act as monitors to ensure that only one vehicle can pass the bridge at a time and 
all other vehicles have to wait until the bridge is free again.

The method `void enterLeft(Vehicle v)` makes the vehicle v from the left-hand side of the bridge enter.

The method `void enterRight(Vehicle v)` makes the vehicle v from the right-hand side of the bridge enter.

The method `void leaveLeft(Vehicle v)` makes the vehicle v from the left-hand side of the bridge leave.

The method `void leaveRight(Vehicle v)` makes the vehicle v from the right-hand side of the bridge leave.

### Class `TrafficControllerSimple`

`TrafficControllerSimple` is an implementation of `TrafficController`. It uses `synchronized`, `wait()`, `notify()`,
and `notifyAll()` on the vehicle objects to control the vehicle-bridge access flow.

The constructor `TrafficControllerSimple(TrafficRegistrar registrar)` has to be implemented in such a way, that it
allows injections of implementations of the interface `TrafficRegistrar`.

When implementing the methods of `TrafficController`, it has to be ensured that the vehicles are correctly registered
and unregistered by calling the corresponding methods in `registrar` when entering and exiting the bridge.

### Class `TrafficControllerFair`

`TrafficControllerFair` is an implementation of `TrafficController`. It uses explicit locks and condition variables on 
the vehicle objects to control the vehicle-bridge access flow. To support fairness, the class 
`java.util.concurrent.locks.ReentrantLock` should be used, so that the selection of the car that can pass the bridge 
next from the set of waiting cars exhibits some degree of fairness.

The constructor `TrafficControllerFair(TrafficRegistrar registrar)` has to be implemented in such a way, that it
allows injections of implementations of the interface `TrafficRegistrar`.

When implementing the methods of `TrafficController`, it has to be ensured that the vehicles are correctly registered 
and unregistered by calling the corresponding methods in `registrar` when entering and exiting the bridge.




